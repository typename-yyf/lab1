package operations;

import log.Loggers;
import utils.Parser;

import java.util.Stack;

public class OpUndo extends Operation {
    static final private Stack<EditOperation> undoList = new Stack<EditOperation>();

    static public void insert(EditOperation operation) {
        undoList.push(operation);
    }

    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() {

        if (!undoList.isEmpty()) {
            EditOperation operation = undoList.pop();
            OpRedo.insert(operation);
            operation.undo();
        }
        else {
            Loggers.e.log("No operations have been executed before.");
        }

    }
}
