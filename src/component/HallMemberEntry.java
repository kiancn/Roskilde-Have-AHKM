package component;

import utility.Grouper;
import utility.TO;

import java.util.ArrayList;

/// I believe the area where they store less adult people for later retrieval is called a Hall
// (dansk: en stue)
/// Class instances represent a link between a personID and a hall-name.
public class HallMemberEntry implements ICSV
{
    private String _nameOfHall;
    private int _personID;

    public HallMemberEntry(String nameOfHall, int personID)
    {
        this._nameOfHall = nameOfHall;
        this._personID = personID;
    }

    public String getNameOfHall(){ return _nameOfHall; }

    public void setNameOfHall(String nameOfHall){ this._nameOfHall = nameOfHall; }

    public int getPersonID(){ return _personID; }

    public void setPersonID(int _personID){ this._personID = _personID; }

    public void setPersonID(Person person){ _personID = person.getID(); }


    public String toCSV(){ return _nameOfHall + "," + _personID; }

    /// Method returns a HallMemberEntity formed from supplied String.
    public static HallMemberEntry fromCSV(String csvString)
    {
        HallMemberEntry result = null;
        ArrayList<String> parts = Grouper.splitStringAsCSV(csvString, ",", 2);

        try // trying because parseInt might throw (though it doesn't tell you)
        {

            String nameOfHall;
            int personID;
            if((nameOfHall = parts.get(0)) != null
               && ((personID = Integer.parseInt(parts.get(1))) != Integer.MIN_VALUE))
            {
                result = new HallMemberEntry(nameOfHall, personID);
            }

        } catch(NumberFormatException nfe)
        {
            System.out.println("A number format exception happened.\n" +
                               TO.yellow(nfe.getMessage()));
        }
        if(result == null)
        {
            System.out.println("There seems to have been an error in supplied Hall member entry.\n" +
                               "A number format exception happened. Dud hall member returned.");
            /// code only reaches here if code was broken
            result = emptyHallMember();
        }

        return result;
    }

    /// Method returns a new 'empty' hall member. For times of crisis.
    public static HallMemberEntry emptyHallMember(){return new HallMemberEntry("N/A", -1);}
}
