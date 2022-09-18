package com.kenzie.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.NoSuchElementException;
import java.util.Random;


public class Main {

    /*** Declare Statics and Constants Here ***/
    //static variables that will be used to track progress:

    public static int daysTravelled;
    public static int milesTravelled;

    public static final int TOTAL_MILES = 1600; //Total miles required to reach Oregon
    public static final int MILES_PER_DAY = 20;//Total miles travelled per day
    public static final int FOOD_EXCHANGE = 2; //How much food the travelers give each other each time
    public static final int MAX_DAYS = 100; //Maximum number of days to reach Oregon or bust
    public static final int WAGON_SIZE = 12;//Total capacity -- choose either 4, 8 or 12
    public static final int HUNT_DAYS = 4; //this represents how often the party will stop to hunt, 4 means once every 4 days
    //The number of each type of Traveler is up to you to set. All three numbers together should total up to the value of WAGON_SIZE.
    public static final int NUM_TRAVELERS = 5;
    public static final int NUM_HUNTERS = 1;
    public static final int NUM_DOCTORS = 1;

    /***************** Assessment - Object Oriented Design ************************/

    // Purpose: Use a random number generator on hunting days to "roll" and select the wildlife available or that day.
    // modify the food store of Hunter class.
    // Ex: If BISON is available, the Hunter class gets to add +10 to the food hunted that day.
    // If RABBIT is available, add +4 to the food hunted that day.

    private static Random randomNumbers = new Random(); //1.static variable declaration

    private static void gameHunt() {
        // Creating an EnumMap of the Size enum
        EnumMap<Wildlife, Integer> gameHuntUnits = new EnumMap<>(Wildlife.class);
        gameHuntUnits.put(Wildlife.BEAR, 10);
        gameHuntUnits.put(Wildlife.BISON, 10);
        gameHuntUnits.put(Wildlife.DEER, 5);
        gameHuntUnits.put(Wildlife.RABBIT, 4);
        gameHuntUnits.put(Wildlife.SQUIRREL, 2);
        System.out.println("HuntMap: " + gameHuntUnits);

        Hunter hunter = new Hunter();
        hunt(hunter, gameHuntUnits);
    }

    public static int rollDice() {
        int dieValue = randomNumbers.nextInt(6); // first die roll
        return dieValue;
    }

    private static void hunt(Hunter hunter, EnumMap<Wildlife, Integer> gameHuntUnits) {
        int huntUnits = 0;
        int foodStore = hunter.getFood();

        switch (rollDice()) {
            case 1:
                huntUnits = gameHuntUnits.get(Wildlife.BEAR);
                System.out.println("Food stock before the hunt: "+ foodStore);
                System.out.println("Food hunted on hunt day: " + huntUnits + " units!");
                hunter.setFood(foodStore + huntUnits);
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
            case 2:
                huntUnits = gameHuntUnits.get(Wildlife.BISON);
                System.out.println("Food hunted on hunt day: " + huntUnits + " units!");
                hunter.setFood(foodStore + huntUnits);
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
            case 3:
                huntUnits = gameHuntUnits.get(Wildlife.DEER);
                System.out.println("Food hunted on hunt day: " + huntUnits + " units!");
                hunter.setFood(foodStore + huntUnits);
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
            case 4:
                huntUnits = gameHuntUnits.get(Wildlife.RABBIT);
                System.out.println("Food hunted on hunt day: " + huntUnits + " units!");
                hunter.setFood(foodStore + huntUnits);
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
            case 5:
                huntUnits = gameHuntUnits.get(Wildlife.SQUIRREL);
                System.out.println("Food hunted on hunt day: " + huntUnits + " units!");
                hunter.setFood(foodStore + huntUnits);
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
            default:
                System.out.println("No game! It was an unlucky hunt!");
                System.out.println("The current food stock is: " + foodStore + huntUnits);
                break;
        }
    }


    /*** DO NOT CHANGE THE CODE BELOW THIS LINE ***/
    public static void main(String[] args) {
        // This activity has a part one and part two
        //This will only run through if all of the required elements for Part 1 are coded
        OregonTrailPartOne();

        //This will only run through if all of the required elements for Part 2 are coded
        OregonTrailPartTwo();

    }

