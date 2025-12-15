package edu.curtin.oose2024s1.assignment2.observers;

import java.io.*;
import java.util.logging.Logger;

/*
 * The observer responsible for writing the simulation results to a text file
 */

public class WriteToFile implements BikeShopObserver 
{
    private Logger logger = Logger.getLogger(WriteToFile.class.getName());
    
    @Override
    public void update(String message)
    {
        //write text to a file, appending to the end of the file
        try (var writer = new FileWriter("sim_results.txt", true)) 
        {
            writer.write(message + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error writing to file");
            logger.severe("Error writing to file");
        }
    }

    //method to clear the text file
    public void clearFile()
    {
        try (var ignored = new FileWriter("sim_results.txt"))
        {
            ignored.write(""); 
        }
        catch(IOException e)
        {
            System.out.println("Error writing to file");
            logger.severe("Error writing to file");
        }
    }
}
