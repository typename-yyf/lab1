package operations;

import utils.Parser;

public abstract class OpAbstractInsert extends EditOperation {
    protected String text;
    protected int insertLine;

    @Override
    public void undo() {
        textWorkload.delete(insertLine);
    }

    @Override
    public void _execute() {
        textWorkload.insert(text, insertLine);
    }
}
