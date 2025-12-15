package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.*;

/*
 * Represents the bikes currently being serviced
 */

public class InServiceState implements BikeState
{
    private int time; //track time bike entered this state

    public InServiceState(int time)
    {
        this.time = time;
    }

    @Override
    public void purchaseInStore(Bike bike) { /* do nothing */ }

    @Override
    public void purchaseOnline(Bike bike) { /* do nothing */ }

    
    @Override
    public void twoDays(Bike bike, int currentTime)
    {
        var bikeShop = bike.getBikeShop();
        if (currentTime - time >= 2) //checks if two (or more) days have passed in service
        {
            bikeShop.setWaitingBikes(bike.getBikeShop().getWaitingBikes() + 1); //increment count of bikes waiting for pickup
            bikeShop.setServicingBikes(bike.getBikeShop().getServicingBikes() - 1); //decrement count of bikes in service
            bike.setServiced(); //mark bike as serviced

            bike.setState(new WaitingState()); //InService -> Waiting transition
        }
    }

    @Override
    public void onlinePurchasePickup(Bike bike) { /* do nothing */ }

    @Override
    public void afterServicePickup(Bike bike) { /* do nothing */ }
}
