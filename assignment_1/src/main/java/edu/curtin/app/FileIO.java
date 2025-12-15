package edu.curtin.app;

import java.io.*;
import java.util.*;

/*
 * Represents the IO operations regarding file reading and file writing
 */
public class FileIO 
{
    /*
     * Reads a file containing sample task data and uses the composite pattern to design a
     * tree that contains tasks and subtasks with specific formats.
     * The root task is then returned.
     */
    public TaskInterface readWBS(String filename) throws IOException, WBSFormatException
    {
        Map<String, TaskList> wbs = new HashMap<>();
        TaskList rootTask = new TaskList("", "", ""); //root task has empty fields since not specified
        wbs.put("", rootTask);

        try(BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                line.trim();
                if (!line.isEmpty()) //ignore empty lines
                {
                    String[] parts = line.split(";", 4);
                    String parentTaskID = parts[0].trim();
                    String currentTaskID = parts[1].trim();
                    String description = parts[2].trim();

                    TaskList parentTask = wbs.get(parentTaskID);
                    if (parentTask == null) //if parent task doesn't exist or appears after sub task
                    {
                        throw new WBSFormatException("Parent Task " + parentTaskID + " is Not Found!");
                    }

                    switch (parts.length) 
                    {
                        case 3: //has subtasks
                            TaskList taskList = new TaskList(parentTaskID, currentTaskID, description);
                            parentTask.addSubTask(taskList);
                            wbs.put(currentTaskID, taskList);
                            break;
                    
                        case 4: //is subtask
                            int effortEstimate = 0; //set to zero initially

                            try 
                            {
                                if (!parts[3].trim().isEmpty()) //if effort estimate is not empty
                                {
                                    effortEstimate = Integer.parseInt(parts[3].trim());
                                    if (effortEstimate < 0){
                                        throw new WBSFormatException("Invalid Effort Estimate Format in File: Negative!");
                                    } 
                                }
                            } 
                            catch (NumberFormatException error) //returns the error location if not integer
                            {
                                throw new WBSFormatException("Invalid Effort Estimate Format - " + error, error);
                            }
                            
                            SimpleTask simpleTask = new SimpleTask(parentTaskID, currentTaskID, description, effortEstimate);
                            parentTask.addSubTask(simpleTask);
                            break;

                        default: //unexpected length
                            throw new WBSFormatException("Invalid Length of Line in File"); 
                    }
                }
                line = reader.readLine(); //read next line
            }
        }
        return rootTask;
    }

    /*
     * Writes to the sample task data file with updated values, overwriting existing data
     */
    public void writeToFile(String filename, TaskInterface workBreakdownStructure) throws IOException
    {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(filename)))
        {
            workBreakdownStructure.write(writer); 
        }
    }
}
