package edu.curtin.app;

import java.util.*;
import java.io.*;

/*
 * Represents tasks which are broken down further into subtasks
 */
public class TaskList implements TaskInterface
{
    private List<TaskInterface> subtasks = new ArrayList<>();
    private String parentTaskID;
    private String currentTaskID;
    private String description;

    //Constructor
    public TaskList(String parentTaskID, String currentTaskID, String description)
    {
        this.parentTaskID = parentTaskID;
        this.currentTaskID = currentTaskID;
        this.description = description;
    }

    /*
     * Adds a subtask to the list
     */
    public void addSubTask(TaskInterface subTask){
        subtasks.add(subTask);
    }

    @Override
    public List<TaskInterface> getSubTaskList(){
        return this.subtasks;
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
     * Iterates through each task in the subtasks list and returns the sum
     */
    @Override
    public int getSumOfEffortEstimate(){
        int sum = 0;
        for (TaskInterface subTask : subtasks)
        {
            sum += subTask.getSumOfEffortEstimate();
        }
        return sum;
    }

    /*
     * Iterates through each subtask in the list to return the count of unknown tasks
     */
    @Override
    public int getUnknownTaskCount(){
        int count = 0;
        for (TaskInterface task : subtasks)
        {
            count += task.getUnknownTaskCount();
        }
        return count;
    }

    /*
     * Compares the parameter, taskID to the ID of either the taskList or each of it's subtasks
     */
    @Override
    public boolean hasTask(String taskID){
        boolean found = false;
        if (currentTaskID.equals(taskID))
        {
            found = true;
        }
        else
        {
            for (TaskInterface task : subtasks)
            {
                found = task.hasTask(taskID);
                if (found == true) { break; }
                
            }
        }
        return found;
    }

    /*
     * Displays each task and subtask with indents for visibility and ignores printing out the root of the work breakdwon structure
     */
    @Override
    public void display(String indent) {
        if (!currentTaskID.isEmpty()) //ignore first row
        {
            System.out.println(indent + currentTaskID + ": " + description);
            indent += "     ";
        }        
        
        for (TaskInterface subTask : subtasks)
        {
            subTask.display(indent);
        }
    }

    /*
     * Iterates through the subtask, and if a subtask is a TaskList object (i.e. it has subtasks), the parameter taskID
     * is updated to hold its taskID, so that it can recurse deeper into tasks that have subtasks and subtasks that have 
     * subtasks
     */
    @Override
    public void goToTask(String taskID, int n,  ApproachInterface approach){
        for (TaskInterface task : subtasks)
        {
            if (taskID.equals(task.getParentTaskID()) && task.getSubTaskList() != null)
            {
                taskID = task.getCurrentTaskID();
            }
            task.goToTask(taskID, n, approach);            
        }
    }

    /*
     * Writes the current task into a file (ignoring the root task) and iterates through its subtasks list to do the same
     */
    @Override
    public void write(PrintWriter writer) throws IOException {
        if (!currentTaskID.isEmpty())
        {
            writer.write(parentTaskID + " ; " + currentTaskID + " ; " + description + "\n");
        }
        for (TaskInterface task : subtasks)
        {
            task.write(writer);
        }
    }
}
