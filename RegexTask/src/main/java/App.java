import commands.task.CommandExecuter;
import configuration.Configuration;
import html.task.HtmlParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class App {
    public static Configuration conf;

    public static void main(String[] args) {
        Logger log = Logger.getLogger(App.class.getName());
        try {
            conf = new Configuration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path sourcePath = Paths.get(conf.getProperty("source.file.path"));
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
        log.info("First task \"Commands with collections\" finished");


        sourcePath = Paths.get(conf.getProperty("html.file.path"));
        String htmlLines = null;
        try {
            htmlLines = Files.lines(sourcePath, StandardCharsets.UTF_8).collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        resFile = new File(conf.getProperty("html.output.file.path"));
        try (FileWriter fw = new FileWriter(resFile)) {
            if ((htmlLines != null) && (!htmlLines.isEmpty())) {
                HtmlParser htmlParser = new HtmlParser(fw);
                htmlParser.parseHtml(htmlLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Second task \"Html parsing\" finished");

    }


}


