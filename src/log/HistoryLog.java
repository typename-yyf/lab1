package log;

import java.io.File;
import java.util.LinkedList;

public class HistoryLog extends Log {
    private File logFile;

    public HistoryLog(String logFileName) {
        /* TODO */
        /* 初始化相关参数 */
    }

    public String get(int n) {
        /* TODO */
        /* 从文件中获取最近n条历史记录，n=-1则获取所有记录 */

        return null;
    }

    @Override
    public void log(String... args) {
        /* TODO */
        /* 将所有的args写入文件中的同一行并加上时间戳，具体要求看lab要求 */

    }
}
