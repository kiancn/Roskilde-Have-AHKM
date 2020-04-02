package enums;
/** Enums registers exactly two genders for athletic purposes and one for Not Registered. */
public enum Gender
{
    Male, Female, NotRegistered;

    /**
     * Returns one of the supplied 'gender-strings', based on Person Gender
     */
    public static String genderString(Gender gender, String female, String male, String genderNotRegistered)
    {
        // switching on gender
        switch(gender)
        {
            case Male:
                return male;
            case Female:
                return female;
            case NotRegistered:
                return genderNotRegistered;
        }
        // this should never happen
        return "furniture"; // flag
    }
}