package com.kenzie.library;

//Purpose:
// 1. Design/Code the Wagon object
// 2. Code the rules for loading the wagon,
// 3. Checking the health of the travelers.

import java.util.Arrays;

public class Wagon {
    private int capacity;

    Traveler[] passengers;

    //will keep count of number of Passengers
    private int currentPassengerCount;

    public Wagon(int capacity) {
        this.capacity = capacity;
        this.currentPassengerCount =0;
        this.passengers = new Traveler[capacity];
    }
    //getters and setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Traveler[] getPassengers() {
        return passengers;
    }

    public void setPassengers(Traveler[] passengers) {
        this.passengers = passengers;
    }

    public int getAvailableSeatCount(){
        int emptySeatCount = 0;
        // seats available in wagon to be filled
        emptySeatCount = getCapacity() - currentPassengerCount;
        //System.out.println(emptySeatCount);
        return emptySeatCount;
    }
    public boolean join(Traveler traveler){
        //if space is available, fill the array with passengers and return true;

        if(getAvailableSeatCount()>0) {//there is space

            // index at which to place the new traveler[currentPassengerCount]
            //first passenger gets seat at 0th index, currentPassengerCount increased
            // now index 1, is available for next passenger

            passengers[currentPassengerCount] = traveler;
            currentPassengerCount++;
            //System.out.println(true +" " +currentPassengerCount +" " +passengers);
            return true;
        }
        System.out.println(false);
        return false;
    }
    public boolean shouldQuarantine(){

        //if at least one unhealthy person in the wagon return true
        for(Traveler passenger: passengers){
            //if there are passengers in the wagon check if any are unhealthy
            if(passenger != null && !passenger.isHealthy) {
                //System.out.println(true);
                return true;
            }
        }
        //System.out.println(false);
        return false;
    }

    public int totalFood(){

        int totalAmountOfFood =0;
        for(Traveler passenger: passengers){
            //if wagon has passenger or passengers
            if (passenger != null) {
                int foodUnitPerPassenger = passenger.getFood();
                totalAmountOfFood = totalAmountOfFood + foodUnitPerPassenger;
            }
        }
        //System.out.println(totalAmountOfFood);
        return totalAmountOfFood;
    }
    public void loadWagon(int numTravelers, int numHunters, int numDoctors){


        for (int i=0; i<numTravelers; i++) {
            //Call new to make sure a unique Traveler is added
            Traveler mrTraveler = new Traveler("Traveler-"+i);
            join(mrTraveler);
        }
        //System.out.println(passengers);

        for (int i=0; i<numHunters; i++) {
            Hunter mrHunter = new Hunter("Hunter-"+i);
            join(mrHunter);
        }
        //System.out.println(passengers);

        for (int i=0; i<numDoctors; i++) {
            Doctor mrDoctor = new Doctor("Doctor-"+i);
            join(mrDoctor);
        }
        //System.out.println(passengers);
    }
}