//package session;
//
//import EditorExceptions.WorkloadTextNotFound;
//
//import java.util.LinkedList;
//
//public class Workload {
//    private class Stat {
//        String fileName;
//    }
//    private LinkedList<String> textList;
//    private Stat stat;
//
//    public String getFileName() {
//        return stat.fileName;
//    }
//
//    public Workload(String file) {
//        /* TODO */
//        /* 读入文件，并且以行为单位，组织成一个链表存在textList里 */
//        System.out.println("load file: " + file);
//    }
//
//    public void save() {
//        /* TODO */
//        /* 将textList存为文件 */
//        System.out.println("save file: " + stat.fileName);
//    }
//
//    public void insert(String s, int n) {
//        /* TODO */
//        /* 在第n行插入文本，n=-1时插入在倒数第1行，-2时倒数第二行以此类推 */
//        System.out.println("insert: " + s +  " at: " + n);
//    }
//
//    public int find(String s) throws WorkloadTextNotFound {
//        /* TODO */
//        /* 寻找字符串s第一次出现的行数，未找到时抛出WorkloadTextNotFound */
//
//        return 0;
//    }
//
//    public String delete(int n) {
//        /* TODO */
//        /* 删除第n行的文本，并返回被删除的文本，若n不符合要求返回null，n=-1时删除倒数第1行，-2时倒数第二行以此类推 */
//        System.out.println("delete: " + "at: " + n);
//
//        return null;
//    }
//
//    public String list(int n) {
//        /* TODO */
//        /* 返回第n行以后的所有文本 */
//
//        return "";
//
//    }
//
//    public String listTree(int n) {
//        /* TODO */
//        /* 与需求中的list相同，放回第n行开始的所有树状结构 */
//
//        return "";
//    }
//}
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
    }
    private LinkedList<String> textList;
    private LocalDateTime sessionStart;
    private HashMap<String, Long> fileWorkTimeMap;
    private HashMap<String, LocalDateTime> fileLastSaveTimeMap;
    private Stat stat;

    public String getFileName() {
        return stat.fileName;
    }

    public Workload(String file) throws IOException {
        this.stat = new Stat();
        this.stat.fileName = file;
        this.sessionStart = LocalDateTime.now();
        this.fileWorkTimeMap = new HashMap<>();
        this.fileLastSaveTimeMap = new HashMap<>();
        this.fileLastSaveTimeMap.put(file, LocalDateTime.now());
        this.textList = new LinkedList<>();

        // 检查路径合法性
        Path path = null;
        try {
            path = Paths.get(file);
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
        while ((line = br.readLine()) != null) {
            textList.add(line);
        }
        br.close();
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

    //插入行，如果行号为-1，则在末尾插入，若行号非-1，在对应行插入（第一行行号为0）
    public void insert(String s, int n) {
        if(n < 0){
            textList.add(textList.size() + n + 1, s);
        } else {
            textList.add(n, s);
        }
    }

    //查找标题，只需输入标题内容即可
    public int findDir(String s) throws Exception {
        int index = -1;
        for (int i = 0; i < textList.size(); i++) {
            String line = textList.get(i);
            if (line.matches("^#+\\s" + s)) {
                index = i;
                break;
            }
        }

        if(index == -1){
            throw new Exception("WorkloadTextNotFound");
        }
        return index;
    }
    //查找标题，只需输入标题内容即可
    public int getDirLevel(int i){
        String dirText = textList.get(i);
        return countLevel(dirText);
    }

    public int find(String s) throws Exception {
        int index = textList.indexOf(s);
        if(index == -1){
            throw new Exception("WorkloadTextNotFound");
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
    private class TreeNode {
        int level;
        boolean is_title;
        String texts;
        TreeNode parent;
        List<TreeNode> children;

        public TreeNode(int level,boolean is_title,String texts) {
            this.level = level;
            this.is_title = is_title;
            this.texts = texts;
            this.children = new ArrayList<>();
        }

        public void addChild(TreeNode child) {
            child.parent = this;  // Add parent to the child
            this.children.add(child);
        }

    }

    private int buildTree(int min_level,int position_now,TreeNode root) {
        position_now++;
        while (position_now < textList.size()) {
            if(countLevel(textList.get(position_now))!=-1 &&countLevel(textList.get(position_now))<=min_level){
                //若为下一个大标题
                return position_now;
            }
            if (countLevel(textList.get(position_now))==-1){//若非标题，则设置成与根节点相同的级别
                TreeNode child = new TreeNode(root.level,false,textList.get(position_now).replaceFirst("^\\*+\\s", ""));
                root.addChild(child);
                position_now++;
            }
            else if (countLevel(textList.get(position_now))>root.level){//若为子标题
                TreeNode child = new TreeNode(countLevel(textList.get(position_now)),true,textList.get(position_now).replaceFirst("^#+\\s", ""));
                position_now = buildTree(min_level,position_now,child);
                root.addChild(child);
            }else if (countLevel(textList.get(position_now))==root.level){//若为同级别标题
                return position_now;
            }
            else if (countLevel(textList.get(position_now))<root.level){//若为级别比自己大
                return position_now;
            }
        }
        return position_now;
    }

    //得到第n行的级别（有几个#为几级），如果是内容而非标题，返回-1
    private int countLevel(String line) {
        int level = 0;
        if (line.charAt(0) == '*'){
            level=-1;
            return level;
        }
        while (line.charAt(level) == '#') {
            level++;
        }
        return level;
    }
    private String printTree(TreeNode node,boolean isLast) {//打印不包含当前节点的子树
        StringBuilder sb = new StringBuilder();
        if(!(node.level==-1 && node.is_title==true)) {//若不是"root"节点，则打印节点
            sb.append("    ".repeat(Math.max(0, node.level - 1)));
            if(!node.is_title && node.level!=-1)sb.append("    ");//若为
            if(isLast)sb.append("└── ");
            else sb.append("├── ");
            if(!node.is_title)sb.append("·");
            sb.append(node.texts).append('\n');
        }
        for(int i = 0;i<node.children.size();i++){
            TreeNode child = node.children.get(i);
            if(i!=node.children.size()-1 && child.level<=node.children.get(i+1).level) //不是最后一个同等级的节点
                sb.append(printTree(child,false));
            else
                sb.append(printTree(child,true));
        }
        return sb.toString();
    }

    public String listTree(int min_level,int n) {
        TreeNode root = null;
        if(n==-1) root = new TreeNode(-1,true,"root");
        else root = new TreeNode(min_level,true,textList.get(n).replaceFirst("^#+\\s", ""));
        buildTree(min_level,n,root);

        String result = null;
        if(n==-1)result = printTree(root,false);
        else result = printTree(root,true);

        return result.toString();
    }


    public void stats(String option) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd:HHmmss");
        System.out.println("session start " + sessionStart.format(formatter));

        if ("all".equals(option)) {
            for (Map.Entry<String, Long> entry : fileWorkTimeMap.entrySet()) {
                printWorkTime(getRelativePath(entry.getKey()), entry.getValue());
            }
        } else { // 默认为 "current"
            printWorkTime(getRelativePath(stat.fileName), fileWorkTimeMap.getOrDefault(stat.fileName, 0L));
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