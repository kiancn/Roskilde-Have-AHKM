package menus;

import utility.GET;

import java.util.Scanner;

public class MainMenu extends Menu
{
    private ContactsMenu _contactsMenu;
    private WaitingListMenu _waitingListMenu;
    private KidMenu _kidMenu;

    public MainMenu()
    {
        super("Hoved Menu");
        _contactsMenu = new ContactsMenu();
        _waitingListMenu = new WaitingListMenu();
        _kidMenu = new KidMenu();
    }

    public void show()
    {

        int menuvalg = -1;
        while(menuvalg != 0) {
            menuvalg = GET.getInteger("Velkommen\n" +
                    "\t[1] Håndter barn\n" +
                    "\t[2] Håndter kontakter\n" +
                    "\t[3] Venteliste\n" +
                    "\t[0]  Afslut\n" +
                    "\t|> ");
            switch (menuvalg) {
                case 1:
                    _kidMenu.show();
                    break;
                case 2:
                    _contactsMenu.show();
                    break;
                case 3:
                    _waitingListMenu.show();
                case 0:
                    break;
            }
        }

    }
}
