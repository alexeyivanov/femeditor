package com.fem.views.annotations;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class CommandPartitionScanner extends RuleBasedPartitionScanner {
	
	public final static String FEM_COMMAND = "__fem_cmd";
	public final static String FEM_COMMENT = "__fem_comment";

	public CommandPartitionScanner() {

		IToken cmd = new Token(FEM_COMMAND);

		IPredicateRule[] rules = new IPredicateRule[1];
		
		IToken xmlComment = new Token(FEM_COMMENT);

		rules[0] = new MultiLineRule("/*", "*/", xmlComment);
//		rules[1] = new CmdRule("", "" cmd);

		setPredicateRules(rules);
	}
	

}
