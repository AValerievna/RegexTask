package commands.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static commands.task.App.log;

public class CommandExecuter {
    public static final Pattern CREATE_PATT = Pattern.compile("^create\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern ADD_PATT = Pattern.compile("^add\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DIFF_PATT = Pattern.compile("^diff\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<coll2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern AND_PATT = Pattern.compile("^and\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<coll2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern DEL_N_PATT = Pattern.compile("^del\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DEL_ALL_PATT = Pattern.compile("^del\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s*$");

    private CollectionCommandHandler cw;
    private File resFile;

    private void doCommandWithLine(String line) {

        Matcher m = CREATE_PATT.matcher(line);
        if (m.matches()) {
            cw.createCollection(m.group("coll1"));
        } else {
            m = ADD_PATT.matcher(line);
            if (m.matches()) {
                cw.addIntoCollection(m.group("coll1"), Integer.parseInt(m.group("num")));
            } else {
                m = DIFF_PATT.matcher(line);
                if (m.matches()) {
                    LinkedHashSet<Integer> resSet = cw.findCollectionsDiff(m.group("coll1"), m.group("coll2"));
                    writeSetToFile(resFile, resSet);
                } else {
                    m = AND_PATT.matcher(line);
                    if (m.matches()) {
                        Set<Integer> resSet = cw.findCollectionsCommon(m.group("coll1"), m.group("coll2"));
                    } else {
                        m = DEL_N_PATT.matcher(line);
                        if (m.matches()) {
                            cw.delNumberOfLinesFromCollection(m.group("coll1"), Integer.parseInt(m.group("num")));
                        } else {
                            m = DEL_ALL_PATT.matcher(line);
                            if (m.matches()) {
                                cw.delElemsFromCollection(m.group("coll1"));
                            } else {
                                log.info("Unable to parse command");
                            }
                        }
                    }
                }
            }
        }
    }

    public void doCommandsWithLines(List<String> allLines, String resPathStr) {
        int linesCount = 0;
        if (allLines != null) {
            linesCount = allLines.size();
        }

        if (allLines != null) {
            cw = new CollectionCommandHandler();
            resFile = new File(resPathStr);
            for (int i = 0; i < linesCount; i++) {
                doCommandWithLine(allLines.get(i));
            }
        }
    }

    private void writeSetToFile(File sourceFile, LinkedHashSet<String> lines) {
        try (FileWriter fw = new FileWriter(sourceFile)) {
            for (String line : lines) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
