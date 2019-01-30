package commands_task;

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
    public static final String CREATE_PATT = "^create [a-zA-Z_$]+[\\w_$]* *$";
    private static final String ADD_PATT = "";
    private static final String DIFF_PATT = "";
    private static final String AND_PATT = "";
    private static final String DEL_N_PATT = "";
    private static final String DEL_ALL_PATT = "";
    protected static Logger log;
    public static Configuration conf;
    List<Integer> a, b, res;
    String d;


    public static void main(String[] args) {
        log = Logger.getGlobal();
        try {
            conf = new Configuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommandWorker cw = new CommandWorker();

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
        if (checkWithRegExp(line, CREATE_PATT) != null) {
            //cw.createArray();
        } else if (checkWithRegExp(line, ADD_PATT) != null) {
            //cw.addIntoArray(this.a, 8);
        } else if (checkWithRegExp(line, DIFF_PATT) != null) {
            //cw.findArraysDiff();
        } else if (checkWithRegExp(line, AND_PATT) != null) {
            //cw.findArraysCommon();
        } else if (checkWithRegExp(line, DEL_N_PATT) != null) {
            //cw.delElemsArray();
        } else if (checkWithRegExp(line, DEL_ALL_PATT) != null) {
            //cw.delNumberOfLinesInArray();
        } else {
            log.info("Unable to parse command");
        }
    }

    public static String checkWithRegExp(String str, String pattStr) {
        Pattern p = Pattern.compile(pattStr);
        Matcher m = p.matcher(str);
        m.matches();
        System.out.println(m.matches());
        String parsedLine = m.group();
        System.out.println(m.group());
        System.out.println(parsedLine);
        return parsedLine;
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
