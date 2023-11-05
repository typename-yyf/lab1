//package log;
//
//import java.io.File;
//import java.util.LinkedList;
//
//public class HistoryLog extends Log {
//    private File logFile;
//
//    public HistoryLog(String logFileName) {
//        /* TODO */
//        /* 初始化相关参数 */
//    }
//
//    public String get(int n) {
//        /* TODO */
//        /* 从文件中获取最近n条历史记录，n=-1则获取所有记录 */
//
//        return null;
//    }
//
//    @Override
//    public void log(String... args) {
//        /* TODO */
//        /* 将所有的args写入文件中的同一行并加上时间戳，具体要求看lab要求 */
//    }
//}
package log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HistoryLog extends Log {
    private File logFile;
    private List<String> logList = new LinkedList<>();

    public HistoryLog(String logFileName) {
        this.logFile = new File(logFileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        readLogFile();
    }

    private void readLogFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(int n) {
        StringBuilder sb = new StringBuilder();
        if (n == -1) {
            for (String log : logList) {
                sb.append(log).append("\n");
            }
        } else {
            n = Math.min(n, logList.size());
            for (int i = logList.size() - n; i < logList.size(); i++) {
                sb.append(logList.get(i)).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void log(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (String arg : args) {
            sb.append(" ").append(arg);
        }
        logList.add(sb.toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(sb.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}