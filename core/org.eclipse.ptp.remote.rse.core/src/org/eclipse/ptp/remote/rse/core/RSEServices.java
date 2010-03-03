/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ptp.remote.rse.core;

import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ptp.remote.core.IRemoteConnection;
import org.eclipse.ptp.remote.core.IRemoteConnectionManager;
import org.eclipse.ptp.remote.core.IRemoteFileManager;
import org.eclipse.ptp.remote.core.IRemoteProcessBuilder;
import org.eclipse.ptp.remote.core.IRemoteServicesDelegate;
import org.eclipse.rse.core.RSECorePlugin;
import org.eclipse.rse.core.model.ISystemRegistry;


public class RSEServices implements IRemoteServicesDelegate {
	private ISystemRegistry registry;
	private IRemoteConnectionManager connMgr;

	/* (non-Javadoc)
	 * @see org.eclipse.ptp.remote.IRemoteServicesDelegate#getProcessBuilder(org.eclipse.ptp.remote.IRemoteConnection, java.util.List)
	 */
	public IRemoteProcessBuilder getProcessBuilder(IRemoteConnection conn, List<String>command) {
		return new RSEProcessBuilder(conn, command);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ptp.remote.IRemoteServicesDelegate#getProcessBuilder(org.eclipse.ptp.remote.IRemoteConnection, java.lang.String[])
	 */
	public IRemoteProcessBuilder getProcessBuilder(IRemoteConnection conn, String... command) {
		return new RSEProcessBuilder(conn, command);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ptp.remote.IRemoteServicesDelegate#getConnectionManager()
	 */
	public IRemoteConnectionManager getConnectionManager() {
		return connMgr;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ptp.remote.IRemoteServicesDelegate#getFileManager(org.eclipse.ptp.remote.IRemoteConnection)
	 */
	public IRemoteFileManager getFileManager(IRemoteConnection conn) {
		if (!(conn instanceof RSEConnection)) {
			return null;
		}
		return new RSEFileManager((RSEConnection)conn);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ptp.remote.IRemoteServicesDelegate#initialize()
	 */
	public boolean initialize() {
		registry = RSECorePlugin.getTheSystemRegistry();
		if (registry == null) {
			return false;
		}
		
		// The old code that tried to wait for RSE to initialize
		// was wrong.  If the init job hadn't run yet, it wouldn't block.
		// However, we can't block here anyway, because this can get called
		// from the main thread on startup, before RSE is initialized.
		// This would mean we deadlock ourselves.
		
		// So if RSE isn't initialized, report out initialization failed,
		// and the next time someone tries to use the service, initialization
		// will be attempted again.
		
		if(!RSECorePlugin.isInitComplete(RSECorePlugin.INIT_ALL)) {
			return false;
		}
		
		if (!RSECorePlugin.getThePersistenceManager().isRestoreComplete()) {
			return false;
		}

		connMgr = new RSEConnectionManager(registry);
		return true;
	}
}
