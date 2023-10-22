package operations;

import EditorExceptions.ParserNoElementFound;
import EditorExceptions.ParserNotAInteger;
import log.Loggers;
import utils.Parser;

public class OpDelete extends EditOperation {
    String text;
    int deleteLine;

    @Override
    public void undo() {
        textWorkload.insert(text, deleteLine);
    }

    @Override
    public void parseArgument(Parser parser) {
        try {
            deleteLine = parser.getInteger();
        } catch (ParserNoElementFound e) {
            Loggers.e.log("Wrong arguments.");
        } catch (ParserNotAInteger e) {
            deleteLine = textWorkload.find(parser.getAll());
        }
    }

    @Override
    public void _execute() {
        text = textWorkload.delete(deleteLine);
    }
}
