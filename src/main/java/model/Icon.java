package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Icon {
    NORMAL,
    COUNTER,
    CONTINUOUS,
    QUICK_PLAY,
    FIELD,
    EQUIP,
    RITUAL;
    public String getNamePascalCase() {
        String name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        Pattern pattern = Pattern.compile("_([a-z])[a-z]+");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find())
            name = name.replace("_" + matcher.group(1), "_" + matcher.group(1).toUpperCase());
        name = name.replaceAll("_", " ");
        return name;
    }
}
