package edu.curtin.oose2024s1.assignment2;

import edu.curtin.oose2024s1.assignment2.states.*;

public class Bike 
{
    private BikeState state;
    private BikeShop bikeShop; //reference to the bikeshop
    private boolean serviced; //track whether the bike has been serviced
    private String customerEmail;

    //Constructor
    public Bike(BikeShop bikeShop, BikeState state, String customerEmail) 
    { 
        this.bikeShop = bikeShop; 
        serviced = false;
        this.state = state; //set initial bike state
        this.customerEmail = customerEmail; //register associated email (if any)
    }

    //Transition to different states
    public void setState(BikeState state)
    {
        this.state = state;
    }

    //STATE DEPENDENT METHODS
    public void purchaseInStore()
    {
        state.purchaseInStore(this);
    }
    public void purchaseOnline()
    {
        state.purchaseOnline(this);
    }
    public void twoDays(int time)
    {
        state.twoDays(this, time);
    }
    public void onlinePurchasePickup()
    {
        state.onlinePurchasePickup(this);
    }
    public void afterServicePickup()
    {
        state.afterServicePickup(this);
    }
    

    //Getters and setters
    public String getEmail() { return customerEmail; }
    public void setEmail(String customerEmail) 
    { 
        this.customerEmail = customerEmail;
    }

    public BikeShop getBikeShop() { return bikeShop; }

    public boolean getServiced() { return serviced; }
    public void setServiced() { serviced = true; } //if repairs are completed
}