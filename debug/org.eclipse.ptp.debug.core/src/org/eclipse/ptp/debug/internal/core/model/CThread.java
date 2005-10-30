/*******************************************************************************
 * Copyright (c) 2005 The Regents of the University of California. 
 * This material was produced under U.S. Government contract W-7405-ENG-36 
 * for Los Alamos National Laboratory, which is operated by the University 
 * of California for the U.S. Department of Energy. The U.S. Government has 
 * rights to use, reproduce, and distribute this software. NEITHER THE 
 * GOVERNMENT NOR THE UNIVERSITY MAKES ANY WARRANTY, EXPRESS OR IMPLIED, OR 
 * ASSUMES ANY LIABILITY FOR THE USE OF THIS SOFTWARE. If software is modified 
 * to produce derivative works, such modified software should be clearly marked, 
 * so as not to confuse it with the version available from LANL.
 * 
 * Additionally, this program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * LA-CC 04-115
 *******************************************************************************/
package org.eclipse.ptp.debug.internal.core.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDIEndSteppingRange;
import org.eclipse.cdt.debug.core.cdi.ICDISessionObject;
import org.eclipse.cdt.debug.core.cdi.ICDISignalReceived;
import org.eclipse.cdt.debug.core.cdi.event.ICDIChangedEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIDestroyedEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIDisconnectedEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.core.cdi.event.ICDIResumedEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDISuspendedEvent;
import org.eclipse.cdt.debug.core.cdi.model.ICDIBreakpoint;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.model.CDebugElementState;
import org.eclipse.cdt.debug.core.model.ICDebugElementStatus;
import org.eclipse.cdt.debug.core.model.ICStackFrame;
import org.eclipse.cdt.debug.core.model.ICThread;
import org.eclipse.cdt.debug.core.model.IDummyStackFrame;
import org.eclipse.cdt.debug.core.model.IJumpToAddress;
import org.eclipse.cdt.debug.core.model.IJumpToLine;
import org.eclipse.cdt.debug.core.model.IRestart;
import org.eclipse.cdt.debug.core.model.IResumeWithoutSignal;
import org.eclipse.cdt.debug.core.model.IRunToAddress;
import org.eclipse.cdt.debug.core.model.IRunToLine;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlockRetrieval;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.ptp.debug.core.cdi.event.IPCDIEvent;

public class CThread extends PDebugElement implements ICThread, IRestart, IResumeWithoutSignal, ICDIEventListener {
	private final static int MAX_STACK_DEPTH = 100;
	private ICDIThread fCDIThread;
	private ArrayList fStackFrames;
	private boolean fRefreshChildren = true;
	private ICDITargetConfiguration fConfig;
	private boolean fIsCurrent = false;
	private int fLastStackDepth = 0;
	private boolean fDisposed = false;