    //Oregon Trail Part 1 and Part 2 are coded with runtime variable and method invocation
    //Using reflect API in order to allow compile with activity components
    public static void OregonTrailPartOne() {
        try {
            //check that required methods are defined
            @SuppressWarnings("unchecked")
            Constructor doctorConstructor = Doctor.class.getConstructor(String.class);
            @SuppressWarnings("unchecked")
            Doctor drHouse = (Doctor) doctorConstructor.newInstance("Greg House");

            //check that required methods are defined
            @SuppressWarnings("unchecked")
            Constructor travelerConstructor = Traveler.class.getConstructor(String.class);
            @SuppressWarnings("unchecked")
            Traveler billyBob = (Traveler) travelerConstructor.newInstance("Billy Bob");

            @SuppressWarnings("unchecked")
            Traveler ezra = (Traveler) travelerConstructor.newInstance("Ezra");

            @SuppressWarnings("unchecked")
            Method eat = Traveler.class.getMethod("eat");
            eat.invoke(ezra);
            eat.invoke(ezra);

            @SuppressWarnings("unchecked")
            Method getIsHealthy = Traveler.class.getMethod("getIsHealthy");
            @SuppressWarnings("unchecked")
            Method heal = Doctor.class.getMethod("heal", Traveler.class);
            eat.invoke(ezra);
            eat.invoke(ezra);
            boolean isHealthyValue = (boolean) getIsHealthy.invoke(ezra);
            if (isHealthyValue) {
                System.out.println("Ezra is healthy!");
            } else {
                System.out.println("Oh no! Ezra is sick!");
                heal.invoke(drHouse, ezra);

                isHealthyValue = (boolean) getIsHealthy.invoke(ezra);
                if (isHealthyValue) {
                    System.out.println("Ezra is healed!!");
                } else {
                    System.out.println("Oh no! The doctor didn't heal");
                }
            }

            @SuppressWarnings("unchecked")
            Traveler horace = (Traveler) travelerConstructor.newInstance("Horace");
            @SuppressWarnings("unchecked")
            Traveler sylvia = (Traveler) travelerConstructor.newInstance("Sylvia");

            @SuppressWarnings("unchecked")
            Constructor hunterConstructor = Hunter.class.getConstructor(String.class);
            @SuppressWarnings("unchecked")
            Hunter steveRinella = (Hunter) hunterConstructor.newInstance("Steve Rinella");

            //call hunt and eat
            @SuppressWarnings("unchecked")
            Method hunt = Hunter.class.getMethod("hunt");
            @SuppressWarnings("unchecked")
            Method hunterEat = Hunter.class.getMethod("eat");
            @SuppressWarnings("unchecked")
            Method hunterGetFood = Hunter.class.getMethod("getFood");

            hunt.invoke(steveRinella);
            hunterEat.invoke(steveRinella);

            @SuppressWarnings("unchecked")
            int foodValue = (int) hunterGetFood.invoke(steveRinella);
            System.out.println("Steve hunted and ate. He has this much food - " + foodValue);

            //Traveler[] travelers = new Traveler[]{billyBob, ezra, drHouse, steveRinella, horace, sylvia};
            Object[] travelers = new Object[]{billyBob, ezra, drHouse, steveRinella, horace, sylvia};

            System.out.println("Oh no! Billy Bob ate the supplies! No food for him!");
            eat.invoke(billyBob);
            eat.invoke(billyBob);

            @SuppressWarnings("unchecked")
            Method getName = Traveler.class.getMethod("getName");
            @SuppressWarnings("unchecked")
            Method giveFood = Hunter.class.getMethod("giveFood", Traveler.class, int.class);

            for (Object traveler : travelers) {
                int amountOfFood = (int) Math.round(Math.random());
                @SuppressWarnings("unchecked")
                String nameValue = (String) getName.invoke((Traveler) traveler);
                System.out.println("Traveler: " + nameValue);
                if (!(traveler instanceof Hunter) && !nameValue.equals("Billy Bob")) {
                    giveFood.invoke(steveRinella, traveler, amountOfFood);
                    System.out.println("\tSteve gave " + nameValue + " " + amountOfFood + " food ");
                }

                @SuppressWarnings("unchecked")
                boolean healthyValue = (boolean) getIsHealthy.invoke(traveler);
                @SuppressWarnings("unchecked")
                Method getFood = Traveler.class.getMethod("getFood");

                @SuppressWarnings("unchecked")
                int travelerFoodValue = (int) getFood.invoke(traveler);
                System.out.println("\thas " + travelerFoodValue + " food\n");

                if (healthyValue) {
                    System.out.println("\tis very healthy\n");
                } else {
                    System.out.println("\tis not healthy\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Part I: All required classes, constructors, and methods must be defined");
            System.out.println(e.getMessage());
        }
    }

    //Oregon trail game engine with runtime method and variable invocation
    public static void OregonTrailPartTwo() {
        try {
            //load all static and constant variables if they exist. Otherise throw exception
            @SuppressWarnings("unchecked")
            int wagon_size = (int) OregonTrail.getStaticFieldValue("WAGON_SIZE");
            @SuppressWarnings("unchecked")
            int max_days = (int) OregonTrail.getStaticFieldValue("MAX_DAYS");
            @SuppressWarnings("unchecked")
            int hunt_days = (int) OregonTrail.getStaticFieldValue("HUNT_DAYS");
            @SuppressWarnings("unchecked")
            int food_exchange = (int) OregonTrail.getStaticFieldValue("FOOD_EXCHANGE");
            @SuppressWarnings("unchecked")
            int miles_per_day = (int) OregonTrail.getStaticFieldValue("MILES_PER_DAY");
            @SuppressWarnings("unchecked")
            int total_miles = (int) OregonTrail.getStaticFieldValue("TOTAL_MILES");

            @SuppressWarnings("unchecked")
            int days_travelled = (int) OregonTrail.getStaticFieldValue("daysTravelled");
            @SuppressWarnings("unchecked")
            int miles_travelled = (int) OregonTrail.getStaticFieldValue("milesTravelled");
            @SuppressWarnings("unchecked")
            int num_travelers = (int) OregonTrail.getStaticFieldValue("NUM_TRAVELERS");
            @SuppressWarnings("unchecked")
            int num_doctors = (int) OregonTrail.getStaticFieldValue("NUM_DOCTORS");
            @SuppressWarnings("unchecked")
            int num_hunters = (int) OregonTrail.getStaticFieldValue("NUM_HUNTERS");

            // Initialize wagon
            @SuppressWarnings("unchecked")
            Constructor wagonConstructor = Wagon.class.getConstructor(int.class);
            @SuppressWarnings("unchecked")
            Wagon wagon = (Wagon) wagonConstructor.newInstance(wagon_size);

            //Set variables in preparation to start game
            days_travelled = 0;
            miles_travelled = 0;
            boolean huntFlag = false;

            OregonTrail.loadWagon(wagon, num_travelers, num_doctors, num_hunters);

            // Display starting state
            OregonTrail.displayStatus(wagon, days_travelled, miles_travelled);

            // preparing eat method in Hunter and Traveler for runtime invocation
            @SuppressWarnings("unchecked")
            Method getPassengers = Wagon.class.getMethod("getPassengers");
            //get the passengerArray
            Traveler[] passengerArray = (Traveler[]) getPassengers.invoke(wagon);

            for (int i = 0; i < max_days; i++) {
                OregonTrail.feedWagon(passengerArray);

                //HUNTING ROUTINE
                // Check if hunting day - use modulo math to see if HUNT_DAYS divides evenly into totalDays
                // Modulo math with zero ensures there is one hunt day to begin at day 0.
                // Very simplified - just go with it
                if (days_travelled % hunt_days == 0) {
                    // For-each member in wagon: hunt
                    huntFlag = true;
                    System.out.println("Hunting Day!");
                    /***********************************/
                    gameHunt();
                    /**********************************/
                    OregonTrail.goHunting(passengerArray);
                } else {
                    huntFlag = false;
                }

                //Sharing food and healing
                // preparing shouldQuarantine for runtime invocation
                @SuppressWarnings("unchecked")
                Method shouldQuarantine = Wagon.class.getMethod("shouldQuarantine");

                // preparing Hunter giveFood for runtime invocation
                @SuppressWarnings("unchecked")
                Method giveFood = Hunter.class.getMethod("giveFood", Traveler.class, int.class);

                // preparing Doctor heal for runtime invocation
                @SuppressWarnings("unchecked")
                Method heal = Doctor.class.getMethod("heal", Traveler.class);

                // Check quarantine status and care for passengers
                if ((boolean) shouldQuarantine.invoke(wagon)) {
                    OregonTrail.quarantineCare(passengerArray, food_exchange);
                }

                // If healthy and not a hunt day, advance milesTraveled
                if (!(boolean) shouldQuarantine.invoke(wagon) && !huntFlag) {
                    miles_travelled = miles_travelled + miles_per_day;
                }

                // Advance the day
                days_travelled = days_travelled + 1;

                // Display daily status
                OregonTrail.displayStatus(wagon, days_travelled, miles_travelled);
            }

            //Check after MAX_DAYS loop if the wagon made it to Oregon
            System.out.println("**********************OREGON TRAIL****************************");
            System.out.println("The wagon traveled: " + miles_travelled + " miles.");
            System.out.println("You needed to travel a total of " + total_miles + " to make it to Oregon");
            System.out.println("**************************************************************");
            if (miles_travelled >= total_miles) {
                //You traveled enough miles to make it to Oregon
                System.out.println("Your wagon made it to Oregon. Congratulations!");
            } else {
                //You didn't travel enough miles to make it to Oregon
                System.out.println("Time has run out! You've died a horrible death on the Oregon Trail.");
            }
        } catch (NoSuchElementException | NoSuchMethodException | InstantiationException e) {
            System.out.println("Part II: There are required elements that are missing. Finish coding all required elements before running Oregon Trail");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
    }

}
