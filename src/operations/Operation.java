package operations;

import session.Workload;
import log.Loggers;

import utils.Parser;
public abstract class Operation {
    protected static Workload textWorkload;

    public void execute(Parser parser) {
        if (!(this instanceof OpLoad) && textWorkload == null) {
            Loggers.e.log("There's no file in the working space.");
            return;
        }

        parseArgument(parser);
        _execute();
    }

    public abstract void parseArgument(Parser parser);
    public abstract void _execute();

}

