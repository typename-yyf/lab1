package operations;

import EditorExceptions.ParserNoElementFound;
import EditorExceptions.ParserNotAInteger;
import EditorExceptions.OperationWrongArgument;
import EditorExceptions.WorkloadTextNotFound;
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
    public void parseArgument(Parser parser) throws OperationWrongArgument {
        try {
            deleteLine = parser.getInteger();

        } catch (ParserNoElementFound e) {
            throw new OperationWrongArgument("Wrong arguments.");
        } catch (ParserNotAInteger e) {
            try {
                deleteLine = textWorkload.find(parser.getAll());
            } catch (WorkloadTextNotFound e1) {
                throw new OperationWrongArgument("Text Not Found.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void _execute() {
        text = textWorkload.delete(deleteLine);
    }
}
