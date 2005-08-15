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
/*
 * Created on Feb 18, 2005
 *
 */
package org.eclipse.ptp.debug.external;


import java.util.Observer;

import org.eclipse.ptp.core.IPJob;
import org.eclipse.ptp.debug.external.event.DebugEvent;
import org.eclipse.ptp.debug.external.model.MProcess;
import org.eclipse.ptp.debug.external.model.MProcessSet;
import org.eclipse.ptp.debug.external.utils.Queue;



/**
 * @author donny
 *
 */
public interface IDebugger {
	public void initialize(IPJob job);
	
	/* General Debugger Interface */

	/* Process/Thread Sets */
	public void focus(String name);
	public MProcessSet defSet(String name, int[] procs);
	public void undefSet(String name);
	public void undefSetAll();
	public MProcess[] viewSet(String name);
	
	/* Debugger Initialization/Termination */
	public abstract void load(String prg);
	public abstract void load(String prg, int numProcs);
	public abstract void run(String[] args);
	public abstract void run();
	public abstract void detach();
	public abstract void kill();
	public void exit();
	
	/* Program Information */
	
	/* Data Display and Manipulation */
	
	/* Execution Control */
	public abstract void step();
	public abstract void step(int count);
	public abstract void stepOver();
	public abstract void stepOver(int count);
	public abstract void stepFinish();
	public abstract void halt();
	public abstract void go();
	public void stepSet(String set);
	public void stepSet(String set, int count);
	public void stepOverSet(String set);
	public void stepOverSet(String set, int count);
	public void stepFinishSet(String set);
	public void haltSet(String set);
	public void goSet(String set);
	public void step(int[] procs);
	public void step(int[] procs, int count);
	public void stepOver(int[] procs);
	public void stepOver(int[] procs, int count);
	public void stepFinish(int[] procs);
	public void halt(int[] procs);
	public void go(int[] procs);


	
	/* Actionpoints */
	public abstract void breakpoint(String loc);
	public abstract void breakpoint(String loc, int count);
	public abstract void breakpoint(String loc, String cond);
	public abstract void watchpoint(String var);
	public void breakpointSet(String set, String loc);
	public void breakpointSet(String set, String loc, int count);
	public void breakpointSet(String set, String loc, String cond);
	public void watchpointSet(String set, String var);
	public void breakpoint(int[] procs, String loc);
	public void breakpoint(int[] procs, String loc, int count);
	public void breakpoint(int[] procs, String loc, String cond);
	public void watchpoint(int[] procs, String var);
	public abstract void delete(int[] ids);
	public abstract void delete(String type);
	public abstract void disable(int[] ids);
	public abstract void disable(String type);
	public abstract void enable(int[] ids);
	public abstract void enable(String type);
	
	/* The methods below will not be found in the HPDF Spec */
	
	/* Events */
	public void addDebuggerObserver(Observer obs);
	public void deleteDebuggerObserver(Observer obs);
	public void fireEvents(DebugEvent[] events);
	public void fireEvent(DebugEvent event);
	public void notifyObservers(Object arg);
	public Queue getEventQueue();
	public boolean isExiting();
	
	/* Methods that are required to interface with Eclipse Debug/CDI Model */
	public abstract Process getSessionProcess();
	public abstract MProcess getProcess(int number);
	public abstract MProcess getProcess();
	public abstract MProcess[] getProcesses();
	public abstract void restart();

}

