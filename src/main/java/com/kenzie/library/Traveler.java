package com.kenzie.library;

public class Traveler {

    protected String name;
    protected int food;
    protected boolean isHealthy;


    public Traveler(){
        this.name = "";
        this.food = 1;
        this.isHealthy = true;
    }

    public Traveler(String name){
        this.name = name;
        this.food = 1;
        this.isHealthy = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public boolean getIsHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }

    public void hunt(){
        setFood(getFood() + 2);
    }

    public void eat(){

        if(getFood() >= 1){
            setFood(food - 1);
        }else{
            setHealthy(false);
        }

    }
}

class Doctor extends Traveler {

    public Doctor() {
        super();
    }

    public Doctor(String name) {
        super(name);
    }

    @Override
    public void hunt() {
        super.hunt();
    }

    @Override
    public void eat() {
        super.eat();
    }

    public void heal(Traveler traveler){
        traveler.setHealthy(true);
    }
}

class Hunter extends Traveler {
    public Hunter() {
        super();
        this.food = 2;
    }

    public Hunter(String name) {
        super(name);
        this.food = 2;
    }

    @Override
    public void hunt() {
        setFood(getFood() + 5);
    }

    @Override
    public void eat() {
        if(getFood() == 2){
           setFood(food - 2);
        }else if(getFood() == 1) {
            setFood(food - 1);
            setHealthy(false);
        }else{
            setHealthy(false);
        }
    }
    public void giveFood(Traveler traveler, int numOfFoodUnits){
        if(getFood() >= numOfFoodUnits){
            int travelersCurrentFoodUnit = traveler.getFood();
            traveler.setFood(travelersCurrentFoodUnit + numOfFoodUnits);
            setFood(food - numOfFoodUnits);
        }else{
            //No food is transferred - numOfFoodUnits < huntersFoodStock
            System.out.println("No food is transferred - numOfFoodUnits < huntersFoodStock");
        }

    }
}

