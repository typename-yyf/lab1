package log;

public class StdoutLog extends Log {

    @Override
    public void log(String... args) {
        for (String arg: args) {
            System.out.print(arg);
        }
        System.out.print('\n');
    }
}
