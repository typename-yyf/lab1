package operations;

import session.Workload;
import utils.Parser;

public class OpLoad extends Operation{
    String fileName;

    @Override
    public void parseArgument(Parser parser) {
        fileName = parser.getAll();
    }

    @Override
    public void _execute() {
        textWorkload = new Workload(fileName);
    }
}
