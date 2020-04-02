package menus.crud;

import RoskildeHave.RoskildeHaveFileIO;
import component.Person;
import component.PhoneNumber;
import component.WaitingListSpot;
import enums.Role;

import java.util.LinkedList;

public class WaitingListCRUD
{
    private PersonCRUD _personCRUD;
    private PhoneNumberCRUD _phoneCRUD;

    public WaitingListCRUD()
    {

        _personCRUD = new PersonCRUD();
        _phoneCRUD = new PhoneNumberCRUD();
    }

    /**
     * Method returns a WaitingListSpot object where either main or contact id match search
     */
    public WaitingListSpot findWaitingListSpotByID(int id)
    {
        for(WaitingListSpot spot : RoskildeHaveFileIO.instance().getWaitingList())
        {
            if(spot.getApplyingKidID() == id || spot.getContactID() == id)
            {
                return spot;
            }
        }

        // code only reaches here if no spot was found
        return WaitingListSpot.returnEmptyWaitingListSpot();
    }

    public LinkedList<WaitingListSpot> findWaitingListSpotsByName(String nameOfKid)
    {
        LinkedList<WaitingListSpot> results = new LinkedList<>();

        LinkedList<Person> _waitingListKids = new LinkedList<>();

        for(Person person : _personCRUD.findPersonsByName(nameOfKid, true))
        {
            if(person.getRole() == Role.WaitingList)
            {
                _waitingListKids.add(person);
            }
        }


        for(Person waitingPerson : _waitingListKids)
        {
            results.add(findWaitingListSpotByID(waitingPerson.getID()));
        }

        return results;

    }

    public boolean addWaitingListSpotToPersistentLists(WaitingListSpot waitingListSpot)
    {
        if(!RoskildeHaveFileIO.instance().getWaitingList().contains(waitingListSpot))
        {
            RoskildeHaveFileIO.instance().getWaitingList().add(waitingListSpot);
            RoskildeHaveFileIO.instance().updateFilesAndReloadLiveLists();
            return true; // true is returned because purpose was served
        }
        // something went wrong, so return false
        return false;
    }

    public boolean removeWaitListSpotFromPersistentListsByID(int id)
    {
        for(WaitingListSpot spot : RoskildeHaveFileIO.instance().getWaitingList())
        {
            if(spot.getApplyingKidID() == id)
            {
                RoskildeHaveFileIO.instance().getWaitingList().remove(spot);
                return true;
            }
        }

        // code reaches here only if no matching id was found, which was only a procedural success
        return false;
    }

    /**
     * Method returns a descriptive string from supplied object; this functionality
     * is here - and present as toString in the WaitingListSpot class - because it makes
     * extensive use of the fileIO... but maybe it should be
     */
    public String returnPrettyString(WaitingListSpot spot)
    {

        // just fleshing out parts of the computation, to make it easier to read
        Person applyingKid = _personCRUD.findPersonByID(spot.getApplyingKidID());
        Person contactAdult = _personCRUD.findPersonByID(spot.getContactID());
        PhoneNumber contactPhoneNumber = _phoneCRUD.getPhoneNumberByID(contactAdult.getID());

        String result = "Ansøgningsdato: " + spot.getDateOfApplication() +
                        "\nAnsøgende barn:\n" + applyingKid +
                        "\nKontakt:\n" + contactAdult+
                        "\nTelefonnummer:\t" + contactPhoneNumber.getPhoneNumber();

        return result;
    }

}
