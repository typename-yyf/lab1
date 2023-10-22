package operations;

import utils.Parser;

public class OpAppendTail extends OpAbstractInsert{
    @Override
    public void parseArgument(Parser parser) {
        text = parser.getAll();
        insertLine = -1;
    }
}
