package com.kenzie.library;

import java.sql.SQLOutput;
import java.util.Random;

/**
 * In the game the sum of two random rolls of dice represents a day and Traveller is given fixed ration for the day.
 * Every dice roll adds a fixed miles(15) and depletes the fixed ration of food(20).
 * Few sum of dice roll can delay(4 and 7) the journey, make you complete the game(12) or
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
    private enum UserStatus {
        PLAY,
        DELAY,
        WON,
        LOST
    }

    // constants that represent common rolls of the dice
    private final static int IS_SICK = 4; //invoke healthy - stop for 2 days
    private final static int HUNT_DAY = 7; //give food
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

        UserStatus gameStatus = UserStatus.PLAY; // can contain PLAY, DELAY, WON or LOST

        // while game is not complete
        while (gameStatus == UserStatus.PLAY && FOOD_STOCK >= foodRationPerDay) { // not WON or LOST

            //every roll of dice represents a day
            //you are given ration for that day
            //you have traveled that day
            int sumOfDice = rollDice(); // roll dice again
            FOOD_STOCK = FOOD_STOCK - foodRationPerDay;
            daysTraveled++;

            // determine game status and point based on the roll

            switch (sumOfDice) {
                case IS_SICK: // sum 4: lose travel days
                    gameStatus = UserStatus.DELAY;
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED - (MILES_TRAVELED_PER_DAY *2); //delay by 2 days
                    System.out.println("You rolled 4! Delay your journey by 2 days because of sickness!");
                    break;

                case HUNT_DAY: // sum 7:
                    gameStatus = UserStatus.DELAY;
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED - MILES_TRAVELED_PER_DAY;
                    FOOD_STOCK = FOOD_STOCK + foodRationPerDay;
                    System.out.println(("You rolled 7! Hunt Day! Need to gather food"));
                    System.out.println(("Delay your journey by a day but added stock."));
                    break;

                case REACHED_A_MILESTONE: // sum 8: added vigor - travelled twice & restocked
                    gameStatus = UserStatus.PLAY;
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED + (MILES_TRAVELED_PER_DAY *2);
                    FOOD_STOCK = FOOD_STOCK + foodRationPerDay * 2;
                    System.out.println(("You rolled 8! Hurray! Reached a mile stone."));
                    System.out.println("Add twice the stock and journey twice the miles");
                    break;

                case DISASTER: // lose with 11
                    gameStatus = UserStatus.LOST;
                    System.out.println("You rolled 11! Disaster hit!");
                    break;

                case REACHED_SANTA_FE_TRAIL: // sum 12: win
                    gameStatus = UserStatus.WON;
                    System.out.println("You rolled 12! Finally reached Santa Fe Trail");
                    break;

                default: // did not win or lose
                    gameStatus = UserStatus.PLAY; // game is not over
                    System.out.println("You can continue! And miles to go before i sleep!");
                    TOTAL_MILES_TRAVELLED = TOTAL_MILES_TRAVELLED + MILES_TRAVELED_PER_DAY;
                    MILES_REMAINING = TOTAL_MILES - TOTAL_MILES_TRAVELLED;
                    currentPoints = currentPoints + sumOfDice; // remember the point
                    break; // optional at end of switch
                // end switch
            }
            gameStatus = getUserStatus(daysTraveled, currentPoints, gameStatus, sumOfDice);
            displayMessage(gameStatus);
        }

    }

    private UserStatus getUserStatus(int daysTraveled, int currentPoints, UserStatus gameStatus, int sumOfDice) {
        // determine game status
        if (gameStatus == UserStatus.DELAY) {
            gameStatus = UserStatus.PLAY;
        } else if(TOTAL_MILES_TRAVELLED >= MILES_REMAINING ||
                currentPoints == MAX_DAYS) {// win by making point
            gameStatus = UserStatus.WON;
        } else if (sumOfDice == DISASTER) {// lose by rolling 10
            gameStatus = UserStatus.LOST;
        }else if(daysTraveled > MAX_DAYS){
            gameStatus = gameStatus.LOST;
        }
        return gameStatus;
    }

    private static void displayMessage(UserStatus gameStatus) {


        System.out.println("****************OREGON TRAIL TRAVEL SUMMARY****************");
        // display won or lost message
        if (gameStatus == UserStatus.WON) {
            System.out.println("Your wagon made it to Oregon. Congratulations!");
        }else if(gameStatus == UserStatus.PLAY){
            System.out.println("You have survived to travel another day!");
        } else {
            System.out.println("Time has run out! You've died a horrible death on the Oregon Trail.");
        } // end method play
        System.out.println("The wagon traveled: " + TOTAL_MILES_TRAVELLED + " miles."); //miles travelled
        System.out.println("You needed to travel a total of " + MILES_REMAINING + " to make it to Oregon");//total miles
        System.out.println("Remaining food stock: " + FOOD_STOCK);
        System.out.println("**************************************************************");

        System.out.println("**************************************************************");

    }


    public static void main (String[]args){
            OregonTrailMyAdditions game = new OregonTrailMyAdditions();
            game.play();

        }
    }