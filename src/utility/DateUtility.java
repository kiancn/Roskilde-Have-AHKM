package utility;

import java.util.ArrayList;

/**
 * Class will only contain static dolphin.utility methods
 */
public class DateUtility
{
    private static final ArrayList<String[]> monthLocalizationLanguages;
    /* If boolean is switched to Danish, month names will be shown in Danish, else English, duh*/
    private static String[] monthsEnglish = {"English",
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};
    private static String[] monthsDanish = {"Dansk",
            "Januar", "Februar", "Marts", "April",
            "Maj", "Juni", "Juli", "August",
            "September", "Oktober", "November", "December"};
    private static String languageLocalization; // String set to Danish by default ()

    static
    {    /* link om static block i bunden */
        monthLocalizationLanguages = new ArrayList<>();
        monthLocalizationLanguages.add(monthsDanish);
        monthLocalizationLanguages.add(monthsEnglish);

        languageLocalization = "Dansk";
    }

    /**
     * Returns a string corresponding to month-number in logical order from 1-12, 12 being December.
     */
    public static String month(int monthNumericValue)
    {
        if(monthNumericValue < 13 && monthNumericValue > 0)
        {
            for(String[] languages : monthLocalizationLanguages)
            {
                if(languages[0].equalsIgnoreCase(languageLocalization))
                {
                    return languages[monthNumericValue];
                }
            }
        }
        return "Marchygustber";
    }

    /**
     * If language name is found in the 0'th place or any of the language arrays, change
     * languageLocalization string - to change the language months are presented in.
     *
     * @param languageToChangeTo Danish and English are hard-coded.
     */
    public static String setLanguageLocalization(String languageToChangeTo)
    {
        for(String[] language : monthLocalizationLanguages)
        {
            if(language[0].equalsIgnoreCase(languageToChangeTo))
            {
                languageLocalization = languageToChangeTo;
                return "Måneder vil nu blive skrevet på" + languageToChangeTo;
            }
        }
        /* code only reaches here if a match for language was not found */
        return languageToChangeTo + " er ikke et sprog vi har i systemet.";
    }
}
/*
 * Static blocks
 * https://www.geeksforgeeks.org/g-fact-79/
 * */