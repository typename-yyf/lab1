package session;

import EditorExceptions.ParserNoElementFound;
import operations.*;
import utils.Parser;
import log.Loggers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;


public class Session {
    private static final HashMap<String, Class> commandMap = new HashMap<String, Class>();
    static {
        commandMap.put("append-head", OpAppendHead.class);
        commandMap.put("append-tail", OpAppendTail.class);
        commandMap.put("delete",      OpDelete.class);
        commandMap.put("dir-tree",    OpDirTree.class);
        commandMap.put("history",     OpHistory.class);
        commandMap.put("insert",      OpInsert.class);
        commandMap.put("list",        OpList.class);
        commandMap.put("list-tree",   OpListTree.class);
        commandMap.put("load",        OpLoad.class);
        commandMap.put("redo",        OpRedo.class);
        commandMap.put("save",        OpSave.class);
        commandMap.put("undo",        OpUndo.class);
    }

    public int run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            Parser parser = new Parser(command);
            String opName;
            try {
                opName = parser.get();
            } catch (ParserNoElementFound e) {
                continue;
            }

            if (opName.equals("quit"))
                break;

            Operation operation;
            try {
                operation = (Operation)commandMap.get(opName).getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                continue;
            } catch (NullPointerException e) {
                Loggers.e.log("No command named: ", opName);
                continue;
            }

            if (operation.execute(parser) == Operation.OPERATION_COMPLETED)
                Loggers.h.log(command);
            OpSave save = new OpSave();
            try {
                save._execute();
            } catch (IOException e){

            }
        }

        return 0;
    }
}
