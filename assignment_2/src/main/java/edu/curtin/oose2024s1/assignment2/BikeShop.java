package edu.curtin.oose2024s1.assignment2;

import java.util.*;

import edu.curtin.oose2024s1.assignment2.observers.*;
import edu.curtin.oose2024s1.assignment2.states.*;

public class BikeShop 
{    
    private int time;
    private int balance; //track account balance
    private int bikeCount;
    private int servicingBikes; //track number of bikes currently being serviced
    private int availableBikes; //track number of bikes currently available for purchase
    private int waitingBikes; //track number of bikes currently waiting for pickup

    private List<BikeShopObserver> observers = new ArrayList<>(); 
    private List<Bike> purchasedOnlineBikes = new ArrayList<>(); //track bikes purchased online
    private List<Bike> serviceBikes = new ArrayList<>(); //track bikes dropped off for service

    //Constructor
    public BikeShop()
    {
        time = 0;
        balance = 15000;
        bikeCount = 50;
        availableBikes = 50;
        servicingBikes = 0;
        waitingBikes = 0;
    }

    public void addObserver(BikeShopObserver observer) 
    { 
        observers.add(observer); //register observers
    }
    public void removeObserver(BikeShopObserver observer) 
    { 
        observers.remove(observer); //remove observers
    }
    public void notifyObservers(String message) 
    { 
        for (BikeShopObserver observer : observers)
        {
            observer.update(message); //notifies observers during events
        }
    }

    // Deliver 10 bikes to the bike shop
    public void delivery()
    {
        if (bikeCount <= 90 && balance >= 10000) //bike shop has space and has enough money
        {
            bikeCount += 10;
            availableBikes += 10;
            balance -= 500;
        } 
        else if (balance < 10000) //not enough money
        {
            notifyObservers("FAILURE: Insufficient Account Balance");
        }
        else if (bikeCount > 90) //not enough space
        {
            notifyObservers("FAILURE: Insufficient Space Available");
        }
    }

    // Drop off a bike at the bike shop
    public void dropOff(String email, BikeState bikeState)
    {
        if (bikeCount < 100)
        {
            Bike bike = new Bike(this, bikeState, email);
            bikeCount += 1;
            servicingBikes += 1;

            serviceBikes.add(bike);
        }
    }

    // Purchase a bike in-store from the bike shop
    public void purchaseInStore(BikeState bikeState)
    {
        Bike bike = new Bike(this, bikeState, "");
        bike.purchaseInStore(); //perform purchase action
    }

    // Purchase a bike online from the bike shop
    public void purchaseOnline(String email, BikeState bikeState)
    {
        Bike bike = new Bike(this, bikeState, email);
        bike.purchaseOnline(); //perform purchase action

        purchasedOnlineBikes.add(bike); //add bike to purchase list
    }

    // Pickup a bike from the bike shop
    // Determine if the bike being picked up was purchased online or dropped off for service
    public void pickup(String email)
    {
        //check if bike was serviced
        for (Bike bike : serviceBikes)
        {
            if (bike.getEmail().equals(email)) //bike is in service list
            {
                bike.afterServicePickup();
                return;
            }
        }
        
        //if not registered for service,
        //check if bike was purchased online
        for (Bike bike : purchasedOnlineBikes)
        {
            if (bike.getEmail().equals(email)) //bike is in purchase list
            {
                bike.onlinePurchasePickup();
                return;
            }
        }
        
        //if neither
        notifyObservers("FAILURE: No Bike Registered To This Customer Email"); 
    }

    // Return a String representation of a day's statistics
    public String getStats()
    {
        return String.format("\nDay: %d  Cash: $%d  Bikes_For_Purchase: %d   Bikes_In_Service: %d   Bikes_For_Pickup: %d  Total_Bike_Count: %d", 
                                    time, balance, availableBikes, servicingBikes, waitingBikes, bikeCount);
    }

    //GETTERS
    public int getBalance() { return balance; }
    public int getTime() { return time; }
    public int getBikeCount() { return bikeCount; }

    public int getAvailableBikes() { return availableBikes; }
    public int getServicingBikes() { return servicingBikes; }
    public int getWaitingBikes() { return waitingBikes; }

    public List<Bike> getPurchasedOnlineBikes() { return purchasedOnlineBikes; }
    public List<Bike> getServiceBikes() { return serviceBikes; }

    //SETTERS
    public void setAvailableBikes(int availableBikes) { this.availableBikes = availableBikes; }
    public void setServicingBikes(int servicingBikes) { this.servicingBikes = servicingBikes; }
    public void setWaitingBikes(int waitingBikes) { this.waitingBikes = waitingBikes; }
    public void setBalance(int balance) { this.balance = balance; }

    public void setBikeCount(int bikeCount) 
    { 
       this.bikeCount = bikeCount; 
    }

    public void setTime(int time) 
    { 
        this.time = time; 
        updateBalance(); //check if employee needs to be paid
        checkServiceBikes(); //check if bikes finished being serviced
    }

    public void updateBalance()
    {
        if (time != 0 && time%7 == 0){ //employee's pay day
            balance -= 1000; //deduct salary
        }
    }

    public void checkServiceBikes()
    {
        for (Bike bike : serviceBikes){ //check progress of repairs
            bike.twoDays(time);
        }
    }    
}