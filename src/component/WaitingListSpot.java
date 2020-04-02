package component;

import utility.Grouper;
import utility.TO;

import java.util.ArrayList;
import java.util.Objects;

public class WaitingListSpot implements ICSV, Comparable<WaitingListSpot>
{
    private Date _dateOfApplication;
    private int _applyingKidID;
    private int _contactID;

    public WaitingListSpot(Date dateOfApplication, int kidID, int contactID)
    {
        _dateOfApplication = dateOfApplication;
        _applyingKidID = kidID;
        _contactID = contactID;
    }

    public static WaitingListSpot fromCSV(String csvString)
    {
        ArrayList<String> csvs = Grouper.splitStringAsCSV(csvString, ",", 5);

        WaitingListSpot result;

        try
        {
            int day = Integer.parseInt(csvs.get(0));
            int month = Integer.parseInt(csvs.get(1));
            int year = Integer.parseInt(csvs.get(2));

            Date applicationDate = new Date(day, month, year);

            int kidID = Integer.parseInt(csvs.get(3));
            int contactID = Integer.parseInt(csvs.get(4));

            result = new WaitingListSpot(applicationDate, kidID, contactID);

        } catch(Exception e)
        {
            System.out.println("Exception occurred while reading csv data from string.\n" +
                               TO.yellow(e.getMessage()));
            result = returnEmptyWaitingListSpot();
        }

        return result;
    }

    @Override
    public String toCSV()
    {
        return _dateOfApplication.getDay() + "," +
               _dateOfApplication.getMonth() + "," +
               _dateOfApplication.getYear() + "," +
               _applyingKidID + "," +
               _contactID;
    }

    public Date getDateOfApplication(){ return _dateOfApplication; }

    public void setDateOfApplication(Date dateOfApplication){ this._dateOfApplication = dateOfApplication; }

    public int getApplyingKidID(){ return _applyingKidID; }

    public void setKidID(Person applyingKidID){ this._applyingKidID = applyingKidID.getID(); }

    public int getContactID(){ return _contactID; }

    public void setContactID(Person contactID){ this._contactID = contactID.getID(); }


    public static WaitingListSpot returnEmptyWaitingListSpot()
    {
        return new WaitingListSpot(Date.now(), -1, -1);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof WaitingListSpot)) return false;
        WaitingListSpot spot = (WaitingListSpot)o;
        return _applyingKidID == spot._applyingKidID &&
               _contactID == spot._contactID &&
               _dateOfApplication.equals(spot._dateOfApplication);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(_dateOfApplication, _applyingKidID, _contactID);
    }

    /** Waitinglist spots are compared using only the date of application. This is slightly dirty */
    @Override
    public int compareTo(WaitingListSpot waitingListSpot)
    {
        int datesCompared = getDateOfApplication().compareTo(waitingListSpot.getDateOfApplication());
//        int contactIDsCompared = getContactID() - waitingListSpot.getContactID();
//        int applicantIDsCompared = getApplyingKidID() - waitingListSpot.getApplyingKidID();

        return datesCompared;
    }
}
