package operations;

import EditorExceptions.OperationWrongArgument;
import EditorExceptions.ParserNoElementFound;
import EditorExceptions.ParserNotAInteger;
import log.Loggers;
import session.Workload;
import utils.Parser;

import java.io.IOException;

public class OpInsert extends OpAbstractInsert{
    @Override
    public void parseArgument(Parser parser) throws OperationWrongArgument {
        try {
            insertLine = parser.getInteger();
            //TODO 添加对行号超出范围的判断
        } catch (ParserNoElementFound e) {
            throw new OperationWrongArgument("Wrong arguments.");
        } catch (ParserNotAInteger e) {
            insertLine = -1;
        }
        text = parser.getAll();
    }
    @Override
    public void _execute(){
        textWorkload.insert(text,insertLine);
    }

}
