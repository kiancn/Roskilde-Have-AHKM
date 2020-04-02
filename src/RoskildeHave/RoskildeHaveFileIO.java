package RoskildeHave;

import component.*;
import utility.FileIO;
import utility.TO;

import java.io.File;
import java.util.ArrayList;

/* Class will read saved data from files and write data to the proper files.
 * Data access is intended to happen via RoskildeHaveData */
public class RoskildeHaveFileIO
{
    private static RoskildeHaveFileIO _instance; // singleton instance;
    ArrayList<Person> people;
    ArrayList<PhoneNumber> phoneNumbers;
    ArrayList<WaitingListSpot> waitingList;
    ArrayList<HallMemberEntry> hallMembers;
    ArrayList<Contact> contacts;
    private String pathToPersons = "/resource/persons.txt";
    private String pathToPhoneNumbers = "/resource/phonenumbers.txt";
    private String pathToWaitingList = "/resource/waitinglist.txt";
    private String pathToHallMemberEntries = "/resource/hallmemberentries.txt";
    private String pathToContacts = "/resource/contacts.txt";

    private String absolutePath;

    private RoskildeHaveFileIO()
    {
        adjustPathsToAbsoluteLocation();
        newAllLists();
    }

    // Singleton instance
    public static RoskildeHaveFileIO instance()
    {

        if(_instance != null)
        {
            return _instance;
        }

        if(_instance == null)
        {
            _instance = new RoskildeHaveFileIO();
            _instance.readInAllData();
        }

        return _instance;
    }

    public ArrayList<Person> getPeople(){ return people; }

    public ArrayList<PhoneNumber> getPhoneNumbers(){ return phoneNumbers; }

    public ArrayList<WaitingListSpot> getWaitingList(){ return waitingList; }

    public ArrayList<HallMemberEntry> getHallMembers(){ return hallMembers; }

    public ArrayList<Contact> getContacts(){ return contacts; }

    private void newAllLists()
    {
        people = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        waitingList = new ArrayList<>();
        hallMembers = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    private void adjustPathsToAbsoluteLocation()
    {
        String absolutePath = new File("").getAbsolutePath();
        pathToPersons = absolutePath + pathToPersons;
        pathToPhoneNumbers = absolutePath + pathToPhoneNumbers;
        pathToWaitingList = absolutePath + pathToWaitingList;
        pathToHallMemberEntries = absolutePath + pathToHallMemberEntries;
        pathToContacts = absolutePath + pathToContacts;
    }

    private void readInAllData()
    {
        newAllLists();

        readInPersons(); //         these five methods are repeated almost verbatim
        readInPhoneNumbers(); //    because of the problems associated with using static
        readInWaitingList(); //     with generics.
        readInContactsList(); //    Only resolution I found was to add a wildcard parameter
        readInHallMemberList(); //  to static fromCSV() method, and it was very ugly.
    }

    /**
     * Method writes all content from loaded lists back to the files from whence they came!
     * Updates all files
     */
    private void writeOutAllData()
    {
        writeListToFile(pathToPersons, people);
        writeListToFile(pathToPhoneNumbers, phoneNumbers);
        writeListToFile(pathToWaitingList, waitingList);
        writeListToFile(pathToContacts, contacts);
        writeListToFile(pathToHallMemberEntries, hallMembers);
    }

    public void updateFilesAndReloadLiveLists()
    {
        writeOutAllData();
        readInAllData();
    }

    private <T extends ICSV> void writeListToFile(String path, ArrayList<T> list)
    {
        var outLines = new ArrayList<String>();
        for(T thingOfTypeT : list) { outLines.add(thingOfTypeT.toCSV());}

        FileIO.writeLinesToTextFile(outLines, path, false);
    }

    /*** Method reads in Persons from csv-text file. */
    private void readInPersons()
    {
        ArrayList<String> csvPersons = FileIO.load_TextFile_as_Strings(pathToPersons, -1);
        for(String csv : csvPersons) { people.add(Person.fromCSV(csv)); }
    }

    /***/
    private void readInPhoneNumbers()
    {
        ArrayList<String> csvPhoneNumbers = FileIO.load_TextFile_as_Strings(pathToPhoneNumbers, -1);
        for(String csv : csvPhoneNumbers) { phoneNumbers.add(PhoneNumber.fromCSV(csv)); }
    }

    private void readInWaitingList()
    {
        ArrayList<String> csvWaitingList = FileIO.load_TextFile_as_Strings(pathToWaitingList, -1);
        for(String csv : csvWaitingList) {waitingList.add(WaitingListSpot.fromCSV(csv));}
    }

    private void readInHallMemberList()
    {
        ArrayList<String> csvHallMembers = FileIO.load_TextFile_as_Strings(pathToHallMemberEntries, -1);
        for(String csv : csvHallMembers) {hallMembers.add(HallMemberEntry.fromCSV(csv));}
    }

    private void readInContactsList()
    {
        ArrayList<String> csvContacts = FileIO.load_TextFile_as_Strings(pathToContacts, -1);
        for(String csv : csvContacts) { contacts.add(Contact.fromCSV(csv)); }
    }

    public void printReadInReport()
    {
        System.out.println("Indlæste " + people.size() + " børn og voksne.");
        System.out.println("Indlæst: " + phoneNumbers.size() + " telefon-numre");
        System.out.println("Indlæst: " + waitingList.size() + " venteliste-pladser.");
        System.out.println("Indlæst: " + hallMembers.size() + " børn med plads i stuen.");
        System.out.println("Indlæst: " + contacts.size() + " kontakter");
    }
}