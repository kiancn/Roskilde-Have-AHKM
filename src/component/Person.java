package component;

//import DolphinID;

import enums.Gender;
import enums.Role;
import utility.Grouper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Person class instances are used to organize/keep basic data about everyone affiliated with
 * the club.
 */
public class Person implements ICSV
{
    private final int ID;
    private String name;
    private Date birthDate;
    private Address address;
    private Role organizationRole;
    private Gender gender;

    /**
     * Important:
     *
     * @param existingID the system will generate a new id if existingID set as -1
     */
    public Person(int existingID, String name, Date birthDate, Gender sex, Address address,
                  Role institutionRole)
    {
        if(existingID == -1)
        {
            ID = RoskildeHaveID.instance().getNewID();
        } else
        {
            ID = existingID;
        }

        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.organizationRole = institutionRole;
        gender = sex;

    }

    /**
     * Return an initialized Person object - that clearly states about itself,
     * that it is not, in fact, a real Person.
     * <p>Purpose is to return a proxy; if you need to return a Person no info is given.</p>
     */
    public static Person returnBlankPerson()
    {

        Address address = new Address("Ukendt", -1, -1);
        Date birthDate = new Date(1, 1, 0);

        return new Person(-1, "Ikke angivet", birthDate, Gender.NotRegistered, address, Role.NoRole);

    }

    /**
     * Returns string of comma separated values, a value for each attribute on a Person
     */
    public static Person fromCSV(String strPersonCSV)
    {
        /* each string in the returned array 'is an attribute' */
        ArrayList<String> attributesOfPersonAsStrings = Grouper.splitStringAsCSV(strPersonCSV, ",", 10);
        /* splitStringAsCSV returns an empty list if something was wrong with the formatting */
        if(attributesOfPersonAsStrings.size() == 10) //
        {
            /* Henter info fra hver String-bid - og laver til sidst et Person-objekt */

            int clubID = Integer.parseInt(attributesOfPersonAsStrings.get(0));

            String name = attributesOfPersonAsStrings.get(1);

            int birthday_day = Integer.parseInt(attributesOfPersonAsStrings.get(2));
            int birthday_month = Integer.parseInt(attributesOfPersonAsStrings.get(3));
            int birthday_year = Integer.parseInt(attributesOfPersonAsStrings.get(4));
            /* birthdate secured */
            Date birthDate = new Date(birthday_day, birthday_month, birthday_year);

            String streetName = attributesOfPersonAsStrings.get(5);
            int houseNo = Integer.parseInt(attributesOfPersonAsStrings.get(6));
            int zipCode = Integer.parseInt(attributesOfPersonAsStrings.get(7));
            /* Address secured */
            Address address = new Address(streetName, houseNo, zipCode);

            Role organizationRole;
            /* switching Roles */
            switch(attributesOfPersonAsStrings.get(8))
            {
                case "Leder":
                    organizationRole = Role.Leader;
                    break;
                case "Medarbejder":
                    organizationRole = Role.Staff;
                    break;
                case "Voksen":
                    organizationRole = Role.Adult;
                    break;
                case "Barn":
                    organizationRole = Role.Kid;
                    break;
                case "Venteliste":
                    organizationRole = Role.WaitingList;
                    break;
                case "Afvist":
                    organizationRole = Role.Banned;
                    break;
                default:
                    organizationRole = Role.NoRole;
            }

            Gender gender;
            /* and Genders */
            switch(attributesOfPersonAsStrings.get(9))
            {
                case "K":
                    gender = Gender.Female;
                    break;
                case "M":
                    gender = Gender.Male;
                    break;
                default:
                    gender = Gender.NotRegistered;
            }

            return new Person(clubID,
                              name,
                              birthDate,
                              gender,
                              address,
                              organizationRole);
        }

        /* code only reaches here if something went wrong; so return a proxy Person */
        return Person.returnBlankPerson();
    }

    /**
     * Returns a string of comma separated values based on the Person
     */
    public static String toCSV(Person person)
    {
        String personCSV; // CSV being built
        String comma = ","; // comma is our seperator string of choice

        personCSV = String.valueOf(person.getID()) + comma;

        personCSV += person.getName() + comma;

        personCSV += person.getBirthDate().getDay() + comma;
        personCSV += person.getBirthDate().getMonth() + comma;
        personCSV += person.getBirthDate().getYear() + comma;

        personCSV += person.getAddress().getStreetName() + comma;
        personCSV += person.getAddress().getHouseNo() + comma;
        personCSV += person.getAddress().getZipCode() + comma;

        String roleString = "NoRole";
        switch(person.getRole())
        {
            case Leader:
                roleString = "Leder";
                break;
            case Staff:
                roleString = "Medarbejder";
                break;
            case Adult:
                roleString = "Voksen";
                break;
            case Kid:
                roleString = "Barn";
                break;
            case WaitingList:
                roleString = "Venteliste";
                break;
            case Banned:
                roleString = "Banned";
                break;
            case NoRole:
                roleString = "NoRole";
                break;
        }
        personCSV += roleString + comma;

        personCSV += person.genderString();

        return personCSV;
    }

    public void setOrganizationRole(Role organizationRole){ this.organizationRole = organizationRole; }

    public String toCSV()
    {
        return toCSV(this);
    }

    public int getID(){ return ID; }

    public Gender getGender(){ return gender; }

    public void setGender(Gender newGender){gender = newGender;}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getBirthDate(){ return birthDate; }

    public void setBirthDate(Date newDate){ birthDate = newDate; }

    public Address getAddress(){ return address; }

    public void setAddress(Address address){ this.address = address; }

    public Role getRole(){ return organizationRole; }

    public void setRole(Role newRole){organizationRole = newRole;}

    @Override
    public String toString()
    {
        String genderString = (gender == Gender.Female) ? "K" : "M";

        return String.format("[#%4d] Navn: [ %26s ] [ %1s ] Alder: [ %3d ] Adresse: [ %25s %3d, %4d ]"
                , getID(), name, genderString(), birthDate.getAge(), address.getStreetName(),
                             address.getHouseNo(), address.getZipCode());

    }

    /**
     * Returns gender of Person as String:
     * <p><i>'K' for female, 'M' for male, U for NotRegistered<i/></p>
     * <p>genderString returning "K"/"M" fits with DolphinIO::makeCSVStringFromPerson(Person person) <p/>
     */
    public String genderString()
    {
        return Gender.genderString(gender, "K", "M", "U");
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Person)) return false;
        Person person = (Person)o;
        return getName().equals(person.getName()) &&
               getBirthDate().equals(person.getBirthDate()) &&
               getAddress().equals(person.getAddress()) &&
               organizationRole == person.organizationRole &&
               getGender() == person.getGender();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), getBirthDate(), getAddress(), organizationRole, getGender());
    }
}


//**
// https://www.answers.com/Q/When_is_Santa_Claus'_birthday
// */