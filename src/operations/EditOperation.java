package operations;

import utils.Parser;

public abstract class EditOperation extends Operation {
    @Override
    public void execute(Parser parser) {
        super.execute(parser);

        OpUndo.insert(this);
        OpRedo.emptyStack();
    }

    public abstract void undo();
}
