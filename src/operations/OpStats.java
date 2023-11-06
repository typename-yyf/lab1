package operations;

import log.Loggers;
import utils.Parser;

import java.io.IOException;

public class OpStats extends Operation{
    String option;
    @Override
    public void parseArgument(Parser parser) {

    }

    @Override
    public void _execute() throws IOException {
       textWorkload.stats(option);
    }
}
