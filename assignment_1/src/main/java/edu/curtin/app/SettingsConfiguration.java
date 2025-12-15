package edu.curtin.app;

import java.util.*;

/*
 * Represents the class that handles the configuration settings for (effort estimate) operations on the Work Breakdown Structure
 */
public class SettingsConfiguration
{
    private Map<Integer, ApproachInterface> approach = new HashMap<>();
    private int n;
    private int option;

    //Constructor with default values for N and option
    public SettingsConfiguration()
    {
        this.n = 3;
        this.option = 3;
    }

    /*
     * Initializes the approach map with keys for each strategy 
     */
    public void initializeApproachMap()
    {
        approach.put(1, new FindMax());
        approach.put(2, new FindMedian());
        approach.put(3, new DiscussEstimate());
    }

    /*
     * n indicates the number of people involved in effort estimation
     */
    public int getN(){
        return this.n;
    }
    public void setN(int n){
        if(n <= 0){
            throw new IllegalArgumentException("N should be a positive integer");
        }
        this.n = n;
    }

    /*
     * option specifies which approach is selected
     */
    public int getOption(){
        return this.option;
    }
    public void setOption(int option){
        if (option <= 0 || option > 3){
            throw new IllegalArgumentException("Invalid Approach Input: Option Does Not Exist!");
        }
        this.option = option;
    }

    /*
     * Retrieves the approach strategy associated with the 'option' from the approach map
     * without direct access to the map
     */
    public ApproachInterface useApproach()
    {
        return approach.get(option);
    }
}
