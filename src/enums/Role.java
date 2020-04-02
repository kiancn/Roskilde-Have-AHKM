package enums;


/**
 * The Role enum describes a Persons role is the club.
 * Der er 2. koncepter
 * 1) en Person logger på, og alt efter sin Role, så ser vedkommende
 * forskellige menuer, dvs får adgang til forskellig funktionalitet.
 * <p>Derudover</p>
 */
public enum Role
{
    Leader,Staff,Adult,Kid,WaitingList,Banned,NoRole;


    /* Returns Junior role if age less than 18, Senior if above. */
    public static Role getMemberAgeBracket(int age)
    {
        return (age < 18) ? Adult : Kid;
    }



}
