package operations;

import utils.Parser;

import java.io.IOException;

public class OpSave extends Operation{
    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() throws IOException {
        textWorkload.save();
    }
}
