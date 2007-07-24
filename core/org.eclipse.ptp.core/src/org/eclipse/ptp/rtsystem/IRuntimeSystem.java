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

package org.eclipse.ptp.rtsystem;

import org.eclipse.core.runtime.CoreException;

public interface IRuntimeSystem extends IControlSystem, IMonitoringSystem {	

	/**
	 * Called to start the runtime system.
	 * @throws CoreException
	 */
	public boolean startup() throws CoreException;
	
	/**
	 * Called when the control system is being shutdown.
	 */
	public void shutdown();
	
	/**
	 * Adds a listener to the runtime system.  The runtime system may fire {@link RuntimeEvent}s
	 * and will use this list of listeners to determine who to send these events to.
	 * 
	 * @param listener someone that wants to listener to {@link RuntimeEvent}s
	 * @see RuntimeEvent
	 */
	public void addRuntimeEventListener(IRuntimeEventListener listener);

	/**
	 * Removes a listener from the list of things listening to {@link RuntimeEvent}s on
	 * this runtime system.
	 * 
	 * @param listener the listener to remove
	 * @see RuntimeEvent
	 */
	public void removeRuntimeEventListener(IRuntimeEventListener listener);
}