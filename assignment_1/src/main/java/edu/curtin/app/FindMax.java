package edu.curtin.app;

import java.util.List;

/*
 * Locates the highest estimate from the list of estimates and to set as the final estimate
 */
public class FindMax implements ApproachInterface
{
    @Override
    public int doOperation(List<Integer> effortList) 
    {
        int max = effortList.get(0);
        for (int i = 1; i < effortList.size(); i++)
        {
            if (effortList.get(i) > max)
            {
                max = effortList.get(i);
            }
        }
        return max;
    }
}
