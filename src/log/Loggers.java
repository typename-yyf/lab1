package log;

public class Loggers {
    public static final HistoryLog h = new HistoryLog(".history.log");
    public static final StatLog    s = new StatLog(".stat.log");
    public static final StderrLog  e = new StderrLog();
    public static final StdoutLog  o = new StdoutLog();
}
