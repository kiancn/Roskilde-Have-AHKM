package menus;

import component.*;
import enums.Gender;
import enums.Role;
import menus.crud.ContactCRUD;
import menus.crud.PersonCRUD;
import menus.crud.PhoneNumberCRUD;
import menus.crud.WaitingListCRUD;
import utility.GET;

public class KidMenu extends Menu
{
    private ContactCRUD _contactCRUD;
    private PersonCRUD _personCRUD;
    private PhoneNumberCRUD _numberCRUD;
    private WaitingListCRUD _waitingListCRUD;
    private PhoneNumberCRUD _phoneNumberCRUD;

    public KidMenu()
    {
        this("Håndtér børn.");
        _contactCRUD = new ContactCRUD();
        _personCRUD = new PersonCRUD();
        _numberCRUD = new PhoneNumberCRUD();
        _waitingListCRUD = new WaitingListCRUD();
        _phoneNumberCRUD = new PhoneNumberCRUD();
    }

    private KidMenu(String selfDescription){super(selfDescription);}

    @Override
    public void show()
    {
        int menuvalg = -1;

        while(menuvalg != 0)
        {
            menuvalg = GET.getInteger("[1]Optag barn\n" +
                                      "\t[2]Opskriv barn\n" +
                                      "\t[3]Søg efter barn\n" +
                                      "\t[4]Redigér barn\n" +
                                      "\t[5]Slet barn\n" +
                                      "\t[0]Tilbage\n" +
                                      "\t|> ");
            switch(menuvalg)
            {
                case 1:
                    optagBarn_Menu();
                    break;
                case 2:
                    signUpAndCreateWaitingListSpot_Menu();
                    break;
                case 3:
                    System.out.println("\t[ Ikke implementeret i prototypen ]"); // eller endnu?
                    break;
                case 4:
                    System.out.println("\t[ Ikke implementeret i prototypen ]"); // eller endnu?
                    break;
                case 5:
                    System.out.println("\t[ Ikke implementeret i prototypen ]"); // eller endnu?
                    break;
                case 0:
                    break;
            }
        }
    }

    private void optagBarn_Menu()
    {

        // find barn

        Person kid = Person.returnBlankPerson();

        boolean rightPersonFound = false;
        while(!rightPersonFound)
        {
            int inputID = GET.getInteger("Indtast ID på barnet der skal optages");

            kid = _personCRUD.findPersonByID(inputID);

            System.out.println("Der blev fundet denne person med dette id:\n" + kid);

            rightPersonFound = GET.getConfirmation("Er dette den rigtige person?" +
                                                   "\n\t ja / nej ", "ja", "nej");
        }

        // ændre barns Role (tavs)
        kid.setRole(Role.Kid);

        // find ventelisteplads (tavs)
        var spot = _waitingListCRUD.findWaitingListSpotByID(kid.getID());
        // oprette kontakt (tavs)

        Contact newContact = new Contact(spot.getApplyingKidID(), spot.getContactID());

        _contactCRUD.addContactToPersistentList(newContact);


        // slette ventelistepladsen (tavs)
        _waitingListCRUD.removeWaitListSpotFromPersistentListsByID(kid.getID());
        // bekræftelse
        System.out.println(_personCRUD.findPersonByID(kid.getID()) + "\nblev optaget i børnehaven.");
        // bekræftelse på oprettelse af Contact object i systemet
        System.out.println("Der er oprettet en ny kontakt i systemet:");
        for(String string : _contactCRUD.getContactStringsForID(kid.getID()))
        {
            System.out.println(string);
        }


    }

