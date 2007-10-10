/**********************************************************************
 * Copyright (c) 2005 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ptp.pldt.mpi.core.editorHelp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.ui.ICHelpBook;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ptp.pldt.common.editorHelp.CHelpBookImpl;
import org.eclipse.ptp.pldt.common.editorHelp.FunctionSummaryImpl;
import org.eclipse.ptp.pldt.mpi.core.MpiPlugin;
import org.osgi.framework.Bundle;

/**
 * Help book for C++ MPI functions
 * @author tibbitts
 *
 */
public class MpiCPPHelpBook extends CHelpBookImpl
{
    private static final String TITLE = "MPI C++ Help Book";

    public MpiCPPHelpBook()
    {
		super(MpiPlugin.getPluginId());
		System.out.println("MPI CPP help book ctor()...");
		// populate func map
		Bundle bundle = Platform.getBundle(MpiPlugin.getPluginId());
		Path path = new Path("mpiref.xml");

		URL fileURL = FileLocator.find(bundle, path, null);
		InputStream xmlIn = null;
		try {
			xmlIn = fileURL.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<FunctionSummaryImpl> mpiFuncList = MPIDocXMLParser.parseDOM(xmlIn, "cppname");
		int temp=0;
		for (Iterator<FunctionSummaryImpl> it = mpiFuncList.iterator(); it.hasNext();) {
			FunctionSummaryImpl functionSummary = it.next();
			if(2>temp++)System.out.println("  "+functionSummary.getName()+"-"+functionSummary.getDescription());
			funcName2FuncInfo.put(functionSummary.getName(), functionSummary);
		};
    	// set title
    	setTitle(TITLE);

    }
    
    public int getCHelpType()
    {
        return ICHelpBook.HELP_TYPE_CPP;
    }

}
