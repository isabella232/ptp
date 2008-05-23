/**********************************************************************
 * Copyright (c) 2008 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ptp.pldt.upc.prefs;



import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ptp.pldt.upc.UPCIDs;
import org.eclipse.ptp.pldt.upc.UPCPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Preference page based on FieldEditorPreferencePage
 * 
 * @author xue
 */

public class UPCPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    private static final String INCLUDES_PREFERENCE_LABEL  = "UPC include paths:";
    private static final String INCLUDES_PREFERENCE_BROWSE = "Please choose a directory:";
    private static final String UPC_HELP="Location of UPC help files:";
    private static final String UPC_HELP_DEFAULT="Use default";
    private static final String UPC_HELP_DEFAULT_ID="upcHelpUseDefault";
    //private static final String UPC_HELP_LINUX="Use Linux location: ";
    //private static final String UPC_HELP_AIX="Use AIX location: ";
    //private static final String UPC_HELP_OTHER="Other:";
    //private static final String UPC_HELP_OTHER_ID="upcHelpOther";
    
    //private static final String UPC_LOCATION_AIX="/opt/rsct/lapi/eclipse/help";
    //private static final String UPC_LOCATION_LINUX="opt/ibmhpc/lapi/eclipse/help";
    
    private static final String UPC_WHICH_HELP_ID="default";  // alternatives are: default, aix, linux, other

    public UPCPreferencePage()
    {
        super(FLAT);
        initPreferenceStore();
    }

    public UPCPreferencePage(int style)
    {
        super(style);
        initPreferenceStore();
    }

    public UPCPreferencePage(String title, ImageDescriptor image, int style)
    {
        super(title, image, style);
        initPreferenceStore();
    }

    public UPCPreferencePage(String title, int style)
    {
        super(title, style);
        initPreferenceStore();
    }

    /**
     * Init preference store and set the preference store for the preference page
     */
    private void initPreferenceStore()
    {
        IPreferenceStore store = UPCPlugin.getDefault().getPreferenceStore();
        setPreferenceStore(store);
    }

    public void init(IWorkbench workbench)
    {
    }

    protected void createFieldEditors()
    {
        PathEditor pathEditor = new PathEditor(UPCIDs.UPC_INCLUDES, INCLUDES_PREFERENCE_LABEL,
                INCLUDES_PREFERENCE_BROWSE, getFieldEditorParent());
        addField(pathEditor);

        //"Use default?"
//        BooleanFieldEditor bed = new BooleanFieldEditor(UPC_HELP_DEFAULT_ID,UPC_HELP_DEFAULT,getFieldEditorParent());
//        addField(bed);
  /*      
        int numCol=1;
    	RadioGroupFieldEditor choiceFE = new RadioGroupFieldEditor(UPC_WHICH_HELP_ID, UPC_HELP, numCol, new String[][] {
				{ UPC_HELP_DEFAULT, "choice1" }, 
				{ UPC_HELP_AIX+UPC_LOCATION_AIX, "choice2" }, 
				{ UPC_HELP_LINUX+UPC_LOCATION_LINUX, "Choice3" },
				{ UPC_HELP_OTHER, "Choice4" }},
				getFieldEditorParent());
    	addField(choiceFE);
    	
    	StringFieldEditor otherLoc=new StringFieldEditor(UPC_HELP_OTHER_ID, UPC_HELP_OTHER,getFieldEditorParent());
    	addField(otherLoc);
    	*/
    	
    }
    
}
