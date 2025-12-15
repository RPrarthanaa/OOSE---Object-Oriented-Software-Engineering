package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.Bike;

/*
 * Defines the interface for different states of a bike
 */
public interface BikeState 
{
    void purchaseInStore(Bike bike); 
    void purchaseOnline(Bike bike);

    void twoDays(Bike bike, int currentTime);
    
    void onlinePurchasePickup(Bike bike);
    void afterServicePickup(Bike bike);
}
