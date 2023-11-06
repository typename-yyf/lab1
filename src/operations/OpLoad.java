package operations;

import session.Workload;
import utils.Parser;

import java.io.IOException;

public class OpLoad extends Operation{
    String fileName;

    @Override
    public void parseArgument(Parser parser) {
        fileName = parser.getAll();
    }

    @Override
    public void _execute() throws IOException {
        textWorkload = new Workload(fileName);
    }
}
