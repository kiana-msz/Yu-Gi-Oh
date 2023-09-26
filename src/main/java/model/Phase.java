package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Phase {
    DRAW_PHASE,
    STANDBY_PHASE,
    MAIN_PHASE1,
    BATTLE_PHASE,
    MAIN_PHASE2,
    END_PHASE;

    public String getNamePascalCase() {
        String name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        Pattern pattern = Pattern.compile("_([a-z])[a-z]+");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find())
            name = name.replace(matcher.group(1), matcher.group(1).toUpperCase());
        name = name.replaceAll("_", " ");
        return name;
    }
}
