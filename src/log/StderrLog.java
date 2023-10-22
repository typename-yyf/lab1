package log;

public class StderrLog extends Log {
    @Override
    public void log(String... args) {
        for (String arg: args) {
            System.err.print(arg);
        }
        System.err.print('\n');
    }
}
