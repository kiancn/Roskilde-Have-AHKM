

package utility;

/** Text Ornaments - TO
 * Klassen tilbyder en række metoder til at pynte tekst.
 * Vær opmærksom på at refreshTerminal() kun virker i nogle consoller (ex. repl.it men ikke IntelliJ)
 */
public class TO
{
    /**
     * VARIABLES
     **/
    /* Farver!! :D */
    /* disse strings er ANSI escape codes; kort forklaring: strengene fortolkes som farve-koder.
     * ANSI_RESET afslører hvad der egentlig sker; det er kommandoer til terminalen / 'System.out' ..
     * Referencer nederst i Main */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BBLUE = "\u001B[94m";

    public static final String ANSI_U = "\u001B[4m"; /* underline */
    public static final String ANSI_ALTFONT1 = "\u001B[11m";
    public static final String ANSI_ITALIC = "\u001B[3m";


    public static final String ANSI_CLS = "\u001b[2J";
    public static final String ANSI_CLS_ALT = "\033[H\033[2J";
    public static final String ANSI_HOME = "\u001b[H";



    public static final String ANSI_BCKSPC = "\u0008"; /* works! */
    public static final String ANSI_TAB = "\u0009"; /* horizontal tabulation, a single tab action */
    /*public static final String ANSI_NEWLINE = "\u000A"; *//* Illegal :D :D :D*/
    public static final String ANSI_VTAB = "\u000B"; /* vertical tabulation, a single tab action */ /* !! terminal unable to interpret !! */
    public static final String ANSI_FORMFEED = "\u000C"; /* don't know what might do, gives 'uninterpreted char' */
    public static final String ANSI_CARRIAGERETURN = "\\u000D"; /* Illegal :D :D :D*/



    /** METHODS **/

    /**
     * Direct Terminal Command Methods
     **/

    public static void refreshTerminal() {
        System.out.print(ANSI_CLS + ANSI_HOME);
    }
    public static void refreshTerminal_Alt() {
        System.out.print(ANSI_CLS_ALT);
    }

    /**
     * String augmenting methods
     **/

    /* method returns input string in colour  */
    private static String modifyString(String userInput, String aNSI_CODE) {
        return String.join("", aNSI_CODE, userInput, ANSI_RESET);
    }

    /* method returns input string in colour  */
    public static String blueDark(String input) {
        return modifyString(input, ANSI_BLUE);
    }

    /* method returns input string in colour  */
    public static String black(String input) {
        return modifyString(input, ANSI_BLACK);
    }

    /* method returns input string in colour  */
    public static String purple(String input) {
        return modifyString(input, ANSI_PURPLE);
    }

    /* method returns input string in colour  */
    public static String red(String input) {
        return modifyString(input, ANSI_RED);
    }

    /* method returns input string in colour  */
    public static String green(String input) {
        return modifyString(input, ANSI_GREEN);
    }

    /* method returns input string in colour  */
    public static String yellow(String input) {
        return modifyString(input, ANSI_YELLOW);
    }

    /* method returns input string in colour blue */
    public static String blue(String input) {
        return modifyString(input, ANSI_BBLUE);
    }

    /* method returns input string in colour underlined */
    public static String underline(String input) {
        return modifyString(input, ANSI_U);
    }

    /* method returns input string in italic style, if available */
    public static String italic(String input) {
        return modifyString(input, ANSI_ITALIC);
    }


    /* returns a red exclamation mark */
    public static String redExclamation() {
        return ANSI_RED + "!" + ANSI_RESET;
    }

}

/*
 * https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...)
 * https://www.alexjamesbrown.com/blog/development/c-string-concat-vs-string-join/ - on String.join String.concat
 */
