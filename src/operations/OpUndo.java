package operations;

import EditorExceptions.OperationExecutionFailed;
import EditorExceptions.OperationWrongArgument;
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
    public void _execute() throws OperationExecutionFailed {

        if (!undoList.isEmpty()) {
            EditOperation operation = undoList.pop();
            OpRedo.insert(operation);
            operation.undo();
        }
        else {
            throw new OperationExecutionFailed("No operations have been executed before");
        }

    }
}
