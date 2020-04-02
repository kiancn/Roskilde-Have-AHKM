import RoskildeHave.RoskildeHaveFileIO;
import menus.LoginMenu;
import menus.MainMenu;
import menus.Menu;
import RoskildeHave.RoskildeHavenSplashStrings;

public class Program
{
    private Menu loginMenu;

    private RoskildeHavenSplashStrings splashStrings;

    public Program()
    {
        Menu mainMenu = new MainMenu();
        loginMenu = new LoginMenu(mainMenu);
        splashStrings = new RoskildeHavenSplashStrings();
    }

    public void run()
    {
        System.out.println(splashStrings.PROTOTYPEMESSAGE);
        // initializing data and printing report to make sure data is GO.
        RoskildeHaveFileIO.instance().printReadInReport();

        System.out.println(splashStrings.WELCOME);
        // login menu handles execution of constructor supplied menu, MainMenu
        loginMenu.show();

        System.out.println(splashStrings.GOODBYE);
    }
}
