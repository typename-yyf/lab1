package operations;

import utils.Parser;

public class OpAppendTail extends OpAbstractInsert{
    @Override
    public void parseArgument(Parser parser) {
        text = parser.getAll();
        insertLine = -1;
    }
    @Override
    public void _execute(){
        textWorkload.insert(text,insertLine);
    }
}
