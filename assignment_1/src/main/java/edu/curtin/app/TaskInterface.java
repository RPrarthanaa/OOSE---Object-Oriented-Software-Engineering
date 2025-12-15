package edu.curtin.app;

import java.util.*;
import java.io.*;

/*
 * Represents the structure of any tasks of a work breakdown structure; tasks with subtasks (TaskList) 
 * and those without (SimpleTaask)
 */
public interface TaskInterface 
{
    /*
     * The task's sub task list is returned (if any)
     */
    List<TaskInterface> getSubTaskList();

    /*
     * The task's parent ID
     */
    String getParentTaskID();

    /*
     * The task's ID
     */
    String getCurrentTaskID();

    /*
     * Returns sum of all estimates by iterating over the Work Breakdown Structure, starting from the Root
     */
    int getSumOfEffortEstimate();

    /*
     * Returns count of tasks with effortEstimate = 0 by iterating through the Structure consisting of TaskInterface objects, starting from the Root
     */
    int getUnknownTaskCount();
    
    /*
     * Checks for an existing task within the Work Breakdown Structure which has the same task ID as the provided ID.
     */
    boolean hasTask(String taskID);
    
    /*
     * Displays this task with spacing infront of each String to indicate a visual tree structure
     */
    void display(String indent);

    /*
     * Iterates through the Work Breakdown Structure to find the task that matches with the provided taskID and decides on 
     * a final estimate through Planning Poker.
     * If the task is a TaskList object (i.e. it has subtasks), it iterates through every sub task and for each sub task, 
     * this operation is carried out (by comparing the parent ID of the simpleTasks with the taskID parameter, which is updated
     * recursively). 
     */
    void goToTask(String taskID, int n, ApproachInterface approach);

    /*
     * Writes to the same file the sample data was being read from, in the same format as it was in the file.
     * TaskLists will have three fields in the format : "<parentTaskID> ; <currentTaskID> ; <description>"
     * SimpleTasks have an additional field (effort estimate) to be included
     *      "<parentTaskID> ; <currentTaskID> ; <description> ; <effortEstimate>" if effortEstimate > 0
     *      "<parentTaskID> ; <currentTaskID> ; <description> ; " if effortEstimate is 0
     */
    void write(PrintWriter writer) throws IOException;

}