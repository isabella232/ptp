/*******************************************************************************
 * Copyright (c) 2011 University of Illinois All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html 
 * 	
 * Contributors: 
 * 	Albert L. Rossi - design and implementation
 ******************************************************************************/
package org.eclipse.ptp.rm.jaxb.core.data.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ptp.rm.jaxb.core.IAssign;
import org.eclipse.ptp.rm.jaxb.core.IJAXBNonNLSConstants;
import org.eclipse.ptp.rm.jaxb.core.data.Match;
import org.eclipse.ptp.rm.jaxb.core.data.Regex;
import org.eclipse.ptp.rm.jaxb.core.data.Target;
import org.eclipse.ptp.rm.jaxb.core.data.Test;

public class MatchImpl implements IJAXBNonNLSConstants {

	private RegexImpl regex;
	private TargetImpl target;
	private List<IAssign> assign;
	private List<TestImpl> tests;
	private boolean matched;

	public MatchImpl(String uuid, Match match) {
		Regex r = match.getExpression();
		if (r != null) {
			regex = new RegexImpl(r);
		}
		Target t = match.getTarget();
		if (t != null) {
			target = new TargetImpl(uuid, t);
		}

		List<Object> assign = match.getAddOrAppendOrPut();
		if (!assign.isEmpty()) {
			this.assign = new ArrayList<IAssign>();
			for (Object o : assign) {
				AbstractAssign.add(uuid, o, this.assign);
			}
		}

		List<Test> tests = match.getTest();
		if (!tests.isEmpty()) {
			this.tests = new ArrayList<TestImpl>();
			for (Test test : tests) {
				this.tests.add(new TestImpl(uuid, test));
			}
		}
	}

	public synchronized void clear() throws Throwable {
		if (target != null) {
			if (tests != null) {
				for (TestImpl t : tests) {
					t.setTarget(target.getTarget());
					t.doTest();
				}
			}
			target.clear();
		}
		matched = false;
	}

	public synchronized int doMatch(String sequence) throws Throwable {
		int end = 0;
		if (matched) {
			return end;
		}

		String[] tokens = null;

		if (regex == null) {
			matched = true;
		} else {
			tokens = regex.getMatched(sequence);
			if (tokens == null) {
				return end;
			}
			matched = true;
			/*
			 * return pos of the unmatched remainder
			 */
			end = regex.getLastChar();
		}

		if (target == null || assign == null) {
			return end;
		}

		Object t = target.getTarget(tokens);

		for (IAssign a : assign) {
			a.setTarget(t);
			a.assign(tokens);
		}

		return end;
	}

	public synchronized boolean getMatched() {
		return matched;
	}

	public RegexImpl getRegex() {
		return regex;
	}

	public TargetImpl getTarget() {
		return target;
	}

	public void setTarget(TargetImpl target) {
		this.target = target;
	}
}
