package operations;

import log.Loggers;
import utils.Parser;

public class OpListTree extends Operation {
    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() {
        Loggers.o.log(textWorkload.listTree(-1));
    }
}
