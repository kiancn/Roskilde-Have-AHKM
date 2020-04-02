package menus;

/**
 * Intent is that show() is the only outward open menu-method on the class;
 * following this principle will reveal when a 'Menu object' is doing
 * more than one menu's job
 * Note: CRUD-menu classes will be different
 */
public abstract class Menu
{
    private String selfDescription;

    public Menu(String selfDescription)
    {
        this.selfDescription = selfDescription;
    }

    /**
     * selfDescription is intended to be used for display to user in menu options
     */
    public final String getSelfDescription(){ return selfDescription; }

    /**
     * Method signifies immediately activating/showing a menu.
     */
    public abstract void show();
}
