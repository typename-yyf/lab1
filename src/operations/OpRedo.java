package operations;

import utils.Parser;

import java.util.Stack;

public class OpRedo extends Operation {
    static final private Stack<EditOperation> redoList = new Stack<EditOperation>();


    static public void insert(EditOperation operation) {
        redoList.push(operation);
    }

    static public void emptyStack() {
        redoList.removeAllElements();
    }

    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() {
        if (!redoList.isEmpty()) {
            EditOperation operation = redoList.pop();
            OpUndo.insert(operation);
            operation._execute();
        }
    }
}
