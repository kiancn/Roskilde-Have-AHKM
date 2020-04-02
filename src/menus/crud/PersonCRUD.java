package menus.crud;

import RoskildeHave.RoskildeHaveFileIO;
import component.Person;
import enums.Role;

import java.util.LinkedList;

public class PersonCRUD
{

    public boolean checkIfIDExists(int id){
        // simply going through the list of people and returning true if there is an id match
        for(Person person: RoskildeHaveFileIO.instance().getPeople()){
            if(person.getID() == id){
                return true;
            }
        }
        // if there was no match, return false;
        return false;
    }

    public Person findPersonByID(int id)
    {
        Person result = null;

        for(Person person : RoskildeHaveFileIO.instance().getPeople())
        {
            if(person.getID() == id)
            {
                result = person;
                return result; // no reason to keep going, if person is found, so return
            }
        }
        return Person.returnBlankPerson();
    }


    /**
     * Method returns Persons object fully or partially matching search match.
     * The first returned Person is the primary person, the remaining are
     * contact Persons for that first person.
     *
     * @param searchString  full or partial name to search for
     * @param partialString if true, return partial results: if false, return only full matches
     * @return returned list of Persons, length is 0 if no Persons returned.
     */
    public LinkedList<Person> findPersonsByName(String searchString, boolean partialString)
    {
        var results = new LinkedList<Person>();

        for(Person person : RoskildeHaveFileIO.instance().getPeople())
        {
            if(partialString)
            {
                // making search string lower case to max out matches
                searchString = searchString.toLowerCase();

                if(person.getName().toLowerCase().contains(searchString))
                {
                    results.add(person);
                }
            }
            if(!partialString)
            {
                if(person.getName().equalsIgnoreCase(searchString))
                {
                    results.add(person);
                }
            }
        }

        return results;
    }

    public LinkedList<Person> findPersonsByRole(Role requestedRole)
    {
        var results = new LinkedList<Person>();

        for(Person person : RoskildeHaveFileIO.instance().getPeople())
        {
            if(person.getRole() == requestedRole){
                results.add(person);
            }
        }

        return results;
    }

    /**
     * Method includes supplied Person on persistent list
     * and saves all persistent lists immediately, refreshing live lists at the same time
     * Method returns false if another Person with duplicate information is found
     */
    public boolean addPersonToPersistentList(Person newPerson)
    {
        /** Making sure new Person is not already in system */
        for(Person person : RoskildeHaveFileIO.instance().getPeople())
        {
            if(newPerson.equals(person))
            {
                return false; // return false because the activity was not successful
            }
        }
        // if no duplicate ids were found, simply add person to list, and update data immediately
        RoskildeHaveFileIO.instance().getPeople().add(newPerson);

        RoskildeHaveFileIO.instance().updateFilesAndReloadLiveLists();
        return true; // Person added to persistent list
    }
}
