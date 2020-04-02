package utility;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class will contain static utility methods to organize data:
 */
public class Grouper
{
    /** Returns list containing content of supplied string split
     * into as many parts as intended.
     * @param stringToSplit string to split
     * @param separatorString content will be split by seperator supplied
     * @param intendedPartCount number of elements to expect when splitting
     * string; an empty array will be returned if a number of parts other
     * than the expected number is found; supply -1 to return any number of parts */
    public static ArrayList<String> splitStringAsCSV(String stringToSplit,
                                                     String separatorString,
                                                     int intendedPartCount)
    {

        ArrayList<String> splitStrings = new ArrayList<>();

        if(stringToSplit.length() > 1)
        {
            String[] tempStrings = stringToSplit.split("\\s*" + separatorString + "\\s*");

            if(tempStrings.length == intendedPartCount || intendedPartCount == -1)
            {
                for(String string : tempStrings)
                {
                    splitStrings.add(string);
                }
            }
        }

        /* return a single place, empty array */
        return splitStrings;
    }

    /**
     * Method splits supplied string into length intervals of maxLineLength
     * taking care not to split words, and returns the split parts as an
     *
     */
    public static ArrayList<String> splitStringToLines(String stringToSplit, int maxLineLength)
    {
        /* Arraylist will hold all part strings */
        ArrayList<String> listOfLines = new ArrayList<>();
        /* Creating pattern that will split string */
        Pattern pattern = Pattern.compile("\\b.{1," + (maxLineLength - 1) + "}\\b\\W?");
        /* A Matcher is then created; with it a supplied string can be traversed
         * matching the patterns supplied.*/
        Matcher matcher = pattern.matcher(stringToSplit.trim());

        /* Traversing string, adding each line to list of lines */
        while(matcher.find())
        {
            /* group returns pattern-matched substring of string to split; a line. line is added to list. */
            listOfLines.add(matcher.group());
        }

        return listOfLines;
    }

}

/*
 *
 * https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/regex/Pattern.html
 * https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/regex/Matcher.html
 * The pattern to split string into lines was learned from:
 * http://www.davismol.net/2015/02/03/java-how-to-split-a-string-into-fixed-length-rows-without-breaking-the-words/
 * */
