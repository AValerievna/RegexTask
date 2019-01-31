package commands.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class App {
    protected static Logger log;
    public static Configuration conf;

    public static void main(String[] args) {
        log = Logger.getGlobal();
        try {
            conf = new Configuration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sourcePathStr = conf.getProperty("source.file.path");
        Path sourcePath = Paths.get(sourcePathStr);

        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(sourcePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File resFile = new File(conf.getProperty("output.file.path"));
        try (FileWriter fw = new FileWriter(resFile)) {
            if ((allLines != null) && (!allLines.isEmpty())) {
                CommandExecuter comExec = new CommandExecuter(fw);
                comExec.doCommandsWithLines(allLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