	public CThread(PDebugTarget target, ICDIThread cdiThread) {
		super(target);
		setState(cdiThread.isSuspended() ? CDebugElementState.SUSPENDED : CDebugElementState.RESUMED);
		setCDIThread(cdiThread);
		fConfig = getCDITarget().getConfiguration();
		initialize();
		getCDISession().getEventManager().addEventListener(this);
	}
	protected void initialize() {
		fStackFrames = new ArrayList();
	}
	public IStackFrame[] getStackFrames() throws DebugException {
		List list = Collections.EMPTY_LIST;
		try {
			list = computeStackFrames();
		} catch (DebugException e) {
			setStatus(ICDebugElementStatus.ERROR, e.getStatus().getMessage());
			throw e;
		}
		return (IStackFrame[]) list.toArray(new IStackFrame[list.size()]);
	}
	public boolean hasStackFrames() throws DebugException {
		// Always return true to postpone the stack frames request
		return true;
	}
	protected synchronized List computeStackFrames(boolean refreshChildren) throws DebugException {
		if (isSuspended()) {
			if (isTerminated()) {
				fStackFrames = new ArrayList();
			} else if (refreshChildren) {
				if (fStackFrames.size() > 0) {
					Object frame = fStackFrames.get(fStackFrames.size() - 1);
					if (frame instanceof IDummyStackFrame) {
						fStackFrames.remove(frame);
					}
				}
				int depth = getStackDepth();
				ICDIStackFrame[] frames = (depth != 0) ? getCDIStackFrames(0, (depth > getMaxStackDepth()) ? getMaxStackDepth() : depth) : new ICDIStackFrame[0];
				if (fStackFrames.isEmpty()) {
					if (frames.length > 0) {
						addStackFrames(frames, 0, frames.length);
					}
				} else if (depth < getLastStackDepth()) {
					disposeStackFrames(0, getLastStackDepth() - depth);
					if (frames.length > 0) {
						updateStackFrames(frames, 0, fStackFrames, fStackFrames.size());
						if (fStackFrames.size() < frames.length) {
							addStackFrames(frames, fStackFrames.size(), frames.length - fStackFrames.size());
						}
					}
				} else if (depth > getLastStackDepth()) {
					disposeStackFrames(frames.length - depth + getLastStackDepth(), depth - getLastStackDepth());
					addStackFrames(frames, 0, depth - getLastStackDepth());
					updateStackFrames(frames, depth - getLastStackDepth(), fStackFrames, frames.length - depth + getLastStackDepth());
				} else { // depth == getLastStackDepth()
					if (depth != 0) {
						// same number of frames - if top frames are in different
						// function, replace all frames
						ICDIStackFrame newTopFrame = (frames.length > 0) ? frames[0] : null;
						ICDIStackFrame oldTopFrame = (fStackFrames.size() > 0) ? ((CStackFrame) fStackFrames.get(0)).getLastCDIStackFrame() : null;
						if (!CStackFrame.equalFrame(newTopFrame, oldTopFrame)) {
							disposeStackFrames(0, fStackFrames.size());
							addStackFrames(frames, 0, frames.length);
						} else // we are in the same frame
						{
							updateStackFrames(frames, 0, fStackFrames, frames.length);
						}
					}
				}
				if (depth > getMaxStackDepth()) {
					fStackFrames.add(new CDummyStackFrame(this));
				}
				setLastStackDepth(depth);
				setRefreshChildren(false);
			}
		}
		return fStackFrames;
	}
	protected ICDIStackFrame[] getCDIStackFrames() throws DebugException {
		return new ICDIStackFrame[0];
	}
	protected ICDIStackFrame[] getCDIStackFrames(int lowFrame, int highFrame) throws DebugException {
		try {
			return getCDIThread().getStackFrames(lowFrame, highFrame);
		} catch (CDIException e) {
			setStatus(ICDebugElementStatus.WARNING, MessageFormat.format(CoreModelMessages.getString("CThread.0"), new String[] { e.getMessage() })); //$NON-NLS-1$
			targetRequestFailed(e.getMessage(), null);
		}
		return new ICDIStackFrame[0];
	}
	protected void updateStackFrames(ICDIStackFrame[] newFrames, int offset, List oldFrames, int length) throws DebugException {
		for (int i = 0; i < length; i++) {
			CStackFrame frame = (CStackFrame) oldFrames.get(offset);
			frame.setCDIStackFrame(newFrames[offset]);
			offset++;
		}
	}
	protected void addStackFrames(ICDIStackFrame[] newFrames, int startIndex, int length) {
		if (newFrames.length >= startIndex + length) {
			for (int i = 0; i < length; ++i) {
				fStackFrames.add(i, new CStackFrame(this, newFrames[startIndex + i]));
			}
		}
	}
	public List computeStackFrames() throws DebugException {
		return computeStackFrames(refreshChildren());
	}
	public List computeNewStackFrames() throws DebugException {
		return computeStackFrames(true);
	}
	protected List createAllStackFrames(int depth, ICDIStackFrame[] frames) throws DebugException {
		List list = new ArrayList(frames.length);
		for (int i = 0; i < frames.length; ++i) {
			list.add(new CStackFrame(this, frames[i]));
		}
		if (depth > frames.length) {
			list.add(new CDummyStackFrame(this));
		}
		return list;
	}
	public int getPriority() throws DebugException {
		return 0;
	}
	public IStackFrame getTopStackFrame() throws DebugException {
		List c = computeStackFrames();
		return (c.isEmpty()) ? null : (IStackFrame) c.get(0);
	}
	public String getName() throws DebugException {
		return getCDIThread().toString();
	}
	public IBreakpoint[] getBreakpoints() {
		List list = new ArrayList(1);
		if (isSuspended()) {
			IBreakpoint bkpt = null;
			// FIXME Donny
			/*
			 * if ( getCurrentStateInfo() instanceof ICDIBreakpointHit ) bkpt = ((PDebugTarget)getDebugTarget()).getBreakpointManager().getBreakpoint(
			 * ((ICDIBreakpointHit)getCurrentStateInfo()).getBreakpoint() ); else if ( getCurrentStateInfo() instanceof ICDIWatchpointTrigger ) bkpt =
			 * ((PDebugTarget)getDebugTarget()).getBreakpointManager().getBreakpoint( ((ICDIWatchpointTrigger)getCurrentStateInfo()).getWatchpoint() );
			 */if (bkpt != null)
				list.add(bkpt);
		}
		return (IBreakpoint[]) list.toArray(new IBreakpoint[list.size()]);
	}
	public void handleDebugEvents(ICDIEvent[] events) {
		if (isDisposed())
			return;
		for (int i = 0; i < events.length; i++) {
			IPCDIEvent event = (IPCDIEvent)events[i];
			if (!event.containTask(getCDITarget().getTargetID()))
				return;

			ICDIObject source = event.getSource(getCDITarget().getTargetID());
			if (source instanceof ICDIThread && source.equals(getCDIThread())) {
				if (event instanceof ICDISuspendedEvent) {
					handleSuspendedEvent((ICDISuspendedEvent) event);
				} else if (event instanceof ICDIResumedEvent) {
					handleResumedEvent((ICDIResumedEvent) event);
				} else if (event instanceof ICDIDestroyedEvent) {
					handleTerminatedEvent((ICDIDestroyedEvent) event);
				} else if (event instanceof ICDIDisconnectedEvent) {
					handleDisconnectedEvent((ICDIDisconnectedEvent) event);
				} else if (event instanceof ICDIChangedEvent) {
					handleChangedEvent((ICDIChangedEvent) event);
				}
			}
		}
	}
	public boolean canResume() {
		return (fConfig.supportsResume() && isSuspended());
	}
	public boolean canSuspend() {
		CDebugElementState state = getState();
		return (fConfig.supportsSuspend() && (state.equals(CDebugElementState.RESUMED) || state.equals(CDebugElementState.STEPPED)));
	}
	public boolean isSuspended() {
		return getState().equals(CDebugElementState.SUSPENDED);
	}
	public void resume() throws DebugException {
		if (!canResume())
			return;
		CDebugElementState oldState = getState();
		setState(CDebugElementState.RESUMING);
		try {
			getCDIThread().resume(false);
		} catch (CDIException e) {
			setState(oldState);
			targetRequestFailed(e.getMessage(), null);
		}
	}
	public void suspend() throws DebugException {
		if (!canSuspend())
			return;
		CDebugElementState oldState = getState();
		setState(CDebugElementState.SUSPENDING);
		try {
			getCDIThread().suspend();
		} catch (CDIException e) {
			setState(oldState);
			targetRequestFailed(e.getMessage(), null);
		}
	}
	public boolean canStepInto() {
		return canStep();
	}
	public boolean canStepOver() {
		return canStep();
	}
	public boolean canStepReturn() {
		if (!fConfig.supportsStepping() || !canResume()) {
			return false;
		}
		return (fStackFrames.size() > 1);
	}
	protected boolean canStep() {
		if (!fConfig.supportsStepping() || !isSuspended()) {
			return false;
		}
		return !fStackFrames.isEmpty();
	}
	public boolean isStepping() {
		return (getState().equals(CDebugElementState.STEPPING)) || (getState().equals(CDebugElementState.STEPPED));
	}
	public void stepInto() throws DebugException {
		if (!canStepInto())
			return;
		CDebugElementState oldState = getState();
		setState(CDebugElementState.STEPPING);
		try {
			if (!isInstructionsteppingEnabled()) {
				getCDIThread().stepInto(1);
			} else {
				getCDIThread().stepIntoInstruction(1);
			}
		} catch (CDIException e) {
			setState(oldState);
			targetRequestFailed(e.getMessage(), null);
		}
	}
	public void stepOver() throws DebugException {
		if (!canStepOver())
			return;
		CDebugElementState oldState = getState();
		setState(CDebugElementState.STEPPING);
		try {
			if (!isInstructionsteppingEnabled()) {
				getCDIThread().stepOver(1);
			} else {
				getCDIThread().stepOverInstruction(1);
			}
		} catch (CDIException e) {
			setState(oldState);
			targetRequestFailed(e.getMessage(), null);
		}
	}
	public void stepReturn() throws DebugException {
		if (!canStepReturn())
			return;
		IStackFrame[] frames = getStackFrames();
		if (frames.length == 0)
			return;
		CStackFrame f = (CStackFrame) frames[0];
		CDebugElementState oldState = getState();
		setState(CDebugElementState.STEPPING);
		try {
			f.doStepReturn();
		} catch (DebugException e) {
			setState(oldState);
			throw e;
		}
	}
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}
	protected void setCDIThread(ICDIThread cdiThread) {
		fCDIThread = cdiThread;
	}
	protected ICDIThread getCDIThread() {
		return fCDIThread;
	}
	protected synchronized void preserveStackFrames() {
		Iterator it = fStackFrames.iterator();
		while (it.hasNext()) {
			CStackFrame frame = (CStackFrame) (((IAdaptable) it.next()).getAdapter(CStackFrame.class));
			if (frame != null) {
				frame.preserve();
			}
		}
		setRefreshChildren(true);
	}
	protected synchronized void disposeStackFrames() {
		Iterator it = fStackFrames.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof CStackFrame) {
				((CStackFrame) obj).dispose();
			}
		}
		fStackFrames.clear();
		setLastStackDepth(0);
		resetStatus();
		setRefreshChildren(true);
	}
	protected void disposeStackFrames(int index, int length) {
		List removeList = new ArrayList(length);
		Iterator it = fStackFrames.iterator();
		int counter = 0;
		while (it.hasNext()) {
			CStackFrame frame = (CStackFrame) (((IAdaptable) it.next()).getAdapter(CStackFrame.class));
			if (frame != null && counter >= index && counter < index + length) {
				frame.dispose();
				removeList.add(frame);
			}
			++counter;
		}
		fStackFrames.removeAll(removeList);
	}
	protected void terminated() {
		setState(CDebugElementState.TERMINATED);
		dispose();
	}
	private void handleSuspendedEvent(ICDISuspendedEvent event) {
		if (!(getState().equals(CDebugElementState.RESUMED) || getState().equals(CDebugElementState.STEPPED) || getState().equals(CDebugElementState.SUSPENDING)))
			return;
		setState(CDebugElementState.SUSPENDED);
		ICDISessionObject reason = event.getReason();
		setCurrentStateInfo(reason);
		if (reason instanceof ICDIEndSteppingRange) {
			handleEndSteppingRange((ICDIEndSteppingRange) reason);
		} else if (reason instanceof ICDIBreakpoint) {
			handleBreakpointHit((ICDIBreakpoint) reason);
		} else if (reason instanceof ICDISignalReceived) {
			handleSuspendedBySignal((ICDISignalReceived) reason);
		} else {
			// fireSuspendEvent( DebugEvent.CLIENT_REQUEST );
			// Temporary fix for bug 56520
			fireSuspendEvent(DebugEvent.BREAKPOINT);
		}
	}
	private void handleResumedEvent(ICDIResumedEvent event) {
		CDebugElementState state = CDebugElementState.RESUMED;
		int detail = DebugEvent.RESUME;
		if (isCurrent() && event.getType() != ICDIResumedEvent.CONTINUE) {
			preserveStackFrames();
			switch (event.getType()) {
			case ICDIResumedEvent.STEP_INTO:
			case ICDIResumedEvent.STEP_INTO_INSTRUCTION:
				detail = DebugEvent.STEP_INTO;
				break;
			case ICDIResumedEvent.STEP_OVER:
			case ICDIResumedEvent.STEP_OVER_INSTRUCTION:
				detail = DebugEvent.STEP_OVER;
				break;
			case ICDIResumedEvent.STEP_RETURN:
				detail = DebugEvent.STEP_RETURN;
				break;
			}
			state = CDebugElementState.STEPPING;
		} else {
			disposeStackFrames();
			fireChangeEvent(DebugEvent.CONTENT);
		}
		setCurrent(false);
		setState(state);
		setCurrentStateInfo(null);
		fireResumeEvent(detail);
	}
	private void handleEndSteppingRange(ICDIEndSteppingRange endSteppingRange) {
		fireSuspendEvent(DebugEvent.STEP_END);
	}
	private void handleBreakpointHit(ICDIBreakpoint breakpoint) {
		fireSuspendEvent(DebugEvent.BREAKPOINT);
	}
	private void handleSuspendedBySignal(ICDISignalReceived signal) {
		fireSuspendEvent(DebugEvent.UNSPECIFIED);
	}
	private void handleTerminatedEvent(ICDIDestroyedEvent event) {
		setState(CDebugElementState.TERMINATED);
		setCurrentStateInfo(null);
		terminated();
	}
	private void handleDisconnectedEvent(ICDIDisconnectedEvent event) {
		setState(CDebugElementState.TERMINATED);
		setCurrentStateInfo(null);
		terminated();
	}
	private void handleChangedEvent(ICDIChangedEvent event) {}

	protected void cleanup() {
		getCDISession().getEventManager().removeEventListener(this);
		disposeStackFrames();
	}
	private void setRefreshChildren(boolean refresh) {
		fRefreshChildren = refresh;
	}
	private boolean refreshChildren() {
		return fRefreshChildren;
	}
	public boolean canRestart() {
		return getDebugTarget() instanceof IRestart && ((IRestart) getDebugTarget()).canRestart();
	}
	public void restart() throws DebugException {
		if (canRestart()) {
			((IRestart) getDebugTarget()).restart();
		}
	}
	protected boolean isCurrent() {
		return fIsCurrent;
	}
	protected void setCurrent(boolean current) {
		fIsCurrent = current;
	}
	protected int getStackDepth() throws DebugException {
		int depth = 0;
		try {
			depth = getCDIThread().getStackFrameCount();
		} catch (CDIException e) {
			setStatus(ICDebugElementStatus.WARNING, MessageFormat.format(CoreModelMessages.getString("CThread.1"), new String[] { e.getMessage() })); //$NON-NLS-1$
		}
		return depth;
	}
	protected int getMaxStackDepth() {
		return MAX_STACK_DEPTH;
	}
	private void setLastStackDepth(int depth) {
		fLastStackDepth = depth;
	}
	protected int getLastStackDepth() {
		return fLastStackDepth;
	}
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IRunToLine.class) || adapter.equals(IRunToAddress.class) || adapter.equals(IJumpToLine.class) || adapter.equals(IJumpToAddress.class)) {
			try {
				return (ICStackFrame) getTopStackFrame();
			} catch (DebugException e) {
				// do nothing
			}
		}
		if (adapter.equals(CDebugElementState.class))
			return this;
		if (adapter == ICStackFrame.class) {
			try {
				return (ICStackFrame) getTopStackFrame();
			} catch (DebugException e) {
				// do nothing
			}
		}
		if (adapter == IMemoryBlockRetrieval.class) {
			return getDebugTarget().getAdapter(adapter);
		}
		return super.getAdapter(adapter);
	}
	protected void dispose() {
		fDisposed = true;
		cleanup();
	}
	protected boolean isDisposed() {
		return fDisposed;
	}
	public boolean canResumeWithoutSignal() {
		return (getDebugTarget() instanceof IResumeWithoutSignal && ((IResumeWithoutSignal) getDebugTarget()).canResumeWithoutSignal());
	}
	public void resumeWithoutSignal() throws DebugException {
		if (canResumeWithoutSignal()) {
			((IResumeWithoutSignal) getDebugTarget()).resumeWithoutSignal();
		}
	}
	public String toString() {
		String result = "";
		try {
			result = getName();
		} catch (DebugException e) {
		}
		return result;
	}
	protected void resumedByTarget(int detail, List events) {
		if (isCurrent() && detail != DebugEvent.CLIENT_REQUEST && detail != DebugEvent.UNSPECIFIED) {
			setState(CDebugElementState.STEPPED);
			preserveStackFrames();
			events.add(createResumeEvent(detail));
		} else {
			setState(CDebugElementState.RESUMED);
			disposeStackFrames();
			events.add(createChangeEvent(DebugEvent.CONTENT));
		}
		setCurrent(false);
		setCurrentStateInfo(null);
	}
	protected boolean isInstructionsteppingEnabled() {
		return ((PDebugTarget) getDebugTarget()).isInstructionSteppingEnabled();
	}
	protected void suspendByTarget(ICDISessionObject reason, ICDIThread suspensionThread) {
		setState(CDebugElementState.SUSPENDED);
		setCurrentStateInfo(null);
		if (getCDIThread().equals(suspensionThread)) {
			setCurrent(true);
			setCurrentStateInfo(reason);
			if (reason instanceof ICDIEndSteppingRange) {
				handleEndSteppingRange((ICDIEndSteppingRange) reason);
			} else if (reason instanceof ICDIBreakpoint) {
				handleBreakpointHit((ICDIBreakpoint) reason);
			} else if (reason instanceof ICDISignalReceived) {
				handleSuspendedBySignal((ICDISignalReceived) reason);
			} else {
				// fireSuspendEvent( DebugEvent.CLIENT_REQUEST );
				// Temporary fix for bug 56520
				fireSuspendEvent(DebugEvent.BREAKPOINT);
			}
		}
	}
}
