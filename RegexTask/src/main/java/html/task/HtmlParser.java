package html.task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    private static final String NEW_LINE = "\n";
    private FileWriter fw;
    private Logger log;
    private static final Pattern FORM_PATT = Pattern.compile("<form.*?id=\"index_login_form\".*?</form>", Pattern.DOTALL);
    private static final Pattern ACT_PATT = Pattern.compile("<form.*?action=\"(?<ref>.*?)\".*?>", Pattern.DOTALL);
    private static final Pattern INPUT_PATT = Pattern.compile("<input.*?value=\"(?<val>.*?)\".*?>", Pattern.DOTALL);

    public HtmlParser(FileWriter fw) {
        this.fw = fw;
        log = Logger.getLogger(HtmlParser.class.getName());
    }

    public void parseHtml(String line) throws IOException {
        Matcher m = FORM_PATT.matcher(line);
        if (m.find()) {
            String formInnerHtml = m.group();
            fw.write("Form content: " + formInnerHtml + NEW_LINE);
            m = ACT_PATT.matcher(formInnerHtml);
            if (m.find()) {
                String actionRef = m.group("ref");
                fw.write("Action ref: " + actionRef + NEW_LINE);
                m = INPUT_PATT.matcher(formInnerHtml);
                LinkedHashSet<String> inputValues = new LinkedHashSet<>();
                while (m.find()) {
                    inputValues.add(m.group("val"));
                }
                if (!inputValues.isEmpty()) {
                    fw.write("Input values: " + inputValues.toString() + NEW_LINE);
                } else {
                    log.info("There is no input-values in html line");
                }
            } else {
                log.info("There is no action-tag in html line");
            }
        } else {
            log.info("There is no <form> in html line");
        }

    }
}
