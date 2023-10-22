package operations;

import utils.Parser;

public abstract class EditOperation extends Operation {
    @Override
    public int execute(Parser parser) {
        if (super.execute(parser) == OPERATION_FAILED)
            return OPERATION_FAILED;

        OpUndo.insert(this);
        OpRedo.emptyStack();

        return OPERATION_COMPLETED;
    }

    public abstract void undo();
}
