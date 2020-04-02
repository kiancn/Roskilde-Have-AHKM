package menus;

import RoskildeHave.RoskildeHaveFileIO;
import component.WaitingListSpot;
import menus.crud.WaitingListCRUD;
import utility.GET;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class WaitingListMenu extends Menu
{
    private WaitingListCRUD _waitingListCRUD;

    public WaitingListMenu()
    {
        super("Venteliste menu");
        _waitingListCRUD = new WaitingListCRUD();
    }

    private WaitingListMenu(String selfDescription)
    {
        super(selfDescription);
    }

    /**
     * Method signifies immediately activating/showing a menu.
     */
    @Override
    public void show()
    {
        int choice = -1;

        while(choice != 0)
        {
            System.out.println("Du kan nu håndtere ventelisten og pladser.");

            choice = GET.getInteger("\tVil du?\n" +
                                    "\t[1] Se venteliste pladser\n" +
                                    "\t[2] Redigere en plads på listen\n" + // case: tilføj barn
                                    "\t[3] Fjerne en plads fra listen\n" +
                                    "\t[0] Gå tilbage\n" +
                                    "\t|> "
                                   );
            switch(choice)
            {
                case 1:
                    printWaitingList_Menu();
                    break;
                case 2:
                    System.out.println("\t[ Ikke implementeret i prototypen ]");
                    break;
                case 3:
                    System.out.println("\t[ Ikke implementeret i prototypen ]");
                    break;
                default:
                    break;
            }
        }
    }

    private void printWaitingList_Menu()
    {
        int choice = -1;

        while(choice != 0)
        {
            System.out.println("Du kan nu se ventelisten.");

            choice = GET.getInteger("Vil du:\n" +
                                    "\t[1] Finde pladser per navn? \n" +
                                    "\t[2] Se sorteret liste? \n" +
                                    "\t[0] Gå tilbage? \n" +
                                    "\t|> "
                                   );

            switch(choice)
            {
                case 1:
                    findWaitingListSpotsByName_Menu();
                    break;
                case 2:
                    printSortedWaitingList_Menu();
                    break;
                default:
                    break;
            }
        }
    }

    private void findWaitingListSpotsByName_Menu()
    {

        String input = GET.getString("Indtast venligst barnets navn:\n\t|> ");

        LinkedList<WaitingListSpot> spotsByName = _waitingListCRUD.findWaitingListSpotsByName(input);

        Collections.sort(spotsByName);

        Iterator<WaitingListSpot> iterator = spotsByName.descendingIterator();

        printWaitingListSpot(iterator);
    }

    private void printWaitingListSpot(Iterator<WaitingListSpot> iterator)
    {
        int count = 1;

        while(iterator.hasNext())
        {
            System.out.println("\n [" + count++ + "] " + _waitingListCRUD.returnPrettyString(iterator.next()) + "\n");
        }
    }

    private void printSortedWaitingList_Menu()
    {
        boolean newestFirst = GET.getConfirmation("Vil du have sorteret vente-listen sorteret" +
                                                  "\n\t nyeste først: tast [ny] / ældste først [gammel]",
                                                  "ny",
                                                  "gammel");


        int numberToShow = GET.getInteger("Hvor mange pladser vil du gerne have vist?");

        LinkedList<WaitingListSpot> waitingListSpots =
                new LinkedList<>(RoskildeHaveFileIO.instance().getWaitingList());

        Collections.sort(waitingListSpots);

        Iterator<WaitingListSpot> iterator = newestFirst ?
                waitingListSpots.descendingIterator() :
                waitingListSpots.iterator();


        int count = 1;

        for(int i = 0; i < numberToShow && i < waitingListSpots.size(); i++)
        {
            System.out.println("\n [" + count++ + "] " + _waitingListCRUD.returnPrettyString(iterator.next()) + "\n");
        }
    }


}
