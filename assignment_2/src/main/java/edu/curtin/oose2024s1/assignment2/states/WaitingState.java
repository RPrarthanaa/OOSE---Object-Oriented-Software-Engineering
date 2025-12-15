package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.*;

/*
 * Represents the bikes awaiting pick up either after online purchase or completed maintenance
 */
public class WaitingState implements BikeState
{
    @Override
    public void purchaseInStore(Bike bike) { /* do nothing */ }

    @Override
    public void purchaseOnline(Bike bike) { /* do nothing */ }

    @Override
    public void twoDays(Bike bike, int currentTime) { /* do nothing */ }

    @Override
    public void onlinePurchasePickup(Bike bike)
    {
        var bikeShop = bike.getBikeShop();
        bikeShop.getPurchasedOnlineBikes().remove(bike);
        bikeShop.setWaitingBikes(bikeShop.getWaitingBikes() - 1); //decrement count of waiting bikes
        bikeShop.setBikeCount(bikeShop.getBikeCount() - 1); //decrement count of bikes
    }

    @Override
    public void afterServicePickup(Bike bike)
    {
        var bikeShop = bike.getBikeShop();
        if (bike.getServiced()) //if the bike is serviced, account for maintenance profit
        {
            bikeShop.getServiceBikes().remove(bike);
            bikeShop.setBalance(bikeShop.getBalance() + 100); //increment by profit from maintenance
            bikeShop.setWaitingBikes(bikeShop.getWaitingBikes() - 1); //decrement count of waiting bikes
            bikeShop.setBikeCount(bikeShop.getBikeCount() - 1); //decrement count of bikes
        }
        else
        {
            bikeShop.notifyObservers("FAILURE: Bike Still In Service");
        }   
    }
}
