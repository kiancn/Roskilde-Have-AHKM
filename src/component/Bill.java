package component;

import utility.Grouper;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A Bill is a bill is a Bill
 * Notes:
 * <p>- a bill is connected to the debtor by an id - so that id needs to match an id in another list that
 * contains the Person with the ID </p>
 * <p>- call pay() to pay a bill; before bill is paid, payment date reports be ' 1. , Jan., year -1,
 * use this value as a red flag<p/>
 * <p>- call isOverdue() to check if a bill is overdue; boolean returned </p>
 * <p>- isPaid() checks if the payment received date is a real (set) date, and reports back a boolean true
 * if this is the case  </p>
 */
public class Bill implements ICSV
{
    private int debtorID; // clubID of debtor
    private double amount; // amount owed by debtor
    private Date issueDate; // date bill was created (this is a LocalDateTime, NOT Date)
    private int paymentWithinDays; // value is by default 14 days; to change call setPaymentWithDays.
    private LocalDateTime paymentReceivedDate; // field will remain null until bill is paid

    public Bill(double amount, int debtorID)
    {
        this.debtorID = debtorID;
        this.amount = amount;
        this.issueDate = new Date(LocalDateTime.now());
        this.paymentWithinDays = 14;
        /* when initializing the paymentRecievedDate, the year value is set equal to -1,
         * and this year-value is then used to decide/check if a bill has been paid;
         * if year is -1, the bill is unpaid. */
        paymentReceivedDate = LocalDateTime.now().minusYears(LocalDateTime.now().getYear() + 1);
    }

    /**
     * Full constructor only meant to be used to construct bill from CSV
     */
    public Bill(int debtorID, double amount, int issueDay, int issueMonth, int issueYear,
                int paymentWithinDays, int payDay, int payMonth, int payYear)
    {

        this.debtorID = debtorID;
        this.amount = amount;
        this.issueDate = new Date(issueDay, issueMonth, issueYear);
        this.paymentWithinDays = paymentWithinDays;

        this.paymentReceivedDate = LocalDateTime.now().
                withDayOfMonth(payDay).withMonth(payMonth).withYear(payYear);

    }

    public static String toCSV(Bill bill)
    {

        int debtorID = bill.getDebtorID(); // 0
        double amount = bill.getAmount(); // 1
        int issueDay = bill.getIssueDate().getDay(); // 2
        int issueMonth = bill.getIssueDate().getMonth(); // 3
        int issueYear = bill.getIssueDate().getYear(); // 4
        int paymentWitinDays = bill.getPaymentWithinDays(); // 5
        int paidDay = bill.getPaymentReceivedDate().getDay(); // 6
        int paidMonth = bill.getPaymentReceivedDate().getMonth(); // 7
        int paidYear = bill.getPaymentReceivedDate().getYear(); // 8 (count 8 equals a  9-piece CSV)

        String comma = ",";

        String returnString =
                debtorID + comma +
                amount + comma +
                issueDay + comma +
                issueMonth + comma +
                issueYear + comma +
                paymentWitinDays + comma +
                paidDay + comma +
                paidMonth + comma +
                paidYear + comma +
                "";
        return returnString;
    }

    public static Bill fromCSV(String csvBillLine)
    {
        /* creating arraylist of strings to hold parts of csv*/
        ArrayList<String> billAttributeList = Grouper.splitStringAsCSV(csvBillLine, ",", 9);

        if(billAttributeList.size() == 9)
        {

            int debtorID = Integer.parseInt(billAttributeList.get(0)); // 0
            double amount = Double.parseDouble(billAttributeList.get(1)); // 1
            int issueDay = Integer.parseInt(billAttributeList.get(2)); // 2
            int issueMonth = Integer.parseInt(billAttributeList.get(3)); // 3
            int issueYear = Integer.parseInt(billAttributeList.get(4)); // 4
            int paymentWithinDays = Integer.parseInt(billAttributeList.get(5)); // 5
            int paidDay = Integer.parseInt(billAttributeList.get(6)); // 6
            int paidMonth = Integer.parseInt(billAttributeList.get(7)); // 7
            int paidYear = Integer.parseInt(billAttributeList.get(8));

            return new Bill(debtorID, amount, issueDay, issueMonth, issueYear, paymentWithinDays,
                            paidDay, paidMonth, paidYear);
        }
        /* if returned bill-attribute-ArrayList was badly formatted (wrong size), return a blank bill */
        return new Bill(0, -1);
    }

    public String toCSV()
    {
        return toCSV(this);
    }

    public double getAmount(){ return amount; }

    public void setAmount(double newAmount){ amount = newAmount;}

    public int getDebtorID(){ return debtorID; }

    /**
     * Returns a Date object with date info on day, month and year when bill was created.
     */
    public Date getIssueDate(){ return issueDate; }

    /**
     * Returns number of days allotted to pay bill fra date bill fra issued.
     */
    public int getPaymentWithinDays(){ return paymentWithinDays; }

    /**
     * Sets number of day allotted to pay bill.
     */
    public void setPaymentWithinDays(int paymentWithinDays)
    {
        this.paymentWithinDays = paymentWithinDays;
    }

    /**
     * Method effectively pays the bill by setting a payment date.
     */
    public void pay()
    {
        paymentReceivedDate = LocalDateTime.now();
    }

    /**
     * Returns the date when bill was paid; if payment was not received (pay() not yet pulled), the date 1st
     * of January, year -1 is returned: the idea is that that date is to be treated as a red flag.
     */
    public Date getPaymentReceivedDate()
    {
        /* if bill is not paid, (payment year = -1) return a 'blank date' */
        if(paymentReceivedDate.getYear() == -1){return new Date(1, 1, -1); }
        /* else, return the actually recorded date of payment (as a dolphin.component.Date)*/
        return new Date(paymentReceivedDate.getDayOfMonth(), paymentReceivedDate.getMonthValue(),
                        paymentReceivedDate.getYear());
    }

    /**
     * Returns true if date today date bill should be paid by.
     */
    public boolean isOverdue()
    {
        /*if bill isPaid(), then simply return false (because a paid bill is never overdue) */
        if(isPaid()){return false;}
        /* else, get date when bill would be overdue*/
        LocalDateTime overdueDate = LocalDateTime.now()
                .withDayOfMonth(issueDate.getDay())
                .withMonth(issueDate.getMonth())
                .withYear(issueDate.getYear())
                .plusDays(paymentWithinDays);
        /* check if overdueDate already happened,
         * and returns true if bill is overdue (and false bill is not overdue). */
        return LocalDateTime.now().isAfter(overdueDate);

    }

    /**
     * Returns true if the bill registered a payment date when pay() was called
     */
    public boolean isPaid()
    {
        /* if year field in paymentReceived is -1, return that bill is not paid (false) */
        if(paymentReceivedDate.getYear() == -1){return false;}
        /* if pay() was called, a date of payment has been registered (not year = -1),
         and this means that payment happened, so return true */
        return true;
    }

    @Override
    public String toString()
    {

        return "ID: [" + getDebtorID() + "]" +
       // is bill overdue?
       " Status: [" + (isOverdue() ? " Overskredet " : "     OK      ") + "]" +
       // if bill is paid, show Date of payment, else show when bill was issued
                      (isPaid() ?
                              " Betalt d.  [ " + String.format("%18s",getPaymentReceivedDate()) + " ]"
                              :
                              " Udsendt d. [ " + String.format("%18s",getIssueDate()) + " ]") +
       String.format(" Beløb: [ %7.2f kr]", getAmount());
    }
}
