package operations;

import EditorExceptions.ParserNoElementFound;
import EditorExceptions.ParserNotAInteger;
import log.Loggers;
import utils.Parser;

public class OpInsert extends OpAbstractInsert{

    public void parseArgument(Parser parser) {
        try {
            insertLine = parser.getInteger();
        } catch (ParserNoElementFound e) {
            Loggers.e.log("Wrong arguments.");
        } catch (ParserNotAInteger e) {
            insertLine = -1;
        }
        text = parser.getAll();
    }

}
