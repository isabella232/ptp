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
package org.eclipse.ptp.debug.internal.ui;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.cdt.debug.core.cdi.ICDIBreakpointHit;
import org.eclipse.cdt.debug.core.cdi.ICDIExitInfo;
import org.eclipse.cdt.debug.core.cdi.ICDISharedLibraryEvent;
import org.eclipse.cdt.debug.core.cdi.ICDISignalExitInfo;
import org.eclipse.cdt.debug.core.cdi.ICDISignalReceived;
import org.eclipse.cdt.debug.core.cdi.ICDIWatchpointScope;
import org.eclipse.cdt.debug.core.cdi.ICDIWatchpointTrigger;
import org.eclipse.cdt.debug.core.cdi.model.ICDISignal;
import org.eclipse.cdt.debug.core.model.CDebugElementState;
import org.eclipse.cdt.debug.core.model.ICDebugElement;
import org.eclipse.cdt.debug.core.model.ICDebugElementStatus;
import org.eclipse.cdt.debug.core.model.ICStackFrame;
import org.eclipse.cdt.debug.core.model.IEnableDisableTarget;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugEditorPresentation;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ptp.debug.core.model.IPAddressBreakpoint;
import org.eclipse.ptp.debug.core.model.IPBreakpoint;
import org.eclipse.ptp.debug.core.model.IPDebugTarget;
import org.eclipse.ptp.debug.core.model.IPFunctionBreakpoint;
import org.eclipse.ptp.debug.core.model.IPLineBreakpoint;
import org.eclipse.ptp.debug.ui.PTPDebugUIPlugin;
import org.eclipse.ptp.ui.model.IElementHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author Clement chu
 *
 */
public class PDebugModelPresentation extends LabelProvider implements IDebugModelPresentation, IDebugEditorPresentation {
	private static PDebugModelPresentation instance = null;
	public final static String DISPLAY_FULL_PATHS = "DISPLAY_FULL_PATHS";
	
	protected UIDebugManager uiDebugManager = null;
	protected Map attributes = new HashMap(3);
	private OverlayImageCache imageCache = new OverlayImageCache();
	
	public PDebugModelPresentation() {
		uiDebugManager = PTPDebugUIPlugin.getDefault().getUIDebugManager();
		//make sure using the one created by start up
		if (instance == null)
			instance = this;
	}
	
	public static PDebugModelPresentation getDefault() {
		if (instance == null)
			instance = new PDebugModelPresentation();
		return instance;
	}

	public String getEditorId(IEditorInput input, Object element) {
		if (input != null) {
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor descriptor = registry.getDefaultEditor(input.getName());
			if (descriptor != null)
				return descriptor.getId();
			
			//TODO return CEditor id hardcode, CUIPlugin.EDITOR_ID
			return (descriptor != null)?descriptor.getId():"org.eclipse.cdt.ui..editor.CEditor";
		}
		return null;
	}

