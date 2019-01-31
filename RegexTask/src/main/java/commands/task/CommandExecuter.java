package commands.task;

import java.io.FileWriter;
import java.io.IOException;
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

    private CollectionCommandHandler comHandler;
    FileWriter fw;

    public CommandExecuter(FileWriter fw) {
        this.fw = fw;
        this.comHandler = new CollectionCommandHandler();
    }

    public void doCommandsWithLines(List<String> allLines) {
        int linesCount = allLines.size();
        for (int i = 0; i < linesCount; i++) {
            String line = allLines.get(i);
            Matcher m = CREATE_PATT.matcher(line);
            if (m.matches()) {
                comHandler.createCollection(m.group("coll1"));
            } else {
                m = ADD_PATT.matcher(line);
                if (m.matches()) {
                    comHandler.addIntoCollection(m.group("coll1"), Integer.parseInt(m.group("num")));
                } else {
                    m = DIFF_PATT.matcher(line);
                    if (m.matches()) {
                        Set<Integer> resSet = comHandler.findCollectionsDiff(m.group("coll1"), m.group("coll2"));
                        writeSetToFile("diff result", resSet);
                    } else {
                        m = AND_PATT.matcher(line);
                        if (m.matches()) {
                            Set<Integer> resSet = comHandler.findCollectionsCommon(m.group("coll1"), m.group("coll2"));
                            writeSetToFile("and result", resSet);
                        } else {
                            m = DEL_N_PATT.matcher(line);
                            if (m.matches()) {
                                Set<Integer> resSet = comHandler.delNumberOfLinesFromCollection(m.group("coll1"), Integer.parseInt(m.group("num")));
                                writeSetToFile("del num result", resSet);
                            } else {
                                m = DEL_ALL_PATT.matcher(line);
                                if (m.matches()) {
                                    Set<Integer> resSet = comHandler.delElemsFromCollection(m.group("coll1"));
                                    writeSetToFile("del all result", resSet);
                                } else {
                                    log.info("Unable to parse command");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeSetToFile(String comment, Set<Integer> lines) {
        try {
            fw.write(comment + ": ");
            for (int elem : lines) {
                fw.write(elem + " ");
            }
            fw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
