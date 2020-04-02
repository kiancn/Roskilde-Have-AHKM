package menus;

import component.Contact;
import component.Person;
import enums.Role;
import menus.crud.ContactCRUD;
import menus.crud.PersonCRUD;
import menus.crud.PhoneNumberCRUD;
import utility.GET;

import java.util.LinkedList;

public class ContactsMenu extends Menu
{
    private ContactCRUD _contactCRUD;
    private PersonCRUD _personCRUD;
    private PhoneNumberCRUD _numberCRUD;

    public ContactsMenu()
    {
        this("Håndtér kontakter.");
        _contactCRUD = new ContactCRUD();
        _personCRUD = new PersonCRUD();
        _numberCRUD = new PhoneNumberCRUD();
    }

    private ContactsMenu(String selfDescription)
    {
        super(selfDescription);
    }


    /**
     * Method signifies immediately activating/showing the 'main access' menu
     */
    @Override
    public void show()
    {
        int choice = -1;

        while(choice != 0)
        {
            System.out.println("Du kan nu håndtere kontakter.");

            choice = GET.getInteger("\tVil du?\n" +
                                    "\t[1] Skabe kontakt.\n" +
                                    "\t[2] Finde kontakt.\n" +
                                    "\t[3] Slette kontakt.\n" +
                                    "\t[4] Kontakt liste\n" +
                                    "\t[0] Gå tilbage.\n\t|> ");

            switch(choice)
            {
                case 1:
                    createContact_Menu();
                    break;
                case 2:
                    findContact_Menu();
                    break;
                case 3:
                    deleteContactsForID_Menu();
                    break;
                case 4:
                    printContactsForAllOfRequestedRole();
                    break;
                default:
                    break;
            }
        }
    }

    private void findContact_Menu()
    {
        int choice = -1;

        while(choice != 0)
        {

            choice = GET.getInteger("Vil du søge efter kontakt-informationer pr. id eller name?\n" +
                                    "Søg per\t[1] id \t [2] navn \t [0] Gå tilbage\n" +
                                    "\t|> ");
            switch(choice)
            {
                case 1:
                    printContactsByID_Menu();
                    break;
                case 2:
                    printContactsForName_Menu();
                    break;
                default:
                    break;
            }
        }
    }

    public void printContactsByID_Menu()
    {
        System.out.println("Du kan nu søge efter kontakter til en person.\n");

        int idToSeachFor = GET.getInteger("Hvilket ID leder du efter?\n");

        LinkedList<String> contactsString = _contactCRUD.getContactStringsForID(idToSeachFor);
        LinkedList<Contact> contacts = _contactCRUD.findContactsForID(idToSeachFor);

        printFoundContacts(contactsString, contacts);
    }

    public void printContactsForName_Menu()
    {
        LinkedList<Person> matchingPrimaryPersons = searchForPrimaryPersonsByName();

        // iterating through returned matches, finding
        // contacts for each primary person
        for(Person primary : matchingPrimaryPersons)
        {
            LinkedList<String> contactsStrings = _contactCRUD.getContactStringsForID(primary.getID());
            LinkedList<Contact> contacts = _contactCRUD.findContactsForID(primary.getID());

            printFoundContacts(contactsStrings, contacts);
        }
    }

    private void printFoundContacts(LinkedList<String> contactsString, LinkedList<Contact> contacts)
    {
        if(contactsString.size() == 0)
        {
            /// the print turned out to be disturbing.
            //  System.out.println("Der blev ikke fundet nogen kontakter til det id.\n" +
            //                     "Note: der søges kun på det primære kontakt id.");

        } else
        {
            System.out.println("\n" + contactsString.get(0));
            contactsString.remove(0);

            for(int i = 0; i < contactsString.size() && i < contacts.size(); i++)
            {
                System.out.println(contactsString.get(i));
                System.out.println("Telefon nummer:\t" +
                                   _numberCRUD.getPhoneNumberByID(contacts.get(i).getContactID()).getPhoneNumber());
            }
        }
    }

    /**
     * Method returns a list of Persons matching user entered search criteria.
     * Menu method that wraps the findPersonsByName() in a user friendly format
     * and returns a linked-list of Persons.
     */
    private LinkedList<Person> searchForPrimaryPersonsByName()
    {

        System.out.println("Du kan nu søge på en person per navn.");

        String primaryName = GET.getString("Hvilket navn vil du søge på?\t");

        int userChoice = GET.getInteger("Vil du søge på fuldt eller delvist match?\n" +
                                        "\t[1] Delvist match\t [2] Fuldt match\n\t|> ");

        // if user entered 1, do search including partial matches; else include only full matches
        LinkedList<Person> matchingPrimaryPersons =
                new LinkedList<>(_personCRUD.findPersonsByName(primaryName, userChoice == 1));

        if(matchingPrimaryPersons.size() < 1)
        {
            System.out.println("Ingen matches fundet på navnet");
        }

        return matchingPrimaryPersons;
    }


