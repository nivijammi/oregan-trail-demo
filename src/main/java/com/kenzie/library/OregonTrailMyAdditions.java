package com.kenzie.library;

import java.util.Random;

/**
 * In the game the sum of two random rolls of dice represents a day and Traveller is given fixed ration for the day.
 * Every dice roll adds a fixed miles by 15 and depletes the fixed ration of food by 20.
 * Few sum of dice roll can delay(4 and 7) the journey, make you complete the game (12) or
 * can make you loose the game(11). Otherwise, you continue your journey
 * with ups and down in your ration and travel days till you reach OREGON or
 * collect 150 points or till food store runs out. Good luck!
 */

public class OregonTrailMyAdditions {

    // create random number generator for use in method rollDice
    public final Random randomNumbers = new Random();
    /**
     * The Oregon Trail was an early-American wagon thoroughfare that spanned over 2,100 miles.
     * The eastern starting point of the Oregon Trail was in Independence, Missouri,
     * and it ended in Oregon’s Willamette Valley.
     */
    public final static int TOTAL_MILES = 2100;//Total miles required to reach Oregon
    /**
     * It was critical for travelers to leave in April or May if they hoped to reach Oregon before the winter snows began.
     * Leaving in late spring also ensured there’d be ample grass along the way to feed livestock.
     * time taken to reach = 2100/15= 140days + 10 for sick days
     */
    public static final int MILES_TRAVELED_PER_DAY = 15;//Total miles travelled per day
    public static final int MAX_DAYS = 150;//Maximum number of days to reach Oregon or bust

    /** wagons could carry 1800lbs food */
    public static int FOOD_STOCK = 1800;
    public final int foodRationPerDay = 12; // 1800/150 days

    //track miles:
    public static int TOTAL_MILES_TRAVELLED = 0;
    public static int MILES_REMAINING;

    //getters and setters

    public Random getRandomNumbers() {
        return randomNumbers;
    }

    public int getFoodStock() {
        return FOOD_STOCK;
    }

    public void setFoodStock(int foodStock) {
        this.FOOD_STOCK = foodStock;
    }

    public int getFoodRationPerDay() {
        return foodRationPerDay;
    }

    public int getTotalMilesTravelled() {
        return TOTAL_MILES_TRAVELLED;
    }

    public void setTotalMilesTravelled(int totalMilesTravelled) {
        this.TOTAL_MILES_TRAVELLED = totalMilesTravelled;
    }

    public int getMilesRemainingToOregon() {
        return MILES_REMAINING;
    }

    public void setMilesRemainingToOregon(int milesRemainingToOregon) {
        this.MILES_REMAINING = milesRemainingToOregon;
    }

    // enumeration with constants that represent the game status
    private enum GameStatus {
        PLAY,
        DELAY,
        WON,
        LOST
    }

    // constants that represent common rolls of the dice
    private final static int IS_SICK = 4; // delay for 2 days
    private final static int HUNT_DAY = 7; //delay a day but add food
    private final static int REACHED_A_MILESTONE = 8; //add double daily miles & stock food
    private final static int DISASTER = 11;//end game
    private final static int REACHED_SANTA_FE_TRAIL = 12; // won

    public int rollDice () {

        // pick random die values
        int die1 = 1 + randomNumbers.nextInt(6); // first die roll
        int die2 = 1 + randomNumbers.nextInt(6); // second die roll

        int sum = die1 + die2; // sum of die values
        //System.out.printf("Player rolled %d + %d = %d ", die1, die2, sum);

        return sum;
    }

