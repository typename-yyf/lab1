package operations;

import utils.Parser;

public class OpAppendHead extends OpAbstractInsert{

    @Override
    public void parseArgument(Parser parser) {
        text = parser.getAll();
        insertLine = 0;
    }
    @Override
    public void _execute(){
        textWorkload.insert(text,insertLine);
    }
}
