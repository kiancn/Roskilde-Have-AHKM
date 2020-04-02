package utility.passwordmasking;

/**
 * This class is shamelessly lifted from
 * http://www.cse.chalmers.se/edu/year/2018/course/TDA602/Eraserlab/pwdmasking.html
 * Only very minor changes have been incorporated.
 *
 * Class defines a Runnable Thread, intended to used for masking of console output.
 *
 */


class EraserThread implements Runnable
{
    private boolean go;

    /**
     *@param prompt The prompt displayed to the user
     */
    public EraserThread(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Begin masking...display asterisks (*)
     */
    public void run () {
        go = true;
        while (go) {
            System.out.print("\010*");
            try {
                Thread.currentThread().sleep(1);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Instruct the thread to stop masking
     */
    public void stopMasking() {
        this.go = false;
    }
}