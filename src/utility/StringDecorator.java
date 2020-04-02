package utility;
/** This class exists to create and decorate strings */
public class StringDecorator
{

    /**
     * Returns the right ordinal indicator for any integer,
     * which is:
     * <p>X1 returns "st"</p>
     * <p>X2 returns "nd"</p>
     * <p>X3 returns "rd"</p>
     * <p>X4 returns "th"</p>
     * https://en.wikipedia.org/wiki/Ordinal_indicator
     */
    public static String getOrdinalIndicator(int number){

        String numberString = String.valueOf(number);
        /* Getting last digit in numberString */
        String lastDigitString = Character.toString(numberString.charAt(numberString.length()-1));
        /* turning that string-number into a number number */
        int lastDigit = Integer.parseInt(lastDigitString);

        String ordinalIndicator;

        /* Switch casing for the win/right indicator */
        switch(lastDigit){
            case 1:
                ordinalIndicator = "st";
                break;
            case 2:
                ordinalIndicator = "nd";
                break;
            case 3:
                ordinalIndicator = "rd";
                break;
            default:
                ordinalIndicator = "th";
                break;

        }
        return ordinalIndicator;
    }
}
