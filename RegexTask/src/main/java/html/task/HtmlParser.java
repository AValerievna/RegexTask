package html.task;

import java.util.List;
import java.util.regex.Pattern;

public class HtmlParser {
    private static final Pattern FORM_PATT = Pattern.compile("<form.*?<\\/form>", Pattern.DOTALL);

    public void parseHtml(List<String> allLines) {
        for (String line : allLines) {


        }

    }
}
