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
    private static Configuration conf;

    public static void main(String[] args) throws IOException {
        Logger log = Logger.getLogger(App.class.getName());
        conf = new Configuration();

        Path sourcePath = Paths.get(conf.getProperty("source.file.path"));
        List<String> allLines = Files.readAllLines(sourcePath, StandardCharsets.UTF_8);


        File resFile = new File(conf.getProperty("output.file.path"));
        try (FileWriter fw = new FileWriter(resFile)) {
            if (!allLines.isEmpty()) {
                CommandExecuter comExec = new CommandExecuter(fw);
                comExec.doCommandsWithLines(allLines);
            }
        }
        log.info("First task \"Commands with collections\" finished");


        Path htmlSourcePath = Paths.get(conf.getProperty("html.file.path"));
        String htmlLines = Files.lines(htmlSourcePath, StandardCharsets.UTF_8).collect(Collectors.joining());

        File htmlResFile = new File(conf.getProperty("html.output.file.path"));
        try (FileWriter fw = new FileWriter(htmlResFile)) {
            if ((htmlLines != null) && (!htmlLines.isEmpty())) {
                HtmlParser htmlParser = new HtmlParser(fw);
                htmlParser.parseHtml(htmlLines);
            }
        }
        log.info("Second task \"Html parsing\" finished");

    }


}