	public IEditorInput getEditorInput(Object element) {
		if (element instanceof IMarker) {
			IResource resource = ((IMarker)element).getResource();
			if (resource instanceof IFile)
				return new FileEditorInput((IFile)resource);
		}
		if (element instanceof IFile) {
			return new FileEditorInput((IFile)element);
		}
		if (element instanceof IPBreakpoint) {
			IPBreakpoint pbk = (IPBreakpoint)element;
			IFile file = null;
			try {
				String handle = pbk.getSourceHandle();
				IPath path = new Path(handle);
				if (path.isValidPath(handle)) {
					IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path);
					if (files.length > 0)
						file = files[0];
					/*
					 * FIXME
					else {
						File fsFile = new File(handle);
						if (fsFile.isFile() && fsFile.exists()) {
							return new ExternalEditorInput(new LocalFileStorage(fsFile));
						}
					}
					*/
				}
			} catch (CoreException e) {}
			
			if (file == null)
				file = (IFile)pbk.getMarker().getResource().getAdapter(IFile.class);
			if (file != null)
				return new FileEditorInput(file);
		}
		/*
		 * FIXME
		if (element instanceof FileStorage || element instanceof LocalFileStorage) {
			return new ExternalEditorInput((IStorage)element);
		}
		*/
		return null;
	}

	public void computeDetail(IValue value, IValueDetailListener listener) {
		// TODO 
		//PValueDetailProvider.getDefault().computeDetail(value, listener);
		System.out.println("PDebugModelPresentation - ComputeDetails");
	}

	public void setAttribute(String attribute, Object value) {
		if (value == null)
			return;
		getAttributes().put(attribute, value);
	}
	
	public Image getImage(Object element) {
		Image baseImage = getBaseImage(element);
		if (baseImage != null) {
			ImageDescriptor[] overlays = new ImageDescriptor[]{ null, null, null, null };
			/*
			if (element instanceof IPDebugElementStatus && !((IPDebugElementStatus)element).isOK()) {
				switch(((IPDebugElementStatus)element).getSeverity()) {
					case ICDebugElementStatus.WARNING:
						overlays[OverlayImageDescriptor.BOTTOM_LEFT] = CDebugImages.DESC_OVRS_WARNING;
						break;
					case ICDebugElementStatus.ERROR:
						overlays[OverlayImageDescriptor.BOTTOM_LEFT] = CDebugImages.DESC_OVRS_ERROR;
						break;
				}
			}
			if (element instanceof IWatchExpression && ((IWatchExpression)element).hasErrors())
				overlays[OverlayImageDescriptor.BOTTOM_LEFT] = PDebugImages.DESC_OVRS_ERROR;
			if (element instanceof IPVariable && ((IPVariable)element).isArgument())
				overlays[OverlayImageDescriptor.TOP_RIGHT] = PDebugImages.DESC_OVRS_ARGUMENT;
			if (element instanceof IPGlobalVariable && !(element instanceof IRegister))
				overlays[OverlayImageDescriptor.TOP_RIGHT] = PDebugImages.DESC_OVRS_GLOBAL;
			*/
			return getImageCache().getImageFor(new OverlayImageDescriptor(baseImage, overlays));
		}
		return null;
	}
	
	private Image getBaseImage(Object element) {
		//TODO element can be DebugTarget, Thread
		if (element instanceof IMarker) {
			IBreakpoint bp = getBreakpoint((IMarker)element);
			if (bp != null && bp instanceof IPBreakpoint ) {
				return getBreakpointImage((IPBreakpoint)bp);
			}
		}
		if (element instanceof IPBreakpoint) {
			return getBreakpointImage((IPBreakpoint)element);
		}
		return super.getImage(element);
	}	

	protected Image getBreakpointImage(IPBreakpoint breakpoint) {
		try {
			if (breakpoint instanceof IPLineBreakpoint)
				return getLineBreakpointImage((IPLineBreakpoint)breakpoint);
			//TODO implement WatchBreakpoint
		} catch(CoreException e) {
			PTPDebugUIPlugin.log(e);
		}
		return null;
	}
	
	protected Image getLineBreakpointImage(IPLineBreakpoint breakpoint) throws CoreException {
		String job_id = breakpoint.getJobId();
		String cur_job_id = uiDebugManager.getCurrentJobId();

		// Display nothing if the breakpoint is not in current job
		if (!job_id.equals(IPBreakpoint.GLOBAL) && !job_id.equals(cur_job_id))
			return new Image(null, 1, 1);
		
		String descriptor = null;
		IElementHandler setManager = uiDebugManager.getElementHandler(job_id);
		if (setManager == null) //no job running
			descriptor = breakpoint.isEnabled()?PDebugImage.IMG_DEBUG_BPTCURSET_EN:PDebugImage.IMG_DEBUG_BPTCURSET_DI;
		else { //created job
			String cur_set_id = uiDebugManager.getCurrentSetId();
			String bpt_set_id = breakpoint.getSetId();
			
			if (bpt_set_id.equals(cur_set_id)) {
				descriptor = breakpoint.isEnabled()?PDebugImage.IMG_DEBUG_BPTCURSET_EN:PDebugImage.IMG_DEBUG_BPTCURSET_DI;
			}
			else {
				if (setManager.getSet(bpt_set_id).isContainSets(cur_set_id))
					descriptor = breakpoint.isEnabled()?PDebugImage.IMG_DEBUG_BPTMULTISET_EN:PDebugImage.IMG_DEBUG_BPTMULTISET_DI;
				else
					descriptor = breakpoint.isEnabled()?PDebugImage.IMG_DEBUG_BPTNOSET_EN:PDebugImage.IMG_DEBUG_BPTNOSET_DI;
			}
		}
		return getImageCache().getImageFor(new OverlayImageDescriptor(PDebugImage.getImage(descriptor), computeBreakpointOverlays(breakpoint)));
	}

	public String getText(Object element) {
		String bt = getBaseText(element);
		if (bt == null)
			return null;
		StringBuffer baseText = new StringBuffer(bt);
		//FIXME used ICDebugElementStatus - cdt
		if (element instanceof ICDebugElementStatus && !((ICDebugElementStatus)element).isOK()) {
			baseText.append(getFormattedString(" <{0}>", ((ICDebugElementStatus)element).getMessage()));
		}
		if (element instanceof IAdaptable) {
			//FIXME used IEnableDisableTarget - cdt
			IEnableDisableTarget target = (IEnableDisableTarget)((IAdaptable)element).getAdapter(IEnableDisableTarget.class);
			if (target != null) {
				if (!target.isEnabled()) {
					baseText.append(' ');
					baseText.append(PDebugUIMessages.getString("PTPDebugModelPresentation.disabled1"));
				}
			}
		}
		return baseText.toString();
	}

	private String getBaseText( Object element ) {
		boolean showQualified = isShowQualifiedNames();
		StringBuffer label = new StringBuffer();
		try {
			/*
			if (element instanceof ICModule) {
				label.append(getModuleText((ICModule)element, showQualified));
				return label.toString();
			}
			if (element instanceof ICSignal) {
				label.append(getSignalText((ICSignal)element));
				return label.toString();
			}
			if (element instanceof IRegisterGroup) {
				label.append(((IRegisterGroup)element).getName());
				return label.toString();
			}
			if (element instanceof IWatchExpression) {
				return getWatchExpressionText((IWatchExpression)element);
			}
			if (element instanceof IVariable) {
				label.append(getVariableText((IVariable)element));
				return label.toString();
			}
			if (element instanceof IValue) {
				label.append(getValueText((IValue)element));
				return label.toString();
			}
			*/
			if (element instanceof IStackFrame) {
				label.append(getStackFrameText((IStackFrame)element, showQualified));
				return label.toString();
			}
			if (element instanceof IMarker) {
				IBreakpoint breakpoint = getBreakpoint((IMarker)element);
				if (breakpoint != null) {
					return getBreakpointText(breakpoint, showQualified);
				}
				return null;
			}
			if (element instanceof IBreakpoint) {
				return getBreakpointText((IBreakpoint)element, showQualified);
			}
			if (element instanceof IDebugTarget)
				label.append(getTargetText((IDebugTarget)element, showQualified));
			else if ( element instanceof IThread )
				label.append(getThreadText((IThread)element, showQualified));
			if ( label.length() > 0 ) {
				return label.toString();
			}
			if (element instanceof ITerminate)  {
				if (((ITerminate)element).isTerminated()) {
					label.insert(0, PDebugUIMessages.getString("PTPDebugModelPresentation.terminated1"));
					return label.toString();
				}
			}
			if (element instanceof IDisconnect) {
				if (((IDisconnect)element).isDisconnected()) {
					label.insert(0, PDebugUIMessages.getString("PTPDebugModelPresentation.disconnected1"));
					return label.toString();
				}
			}
			if ( label.length() > 0 ) {
				return label.toString();
			}
		}
		catch (DebugException e) {
			PTPDebugUIPlugin.log(e);
		}
		catch (CoreException e) {
			PTPDebugUIPlugin.log(e);
		}
		return null;
	}

	protected boolean isShowQualifiedNames() {
		Boolean showQualified = (Boolean)getAttributes().get(DISPLAY_FULL_PATHS);
		showQualified = (showQualified == null)?Boolean.FALSE:showQualified;
		return showQualified.booleanValue();
	}

	private Map getAttributes() {
		return attributes;
	}

	private OverlayImageCache getImageCache() {
		return imageCache;
	}

	private boolean isEmpty(String string) {
		return (string == null || string.trim().length() == 0);
	}

	protected IBreakpoint getBreakpoint(IMarker marker) {
		return DebugPlugin.getDefault().getBreakpointManager().getBreakpoint(marker);
	}

	protected String getBreakpointText(IBreakpoint breakpoint, boolean qualified) throws CoreException {
		if (breakpoint instanceof IPLineBreakpoint) {
			return getLineBreakpointText((IPLineBreakpoint)breakpoint, qualified);
		}
		return "";
	}

	protected String getLineBreakpointText(IPLineBreakpoint breakpoint, boolean qualified) throws CoreException {
		StringBuffer label = new StringBuffer();
		appendSourceName(breakpoint, label, qualified);
		appendLineNumber(breakpoint, label);
		appendBreakpointStatus(breakpoint, label);
		return label.toString();
	}
	protected StringBuffer appendSourceName(IPBreakpoint breakpoint, StringBuffer label, boolean qualified) throws CoreException {
		String handle = breakpoint.getSourceHandle();
		if (!isEmpty( handle)) {
			IPath path = new Path(handle);
			if (path.isValidPath(handle)) {
				label.append(qualified?path.toOSString():path.lastSegment());
			}
		}
		return label;
	}
	protected StringBuffer appendLineNumber(IPLineBreakpoint breakpoint, StringBuffer label) throws CoreException {
		int lineNumber = breakpoint.getLineNumber();
		if (lineNumber > 0) {
			label.append(" ");
			label.append(MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.line1"), new String[] { Integer.toString(lineNumber) }));
		}
		return label;
	}
	protected StringBuffer appendBreakpointStatus(IPBreakpoint breakpoint, StringBuffer label) throws CoreException {
		String jobName = breakpoint.getJobName();
		if (!jobName.equals(IPBreakpoint.GLOBAL))
			jobName = "Job: " + jobName; 
		label.append(" ");
		label.append("{"); 
		label.append(jobName + " - ");
		label.append(breakpoint.getSetId());
		label.append("}");
		//label.append(MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.details1"), new String[] { jobName, breakpoint.getSetId() }));
		return label;
	}

	private ImageDescriptor[] computeBreakpointOverlays(IPBreakpoint breakpoint) {
		ImageDescriptor[] overlays = new ImageDescriptor[]{ null, null, null, null };
		try {
			if (breakpoint.isGlobal()) {
				overlays[OverlayImageDescriptor.TOP_LEFT] = (breakpoint.isEnabled()) ? PDebugImage.ID_IMG_DEBUG_OVER_BPT_GLOB_EN : PDebugImage.ID_IMG_DEBUG_OVER_BPT_GLOB_DI;
			}
			if (breakpoint.isConditional()) {
				overlays[OverlayImageDescriptor.BOTTOM_LEFT] = (breakpoint.isEnabled()) ? PDebugImage.ID_IMG_DEBUG_OVER_BPT_COND_EN : PDebugImage.ID_IMG_DEBUG_OVER_BPT_COND_DI;
			}
			if (breakpoint.isInstalled()) {
				overlays[OverlayImageDescriptor.BOTTOM_LEFT] = (breakpoint.isEnabled()) ? PDebugImage.ID_IMG_DEBUG_OVER_BPT_INST_EN : PDebugImage.ID_IMG_DEBUG_OVER_BPT_INST_DI;
			}
			if (breakpoint instanceof IPAddressBreakpoint) {
				overlays[OverlayImageDescriptor.TOP_RIGHT] = (breakpoint.isEnabled()) ? PDebugImage.ID_IMG_DEBUG_OVER_BPT_ADDR_EN : PDebugImage.ID_IMG_DEBUG_OVER_BPT_ADDR_DI;
			}
			if (breakpoint instanceof IPFunctionBreakpoint) {
				overlays[OverlayImageDescriptor.BOTTOM_RIGHT] = (breakpoint.isEnabled()) ? PDebugImage.ID_IMG_DEBUG_OVER_BPT_FUNC_EN : PDebugImage.ID_IMG_DEBUG_OVER_BPT_FUNC_DI;
			}
		} catch(CoreException e) {
			PTPDebugUIPlugin.log(e);
		}
		return overlays;
	}

	protected String getTargetText(IDebugTarget target, boolean qualified) throws DebugException {
		IPDebugTarget t = (IPDebugTarget)target.getAdapter(IPDebugTarget.class);
		if (t != null) {
			if (!t.isPostMortem()) {
				//FIXME used CDebugElementState
				CDebugElementState state = t.getState();
				if (state.equals(CDebugElementState.EXITED)) {
					Object info = t.getCurrentStateInfo();
					String label = PDebugUIMessages.getString("PTPDebugModelPresentation.target1");
					String reason = "";
					if (info != null && info instanceof ICDISignalExitInfo) {
						ICDISignalExitInfo sigInfo = (ICDISignalExitInfo)info;
						reason = ' ' + MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.target2"), new String[]{ sigInfo.getName(), sigInfo.getDescription() });
					}
					else if (info != null && info instanceof ICDIExitInfo ) {
						reason = ' ' + MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.target3"), new Integer[] { new Integer( ((ICDIExitInfo)info).getCode() ) });
					}
					return MessageFormat.format(label, new String[] { target.getName(), reason } );
				}
				else if (state.equals(CDebugElementState.SUSPENDED)) {
					return MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.target4"), new String[] { target.getName() });
				}
			}
		}
		return target.getName();
	}
	protected String getThreadText(IThread thread, boolean qualified) throws DebugException {
		IPDebugTarget target = (IPDebugTarget)thread.getDebugTarget().getAdapter(IPDebugTarget.class);
		if (target.isPostMortem()) {
			return getFormattedString(PDebugUIMessages.getString("PTPDebugModelPresentation.thread"), thread.getName());
		}
		if (thread.isTerminated()) {
			return getFormattedString(PDebugUIMessages.getString("PTPDebugModelPresentation.thread2"), thread.getName());
		}
		if (thread.isStepping()) {
			return getFormattedString(PDebugUIMessages.getString("PTPDebugModelPresentation.thread3"), thread.getName());
		}
		if (!thread.isSuspended()) {
			return getFormattedString(PDebugUIMessages.getString("PTPDebugModelPresentation.thread4"), thread.getName());
		}
		if (thread.isSuspended()) {
			String reason = "";
			//FIXME used ICDebugElement
			ICDebugElement element = (ICDebugElement)thread.getAdapter(ICDebugElement.class);
			if (element != null) {
				Object info = element.getCurrentStateInfo();
				if (info != null && info instanceof ICDISignalReceived) {
					ICDISignal signal = ((ICDISignalReceived)info).getSignal();
					reason = MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.thread5"), new String[]{ signal.getName(), signal.getDescription() });
				}
				else if (info != null && info instanceof ICDIWatchpointTrigger) {
					reason = MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.thread6"), new String[]{ ((ICDIWatchpointTrigger)info).getOldValue(), ((ICDIWatchpointTrigger)info).getNewValue() });
				}
				else if (info != null && info instanceof ICDIWatchpointScope) {
					reason = PDebugUIMessages.getString("PTPDebugModelPresentation.thread7");
				}
				else if (info != null && info instanceof ICDIBreakpointHit) {
					reason = PDebugUIMessages.getString("PTPDebugModelPresentation.thread8");
				}
				else if (info != null && info instanceof ICDISharedLibraryEvent) {
					reason = PDebugUIMessages.getString("PTPDebugModelPresentation.thread9");
				}
			}
			return MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.thread10"), new String[] { thread.getName(), reason } );
		}
		return MessageFormat.format(PDebugUIMessages.getString("PTPDebugModelPresentation.thread11"), new String[] { thread.getName() } );
	}
	
	protected String getStackFrameText(IStackFrame f, boolean qualified) throws DebugException {
		//FIXME used ICStackFrame - cdt
		if (f instanceof ICStackFrame) {
			ICStackFrame frame = (ICStackFrame)f;
			StringBuffer label = new StringBuffer();
			label.append(frame.getLevel());
			label.append(' ');
			String function = frame.getFunction();
			if (function != null) {
				function = function.trim();
				if (function.length() > 0) {
					label.append(function);
					label.append("() ");
					if (frame.getFile() != null) {
						IPath path = new Path(frame.getFile());
						if (!path.isEmpty()) {
							label.append(PDebugUIMessages.getString("PTPDebugModelPresentation.frame1"));
							label.append(' ');
							label.append((qualified?path.toOSString():path.lastSegment()));
							label.append(':');
							if (frame.getFrameLineNumber() != 0)
								label.append(frame.getFrameLineNumber());
						}
					}
				}
			}
			if (isEmpty(function))
				label.append(PDebugUIMessages.getString("PTPDebugModelPresentation.frame2"));
			return label.toString();
		}
		//FIXME Dunno what is IDummyStacjFrame for
		//return (f.getAdapter(IDummyStackFrame.class) != null)?getDummyStackFrameLabel(f):f.getName();
		return f.getName();
	}	
	
	public static String getFormattedString(String key, String arg) {
		return getFormattedString(key, new String[]{ arg });
	}

	public static String getFormattedString(String string, String[] args) {
		return MessageFormat.format(string, args);
	}
	
	public void dispose() {
		getImageCache().disposeAll();
		attributes.clear();
		super.dispose();
	}
	
	public boolean addAnnotations(IEditorPart editorPart, IStackFrame stackFrame) {
		return true;
		/*
		try {
			PAnnotationManager.getDefault().addAnnotation(editorPart, stackFrame);
			return true;
		} catch (CoreException e) {
			PTPDebugUIPlugin.log(e);
			return false;
		}
		*/
	}
	
	public void removeAnnotations(IEditorPart editorPart, IThread thread) {
		/*
		try{
			PAnnotationManager.getDefault().removeAnnotation(editorPart, thread);
		} catch (CoreException e) {
			PTPDebugUIPlugin.log(e);
		}
		*/
	}
}
