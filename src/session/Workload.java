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
import java.util.*;
import EditorExceptions.WorkloadTextNotFound;
import java.util.LinkedList;
import java.nio.file.*;
public class Workload {
    private class Stat {
        String fileName;
    }
    private LinkedList<String> textList;
    private Stat stat;

    public String getFileName() {
        return stat.fileName;
    }

    public Workload(String file) throws IOException {
        this.stat = new Stat();
        this.stat.fileName = file;
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

    // Assuming listTree function prints the list with each line indented according to its line number
    public String listTree(int n) {
        StringBuilder result = new StringBuilder();
        for(int i = n; i < textList.size(); i++){
            char[] indentation = new char[i];
            Arrays.fill(indentation, ' ');
            result.append(indentation).append(textList.get(i)).append("\n");
        }
        return result.toString();
    }
}