package edu.curtin.app;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Author : Prarthanaa Ragulan (21323157)
 * Last Date Modified : 15/04/2024
 * Purpose : Serves as the entry into the application
 */

public class WBSEditorApp 
{
    private static final Logger logger = Logger.getLogger(WBSEditorApp.class.getName());

    public static void main(String[] args) 
    {
        if (args.length == 1) 
        {
            String filename = args[0];
            TaskInterface workBreakdownStructure;
            FileIO fileIO = new FileIO();
            SettingsConfiguration configureSettings = new SettingsConfiguration();
                
            try
            {
                logger.log(Level.INFO, "Reading Sample File of Task Data");
                workBreakdownStructure = fileIO.readWBS(filename); //read file

                menu(workBreakdownStructure, configureSettings); //display menu

                logger.log(Level.INFO, "Updating WBS Data in Sample File");
                fileIO.writeToFile(filename, workBreakdownStructure); //write to file
            }
            catch(WBSFormatException error)
            {
                System.err.println(error.getMessage());
                logger.severe(() -> error.getMessage());
            }
            catch(FileNotFoundException error)
            {
                System.err.println("File: '" + filename + "' Not Found!");
                logger.severe(() -> "File: '" + filename + "' Not Found!");
            }
            catch(IOException error)
            {
                System.err.println("Error in File Input/Output: " + error.getMessage());
                logger.log(Level.SEVERE, "Error in File Input/Output: ", error);
            }
            
        }
        else //incorrect number of command line arguments
        {
            System.err.println("Invalid Argument Count: (include only filename/filepath as extra argument)!");
            logger.log(Level.WARNING, "Invalid Argument Count!");           
        }        
    }

    /*
     * Displays the menu and calls functions to execute the menu options until user chooses to exit
     */
    public static void menu(TaskInterface workBreakdownStructure, SettingsConfiguration configSettings) 
    {
        Scanner sc = new Scanner(System.in);
        configSettings.initializeApproachMap(); //populate map

        boolean exitLoop = false;
        while (!exitLoop) 
        {
            try
            {
                displayWBS(workBreakdownStructure); //display the tree
    
                System.out.println("Choose one of the options");
                System.out.println("> 1. Estimate Effort");
                System.out.println("> 2. Configure Settings");
                System.out.println("> 3. Quit");
                System.out.print("Choice: ");
                int choice = sc.nextInt();            
    
                switch (choice) {
                    case 1:
                        estimateEffort(workBreakdownStructure, configSettings);
                        break;
                
                    case 2:
                        configure(configSettings);
                        break;
    
                    case 3:
                        System.out.println("Exiting Now ...");
                        exitLoop = true;
                        break;
    
                    default: //any other numerical choice
                        throw new IllegalArgumentException("Invalid Choice! Try Again");
                }
            }
            catch(InputMismatchException error)
            {
                System.err.println("\nError in User Input: Invalid Type!");
                sc.nextLine(); //to consume new line character
                logger.log(Level.WARNING, "Error in User Input: Invalid Type!");
            }
            catch(IllegalArgumentException | NoSuchElementException error)
            {
                System.err.println(error.getMessage());
                logger.warning(() -> error.getMessage());
            }            
        }
        sc.close(); //close scanner
    }

    /*
     * Calulates effort estimates for valid task IDs
     */
    public static void estimateEffort(TaskInterface workBreakdownStructure, SettingsConfiguration configSettings)
    {
        logger.log(Level.INFO, "Estimating Effort ...");
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter a Task ID: ");
        String taskID = sc.nextLine();

        //checks if task does not exist and throws exception if true
        if (!workBreakdownStructure.hasTask(taskID)){
            throw new NoSuchElementException("Invalid TaskID: Task " + taskID + " Not Found!");
        }

        workBreakdownStructure.goToTask(taskID, configSettings.getN(), configSettings.useApproach());
        logger.log(Level.INFO, "Effort Estimates Updated Successfully");
    }

    /*
     * Sets the settings for the number of people involved in planning poker and
     * the approach for reconciling the estimates
     */
    public static void configure(SettingsConfiguration configSettings)
    {
        logger.log(Level.INFO, "Configuring Settings ...");
        Scanner sc = new Scanner(System.in);

        System.out.print("\nSet a new N (number of people in planning poker): ");
        configSettings.setN(sc.nextInt());

        System.out.println("\nSet an approach from the following");
        System.out.println("> 1. Take the highest estimate");
        System.out.println("> 2. Take the median estimate");
        System.out.println("> 3. Decide and enter an estimate");
        System.out.print("Approach: ");

        configSettings.setOption(sc.nextInt());
        System.out.println("Settings saved!");
        logger.log(Level.INFO, "Configuration Settings Updated Successfully");
    }

    /*
     * Displays the Work Breakdown Structure in a visible tree format along with the total 
     * of the effort estimates and the count of tasks with estimates not yet filled
     */
    public static void displayWBS(TaskInterface workBreakdownStructure)
    {
        System.out.println("\n********************************************************");

        workBreakdownStructure.display("");
        System.out.println("--------------------------------------------------------");
        System.out.println("Total known effort = " + workBreakdownStructure.getSumOfEffortEstimate());
        System.out.println("Unknown Tasks: " + workBreakdownStructure.getUnknownTaskCount());

        System.out.println("********************************************************\n");
    }
}
