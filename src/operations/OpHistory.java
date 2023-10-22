package operations;

import log.Loggers;
import utils.Parser;
import EditorExceptions.*;

public class OpHistory extends Operation {
    private int nHistory;
    @Override
    public void parseArgument(Parser parser) {
        try {
            nHistory = parser.getInteger();
        } catch (ParserNotAInteger e) {
            Loggers.e.log("Wrong arguments.");
        } catch (ParserNoElementFound e) {
            nHistory = -1;
        }

    }

    @Override
    public void _execute() {
        Loggers.o.log(Loggers.h.get(nHistory));
    }
}
