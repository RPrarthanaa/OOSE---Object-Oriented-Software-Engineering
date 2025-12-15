package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.*;

/*
 * Represents the bikes available for purchase and models specific behaviours for different purchases.
 */

public class ForPurchaseState implements BikeState
{
    @Override
    public void purchaseInStore(Bike bike)
    {
        var bikeShop = bike.getBikeShop();
        
        if (bikeShop.getAvailableBikes() >= 1)
        {
            bikeShop.setAvailableBikes(bikeShop.getAvailableBikes() - 1); //decrement count of available bikes
            bikeShop.setBikeCount(bikeShop.getBikeCount() - 1); //decrement count of bikes
            bikeShop.setBalance(bikeShop.getBalance() + 1000); //increment by profit
        }
        else //no bikes available
        {
            bikeShop.notifyObservers("FAILURE: No Bikes Available");
        }
    }

    @Override
    public void purchaseOnline(Bike bike)
    {
        var bikeShop = bike.getBikeShop();
        if (bikeShop.getAvailableBikes() >= 1)
        {
            bikeShop.setBalance(bikeShop.getBalance() + 1000); //increment by profit
            bikeShop.setAvailableBikes(bikeShop.getAvailableBikes() - 1); //decrement count of available bikes
            bikeShop.setWaitingBikes(bikeShop.getWaitingBikes() + 1); //increment count of waiting bikes
            
            bike.setState(new WaitingState()); //ForPurchase -> Waiting transition
        }
        else //no bikes available
        {
            bikeShop.notifyObservers("FAILURE: No Bikes Available");
        }
    }

    
    @Override
    public void twoDays(Bike bike, int currentTime) { /* do nothing */ }
    
    @Override
    public void onlinePurchasePickup(Bike bike) { /* do nothing */ }

    @Override
    public void afterServicePickup(Bike bike) { /* do nothing */ }
}