    /************************ plays game ************************************/
    public void play() {
        int daysTraveled = 0;
        int currentPoints = 0; // start point - no win or loss on first roll

        GameStatus gameStatus = GameStatus.PLAY; // can contain PLAY, DELAY, WON or LOST
        System.out.println("========================================================");
        System.out.println("^^^^^^^^^^^^^   OREGON TRAIL MY ADDITION    ^^^^^^^^^^^^");
        System.out.println("========================================================");
        // while game is not complete
        while (gameStatus == GameStatus.PLAY && FOOD_STOCK >= foodRationPerDay) { // not WON or LOST

            //every roll of dice represents a day
            //you are given ration for that day
            //you have traveled that day
            int sumOfDice = rollDice(); // roll dice again
            FOOD_STOCK = FOOD_STOCK - foodRationPerDay;
            daysTraveled++;

            // determine game status and point based on the roll


            switch (sumOfDice) {
                case IS_SICK: // sum 4: lose travel days
                    gameStatus = GameStatus.DELAY;
                    if(TOTAL_MILES_TRAVELLED >= 30) {
                        TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED - (MILES_TRAVELED_PER_DAY * 2); //delay by 2 days
                    }else{
                        TOTAL_MILES_TRAVELLED = 0;
                    }
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    System.out.println("You rolled 4! Delay your journey by 2 days because of sickness!");
                    break;

                case HUNT_DAY: // sum 7:
                    gameStatus = GameStatus.DELAY;
                    if(TOTAL_MILES_TRAVELLED >= 15) {
                        TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED - 0;
                    }else{
                        TOTAL_MILES_TRAVELLED = 0;
                    }
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    FOOD_STOCK = FOOD_STOCK + foodRationPerDay;
                    System.out.println(("You rolled 7! Hunt Day! Need to gather food"));
                    System.out.println(("Delay your journey by a day but add stock."));
                    break;

                case REACHED_A_MILESTONE: // sum 8: added vigor - travelled twice & restocked
                    gameStatus = GameStatus.PLAY;
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED + (MILES_TRAVELED_PER_DAY *2);
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    FOOD_STOCK = FOOD_STOCK + foodRationPerDay * 2;
                    System.out.println(("You rolled 8! Hurray! Reached a mile stone."));
                    System.out.println("Add twice the stock and journey twice the miles");
                    break;

                case DISASTER: // lose with 11
                    gameStatus = GameStatus.LOST;
                    System.out.println("You rolled 11! Disaster hit!");
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    break;

                case REACHED_SANTA_FE_TRAIL: // sum 12: win
                    gameStatus = GameStatus.WON;
                    System.out.println("You rolled 12! You have finally reached Santa Fe Trail.");
                    MILES_REMAINING = 0;
                    break;

                default: // did not win or lose
                    gameStatus = GameStatus.PLAY; // game is not over
                    System.out.println("PHEW! You are safe, continue your travel!");
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED + MILES_TRAVELED_PER_DAY;
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    currentPoints = currentPoints + sumOfDice; // remember the point
                    break; // optional at end of switch
                // end switch
            }
            gameStatus = getGameStatus(daysTraveled, currentPoints, gameStatus, sumOfDice);
            displayGameStatus(gameStatus);
        }

    }

    private GameStatus getGameStatus(int daysTraveled, int currentPoints, GameStatus gameStatus, int sumOfDice) {
        // determine game status
        if (gameStatus == GameStatus.DELAY) {
            gameStatus = GameStatus.PLAY;
        } else if(TOTAL_MILES_TRAVELLED >= MILES_REMAINING ||
                currentPoints == MAX_DAYS) {// win by making point
            gameStatus = GameStatus.WON;
        } else if (sumOfDice == DISASTER) {// lose by rolling 10
            gameStatus = GameStatus.LOST;
        }else if(daysTraveled > MAX_DAYS){
            gameStatus = gameStatus.LOST;
        }
        return gameStatus;
    }

    private static void displayGameStatus(GameStatus gameStatus) {
        //Every roll of dice is a day.
        System.out.println("************   TODAY'S TRAVEL SUMMARY  *************");
        // display won or lost message
        if (gameStatus == GameStatus.WON) {
            System.out.println("Your wagon made it to Oregon. Congratulations!");
        }else if(gameStatus == GameStatus.PLAY){
            //System.out.println("You have survived to travel another day!");
        } else {
            System.out.println("Sorry! Your time has run out.");
        } // end method play
        System.out.println("Total miles traveled till date: " + TOTAL_MILES_TRAVELLED + " miles."); //miles travelled
        System.out.println("You needed to travel a total of " + MILES_REMAINING + " miles to make it to Oregon.");//total miles
        System.out.println("Remaining food stock: " + FOOD_STOCK);
        System.out.println("=============================================================");
        System.out.println("==============================================================");

    }

    }