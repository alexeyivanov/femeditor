package com.fem.views.annotations;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

public class TagRule extends MultiLineRule {

//	public TagRule(IToken tag) {
//	}
	public TagRule(IToken token) {
		super("<", ">", token);
	}

}
