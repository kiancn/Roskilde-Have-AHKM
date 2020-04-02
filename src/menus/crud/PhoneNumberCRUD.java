package menus.crud;

import RoskildeHave.RoskildeHaveFileIO;
import component.PhoneNumber;

public class PhoneNumberCRUD
{
    /**
     * Method return true if a phone number object exists in persist lists associated with supplied id
     */
    public boolean checkIfPhoneNumberExistsForID(int id)
    {
        for(PhoneNumber number : RoskildeHaveFileIO.instance().getPhoneNumbers()){
            if(number.getPersonID() == id){
                return true;
            }
        }

        return false;
    }

    public PhoneNumber getPhoneNumberByID(int id)
    {
        for(PhoneNumber phoneNumber : RoskildeHaveFileIO.instance().getPhoneNumbers())
        {
            if(phoneNumber.getPersonID() == id)
            {
                return phoneNumber;
            }
        }
        return PhoneNumber.returnEmptyPhoneNumberEntry();
    }

    public boolean addPhoneNumberToPersistentLists(PhoneNumber newNumber)
    {
        if(!RoskildeHaveFileIO.instance().getPhoneNumbers().contains(newNumber))
        {
            RoskildeHaveFileIO.instance().getPhoneNumbers().add(newNumber);
            RoskildeHaveFileIO.instance().updateFilesAndReloadLiveLists();
            return true;
        }
        System.out.println("\tAdvarsel; telefonnummeret blev ikke tilføjet,\n" +
                           "\tda nummeret allerede er registeret.");
        return false;
    }
}
