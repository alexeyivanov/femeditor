package com.fem.views.annotations;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class CommandPartitionScanner extends RuleBasedPartitionScanner {
	
	public final static String XML_TAG = "__xml_tag";

	public CommandPartitionScanner() {

		IToken tag = new Token(XML_TAG);

		IPredicateRule[] rules = new IPredicateRule[1];

//		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[0] = new TagRule(tag);

		setPredicateRules(rules);
	}
	

}
