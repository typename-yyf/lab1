package operations;

import log.Loggers;
import utils.Parser;

public class OpDirTree extends Operation {
    private int dirLine;
    @Override
    public void parseArgument(Parser parser) {
        dirLine = textWorkload.find(parser.getAll());
    }

    @Override
    public void _execute() {
        Loggers.o.log(textWorkload.listTree(dirLine));
    }
}
