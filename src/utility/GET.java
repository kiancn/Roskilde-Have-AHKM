package utility;

import java.util.Scanner;

/**
 * GET klassen indeholder statiske metoder til at hente data fra brugeren via consollen.
 * <p></p>DELFIN-NOTE: Den har klasse er bare så enormt nyttigt at jeg hiver den med fra projekt til projekt.
 * ... første udgave blev til under car-wash-projektet.
 */
public class GET
{
    private static final String[] dontJustPressEnterStrings = {
            "Du kom vist til bare at trykke Enter. Indtast en streng, tak.",
            "Ja, det dur ikke, når du bare trykker Enter. Indtast noget.",
            "Nej. Du skal bruge bogstaverne på tastaturet til at udtrykke dig med.",
            "Det kan være en anden skal prøve?",
            "Indtast nu en streng.",
            "Sidste chance!",
            "Douche"
    };

    /**
     * Method returns an integer by querying the user
     */
    public static int getInteger()
    {
        return getInteger("Indtast et heltal: ");
    }

    /**
     * Method returns an integer by querying the user; parameter presented as user-message
     */
    public static int getInteger(String queryString)
    {
        Scanner scanner;
        int enteredInt = -1;
        /*user entered integer, initialized to -1 to */
        boolean integerDetected = false;
        /*boolean registers if an int was entered */
        /*loop runs while integer is not detected*/
        while(!integerDetected)
        {
            try
            {
                System.out.print("\n\t" + queryString + "");
                /*take user input */
                scanner = new Scanner(System.in);
                /*read next int from scanner object*/
                /*AKA attempt to get entered int */
                enteredInt = scanner.nextInt();
                /*code only reaches this point if what was entered was an int*/
                /*adjustment makes the while loop end*/
                integerDetected = true;

            } catch(Exception e)
            {
                /*catch exception*/
                System.out.println("\n\tDu indtastede noget, der ikke er et heltal.");
            }
        }
        return enteredInt;
    }

    /**
     * Method asks the user to make a choice:
     *
     * @param queryText          text printed in console (your request-text).
     * @param confirmationString string accepted from user as a 'yes'/
     *                           <p> It is possible to </p>
     */
    public static boolean getConfirmation(String queryText, String confirmationString, String negationString)
    {
        String tempText = queryText.equals("") ? "Confirm:\ty / n " : queryText;

        Scanner scanner; // scanner to obtain input from user
        String inputString; // where user input is stored;
        boolean verificationObtained = false;

        /* this while loop will run as long as user has not verified choice; deadlock is never good though */
        while(!verificationObtained)
        {
            System.out.print(tempText);
            scanner = new Scanner(System.in);
            inputString = scanner.nextLine();
//            scanner.close();
            if(inputString.equals((confirmationString == "") ? "y" : confirmationString)){ return true; }
            if(inputString.equals((negationString == "") ? "n" : negationString)){ return false; }
        }
        // code should never reach here; if so, return false;
        return false;
    }

    public static boolean getConfirmation()
    {
        return getConfirmation("", "", "");
    }

    /**
     * Method prints messageString to the console, and halts the program until enter is pressed.
     *
     * @param messageString text printed in console, so that the user understands
     */
    public static void enterToContinue(String messageString)
    {
        System.out.println(messageString);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /** Method stops execution of program and wait for user to press enter.
     * <p>- meant for lazy moments and debugging</p>*/
    public static void enterToContinue()
    {
        enterToContinue("Press enter to continue ...");
    }

    /**
     * Returns a String from keyboard after printing a helpful message (queryString) to console.
     * <p>- By default the console input-cursor is on the same line as the printed message.</p>
     *<p><b>Method does not take blank for an answer.</b></p>
     * @param queryString is printed in console, to help user understand.
     */
    public static String getString(String queryString)
    {
        String returnString = "";

        Scanner scanner = new Scanner(System.in);
        /* stringObtained set to true here means that while-loop will run at least once */
        boolean stringObtained = false;

        int timesUserPressedEnter = 0;

        while(!stringObtained)
        {
            System.out.print("\n\t"+ queryString); // print message to console
            returnString = scanner.nextLine(); // get line from keyboard
            /* checking if user input anything (but user does get to return 'string numbers', beware)*/
            if(returnString.length() < 1) // 0-characters don't get to count as a real string
            {
                stringObtained = false;
                System.out.println(dontJustPressEnterStrings[timesUserPressedEnter]);
                timesUserPressedEnter++;
                if(timesUserPressedEnter == dontJustPressEnterStrings.length - 1)
                {
                    /* if user insists on pressing enter, the last string in the array is returned*/
                    return dontJustPressEnterStrings[dontJustPressEnterStrings.length - 1];
                }
            } else{stringObtained = true;}
        }

        return returnString;
    }



}
