/*******************************************************************************
 * Copyright (c) 2009 School of Computer Science,
 * National University of Defense Technology, P.R.China.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jie Jiang,National University of Defense Technology
 ******************************************************************************/
package org.eclipse.ptp.rm.slurm.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ptp.rm.slurm.ui.wizards.SLURMResourceManagerConfigurationWizardPage;
import org.eclipse.ptp.services.core.IServiceProvider;
import org.eclipse.ptp.services.ui.IServiceProviderContributor;
import org.eclipse.ptp.ui.wizards.IRMConfigurationWizard;
import org.eclipse.ptp.ui.wizards.RMServiceProviderConfigurationWizard;
import org.eclipse.swt.widgets.Composite;

public class SLURMProviderContributor implements IServiceProviderContributor 
{
	/* (non-Javadoc)
	 * @see org.eclipse.ptp.services.ui.IServiceProviderContributor#configureServiceProvider(org.eclipse.ptp.services.core.IServiceProvider, org.eclipse.swt.widgets.Composite)
	 */
	public void configureServiceProvider(IServiceProvider provider, Composite comp) 
	{
		// Nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ptp.services.ui.IServiceProviderContributor#getWizard(org.eclipse.ptp.services.core.IServiceProvider, org.eclipse.jface.wizard.IWizardPage)
	 */
	public IWizard getWizard(IServiceProvider provider, IWizardPage page) {
		return new RMServiceProviderConfigurationWizard(provider, page);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ptp.services.ui.IServiceProviderContributor#getWizardPages(org.eclipse.jface.wizard.IWizard, org.eclipse.ptp.services.core.IServiceProvider)
	 */
	public WizardPage[] getWizardPages(IWizard wizard, IServiceProvider provider) 
	{
		WizardPage wizardPages[];
		
		wizardPages = new WizardPage[1];
		wizardPages[0] = new SLURMResourceManagerConfigurationWizardPage((IRMConfigurationWizard)wizard);
		return wizardPages;
	}

}