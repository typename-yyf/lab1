package utils;

import EditorExceptions.*;

public class Parser {
    private String arg;

    public Parser(String argument) {
        arg = argument;
    }


    public String get() throws ParserNoElementFound {
        String[] sArg = arg.split("\s+", 2);
        if (sArg[0].length() == 0) throw new ParserNoElementFound();

        try {
            arg = sArg[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            arg = "";
        }

        return sArg[0];
    }

    public String getAll() {
        String rv = new String(arg);
        arg = "";
        return rv;
    }

    public int getInteger() throws ParserNoElementFound, ParserNotAInteger {
        String[] sArg = arg.split("\\s+", 2);
        if (sArg[0].length() == 0) throw new ParserNoElementFound();

        int r = 0;

        try {
            r = Integer.parseInt(sArg[0]);
            arg = sArg[1];  // If there is no content after the line number, set arg to an empty string
        } catch (ArrayIndexOutOfBoundsException e) {
            arg = "";
        } catch (NumberFormatException e) {
            arg = sArg[0];
            throw new ParserNotAInteger();
        }

        return r;
    }
}
