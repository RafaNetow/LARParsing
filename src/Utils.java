

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{

    /**
     * Default constructor class
     */
    public Utils()
    {}

   
    public String getCharFromString(int i, String s)
    {
        return s.substring(i, i + 1);
    }


    public boolean isUpperCase(String str)
    {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher m = pattern.matcher(str.substring(0,1));

        return m.matches();
    }

}
