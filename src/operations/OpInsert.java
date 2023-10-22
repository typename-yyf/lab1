package operations;

import EditorExceptions.OperationWrongArgument;
import EditorExceptions.ParserNoElementFound;
import EditorExceptions.ParserNotAInteger;
import log.Loggers;
import utils.Parser;

public class OpInsert extends OpAbstractInsert{

    public void parseArgument(Parser parser) throws OperationWrongArgument {
        try {
            insertLine = parser.getInteger();
        } catch (ParserNoElementFound e) {
            throw new OperationWrongArgument("Wrong arguments.");
        } catch (ParserNotAInteger e) {
            insertLine = -1;
        }
        text = parser.getAll();
    }

}
