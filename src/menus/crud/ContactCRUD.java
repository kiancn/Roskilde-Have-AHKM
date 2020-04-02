package menus.crud;

import RoskildeHave.RoskildeHaveFileIO;
import component.Contact;
import utility.TO;

import java.util.LinkedList;


/**
 * Beklager jeg skifter mellem dansk og engelsk; det virker til at min hjerne har
 * behov for dansk i dag.
 * Idéen med disse CRUD-klasser er at den del af data-behandlingen i menuerne,
 * som med fordel kan ske flere steder uafhængigt af hinanden, defineres her.
 * <p>
 * Der er et mantra i software konstruktion, der hedder at man skal adskille
 * business-logik fra præsentations-logik.
 * Den største fordel er her, at vi meget nemmere vil kunne skifte menuerne
 * ud med en anden bruger-præsentation, hvis den centrale data-handling adskilles
 * ordenligt fra menuerne.
 * I sådan et scenario er menuernes funktion:
 * 1. bruger-interaktion
 * 2. præsentation af resultaterne af data-interaktion
 */
public class ContactCRUD
{
    private PersonCRUD personCRUD;

    public ContactCRUD()
    {
        personCRUD = new PersonCRUD();
    }


    public LinkedList<Contact> findContactsForID(int primaryID)
    {
        var results = new LinkedList<Contact>();

        for(Contact contact : RoskildeHaveFileIO.instance().getContacts())
        {
            if(contact.getPrimaryID() == primaryID){results.add(contact);}
        }

        return results;
    }

    /**
     * Method returns a contact from main live list if either
     * primary id or contact id matches.
     */
    public LinkedList<Contact> findContactsInvolvingID(int ID)
    {
        LinkedList<Contact> results = new LinkedList<>();

        for(Contact contact : RoskildeHaveFileIO.instance().getContacts())
        {
            if(contact.getPrimaryID() == ID || contact.getContactID() == ID)
            {
                results.add(contact);
            }
        }
        return results;
    }

    /**
     * Method returns a list of contact information.
     * If contacts were found for contact ID, a line is returned for
     * the primary ID searched for and one line for each contact.
     * The content of each line is the toString() of each Person with matching ID
     * <p>
     * *
     */
    public LinkedList<String> getContactStringsForID(int primaryID)
    {
        LinkedList<Contact> contacts = findContactsForID(primaryID);

        LinkedList<String> results = new LinkedList<>();

        if(contacts.size() == 0) // if there were no rectums, return an empty list
        {
            return results;
        }

        results.add("[Primære] " + personCRUD.findPersonByID(contacts.get(0).getPrimaryID()).toString());

        for(Contact contact : contacts)
        {
            results.add("[Kontakt] " + personCRUD.findPersonByID(contact.getContactID()).toString());
        }

        return results;
    }

    public boolean addContactToPersistentList(Contact newContact)
    {
        // checking that contact does not already exist
        for(Contact contact : RoskildeHaveFileIO.instance().getContacts())
        {
            if(contact.equals(newContact))
            {
                return false; // duplicates not allowed
            }
        }

        RoskildeHaveFileIO.instance().getContacts().add(newContact);
        RoskildeHaveFileIO.instance().updateFilesAndReloadLiveLists();

        return true; // contact was added, oh lucky day - also, true indicated success
    }

    public boolean removeContactFromPersistentList(Contact oldContact)
    {
        // attempt to remove contact from list; true returned if this was possible
        if(RoskildeHaveFileIO.instance().getContacts().remove(oldContact)){
            // then save new list states to file; update file content and reload lists
            RoskildeHaveFileIO.instance().updateFilesAndReloadLiveLists();
            return true;
        }
        // if there was no contact to remove, return false
        return false;
    }

}
