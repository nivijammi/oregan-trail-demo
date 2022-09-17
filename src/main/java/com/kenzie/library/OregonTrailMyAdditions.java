package com.kenzie.library;

import java.util.Random;

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
    public int foodStock = 1800;
    public final int foodRationPerDay = 12; // 1800/150 days

    //track miles:
    public int totalMilesTravelled = 0;
    public int milesRemainingToOregon ;

    //getts and setters

    public Random getRandomNumbers() {
        return randomNumbers;
    }

    public int getFoodStock() {
        return foodStock;
    }

    public void setFoodStock(int foodStock) {
        this.foodStock = foodStock;
    }

    public int getFoodRationPerDay() {
        return foodRationPerDay;
    }

    public int getTotalMilesTravelled() {
        return totalMilesTravelled;
    }

    public void setTotalMilesTravelled(int totalMilesTravelled) {
        this.totalMilesTravelled = totalMilesTravelled;
    }

    public int getMilesRemainingToOregon() {
        return milesRemainingToOregon;
    }

    public void setMilesRemainingToOregon(int milesRemainingToOregon) {
        this.milesRemainingToOregon = milesRemainingToOregon;
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
        while (gameStatus == UserStatus.PLAY && foodStock >= foodRationPerDay) { // not WON or LOST

            //every roll of dice represents a day
            //you are given ration for that day
            //you have traveled that day
            int sumOfDice = rollDice(); // roll dice again
            foodStock = foodStock - foodRationPerDay;
            daysTraveled++;

            // determine game status and point based on the roll

            switch (sumOfDice) {
                case IS_SICK: // sum 4: lose travel days
                    gameStatus = UserStatus.DELAY;
                    System.out.println("Delayed because of sickness");
                    totalMilesTravelled = totalMilesTravelled - (MILES_TRAVELED_PER_DAY *2); //delay by 2 days
                    break;
                case HUNT_DAY: // sum 7:
                    gameStatus = UserStatus.DELAY;
                    System.out.println(("Hunt Day! Need to gather food"));
                    totalMilesTravelled = totalMilesTravelled - MILES_TRAVELED_PER_DAY;
                    foodStock = foodStock + foodRationPerDay;
                    break;
                case REACHED_A_MILESTONE: // sum 8: added vigor - travelled twice & restocked
                    totalMilesTravelled = totalMilesTravelled + (MILES_TRAVELED_PER_DAY *2);
                    System.out.println("All supplies stalked!");
                    foodStock = foodStock + foodRationPerDay * 2;
                    gameStatus = UserStatus.PLAY;
                    break;
                case DISASTER: // lose with 11
                    gameStatus = UserStatus.LOST;
                    System.out.println("Disaster hit!");
                    break;
                case REACHED_SANTA_FE_TRAIL: // sum 12: win
                    System.out.println("Finally reached Santa Fe Trail");
                    gameStatus = UserStatus.WON;
                    break;
                default: // did not win or lose
                    gameStatus = UserStatus.PLAY; // game is not over
                    System.out.println("And miles to go before i sleep!");
                    totalMilesTravelled = totalMilesTravelled + MILES_TRAVELED_PER_DAY;
                    milesRemainingToOregon = TOTAL_MILES - totalMilesTravelled;
                    currentPoints = currentPoints + sumOfDice; // remember the point
                    //System.out.printf("currentPoints are %d ", currentPoints);
                    break; // optional at end of switch
                // end switch
            }
            // determine game status
            if (gameStatus == UserStatus.DELAY) {
                gameStatus = UserStatus.PLAY;
            } else if (currentPoints == MAX_DAYS) {// win by making point
                gameStatus = UserStatus.WON;
            } else if (sumOfDice == DISASTER) {// lose by rolling 10
                gameStatus = UserStatus.LOST;
            } else if (milesRemainingToOregon == 0) {
                gameStatus = UserStatus.WON;
            }else if(daysTraveled > MAX_DAYS){
                gameStatus = gameStatus.LOST;
            }
            // display won or lost message
            if (gameStatus == UserStatus.WON) {
                System.out.println("Congratulations! You have reached Oregon!");
            }else if(gameStatus == UserStatus.PLAY){
                System.out.println("You have survived to travel another day!");
            } else {
                System.out.println("Sorry!You can no longer travel!");
            } // end method play
        }

    }


    public static void main (String[]args){
            OregonTrailMyAdditions game = new OregonTrailMyAdditions();

            game.play();
            System.out.println("*************************************");
            System.out.println("Sum of dice: " + game.rollDice());
            System.out.println("Miles travelled: " + game.MILES_TRAVELED_PER_DAY);
            System.out.println("Miles remaining to Oregon: " + game.milesRemainingToOregon);
            System.out.println("Remaining food stock: " + game.foodStock);
        }
    }