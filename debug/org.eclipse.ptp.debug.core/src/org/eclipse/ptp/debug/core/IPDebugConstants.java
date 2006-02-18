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
/*******************************************************************************
 * Copyright (c) 2000, 2004 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ptp.debug.core;

/**
 * Constant definitions for PTP debug plug-in.
 */
public interface IPDebugConstants {

	/**
	 * C/C++ debug plug-in identifier (value
	 * <code>"org.eclipse.ptp.debug.core"</code>).
	 */
	public static final String PLUGIN_ID = PTPDebugCorePlugin.getUniqueIdentifier();

	/**
	 * The identifier of the default variable format to use in the variables
	 * view
	 */
	public static final String PREF_DEFAULT_VARIABLE_FORMAT = PLUGIN_ID + "pDebug.default_variable_format";
	
	/**
	 * The identifier of the default expression format to use in the expressions
	 * views
	 */
	public static final String PREF_DEFAULT_EXPRESSION_FORMAT = PLUGIN_ID + "pDebug.default_expression_format";

	/**
	 * The identifier of the common source locations list
	 */
	public static final String PREF_SOURCE_LOCATIONS = PLUGIN_ID + "pDebug.Source.source_locations";
	
	/**
	 * Location of the sdm
	 */
	public static final String PREF_PTP_DEBUGGER_FILE = PLUGIN_ID + ".pDebug.debugger_file";

	/**
	 * Host for the sdm client
	 */
	public static final String PREF_PTP_DEBUGGER_HOST = PLUGIN_ID + ".pDebug.debugger_host";
	public static final String PREF_DEFAULT_DEUBGGER_HOST = "localhost";

	/**
	 * Debugger backend
	 */
	public static final String PREF_PTP_DEBUGGER_BACKEND = PLUGIN_ID + ".pDebug.debugger_backend";
	public static final int PREF_DEFAULT_DEDUGGER_BACKEND_INDEX = 0;
		
	public static final String[] DEBUGGER_BACKENDS = new String[] {
		"gdb-mi"
	};
	
	/**
	 * Path to backend debugger
	 */
	public static final String PREF_PTP_DEBUGGER_BACKEND_PATH = PLUGIN_ID + ".pDebug.debugger_backend_path";
	public static final String PREF_DEFAULT_DEDUGGER_BACKEND_PATH = "";

	public static final String PREF_SHOW_FULL_PATHS = PLUGIN_ID + ".pDebug.show_full_paths";

	public static final String PREF_PTP_DEBUGGER = PLUGIN_ID + ".pDebug.debuggers";
	public static final String PREF_PTP_DEBUG_COMM_TIMEOUT = PLUGIN_ID + ".pDebug.timeout";
	public static final String PREF_PTP_DEBUG_EVENT_TIME = PLUGIN_ID + ".pDebug.eventTime";

	public static final String PREF_PTP_DEBUG_REGISTER_PROC_0 = PLUGIN_ID + ".pDebug.regPro0";
	
	public static final int DEFAULT_DEBUG_TIMEOUT = 0;
	public static final int DEFAULT_DEBUG_EVENTTIME = 0;
	
}
