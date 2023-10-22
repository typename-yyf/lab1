package operations;

import EditorExceptions.OperationExecutionFailed;
import EditorExceptions.OperationWrongArgument;
import session.Workload;
import log.Loggers;

import utils.Parser;
public abstract class Operation {
    public static final int OPERATION_FAILED = -1;
    public static final int OPERATION_COMPLETED = 0;

    protected static Workload textWorkload;

    public int execute(Parser parser) {
        if (!(this instanceof OpLoad) && textWorkload == null) {
            Loggers.e.log("There's no file in the working space.");
            return OPERATION_FAILED;
        }

        try {
            parseArgument(parser);
            _execute();
        } catch (OperationWrongArgument | OperationExecutionFailed e) {
            Loggers.e.log(e.getMessage());
            return OPERATION_FAILED;
        }

        return OPERATION_COMPLETED;
    }

    public abstract void parseArgument(Parser parser) throws OperationWrongArgument;
    public abstract void _execute() throws OperationExecutionFailed;

}

