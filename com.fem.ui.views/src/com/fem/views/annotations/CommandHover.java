package com.fem.views.annotations;

import org.eclipse.jface.text.*;
import org.eclipse.swt.graphics.Point;
//import core.CodeConstants;
//import core.Main;
//
//import loaders.LuaSyntaxLoader;
//import loaders.BlizzardCommandsXMLLoader;
//import luaapi.LuaMethod;
//import blizzard.api.BlizzardFunction;
//import helper.TextHelper;

public class CommandHover implements ITextHover {

    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {

        if (hoverRegion != null) {
            // clear label regardless
            //setInfoText("");

            try {
                // get the word we're hovering over
                String word="";
//                String word = WordHoverManager.extractWord(textViewer.getDocument(), hoverRegion.getOffset());

                if (word != null && word.length() > 0) {

                    // reserved
                    if (word.equalsIgnoreCase("function"))
                        return null;

                    // fetch the corresponding info for that word, if any
//                    String show = WordHoverManager.hover(word);
                    String show = "";
                    // if any info at all, show it via a hover
                    if (show == null || show.length() > 0) {
                        setInfoText(show);
                        return "("+word + ") - " + show;
                    }

                    // if we get here, check if we're hovering a method
                    int line = textViewer.getDocument().getLineOfOffset(hoverRegion.getOffset());
                    IRegion lineRegion = textViewer.getDocument().getLineInformation(line);

                    if (lineRegion.getLength() > 0) {
                        String strLine = textViewer.getDocument().get(lineRegion.getOffset(), lineRegion.getLength());
                        if (strLine != null && strLine.length() > 0) {

                            // check if it's a local method
                            String methodInfo = getMethodInfo(strLine, word, hoverRegion.getOffset());
                            if (methodInfo != null && methodInfo.length() > 0) {
                                //System.err.println("Method!: " + strLine + " " + word);
                                setInfoText(methodInfo);
                                return methodInfo;
                            }

                            // check if it's defined as a blizzard method
//                            BlizzardFunction bf = BlizzardCommandsXMLLoader.findFunction(word);
//                            if (bf != null) {
//                                int fw = 78;
//                                String name = bf.getName();
//                                name = TextHelper.fixStringWidth(name, fw);
//
//                                String desc = null;
//                                if (bf.getDescription() != null) {
//                                    desc = bf.getDescription();
//                                    desc = TextHelper.fixStringWidth(desc, fw);
//                                }
//                                String example = null;
//                                if (bf.getExample() != null) {
//                                    example = bf.getExample();
//                                    example = TextHelper.fixStringWidth(example, fw);
//                                }
//
//                                String ret = " " + name +
//                                        (desc == null ? "" : "\nDescription:\n" + desc) +
//                                        (example == null ? "" : "\nExample:\n" + example);
//
//                                setInfoText(bf.getName());
//                                ret += "\n";
//
//                                // stuff is hugging the border for some reason, so make it space it out a little
//                                ret = ret.replaceAll("\n", "\n ");
//
//                                //Globals.ACTIVE_SHEET.drawBoxAroundWord(hoverRegion.getOffset(), word);
//
//                                return ret;
//                            }

                            // check if it's a lua method
//                            LuaMethod lm = LuaSyntaxLoader.findMethod(word);
//                            if (lm != null) {
//                                //System.err.println("Lua Method!");
//                                setInfoText(lm.getRaw());
//                                return lm.getRaw();
//                            }
                        }
                    }
                }
                else {
                    // if we select text and mouse over it.. hover it
                    if (hoverRegion.getLength() > -1) {
                        //String hr = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
                        //return hr;
                        //David din't like it
                        return null;
                    }
                }
            }
            catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    private void setInfoText(final String text) {
        Runnable run = new Runnable() {
            public void run() {
//                Main.setInformationMessage(text);
            }
        };
//        Main.display.syncExec(run);
    }

    private String getMethodInfo(String text, String word, int offset) {
        try {
            text = text.trim();

            int open = text.indexOf("(");
            int close = text.indexOf(")");
//            int function = text.indexOf(CodeConstants.FUNCTION_NAME);

            // text.endsWith(";")
            // TODO
            /*
            if (open > -1 && close > -1 && function == -1) {

                Vector functions = Globals.ACTIVE_SHEET.getLocalFunctions();
                for (int i = 0; i < functions.size(); i++) {
                    Function f = (Function) functions.get(i);
                    if (f.getName().equals(word)) {
                        return f.toString();
                    }
                }

            }
            */

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
        Point selection = textViewer.getSelectedRange();
        if (selection.x <= offset && offset < selection.x + selection.y)
            return new Region(selection.x, selection.y);

        return new Region(offset, 0);
    }
}
