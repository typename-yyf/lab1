package operations;

import log.Loggers;
import utils.Parser;

public class OpList extends Operation {
    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() {
        Loggers.o.log(textWorkload.list(0));
    }
}
