

import java.util.regex.Pattern;

public class Char
{
    public String character;

    /**
     * Constructor for the class
     * @param str
     */
    public Char(String str)
    {
        if(str.length() > 0)
            this.character = str.substring(0, 1);
        else
            this.character = "";
    }

    public boolean isUpperCase()
    {
        return Pattern.matches("[A-Z]", character);
    }

    public String getCharacter() {
        return character;
    }
}