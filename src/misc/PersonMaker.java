package misc;


import component.Address;
import component.Date;
import component.Person;
import enums.Gender;
import enums.Role;
import utility.FileIO;

import java.util.ArrayList;

/**
 * Purpose of class is to make a lot of members - club needs a population
 */
public class PersonMaker
{
    /* og jeg løb tør for gid lige her */

    private final String[] maleFirstNames = { // længste drengenavn er 9 char
            "Peter", "Jens", "Michael", "Lars", "Henrik",
            "Thomas", "Søren", "Jan", "Christian", "Martin",
            "Niels", "Anders", "Morten", "Jesper", "Hans",
            "Jørgen", "Mads", "Per", "Ole", "Rasmus",
            "Altantsetseg", "Bat Erdene", "Batbayar", "Batjargal",
            "Bolormaa", "Enkhjargal", "Enkhtuya", "Erdenechimeg",
            "Ganbaatar", "Ganbold", "Gantulga", "Ganzorig",
            "Lkhagvasüren", "Monkh Erdene", "Monkhbat", "Mönkhtsetseg",
            "Narantsetseg", "Nergüi", "Otgonbayar", "Oyuunchimeg ",
            "Altan", "Altansarnai", "Arban", "Bataar", "Batsaikhan",
            "Batu", "Batuhan", "Batukhan", "Batzorig", "Bayarmaa",
            "Chaghatai", "Chenghiz", "Chime", "Chingis"};
    private final String[] femaleFirstNames = { // længste pigenavn er, wierdly, også 9 char
            "Anne", "Kirsten", "Mette", "Hanne", "Anna",
            "Helle", "Susanne", "Lene", "Maria", "Marianne",
            "Lone", "Camilla", "Pia", "Louise", "Charlotte",
            "Inge", "Bente", "Karen", "Tina", "Jette",
            "Ida", "Emma", "Alma", "Ella", "Sofia", "Freja",
            "Josefine", "Clara", "Anna", "Karla", "Laura", "Alberte", "Olivia",
            "Agnes", "Nora", "Lærke", "Luna", "Isabella",
            "Frida", "Lily", "Victoria", "Aya", "Ellen", "Ellie",
            "Maja", "Mathilde", "Esther", "Mille", "Sofie",
            "Emily", "Astrid", "Liva", "Marie", "Caroline",
            "Rosa", "Emilie", "Sara", "Saga", "Liv",
            "Andrea", "Alba", "Asta", "Hannah", "Naja", "Vilma",
            "Johanne", "Lea", "Vigga", "Gry", "Eva"
    };
    private final String[] lastNames = { // 12 char i længste efternavn
            "Nielsen", "Jensen", "Hansen", "Pedersen", "Andersen",
            "Christensen", "Larsen", "Sørensen", "Rasmussen",
            "Jørgensen", "Petersen", "Madsen", "Kristensen",
            "Olsen", "Thomsen", "Christiansen", "Poulsen",
            "Johansen", "Møller", "Mortensen"};

    private final String[] roadNames = {"Klaruphave", "Færgevej", "Mediebyen",
            "Ole Lund Kirkegaards Allé", "Seglen", "Peter Panums Vej", "Bellahøjvænget",
            "Flovt Bygade", "Søgård Alle", "Ellipsehaven", "Degns Hauge",
            "Dorthe Lottrups Lund", "Flækken", "Hedvig Billes Top", "Humle Hauge",
            "Karine Krumpen", "Kirsten Munks Dal", "Marie Haves Vænge", "Nørrestrands Alle",
            "Stikkelstien"};


    /**
     * Purpose of method is to populate lists with proxy people
     */
    public ArrayList<Person> createMembers(int numberOfPeople, int baseYear, int ageVariance, Role role)
    {
        ArrayList<Person> newMembers = new ArrayList<>();

        for(int i = 1; i < numberOfPeople + 1; i++)
        {
            newMembers.add(
                    new Person(-1,
                               ((i % 2 == 0) ? getFirstName(false) : getFirstName(true)) +
                               " " + getLastName(),
                               new Date((int)(Math.random() * 30),
                                        (int)(Math.random() * 12),
                                        (int)(Math.random() * ageVariance) + baseYear),
                               ((i % 3 == 0) ? Gender.Male : Gender.Female),
                               new Address(roadNames[(int)((Math.random() * roadNames.length - 1))],
                                           (int)(Math.random() * 342),
                                           1000 + (int)(Math.random() * 8999)),
                               role
                    ));
        }

        return newMembers;
    }


    /**
     * Returns a name
     *
     * @param femaleName if set to true, method returns female name,
     *                   <p>if set to false, method returns a male name </p>
     */
    private String getFirstName(boolean femaleName)
    {

        int randomIndex = (int)(Math.random() * (
                (femaleName ? femaleFirstNames.length - 1 : maleFirstNames.length - 1)));

        return (femaleName ? femaleFirstNames[randomIndex] : maleFirstNames[randomIndex]);
    }

    private String getLastName()
    {
        int randomIndex = (int)(Math.random() * lastNames.length - 1);

        return lastNames[randomIndex];
    }

    /**
     * Development time method; creates x number of Persons Role.Member, and saves then to file
     */
    public void writeNewPeopleToFile(String pathToFile, int baseYear,
                                     int ageVariance, Role role, int numberOfPeopleToMake)
    {
        /* Cooking up som people */
        ArrayList<Person> newPersons = createMembers(numberOfPeopleToMake, baseYear, ageVariance, role);

        ArrayList<String> newPersonsCSV = new ArrayList<>();
        /* Transforming each new person into a CSV string */
        for(Person person : newPersons)
        {
            newPersonsCSV.add(person.toCSV());
        }

        FileIO.writeLinesToTextFile(newPersonsCSV, pathToFile, true);
    }

//    /**
//     * Development time method; USE WITH CAUTION, will delete/overwrite old member-files.
//     * <p><b> Also, leaders (particularly Cashiers )need to have Role adjusted in the files manually,
//     * since writeNewMembersToFile(.) return just CEO leaders - so go edit the files if you
//     * repopulate club; and then new Performance times, teams, and bills need to be
//     * regenerated, because they are tied for particular IDs (). </b></p>
//     */
//    public void repopulateFiles(int numberOfMembers, int numberOfTrainers, int numberOfLeaders)
//    {
//        writeNewMembersToFile("src/resource/MemberList.txt", numberOfMembers, Role.Member);
//        writeNewMembersToFile("src/resource/TrainerList.txt", numberOfTrainers, Role.Trainer);
//        writeNewMembersToFile("src/resource/LeaderList.txt", numberOfLeaders, Role.CEO); // NB; just CEO
//    }


}


//    public static void givePhoneNumberToConsuela(ArrayList<Number> numbers, int lastNumber)
//    {
//        for(int i = -1, k = 0; i < numbers.size() && !(i == lastNumber);
//            numbers.add(i + k),
//                    System.out.print(numbers.size() + " "))
//        {
//            switch((k-- + ++i))
//            {
//                case -1:
//                    System.out.println("My nose, Juan.");
//                    break;
//                case 0:
//                    System.out.println("Lemon pledge!");
//                    break;
//                case 1:
//                    System.out.println("Juan!");
//                    break;
//                case 401555112:
//                    System.out.println("4.");
//                default:
//                    System.out.println((i < k) ? numbers.get(i) : "No.");
//            }
//        }
//        System.out.println("Let me get my pen.");
//    }