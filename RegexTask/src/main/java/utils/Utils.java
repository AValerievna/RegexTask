package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Utils {
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

    public static void writeSetToFile(FileWriter fw, String comment, Set<Integer> lines) {
        try {
            fw.write(comment + ": ");
            for (int elem : lines) {
                fw.write(elem + SPACE);
            }
            fw.write(NEW_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
