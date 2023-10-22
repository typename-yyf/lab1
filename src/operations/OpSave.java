package operations;

import utils.Parser;

public class OpSave extends Operation{
    String fileName;

    @Override
    public void parseArgument(Parser parser) {
        fileName = parser.getAll();
    }

    @Override
    public void _execute() {
        textWorkload.save(fileName);
    }
}
