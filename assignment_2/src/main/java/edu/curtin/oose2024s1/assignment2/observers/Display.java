package edu.curtin.oose2024s1.assignment2.observers;

/*
 * The observer responsible for displaying text onto the console
 * It also tracks the count of failures
 */

public class Display implements BikeShopObserver
{
    private int failureCount;

    //Constructor
    public Display()
    {
        failureCount = 0;
    }

    //GETTER - Returns the count of failures
    public int getFailureCount()
    {
        return failureCount;
    }

    @Override
    public void update(String message)
    {
        System.out.println(message);
        if (message.contains("FAILURE")) { //search for 'FAILURE'
            failureCount++; //increment the failure count
        }
    }
}