    private void signUpAndCreateWaitingListSpot_Menu()
    {
        System.out.println("Du kan nu skrive et barn op på venteliste:" +

                           "\n\t1.\t Angiv kontakt-person (den voksne):");

        /// Maybe the person is already in the system, if so, query by ID
        /// else ask user for all information needed for person - queryForUser
        Person contactPerson = getPersonInformationFromUser(Role.Adult);

        // checking for phonenumber should be next; it exists use already existent phone number
        // else create (usage of old number not implemented)
        PhoneNumber phoneNumber = queryPhoneNumberForID(contactPerson.getID());

        System.out.println("\n\t2.\t Angiv ansøgende barn:");
        Person applyingKid = getPersonInformationFromUser(Role.WaitingList);


        if(contactPerson.getRole() != Role.NoRole && applyingKid.getRole() != Role.NoRole)
        {
            System.out.println("Bekræft venligst at opskrivningen på venteliste af:" +
                               "\n\nBarnet:\t\t" + applyingKid +
                               "\nAnsvarlige:\t" + contactPerson +
                               "Telefonnummer: " + phoneNumber.getPhoneNumber());

            if(GET.getConfirmation("Skriv:\n\tgodkend / afvis\n\t|> ",
                                   "godkend", "afvis"))
            {
                if(writeRegistrationInfoToPersistentLists(contactPerson, phoneNumber, applyingKid))
                {
                    createSaveNewWaitinglistSpot(contactPerson.getID(), applyingKid.getID());
                }
            }
        } else
        {
            System.out.println("Der gik noget galt med registreringen af personerne.\nPrøv igen.");
        }
    }

    private Person getPersonInformationFromUser(Role role)
    {

        Person result; // result is returned

        boolean personFound = false;

        while(!personFound)
        {
            boolean personAlreadyExists = GET.getConfirmation("Findes personen allerede i systemet?\n" +
                                                              "\tja / nej\n" +
                                                              "\t|> ",
                                                              "ja", "nej");
            if(personAlreadyExists)
            {

                int id = GET.getInteger("Hvilket id har personen?\n\t|>");

                if(_personCRUD.checkIfIDExists(id))
                {
                    result = _personCRUD.findPersonByID(id);

                    // hvis der ikke fandtes en person med det id, så returneredes et 'blankt' person object,
                    // hvor ' name = "Ikke angivet" ', derfor:
                    if(!result.getName().contentEquals("Ikke angivet"))
                    {

                        System.out.println(result);

                        return result;
                    }
                } else{ System.out.println("Ingen person fandtes med ID: " + id); }

            } else
            {
                Person tempPerson;

                System.out.println("Du har nu mulighed for at registere en ny person i systemet.\n" +
                                   "\t(laver du en fejl, så kan du undlade at godkende oprettelsen af\n " +
                                   "\tpersonen, og prøve igen.)\n");

                String name = GET.getString("Navn på person:\n\t|> ");


                System.out.println("\n\tIndtast stamdata - Fødselsdag\n");

                int yearOfBirth = GET.getInteger("Føde år:\n\t|> ");
                int monthOfBirth = GET.getInteger("Føde måned:\n\t|> ");
                int dayOfBirth = GET.getInteger("Født dag på måneden:\n\t|> ");

                Date birthDate = new Date(dayOfBirth, monthOfBirth, yearOfBirth);


                System.out.println("\n\tIndtast stamdata - Adresse\n");

                int zip = GET.getInteger("Post Nummer\n\t|> ");
                String streetName = GET.getString("Vejnavn\n\t|> ");
                int houseNumber = GET.getInteger("Hus nummer\n\t|> ");

                Address address = new Address(streetName, houseNumber, zip);


                boolean genderInput = GET.getConfirmation("Personens køn:\n" +
                                                          "\t\t hunkøn [ k ] / hankøn [ m ]" +
                                                          "\n\t|> ",
                                                          "k", "m");

                Gender gender = genderInput ? Gender.Female : Gender.Male;

                tempPerson = new Person(0, name, birthDate, gender, address, role);

                personFound = GET.getConfirmation("Er de indtastede informationer rigtige?\n" +
                                                  "" + tempPerson + "\n" +
                                                  "\nSkriv:\n\t godkend / afvis" +
                                                  "\n\t|> ",
                                                  "godkend", "afvis");

                if(personFound)
                {
                    Person newPerson = new Person(-1, name, birthDate, gender, address, role);
                    System.out.println("Ny person oprettet med ID:\t" + newPerson.getID());
                    return newPerson;
                }
            }

        }

        return Person.returnBlankPerson();
    }

