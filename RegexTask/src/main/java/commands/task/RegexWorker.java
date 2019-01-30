package commands.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexWorker {
    private static final String REGEXP_1 = "\\.([^.]+)$";
    public static final Pattern CREATE_PATT = Pattern.compile("^create\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern ADD_PATT = Pattern.compile("^add\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DIFF_PATT = Pattern.compile("^diff\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<arr2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern AND_PATT = Pattern.compile("^and\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<arr2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern DEL_N_PATT = Pattern.compile("^del\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DEL_ALL_PATT = Pattern.compile("^del\\s+(?<arr1>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    protected static Logger log;
    public static Configuration conf;
    static CommandWorker cw;


    public static void main(String[] args) {
        log = Logger.getGlobal();
        try {
            conf = new Configuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cw = new CommandWorker();

        String pathStr = conf.getProperty("file.path");
        Path path = Paths.get(pathStr);
        File sourceFile = new File(pathStr);
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
                doCommandWithLine(allLines.get(i));
            }
        }


    }

    private static void doCommandWithLine(String line) {

        Matcher m = CREATE_PATT.matcher(line);
        if (m.matches()) {
            cw.createArray(m.group("arr1"));
        } else {
            m = ADD_PATT.matcher(line);
            if (m.matches()) {
                System.out.println(m.group("arr1"));
                System.out.println(m.group("num"));
                cw.addIntoArray(m.group("arr1"), Integer.parseInt(m.group("num")));
            } else {
                m = DIFF_PATT.matcher(line);
                if (m.matches()) {
                    cw.findArraysDiff(m.group("arr1"), m.group("arr2"));
                } else {
                    m = AND_PATT.matcher(line);
                    if (m.matches()) {
                        cw.findArraysCommon(m.group("arr1"), m.group("arr2"));
                    } else {
                        m = DEL_N_PATT.matcher(line);
                        if (m.matches()) {
                            cw.delNumberOfLinesInArray(m.group("arr1"), Integer.parseInt(m.group("num")));
                        } else {
                            m = DEL_ALL_PATT.matcher(line);
                            if (m.matches()) {
                                cw.delElemsArray(m.group("arr1"));
                            } else {
                                log.info("Unable to parse command");
                            }
                        }
                    }
                }
            }
        }
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


