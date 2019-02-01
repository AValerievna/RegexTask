package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Utils {
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";

    public static <T> void writeSetToFile(FileWriter fw, String comment, Set<T> lines) {
        try {
            fw.write(comment + ": ");
            lines.forEach(elem ->
            {
                try {
                    fw.write(elem + SPACE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fw.write(NEW_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
