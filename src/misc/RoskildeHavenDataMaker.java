package misc;

import component.*;
import enums.Gender;
import enums.Role;
import utility.FileIO;

import java.util.ArrayList;

public class RoskildeHavenDataMaker
{
    private String pathToPersons = "resource/persons.txt";
    private String pathToPhoneNumbers = "resource/phonenumbers.txt";

    private String pathToWaitingList = "resource/waitinglist.txt";
    private String pathToHallMemberEntries = "resource/hallmemberentries.txt";
    private String pathToContacts = "resource/contacts.txt";

    private PersonMaker maker;

    private ArrayList<Person> people;
    private ArrayList<PhoneNumber> phoneNumbers;
    private ArrayList<WaitingListSpot> waitingList;
    private ArrayList<HallMemberEntry> hallMembers;
    private ArrayList<Contact> contacts;

    public RoskildeHavenDataMaker()
    {
        maker = new PersonMaker();

        people = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        waitingList = new ArrayList<>();
        hallMembers = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    public ArrayList<Person> getPeople(){ return people; }

    public ArrayList<PhoneNumber> getPhoneNumbers(){ return phoneNumbers; }

    public ArrayList<WaitingListSpot> getWaitingList(){ return waitingList; }

    public ArrayList<HallMemberEntry> getHallMembers(){ return hallMembers; }

    public ArrayList<Contact> getContacts(){ return contacts; }

    public void createNewRoskildeHaveData()
    {
        // create 1 leader + phone number
        createLeader(people, phoneNumbers);

        // create 12 staff + phone numbers
        createAdultInfo(12, phoneNumbers, people, Role.Staff);

        // create 90 kids: 50 where Role == Kid, and 40 where Role == WaitingList
        ArrayList<Person> enrolledKids = createKids(50, Role.Kid);
        ArrayList<Person> waitingListKids = createKids(40, Role.WaitingList);

        // create 40 adults: to associate with the 40 WaitingList kids
        // create 40 Phone numbers associated with those adults
        createWaitingListData(people, phoneNumbers, waitingList, waitingListKids);

        // create 100 adults/'parents' (to associate with enrolled kids through contacts)
        // create 100 Phone Numbers associated with those adults
        // associate two Contacts with every Person where Role == Kid
        createContactAdultsAndContacts(100, enrolledKids);

        // create 50 HallMemberEntries - lets call the halls "Blå Stue","Rød Stue","Grøn Stue","Gul Stue"
        createHallMemberEntries(enrolledKids);
    }

    private void createHallMemberEntries(ArrayList<Person> enrolledKids)
    {
        int hallCount = 0;
        for(Person kid : enrolledKids)
        {
            String hallName = "";

            switch(hallCount)
            {
                case 0:
                    hallName = "Blå Stue";
                    break;
                case 1:
                    hallName = "Rød Stue";
                    break;
                case 2:
                    hallName = "Grøn Stue";
                    break;
                case 3:
                    hallName = "Gul Stue";
                    break;
                default:
                    hallName = "Ingen Stue"; // JIC

            }

            hallMembers.add(new HallMemberEntry(hallName, kid.getID()));

            hallCount = hallCount + 1 < 4 ? hallCount + 1 : 0;
        }
    }

    private void createContactAdultsAndContacts(int numberOfContacts, ArrayList<Person> enrolledKids)
    {
        ArrayList<Person> contactAdults = maker.createMembers(numberOfContacts, 1975, 25, Role.Adult);

        for(Person person : contactAdults)
        {
            phoneNumbers.add(generateRandomizedPhoneNumber(person.getID(), ""));
        }

        people.addAll(contactAdults);

        int contactAdultCount = 0;
        for(Person person : enrolledKids)
        {
            for(int i = 0; i < 2; i++) // assigning two adults to each kid
            {
                contacts.add(new Contact(person.getID(), contactAdults.get(contactAdultCount).getID()));
                contactAdultCount = (contactAdultCount + 1 < contactAdults.size() ? contactAdultCount + 1 : 0);
            }
        }

        people.addAll(enrolledKids);
    }

    private void createWaitingListData(ArrayList<Person> people,
                                       ArrayList<PhoneNumber> phoneNumbers,
                                       ArrayList<WaitingListSpot> waitingList,
                                       ArrayList<Person> waitingListKids)
    {
        ArrayList<Person> waitingListAssociatedAdults = new ArrayList<>();
        ArrayList<PhoneNumber> waitingListAssociatedAdultsPhoneNumbers = new ArrayList<>();
        createAdultInfo(40,
                        waitingListAssociatedAdultsPhoneNumbers,
                        waitingListAssociatedAdults,
                        Role.Adult);

        // create 40 WaitingListSpots from those 80 individuals

        int adultsIndex = 0; // used for counting through waitingListAssociatedAdults

        for(Person waitingKid : waitingListKids)
        {
            waitingList.add(new WaitingListSpot(Date.now(),
                                                waitingKid.getID(),
                                                waitingListAssociatedAdults.get(adultsIndex).getID()));
            if(adultsIndex < waitingListAssociatedAdults.size()){ adultsIndex++;}
        }

        // adults and waiting kids are now added to the mains lists
        people.addAll(waitingListKids);
        people.addAll(waitingListAssociatedAdults);
        phoneNumbers.addAll(waitingListAssociatedAdultsPhoneNumbers);
    }

    private ArrayList<Person> createKids(int numberOfKids, Role role)
    {
        return maker.createMembers(numberOfKids, 2016, 4, role);
    }

    private void createAdultInfo(int numberOfPersons, ArrayList<PhoneNumber> phoneNumbers,
                                 ArrayList<Person> persons, Role roleOfPerson)
    {
        ArrayList<Person> staff = maker.createMembers(numberOfPersons, 1955, 47, roleOfPerson);

        for(Person person : staff)
        {
            phoneNumbers.add(generateRandomizedPhoneNumber(person.getID(), ""));
        }

        persons.addAll(staff);
    }


    private void createLeader(ArrayList<Person> people, ArrayList<PhoneNumber> phoneNumbers)
    {
        Person leader =
                new Person(RoskildeHaveID.instance().getNewID(),
                           "Sandra Madsen",
                           new Date(14, 2, 1962),
                           Gender.Female,
                           new Address("Fuglehøjen", 23, 2260),
                           Role.Leader);
        people.add(leader);
        PhoneNumber leaderNumber = new PhoneNumber("60452450", leader.getID(),
                                                   "Bedst mellem 8.30 - 16");
        phoneNumbers.add(leaderNumber);
    }

    private PhoneNumber generateRandomizedPhoneNumber(int personID, String comment)
    {
        if(comment == null || comment == ""){comment = "Ingen kommentarer.";}
        // generates are number between 10000000 and 94999999
        String number = String.valueOf(10000000 + (int)(Math.random() * 85000000));

        return new PhoneNumber(number, personID, comment);
    }

    public void saveDataToFile()
    {
        if(people.size() < 1)
        {
            System.out.println("Run createNewRoskildeHaveData() first, to generate data");
            return;
        }

        ArrayList<String> peopleCSVLines, phoneNumberCSVLines,
                waitingListCSVLines, hallMemberEntryCSVLines, contactsCSVLines;

        peopleCSVLines = new ArrayList<>();
        phoneNumberCSVLines = new ArrayList<>();
        waitingListCSVLines = new ArrayList<>();
        hallMemberEntryCSVLines = new ArrayList<>();
        contactsCSVLines = new ArrayList<>();

        // For each of the sets of data, a conversion from specific object type to string csv:

        for(Person person : people) { peopleCSVLines.add(person.toCSV()); }
        for(PhoneNumber phoneNumber : phoneNumbers) { phoneNumberCSVLines.add(phoneNumber.toCSV()); }
        for(WaitingListSpot spot : waitingList) { waitingListCSVLines.add(spot.toCSV()); }
        for(HallMemberEntry entry : hallMembers) { hallMemberEntryCSVLines.add(entry.toCSV()); }
        for(Contact c : contacts) { contactsCSVLines.add(c.toCSV()); }

        FileIO.writeLinesToTextFile(peopleCSVLines, pathToPersons, false);
        FileIO.writeLinesToTextFile(phoneNumberCSVLines, pathToPhoneNumbers, false);
        FileIO.writeLinesToTextFile(waitingListCSVLines, pathToWaitingList, false);
        FileIO.writeLinesToTextFile(hallMemberEntryCSVLines, pathToHallMemberEntries, false);
        FileIO.writeLinesToTextFile(contactsCSVLines, pathToContacts, false);
    }
}