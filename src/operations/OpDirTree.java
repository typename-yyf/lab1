package operations;

import EditorExceptions.OperationWrongArgument;
import EditorExceptions.WorkloadTextNotFound;
import log.Loggers;
import utils.Parser;

public class OpDirTree extends Operation {
    private int dirLine;
    private int dirLevel;
    @Override
    public void parseArgument(Parser parser) throws OperationWrongArgument {
        try {
            dirLine = textWorkload.findDir(parser.getAll());
            dirLevel = textWorkload.getDirLevel(dirLine);
        } catch (WorkloadTextNotFound e) {
            throw new OperationWrongArgument("Text not found.");
        }
    }

    @Override
    public void _execute() {

        Loggers.o.log(textWorkload.listTree(dirLevel,dirLine));
    }
}