    /**
     *
     */
    public void createContact_Menu()
    {

        System.out.println("Du kan nu skabe en kontakt mellem to eksisterende Personer.");

        boolean stopChecking = false;

        // while choice != 0, continue activity
        while(!stopChecking)
        {
            // finder 2 id'er, der kan forbindes
            int idOfPrimary = GET.getInteger("Indtast den primære persons id:\n\t|> ");
            Person primaryP = _personCRUD.findPersonByID(idOfPrimary);
            System.out.println(primaryP);


            int idOfContact = GET.getInteger("Indtast kontakt-persons id:\n\t|> ");
            Person contactP = _personCRUD.findPersonByID(idOfContact);
            System.out.println(contactP);

            // hvis et invalidt id er blevet givet, så vil navnet på en returneret person være "Ikke angivet"
            // id'et kan ikke bruges her fordi id-auto-genereres, også for 'tomme personer'
            if(!primaryP.getName().contentEquals("Ikke angivet") &&
               !contactP.getName().contentEquals("Ikke angivet"))
            {

                // addContactToPersistentList returnerer true, hvis alt gik godt.
                stopChecking = _contactCRUD.addContactToPersistentList(new Contact(primaryP.getID(), contactP.getID()));
                if(stopChecking)
                {
                    System.out.println("Success: " + contactP.getName() + " er nu kontakt til " + primaryP.getName());
                }
            }

            // hvis der var bøvl med idéerne gives bruges mulighed for at søge igen eller baile.
            if(!stopChecking)
            {
                stopChecking = !GET.getConfirmation(
                        "Det lykkedes ikke at tilføje en kontakt.\n" +
                        "Et eller flere af de søgte id'er gav intet resultat" +
                        "Vil du prøve igen?\t ja / nej\n" +
                        "\t|> ", "ja", "nej");
            }
        }
    }

    private void deleteContactsForID_Menu()
    {
        System.out.println("Du kan nu slette kontakter.");

        int id = GET.getInteger("Indtast ID der skal fjernes kontakter til:\n\t|> ");

        LinkedList<Contact> contacts = _contactCRUD.findContactsInvolvingID(id);

        if(contacts.size() == 0)
        {
            System.out.println("Ingen kontakter fandtes ved søgning på id " + id);
            return;
        }
        // Brugeren kan entry efter entry afgøre om et Contact objekt skal slettes
        for(Contact contact : contacts)
        {
            System.out.println("Fandt kontakt mellem:\n" +
                               contact.getPrimaryID() + " " + _personCRUD.findPersonByID(contact.getPrimaryID()) + " og \n" +
                               contact.getContactID() + " " + _personCRUD.findPersonByID(contact.getContactID()));

            if(GET.getConfirmation(
                    "Vil du slette denne kontakt?\t ja / nej\n\t|>",
                    "ja", "nej"))
            {
                if(_contactCRUD.removeContactFromPersistentList(contact))
                {
                    System.out.println("Kontakten er slettet.");
                } else
                {
                    System.out.println("Det var ikke muligt at slette kontakten");
                }
            }
        }
    }

    private void printContactsForAllOfRequestedRole()
    {

        System.out.println("Du kan nu få printet en liste over alle af en bestemt rolle:");

        LinkedList<Person> personsWithRole;

        switch(GET.getInteger("[1]\tOptagne børn" +
                              "\n\t[2]\tVenteliste børn\n\t|> "))
        {
            case 1:
                personsWithRole = _personCRUD.findPersonsByRole(Role.Kid);
                break;
            // case 2 does not work, because the waitinglist holds the reference between a waiting kid and
            // an adult; this needs fixing
            case 2:
                personsWithRole = _personCRUD.findPersonsByRole(Role.WaitingList);
                break;
            default:
                personsWithRole = new LinkedList<>();
                break;
        }

        if(personsWithRole.size() > 0)
        {
            for(Person primaryPerson : personsWithRole)
            {
                LinkedList<Contact> contacts = _contactCRUD.findContactsForID(primaryPerson.getID());
                LinkedList<String> contactStrings =
                        _contactCRUD.getContactStringsForID(primaryPerson.getID());

                printFoundContacts(contactStrings, contacts);
            }
        }

    }

}
