package Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commands {
    private String regex;
    public Commands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(input);
    }

}