    /**
     * Ask user for phone number
     */
    private PhoneNumber queryPhoneNumberForID(int id)
    {

        // checking if id is associated with a phone number already
        // first check if id exists, then if phone number exists for id; return phone number if found
        if(_personCRUD.checkIfIDExists(id))
        {
            if(_phoneNumberCRUD.checkIfPhoneNumberExistsForID(id))
            {
                System.out.println("Der blev fundet et registreret telefonnumer for" +
                                   "\n\t " + _personCRUD.findPersonByID(id).getName() +
                                   "\n\t tlf." + _phoneNumberCRUD.getPhoneNumberByID(id).getPhoneNumber());

                if(GET.getConfirmation("Vil du bruge dette telefonnummer, eller oprette et nyt?" +
                                       "\nBekræft med:" +
                                       "\n\tbrug / skab\n\t|> ", "brug", "skab"))
                {
                    return _phoneNumberCRUD.getPhoneNumberByID(id);
                }
            }
        }

        String number;
        String comments;

        System.out.println("\n\tIndtast stamdata - Telefonnummer\n");

        do
        {
            number = GET.getString("Indast telefonnummer:\n\t|> ");

        } while(GET.getConfirmation(
                "Er det indtastede nummer " + number + " korrekt?" +
                "\n\t ja / nej\n\t|> ", "nej", "ja"));

        do
        {
            comments = GET.getString("Kommentarer:\n\t|> ");

        } while(GET.getConfirmation("Godkend kommentarer: \n\t\t ja / nej\n\t|> ",
                                    "nej", "ja"));

        return new PhoneNumber(number, id, comments);
    }

    private boolean writeRegistrationInfoToPersistentLists(Person contactPerson, PhoneNumber phoneNumber,
                                                           Person applyingKid)
    {
        System.out.println("Gemmer ny data i systemet.");

        // attempt to add new person to persistent lists; if success, method returns true, thus the structure
        if(_personCRUD.addPersonToPersistentList(contactPerson))
        {
            System.out.println("Har gemt: " + contactPerson);
            // if adult person is registered, it makes sense to register the supplied phonenumber to p. list
            _numberCRUD.addPhoneNumberToPersistentLists(phoneNumber);
            System.out.println("Har gemt telefonnummer: [ID: " + phoneNumber.getPersonID() + "]\ttlf. " + phoneNumber.getPhoneNumber());

            // attempt to add new person to persistent lists; if success, method returns true.. same same
            if(_personCRUD.addPersonToPersistentList(applyingKid))
            {
                System.out.println("Har gemt: " + applyingKid);

                System.out.println("Success! Begge personer føjet til systemet.");

                return true;
            }
        }

        System.out.println("\tDer gik noget galt i registrerings-processen.\n" +
                           "\tPrøv igen. Kontakt system-administratoren hvis\n" +
                           "\tproblemet bliver ved.");

        return false;
    }

    private void createSaveNewWaitinglistSpot(int contactPersonID, int applyingKidID)
    {
        // actual creation of the new waitinglist spot
        WaitingListSpot newSpot = new WaitingListSpot(Date.now(), applyingKidID, contactPersonID);

        // attempt to add new generated waitinglist spot to p. list;
        if(_waitingListCRUD.addWaitingListSpotToPersistentLists(newSpot))
        {
            System.out.println("\n\tNy ventelisteplads er oprettet.\n" +
                               _waitingListCRUD.returnPrettyString(newSpot) + "\n");
        } else
        {
            // using the PersonCRUD methods that search for objects in the persistent lists
            // to verify that the newly generated Persons and Phonenumber have actually been
            // written to lists (while informing user that an error occurred trying to write
            // the new waitinglist spot to persistent lists).
            System.out.println("\n\t Der gik noget galt!\n" +
                               "\t* Begge personer er oprettede i systemet:\n\t" + _personCRUD.findPersonByID(applyingKidID) +
                               "\n\t" + _personCRUD.findPersonByID(contactPersonID) +
                               "\n\ttlf. " + _numberCRUD.getPhoneNumberByID(contactPersonID).getPhoneNumber() +
                               "\n\n\t * Selve venteliste-pladsen er IKKE oprettet. Prøv igen " +
                               "senere,\n\t  og kontakt system-administratoren, hvis problemet " +
                               "bliver ved med at opstå.");
        }
    }

}