package operations;

import utils.Parser;

public class OpInsert extends OpAbstractInsert{

    public void parseArgument(Parser parser) {
        try {
            insertLine = parser.getInteger();
        } catch (NumberFormatException e) {
            insertLine = -1;
        }
        text = parser.getAll();
    }

}
