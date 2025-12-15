package edu.curtin.app;

import java.util.*;
import java.util.logging.Logger;
import java.io.*;

/*
 * Represents the sub tasks (tasks not broken down further) of the Work Breakdown Structure
 */

public class SimpleTask implements TaskInterface
{
    private String parentTaskID;
    private String currentTaskID;
    private String description;
    private int effortEstimate;
    
    //Constructor
    public SimpleTask(String parentTaskID, String currentTaskID, String description, int effortEstimate)
    {
        this.parentTaskID = parentTaskID;
        this.currentTaskID = currentTaskID;
        this.description = description;
        this.effortEstimate = effortEstimate;
    }

    /*
     * Sets an estimate for effortEstimate
     * Method is private since users are not permitted to modify the 
     * field directly but instead through another method
     */
    private void setEffortEstimate(int effortEstimate){
        this.effortEstimate = effortEstimate;
    }

    /*
     * Returns null as subtasks are not broken down further
     */
    @Override
    public List<TaskInterface> getSubTaskList(){
        return null;
    }

    @Override
    public String getParentTaskID(){
        return this.parentTaskID;
    }
    @Override
    public String getCurrentTaskID(){
        return this.currentTaskID;
    }

    /*
     * Returns the effort estimate since one estimate is present
     */
    @Override
    public int getSumOfEffortEstimate(){
        return this.effortEstimate;
    }

    /*
     * Returns 1 if effort estimate is 0
     */
    @Override
    public int getUnknownTaskCount(){
        if (this.effortEstimate == 0)
        {
            return 1;
        }
        return 0;
    }

    /*
     * Compares the task IDs to check if the task already exists
     * Returns true if already present
     */
    @Override
    public boolean hasTask(String taskID){
        if (currentTaskID.equals(taskID))
        {
            return true;
        }
        return false;
    }

    /*
     * Prints out the task with indents to illustrate the tree structure
     */
    @Override
    public void display(String indent) {
        if (effortEstimate == 0)
        {
            System.out.println(indent + currentTaskID + ": " + description);
        }
        else
        {
            System.out.println(indent + currentTaskID + ": " + description + ", effort = " + effortEstimate);
        }
    }

    /*
     * Searches for required tasks, comparing the taskID or the parentTaskID (if the parent task 
     * is selected) to find a match, and implementing a method to calculate an estimate for effort 
     */
    @Override
    public void goToTask(String taskID, int n,  ApproachInterface approach){
        if (taskID.equals(currentTaskID)|| taskID.equals(parentTaskID))
        {
            calculateFinalEstimate(n, approach);
        }
    }

    /*
     * Writes the task in a specific format to a file
     */
    @Override
    public void write(PrintWriter writer) throws IOException {
        if (effortEstimate == 0)
        {
            writer.write(parentTaskID + " ; " + currentTaskID + " ; " + description + " ; \n");
        }
        else
        {
            writer.write(parentTaskID + " ; " + currentTaskID + " ; " + description + " ; " + effortEstimate + "\n");
        }
    }

    /*
     * Prompts user for effort estimates, validates the inputs, stores each input 
     * in an Array List and then prints the Array List for easy review of inputs
     */
    private List<Integer> getUserEstimates(int n)
    {
        Scanner sc = new Scanner(System.in);
        List<Integer> effortEstimateList = new ArrayList<>();

        System.out.println("\nEnter " + n + " estimates for " + currentTaskID + ":");
        for (int i = 0 ; i < n; i++)
        {
            int input;
            do 
            {
                System.out.print("(" + (i+1) + ")");
                input = sc.nextInt();
                if (input < 0) //for negative effort estimates, print an error message
                {
                    System.out.println("Invalid Estimate! Try Again!");
                }
            }while (input < 0); //until user estimate is valid
            effortEstimateList.add(input);
        }
        System.out.println("\nEstimates For " + currentTaskID + ": " + effortEstimateList); //print out the list

        return effortEstimateList;
    }
    
    /*
     * Implements the logic for calculating effortEstimate using planning poker
     * after which the final estimate is set as the new effortEstimate
     */
    private void calculateFinalEstimate(int n,  ApproachInterface approach){
        Logger logger = Logger.getLogger(SimpleTask.class.getName());

       //list to store estimates from users during planning poker
        List<Integer> effortEstimateList = getUserEstimates(n);

        //assuming estimate inputs are identical or there is only one estimate
        int finalEstimate = effortEstimateList.get(0);
        setEffortEstimate(finalEstimate);
        
        for (int i = 0; i < n-1; i++)
        {
            //if inputs are not identical
            if (!effortEstimateList.get(i).equals(effortEstimateList.get(i+1)))
            { 
                finalEstimate = approach.doOperation(effortEstimateList); //apply the selected approach and return the final estimate
                setEffortEstimate(finalEstimate); //set the estimate
                logger.info(() -> "Effort for Task " + currentTaskID + " Set Successfully");
                break;
            }
        }
        System.out.println("Final Estimate for " + currentTaskID + ": " + finalEstimate);
    }
}