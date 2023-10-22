package session;

import java.util.LinkedList;
import EditorExceptions.TextNotFound;

public class Workload {
    private class Stat {
        String fileName;
    }
    private LinkedList<String> textList;
    private Stat stat;

    public String getFileName() {
        return stat.fileName;
    }

    public Workload(String file) {
        /* TODO */
        /* 读入文件，并且以行为单位，组织成一个链表存在textList里 */
        System.out.println("load file: " + file);
    }

    public void save() {
        /* TODO */
        /* 将textList存为文件 */
        System.out.println("save file: " + stat.fileName);
    }

    public void insert(String s, int n) {
        /* TODO */
        /* 在第n行插入文本，n=-1时插入在倒数第1行，-2时倒数第二行以此类推 */
        System.out.println("insert: " + s +  " at: " + n);
    }

    public int find(String s) {
        /* TODO */
        /* 寻找字符串s第一次出现的行数，未找到时返回-1 */

        return 0;
    }

    public String delete(int n) {
        /* TODO */
        /* 删除第n行的文本，并返回被删除的文本，若n不符合要求返回null，n=-1时删除倒数第1行，-2时倒数第二行以此类推 */
        System.out.println("delete: " + "at: " + n);

        return null;
    }

    public String list(int n) {
        /* TODO */
        /* 返回第n行以后的所有文本 */

        return "";

    }

    public String listTree(int n) {
        /* TODO */
        /* 与需求中的list相同，放回第n行开始的所有树状结构 */

        return "";
    }
}
