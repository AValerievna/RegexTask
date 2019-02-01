package commands.task;

import java.io.FileWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Utils.writeSetToFile;

public class CommandExecuter {
    private static final Pattern CREATE_PATT = Pattern.compile("^create\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final String COLL1_GROUP = "coll1";
    private static final Pattern ADD_PATT = Pattern.compile("^add\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DIFF_PATT = Pattern.compile("^diff\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<coll2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern AND_PATT = Pattern.compile("^and\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<coll2>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final Pattern DEL_N_PATT = Pattern.compile("^del\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s+(?<num>-?\\d+)\\s*$");
    private static final Pattern DEL_ALL_PATT = Pattern.compile("^del\\s+(?<coll1>[a-zA-Z_$]+[\\w_$]*)\\s*$");
    private static final String NUM_GROUP = "num";
    private static final String COLL2_GROUP = "coll2";
    private static final String DIFF_COMMENT = "diff result";
    private static final String AND_COMMENT = "and result";
    private static final String DEL_NUM_COMMENT = "del num result";
    private static final String DEL_ALL_COMMENT = "del all result";

    private CollectionCommandHandler comHandler;
    private FileWriter fw;
    private Logger log;

    public CommandExecuter(FileWriter fw) {
        this.fw = fw;
        this.comHandler = new CollectionCommandHandler();
        log = Logger.getLogger(CommandExecuter.class.getName());
    }

    public void doCommandsWithLines(List<String> allLines) {
        for (String line : allLines) {
            Matcher m = CREATE_PATT.matcher(line);
            if (m.matches()) {
                comHandler.createCollection(m.group(COLL1_GROUP));
            } else {
                m = ADD_PATT.matcher(line);
                if (m.matches()) {
                    comHandler.addIntoCollection(m.group(COLL1_GROUP), Integer.parseInt(m.group(NUM_GROUP)));
                } else {
                    m = DIFF_PATT.matcher(line);
                    if (m.matches()) {
                        Set<Integer> resSet = comHandler.findCollectionsDiff(m.group(COLL1_GROUP), m.group(COLL2_GROUP));
                        writeSetToFile(fw, DIFF_COMMENT, resSet);
                    } else {
                        m = AND_PATT.matcher(line);
                        if (m.matches()) {
                            Set<Integer> resSet = comHandler.findCollectionsCommon(m.group(COLL1_GROUP), m.group(COLL2_GROUP));
                            writeSetToFile(fw, AND_COMMENT, resSet);
                        } else {
                            m = DEL_N_PATT.matcher(line);
                            if (m.matches()) {
                                Set<Integer> resSet = comHandler.delNumberOfLinesFromCollection(m.group(COLL1_GROUP), Integer.parseInt(m.group(NUM_GROUP)));
                                writeSetToFile(fw, DEL_NUM_COMMENT, resSet);
                            } else {
                                m = DEL_ALL_PATT.matcher(line);
                                if (m.matches()) {
                                    Set<Integer> resSet = comHandler.delElemsFromCollection(m.group(COLL1_GROUP));
                                    writeSetToFile(fw, DEL_ALL_COMMENT, resSet);
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


}
