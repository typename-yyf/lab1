
package session;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import EditorExceptions.WorkloadTextNotFound;
import java.util.LinkedList;
import java.nio.file.*;
public class Workload {
    private class Stat {
        String fileName;
        LocalDateTime startTime;
        LocalDateTime endTime=null;
        public Stat(String fileName,LocalDateTime startTime){
            this.fileName = fileName;
            this.startTime = startTime;
            this.endTime = null;
        }
    }
    private LinkedList<Stat> statSeq;

    private LinkedList<String> textList;
    private LocalDateTime sessionStart;//程序开始时间
    private HashMap<String, Long> fileWorkTimeMap;//工作时间
    private HashMap<String, LocalDateTime> fileLastSaveTimeMap;
    private Stat stat;

    public String getFileName() {
        return stat.fileName;
    }

    public Workload(String file) throws IOException {
        this.statSeq = new LinkedList<>();
        this.sessionStart = LocalDateTime.now(); // 初始化 sessionStart
        this.fileWorkTimeMap = new HashMap<>(); // 在构造函数中初始化
        this.fileLastSaveTimeMap = new HashMap<>();
        load(file);
        changeTime(file);
    }

    public void loadNewFile(String file) throws IOException {
        if(Objects.equals(file, getFileName()))return;
        // 在加载新文件前，先更新当前文件的工作时间
        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(fileLastSaveTimeMap.get(stat.fileName), now).toMinutes();
        fileWorkTimeMap.put(stat.fileName, fileWorkTimeMap.getOrDefault(stat.fileName, 0L) + minutes);
        fileLastSaveTimeMap.put(stat.fileName, now);
        // 然后再加载新文件
        load(file);
        changeTime(file);
    }
    private void load(String file) throws IOException {
        // 检查路径合法性
        try {
            Paths.get(file);
        } catch (InvalidPathException e) {
            System.out.println("Invalid file path");
            return;
        }
        // 检查是否存在文件，不存在则新建
        File f = new File(file);
        if(!f.exists()) {
            f.createNewFile();
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        this.textList = new LinkedList<>(); // 初始化 textList
        while ((line = br.readLine()) != null) {
            this.textList.add(line);
        }
        br.close();

    }
    private void changeTime(String file){
        if(this.statSeq.size()!=0)this.statSeq.getLast().endTime=LocalDateTime.now();
        this.stat = new Stat(file,LocalDateTime.now());
        this.statSeq.add(stat);

        this.fileLastSaveTimeMap.put(file, LocalDateTime.now()); // 更新而不是重新创建
        this.textList = new LinkedList<>();
    }

    public void save() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(fileLastSaveTimeMap.get(stat.fileName), now).toMinutes();
        fileWorkTimeMap.put(stat.fileName, fileWorkTimeMap.getOrDefault(stat.fileName, 0L) + minutes);
        fileLastSaveTimeMap.put(stat.fileName, now);
        BufferedWriter bw = new BufferedWriter(new FileWriter(stat.fileName));
        for (String line : textList) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public void insert(String s, int n) {
        if(n < 0){
            textList.add(textList.size() + n + 1, s);
        } else {
            textList.add(n, s);
        }
    }

    public int find(String s) throws WorkloadTextNotFound {
        int index = textList.indexOf(s);
        if(index == -1){
            throw new WorkloadTextNotFound();
        }
        return index;
    }

    public String delete(int n) {
        if(n < 0){
            return textList.remove(textList.size() + n);
        }
        return textList.remove(n);
    }

    public String list(int n) {
        StringBuilder result = new StringBuilder();
        for(int i = n; i < textList.size(); i++){
            result.append(textList.get(i)).append("\n");
        }
        return result.toString();
    }

    public String listTree(int n) {
        StringBuilder result = new StringBuilder();

        Iterator<String> iText = textList.iterator();
        ArrayList<Integer> levelList = new ArrayList<Integer>();

        int tLevel;

        for (int i = 0; i < n; i++) iText.next();
        levelList.add(levelOfText(iText.next()));
        tLevel = levelList.get(0);

        while (iText.hasNext()) {
            String text = iText.next();
            int level = levelOfText(text);

            if (level == 0) level = tLevel + 1;
            else tLevel = level;

            if (level < levelList.get(0)) break;

            levelList.add(level);
        }

        iText = textList.iterator();
        for (int i = 0; i < n; i++) iText.next();

        for (int i = 0; i < levelList.size(); i++) {
            String branch = "└── ";
            if (i < levelList.size() - 1)
                if (levelList.get(i + 1) == 0 ||
                    levelList.get(i + 1).equals(levelList.get(i)))
                    branch = "├── ";

                result.append("    ".repeat((levelList.get(i) - levelList.get(0))));
                result.append(branch);
                result.append(iText.next());
                result.append("\n");
        }

        return result.toString();
    }

    private int levelOfText(String s) {
        int r = 0;
        for (; r < s.length() && s.charAt(r) == '#'; r++);

        return r;
    }


    public void stats(String option) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd:HHmmss");
        System.out.println("session start " + sessionStart.format(formatter));

        // 在统计之前先更新当前文件的工作时间
        LocalDateTime now = LocalDateTime.now();
        long unsavedMinutes = Duration.between(fileLastSaveTimeMap.get(stat.fileName), now).toMinutes();
        long totalMinutes = fileWorkTimeMap.getOrDefault(stat.fileName, 0L) + unsavedMinutes;

        if ("all".equals(option)) {
            for (Map.Entry<String, Long> entry : fileWorkTimeMap.entrySet()) {
                // 如果是当前文件，则使用更新后的工作时间
                if (entry.getKey().equals(stat.fileName)) {
                    printWorkTime(getRelativePath(entry.getKey()), totalMinutes);
                } else {
                    printWorkTime(getRelativePath(entry.getKey()), entry.getValue());
                }
            }
        } else { // 默认为 "current"
            System.out.println(option);;
            printWorkTime(getRelativePath(stat.fileName), totalMinutes);
        }
    }

    private String getRelativePath(String absolutePath) {
        // 根据实际情况修改
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
    }

    private void printWorkTime(String fileName, long minutes) {
        long hours = minutes / 60;
        minutes %= 60;

        System.out.print(fileName + " ");
        if (hours > 0) {
            System.out.print(hours + " 小时 ");
        }
        if (minutes > 0 || hours == 0) {
            System.out.println(minutes + " 分钟");
        } else {
            System.out.println();
        }
    }
}