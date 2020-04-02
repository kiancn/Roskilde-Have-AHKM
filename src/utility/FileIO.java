package utility;

import java.io.*;
import java.util.ArrayList;

/**
 * This class will be under construction for a long time:
 * <p>- Intent is to only have methods that help load files
 * in interesting ways.<p/>
 * <p></p>
 */
public class FileIO
{
    /**
     * Method takes a file path and returns an arraylist of strings,
     * a string for each line; it will chop up long lines
     * <p> If you never want lines chopped, set maxLineLength to -1 </p>
     */
    public static ArrayList<String> load_TextFile_as_Strings(String pathToFile,
                                                             int maxLineLength)
    {
        /* BufferedReader holds the loaded file as to allow efficient file reading */
        BufferedReader bufferedReader = null;
        /* A string for each line, extra for each line chop */
        ArrayList<String> lines = new ArrayList<>();

        /* Opening file and buffering content. Might fail, so you gotta try... */
        try
        {
            bufferedReader = new BufferedReader(new FileReader(pathToFile));
            /* string will hold all incoming lines..*/
            String tempLine;

            /* while querying the buffer for next line does not result in null */
            while((tempLine = bufferedReader.readLine()) != null)
            {
                /* if maxLength supplied is -1, do no splitting, just add */
                if(maxLineLength == -1)
                {
                    lines.add(tempLine);
                } else if(tempLine.length() > maxLineLength)
                {
                    /* else, split long tempLine and add returned shortened string to lines-array */
                    ;
                    lines.addAll(Grouper.splitStringToLines(tempLine, maxLineLength));
                }
            }
        } catch(FileNotFoundException e)
        {
            /// bad style, doing just this. must fix asp
            e.printStackTrace();
        } finally // finally-section will be executed regardless
        {
            if(bufferedReader != null)
            {
                try
                {
                    /* closing buffered reader to avoid memory leaks */
                    bufferedReader.close();
                } catch(IOException e)
                {
                    System.out.println("FileIO: Closing file was interrupted and unsuccessful");
                    e.printStackTrace();
                }
            }

            return lines;
        }
    }

    /**
     * Method overwrites supplied ArrayList of String lines to file at supplied path; and
     * creates that file if it does not exist.
     *
     * @param appendToFile if <b>true</b>, file-write is destructive; lines are overwritten, original content
     *                  replaced; if <b>false</b> lines are added to the end of the lines in file.
     *                     It very much should be the reverse.
     */
    /* pathToFile is meant to be a relative path from resources-folder as root - not sure if this works yet */
    public static boolean writeLinesToTextFile(ArrayList<String> lines,
                                               String pathToFile,
                                               boolean appendToFile)
    {
        /* if there is no file by that name, it is created automatically */
        File file = new File(pathToFile);

        try
        {   /* file writer might not be able to */

            FileWriter fileWriter;
            fileWriter = new FileWriter(file,appendToFile);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(String line : lines)
            {
                printWriter.println(line); // println() here anal. with System.out.println()
//                System.out.println("Written to file: " + line);
            }

            /* calling close() has the effect of flushing the fileWriter buffered text
             *  to file - actually writing to file. Without closing nothing is written to file. */
            printWriter.close();

            return true; // success, so return true

        } catch(Exception e)
        {
            System.out.println("From FileIO: Something happened, here is the exception thrown:\n" + e.getMessage());
            return false;
        }
    }
}

/*
 * And this tutorial helped me split lines into wished for lengths;
 * http://www.davismol.net/2015/02/03/java-how-to-split-a-string-into-fixed-length-rows-without-breaking-the-words/
 *
 * All hail this mighty tutorial on reading a csv-file and splitting its lines (*this script is a thorough
 * makeover of the tutorial script):
 * https://crunchify.com/how-to-read-convert-csv-comma-separated-values-file-to-arraylist-in-java-using-split-operation/
 *
 * On writing to text files, this video was great:
 * https://youtu.be/k3K9KHPYZFc
 * Also:
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileWriter.html
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/PrintWriter.html
 * **/

