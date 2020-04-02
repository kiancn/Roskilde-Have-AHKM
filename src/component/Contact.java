package component;

import utility.Grouper;
import utility.TO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contacts represent a contact point to an adult for a kid in the institution.
 * To lessen possible confusion; the phone list is completely seperate from the contacts list
 */
public class Contact implements ICSV
{
    private int _primaryID; // normally, kids would be primary
    private int _contactID; // adults, the contact

    public Contact(int primaryID, int contactID)
    {
        _primaryID = primaryID;
        _contactID = contactID;
    }

    public static Contact fromCSV(String csvString)
    {
        Contact result = null;

        ArrayList<String> csvs = Grouper.splitStringAsCSV(csvString, ",", 2);

        try
        {
            int kidID = Integer.parseInt(csvs.get(0));
            int contactID = Integer.parseInt(csvs.get(1));

            result = new Contact(kidID, contactID);

        } catch(NumberFormatException e)
        {
            System.out.println("An exception occurred while converting supplied CSV string to ID numbers."
                               + TO.yellow(e.getMessage()));
            result = returnEmptyContact();
        }
        return result;
    }

    public static Contact returnEmptyContact(){ return new Contact(-1, -1); }

    @Override
    public String toCSV(){ return _primaryID + "," + _contactID; }

    public int getPrimaryID(){ return _primaryID; }

    public int getContactID(){ return _contactID; }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Contact)) return false;
        Contact contact = (Contact)o;
        return _primaryID == contact._primaryID &&
               _contactID == contact._contactID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(_primaryID, _contactID);
    }
}
