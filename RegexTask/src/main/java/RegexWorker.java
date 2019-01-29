import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RegexWorker {
    private static final int DEF_NUM = 10;
    private static final String REGEXP_1 = "\\.([^.]+)$";
    private String regexp2;


    public RegexWorker() {

    }

    String selectCases(String str) {
        Path path = Paths.get(str);
        File sourceFile = new File(str);
        String resStr = str.replaceAll(REGEXP_1, regexp2);
        File resFile = new File(resStr);
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int linesCount = 0;
        if (allLines != null) {
            linesCount = allLines.size();
        }

        if (allLines != null) {
            for (int i = 1; i < linesCount; i++) {
                parseLine(allLines.get(i));
            }
        }


        return resStr;
    }

    private void parseLine(String line) {

    }

    private void writeListToFile(File sourceFile, ArrayList<String> lines) {
        try (FileWriter fw = new FileWriter(sourceFile)) {
            for (String line : lines) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
