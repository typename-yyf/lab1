package operations;

import utils.Parser;

public class OpSave extends Operation{
    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() {
        textWorkload.save();
    }
}
