package session;

import java.util.LinkedList;
import EditorExceptions.TextNotFound;

public class Workload {
    private class Stat {
        String fileName;
    }
    private LinkedList<String> text;
    private Stat stat;

    public String getFileName() {
        return stat.fileName;
    }

    public Workload(String file) {
        /* TODO */
        System.out.println("load file: " + file);
    }

    public void save(String file) {
        /* TODO */
        System.out.println("save file: " + file);
    }

    public void insert(String s, int line) {
        /* TODO */
        System.out.println("insert: " + s +  " at: " + line);
    }

    public int find(String s) {
        /* TODO */

        return 0;
    }

    public String delete(int line) {
        /* TODO */
        System.out.println("delete: " + "at: " + line);

        return null;
    }

    public String list(int line) {
        /* TODO */

        return "";

    }

    public String listTree(int line) {
        /* TODO */

        return "";
    }
}
