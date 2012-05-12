package com.fem.views.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.vem.views.utils.TextUtils;

public class CommandContentAssistProcessor implements IContentAssistProcessor {
	
	
	private final Map<String, String> femCommands;
	
	
	public CommandContentAssistProcessor(Map<String, String> femCommands) {
		this.femCommands = femCommands;
	}
	
	

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
				return null;
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int documentOffset) {
		
		IDocument doc = viewer.getDocument();

		List<CompletionProposal> propList = new ArrayList<CompletionProposal>();

		String qualifier = getQualifier(doc, documentOffset);

			// Compute completion proposals
		computeStructureProposals(qualifier, documentOffset, propList);
		ICompletionProposal[] proposals = new ICompletionProposal[propList
				.size()];

		propList.toArray(proposals);

		return proposals;


	}

	private String getQualifier(IDocument doc, int documentOffset) {
		return TextUtils.getLine(doc, documentOffset);
//		   StringBuffer buffer = new StringBuffer();
//		   while (true) {
//		     try {
//               
//		       char c = doc.getChar(--documentOffset);
//
//		       if (Character.isWhitespace(c))
//		          return "";
//
//		       // Collect character
//		       buffer.append(c);
//		       
//		       if(documentOffset == 0){
//		    	   return buffer.reverse().toString();
//		       }
//
//		     } catch (BadLocationException e) {
//
//		       // Document start reached, no tag found
//		       return "";
//		     }
//		   }
	}

	
	private void computeStructureProposals(String qualifier,
			int documentOffset, List<CompletionProposal> propList) {
		 int qlen = qualifier.length();
		 
		 for (String key : femCommands.keySet()) {
			 String cmdInfo = femCommands.get(key);
			 if(key.startsWith(qualifier)){
				 int cursor = key.length();
				   
		         IContextInformation contextInfo = 
		        		 new ContextInformation(null,key);

		         CompletionProposal proposal = new CompletionProposal(key, 
		        		 documentOffset - qlen, qlen, cursor, null, key, 
				         contextInfo, cmdInfo);
		         
		         // and add to result list
		         propList.add(proposal);
			 }
			 
		}

	}

    /**
     *  auto activated assist by typing some character
     */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}


	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return new ContextInformationValidator(this);
	}

}
