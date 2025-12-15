package edu.curtin.app;

import java.util.Collections;
import java.util.List;

/*
 * Sorts the ArrayList and then calculates the median from the sorted elements in the List as the final estimate
 */
public class FindMedian implements ApproachInterface
{
    @Override
    public int doOperation(List<Integer> effortList) 
    {
        int n = effortList.size();
        int median;

        Collections.sort(effortList); //sorts list
        if (n%2 == 0) 
        {
            median = (effortList.get(n/2) + effortList.get(n/2-1))/2; //find average between two mid values if even
        }
        else 
        {
            median = effortList.get(n/2); //get the mid value if odd
        }
        return median;
    }
}
