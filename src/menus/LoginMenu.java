package menus;

import RoskildeHave.RoskildeHavenSplashStrings;
import utility.GET;
import utility.passwordmasking.PasswordField;

/**
 * This class is in the prototype stage, and only implemented
 * 'visually'; a whole subset of the full project would include
 * creating, viewing and managing system-user accounts and rights;
 * here data is hard-coded - and an alternator switch for the login process
 * has been made to make certain this demo will run both in the IDE and the
 * Windows 10 console.
 * <p>
 * It is pattern breaking to include the hard-coded data in this class.
 * Break Noticed.
 * <p>
 * Class
 */

public class LoginMenu extends Menu
{

    private final String[] userNames;
    private final String[] passwords;

    private RoskildeHavenSplashStrings splashStrings;

    private Menu menuLoggedInto;

    public LoginMenu(Menu menuLoggedInto)
    {
        this("Login Menu");
        this.menuLoggedInto = menuLoggedInto;
        splashStrings = new RoskildeHavenSplashStrings();
    }


    private LoginMenu(String selfDescription)
    {
        super(selfDescription);
        userNames = new String[]{"Sandra Madsen", "Admin"};
        passwords = new String[]{"leder007", "loginPassword"};
    }

    /**
     * Method signifies immediately activating/showing a menu.
     */
    @Override
    public void show()
    {
        boolean loginOK; // true if good user info was entered
        // multithreaded code does not execute properly in IDEs; true if IDE run detected
        boolean runningFromIDE = false;

        do // continue login process while user chooses to stay in login menu
        {
            loginOK = false;
            while(!loginOK) // do this while no good login info acquired, or user choose to bail.
            {
                System.out.println(splashStrings.LOGIN); // SPLASH WELCOME

                String userName = GET.getString("Indtast venligst bruger navn:\n\t|> ");

                char[] password;

                if(!runningFromIDE) // getPassword does not work in IDE
                {
                    password = PasswordField.getPassword(System.in, "Indtast venligst password:\n\t|> ");

                    if(password == null)
                    {
                        // getPassword returns a null array reference, if no chars were captured, thus this.
                        runningFromIDE = true;

                        System.out.println("MELDING:\nDet ser ud til at du kører programmet fra en IDE:\t" +
                                           "Password masking slås fra." +
                                           "\nKør shorttest.jar, for at opnå den fulde bruger-oplevelse.\n");

                        continue;
                    }

                    loginOK = checkCredentials(userName, new String(password));

                } else // option is chosen if IDE-execution detected
                {
                    String passw = GET.getString("Indtast venligst password:\n\t|> ");
                    loginOK = checkCredentials(userName, passw);
                }

                if(!loginOK) // if user input were no good, and ONLY if info were no good
                {
                    System.out.println("De indtastede informationer fandtes ikke i systemet.");
                    if(GET.getConfirmation("Vil du prøve igen?\n\tja / nej\n\t|> ", "nej", "ja"))
                    {
                        System.out.println("Du valgte ikke at prøve igen, login afsluttes.");
                        break;
                    }
                }
            }

            if(loginOK)
            {
                System.out.println("Login var en success. Fortsætter til første menu.\n");
                menuLoggedInto.show();
            }

            // when user returns from main menu, they get to choose if they want to log in again
        } while(GET.getConfirmation("Skal en anden logge ind?\n\t ja / nej\n\t|> ", "ja", "nej"));
    }

    /**
     * Method returns true if supplied info is recognized
     */
    private boolean checkCredentials(String userName, String password)
    {
        int count = 0;
        for(String user : userNames)
        {
            if(user.contentEquals(userName))
            {
                if(passwords[count].contentEquals(password))
                {
                    return true;
                }
            }
            count++;
        }

        return false;
    }
}
