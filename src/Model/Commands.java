package Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commands {
    private String regex;
    private Commands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input,Commands command) {
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(input);
    }

}
