package com.fem.views.annotations;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;

public class CmdRule implements IPredicateRule {

	
	
	private IToken fToken;


	public CmdRule(String startSequence, String endSequence, IToken token, char escapeCharacter, boolean breaksOnEOL) {
		Assert.isTrue(startSequence != null && startSequence.length() > 0);
		Assert.isTrue(endSequence != null || breaksOnEOL);
		Assert.isNotNull(token);

		// convert texts to lowercase
		startSequence = startSequence.toLowerCase();
		endSequence = endSequence.toLowerCase();

//		fStartSequence = startSequence.toCharArray();
//		fEndSequence = (endSequence == null ? new char[0] : endSequence
//				.toCharArray());
		fToken = token;
//		fEscapeCharacter = escapeCharacter;
//		fBreaksOnEOL = breaksOnEOL;
	}

	
	protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) {
		
			        // convert sequence to lowercase
			        String seq = new String(sequence);
			        seq = seq.toLowerCase();
			        sequence = seq.toCharArray();
			
			        for (int i = 1; i < sequence.length; i++) {
			            int c = scanner.read();
			            c = (int) Character.toLowerCase((char)c); // convert the character to lowercase for matching
			
			            if (c == ICharacterScanner.EOF && eofAllowed) {
		                return true;
			            } else if (c != sequence[i]) {
			                // Non-matching character detected, rewind the scanner back to the start.
	                // Do not unread the first character.
		                scanner.unread();
			                for (int j = i - 1; j > 0; j--)
			                    scanner.unread();
		                return false;
			            }
			        }
			
			        return true;
			    }


	
	
	
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return null;
	}


	@Override
	public IToken getSuccessToken() {
		return fToken;
	}


	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return null;
	}

}
