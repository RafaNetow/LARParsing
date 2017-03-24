/**
 * This class contains a set of general utility functions.
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{

    /**
     * Default constructor class
     */
    public Utils()
    {}

    /**
     * Take the char in position "i" from the string "s"
     * @param i
     * @param s
     * @return
     */
    public String getCharFromString(int i, String s)
    {
        return s.substring(i, i + 1);
    }

    /**
     * Che if in "str" there is an upper case
     * @param str
     * @return
     */
    public boolean isUpperCase(String str)
    {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher m = pattern.matcher(str.substring(0,1));

        return m.matches();
    }

}
