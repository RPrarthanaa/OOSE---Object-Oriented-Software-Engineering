package edu.curtin.oose2024s1.assignment2.factory;

import edu.curtin.oose2024s1.assignment2.states.*;
public class StateFactory 
{
    /*
     * Assigns an initial state to the bike based on the message received 
     */
    public BikeState assignInitialState(String message, int time)
    {
        BikeState stateloader = null;
        if (message.contains("PURCHASE-ONLINE") || message.contains("PURCHASE-IN-STORE")) //bike for purchase
        {
            stateloader = new ForPurchaseState();
        }
        else if (message.contains("DROP-OFF")) //bike for service
        {
            stateloader = new InServiceState(time);
        }
        return stateloader;
    }
}
