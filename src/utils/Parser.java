package utils;

public class Parser {
    private String arg;

    public Parser(String argument) {
        arg = argument;
    }

    public String get() {
        String[] sArg = arg.split(" ", 2);

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

    public int getInteger() {
        String[] sArg = arg.split(" ", 2);

        int r = Integer.parseInt(sArg[0]);

        try {
            arg = sArg[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            arg = "";
        }

        return r;
    }
}
