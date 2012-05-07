package com.fem.views.annotations;

//import luaparser.LuaParserError;
//import luaparser.LUA_PT.ParseException;
//import luaparser.LUA_PT.Token;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
//import codesheet.CodeSheet;
//import helper.TextHelper;

public class CodeAnnotationManager {



//    public static void addErrorAnnotation(IDocument document, ParseException pe) {
//        String message = pe.getMessage();
//        Token token = pe.currentToken;
//        int lineStart = token.beginLine;
//        ErrorAnnotation errorAnnotation = new ErrorAnnotation(lineStart, message);
//        //int pos = cs.getStyledText().getOffsetAtLine((lineStart-1));
//        int pos = TextHelper.getOffsetAtLine(cs, (lineStart-1));
//        String line = TextHelper.getLine(cs.getDocument(), pos);
//        int realStart = TextHelper.convertToDocumentOffset(line, token, pos, CodeSheet.getTabWidth());
//        cs.getAnnotationModel().addAnnotation(errorAnnotation, new Position(realStart, token.toString().length()));
//    }
//
//    public static ErrorAnnotation getErrorAnnotation(CodeSheet cs, ParseException pe) {
//        String message = pe.getMessage();
//        Token token = pe.currentToken;
//        int lineStart = token.beginLine;
//        ErrorAnnotation errorAnnotation = new ErrorAnnotation(lineStart, message);
//        //int pos = cs.getStyledText().getOffsetAtLine((lineStart-1));
//        int pos = TextHelper.getOffsetAtLine(cs, (lineStart-1));
//        String line = TextHelper.getLine(cs.getDocument(), pos);
//        int realStart = TextHelper.convertToDocumentOffset(line, token, pos, CodeSheet.getTabWidth());
//        errorAnnotation.setPosition(new Position(realStart, token.toString().length()));
//        //cs.getAnnotationModel().addAnnotation(errorAnnotation, new Position(realStart, token.toString().length()));
//        return errorAnnotation;
//    }

}
