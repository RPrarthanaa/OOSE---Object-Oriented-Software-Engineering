package edu.curtin.app;

import java.util.*;

/*
 * Asks for user's input on the final estimate
 */
public class DiscussEstimate implements ApproachInterface
{
    @Override
    public int doOperation(List<Integer> effortList) 
    {
        System.out.println("Please discuss and enter a single revised estimate");
        System.out.print("Revised Estimate: ");
        return (new Scanner(System.in).nextInt());
    }
}
