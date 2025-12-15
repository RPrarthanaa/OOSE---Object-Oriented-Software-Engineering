package edu.curtin.oose2024s1.assignment2;

import java.io.IOException;
import java.util.logging.*;

import edu.curtin.oose2024s1.assignment2.factory.StateFactory;
import edu.curtin.oose2024s1.assignment2.observers.*;


/**
 * AUTHOR: Prarthanaa Ragulan (21323157)
 * LAST MODIFIED: 29/05/2024
 * PURPOSE: This class serves as the entry point for the Bike Shop Simulation Program, 
 *          which simulates a bike shop that can be used to purchase and service bikes.
 *          It uses the BikeShopInput class to generate input messages, and the BikeShop class
 *          to simulate the bike shop. It uses the WriteToFile and Display classes to
 *          write and display the results of the simulation.
 */
public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args)
    {   
        //var inp = new BikeShopInput();
        var inp = new BikeShopInput(123);  // Seed for the random number generator
        var shop = new BikeShop();
        
        int time = 0; //track the day, 1 second = 1 simulated day
        int messageCount = 0; //tracks number of input messages (both valid and invalid )

        try
        {
            var writer = new WriteToFile();
            var display = new Display();

            //register the observers
            shop.addObserver(writer);
            shop.addObserver(display);

            writer.clearFile(); //clear the file of pre-existing data

            logger.info("Bike Shop Simulation Commenced");
            shop.notifyObservers("WELCOME TO THE BIKE SHOP SIMULATOR!!");
            while(System.in.available() == 0)
            {
                shop.setTime(time); //set the current day
                shop.notifyObservers("----------------");

                String msg = inp.nextMessage(); 
                
                logger.info("Process Messages");
                while(msg != null)
                {
                    if (!msg.isEmpty()) //ignore empty messages
                    {
                        processMessages(msg, shop);
                        messageCount++; //increment message count 
                    }
                    msg = inp.nextMessage();
                }
                shop.notifyObservers(shop.getStats()); //display and log current day statistics

                String logStatement = String.format("Day %d Completed", time);
                logger.info(logStatement);
                
                time += 1; //increment day

                // Wait 1 second
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    throw new AssertionError(e);
                }
            }

            logger.info("Bike Shop Simulation Ended");
            
            //display and log overall simulation statistics 
            shop.notifyObservers("\n\n**********************************************");
            shop.notifyObservers(String.format("Total_Input_Messages: %d   Total_Failures: %d", messageCount, display.getFailureCount()));
            shop.notifyObservers("**********************************************");
        }
        catch(AssertionError e)
        {
            logger.warning(() -> e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("Error reading user input");
            logger.severe("Error reading user input");
        }
    }

    public static void processMessages(String msg, BikeShop shop)
    {
        String[] parts = msg.split(" ", 2); 
        String messageType = parts[0]; //get the message

        var stateFactory = new StateFactory();
        var initialState = stateFactory.assignInitialState(messageType, shop.getTime()); //get the initial state of the bike 

        //call the appropriate method based on the message type 
        //parts[1] is the email of the customer
        if (messageType.equals("DROP-OFF"))
        {
            shop.notifyObservers(msg);
            shop.dropOff(parts[1], initialState); 
        }
        else if (messageType.equals("PICK-UP"))
        {
            shop.notifyObservers(msg);
            shop.pickup(parts[1]);
        }
        else if (messageType.equals("PURCHASE-IN-STORE"))
        {
            shop.notifyObservers(msg);
            shop.purchaseInStore(initialState);
        }
        else if (messageType.equals("PURCHASE-ONLINE"))
        {
            shop.notifyObservers(msg);
            shop.purchaseOnline(parts[1], initialState);
        }
        else if (messageType.equals("DELIVERY"))
        {
            shop.notifyObservers(msg);
            shop.delivery();
        }
        else //parsing error
        {
            shop.notifyObservers("FAILURE: Invalid Message");
            logger.info("FAILURE: Invalid Message");
        }
    }
}
