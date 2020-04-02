package component;

import utility.FileIO;
import utility.TO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoskildeHaveID
{
    private static final String pathToIDFile = "resource/id.txt";
    private static RoskildeHaveID instance; // singleton instance, accessed through instance()
    private int idCount; // initialized from file, when a new id is generated, the corresponding file is
    //                      updated  - see getNewID()

    private RoskildeHaveID()
    {
        BufferedReader bufferedReader = null;
        try
        {
            bufferedReader = new BufferedReader(new FileReader(pathToIDFile));
        } catch(FileNotFoundException e)
        {
            System.out.println(TO.blue("Fra DolphinID constructoren:\n") +
                                 "Desværre er der bøvl med en fil i systemet:\n" +
                                 TO.green("Lav venligst en fil ved navn id.txt i ~/src/resource-mappen;\n") +
                                 "id.txt skal indeholde ét enkelt heltal.\n" +
                                 "Dette heltal skal være lig med eller\n" +
                                 " højere end det højeste medlems-nummer.");
            e.printStackTrace();
        }

        String tempLine;
        try
        {
            if((tempLine = bufferedReader.readLine()) != null)
            {
                idCount = Integer.parseInt(tempLine);
            }
        } catch(IOException e)
        {
            System.out.println(TO.blue("Fra DolphinID constructoren:\n") +
                               "Desværre er der bøvl med en fil i systemet:\n" +
                               TO.green("Tjek venligst at id.txt i ~/src/resource-mappen" +
                                        " kun indeholder ét heltal.") +
                               "\nDette heltal skal være lige med eller\n" +
                               " højere end det hidtil højeste medlems-nummer.");
            e.printStackTrace();
        }

    }

    public static RoskildeHaveID instance()
    {
        if(instance == null) // if there is no instance yet, make one.
        {
            instance = new RoskildeHaveID();
        }
        return instance;
    }
    /** Method returns a new id. <b>NB: It is not written to file yet. Call updateIDFile to do that.</b> */
    public int getNewID()
    {
        ++idCount; // incrementing idCount to a new value before returning
        updateIDFile(); // writing new id count to file id.txt
        return idCount;
    }

    /** Method updates (actually overwrites) id.txt content to match internal idCount:
     * It returns nothing, but will yell loudly at user if exceptions occur. */
    public void updateIDFile(){
        ArrayList<String> holderArray = new ArrayList<>();
        holderArray.add(String.valueOf(idCount));
        FileIO.writeLinesToTextFile(holderArray,pathToIDFile,false);
    }

}
