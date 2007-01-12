/*******************************************************************************
 * Copyright (c) 2006 The Regents of the University of California. 
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
package org.eclipse.ptp.core.attributes;

public abstract class AbstractAttribute implements IAttribute {

	private final IAttributeDescription description;
	private boolean enabled;

	public AbstractAttribute(IAttributeDescription description) {
		this.description = description;
		this.enabled = true;
	}
	
	/**
	 * First compare the descriptions. If the descriptions are the same then
	 * do the compare in the subclass.
	 * 
	 * @param other
	 * @return
	 */
	public final int compareTo(Object arg0) {
		AbstractAttribute other = (AbstractAttribute) arg0;
		int compareDesc = this.description.compareTo(other.description);
		if (compareDesc != 0) {
			return compareDesc;
		}
		
		return doCompareTo(other);
	}

	public abstract boolean equals(Object obj);

	public IAttributeDescription getDescription() {
		return description;
	}

	public abstract String getStringRep();

	public abstract int hashCode();

	/* (non-Javadoc)
	 * @see org.eclipse.ptp.core.attributes.IAttribute#isEnabled()
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final String toString() {
		return getStringRep();
	}

	protected abstract int doCompareTo(AbstractAttribute other);

}
