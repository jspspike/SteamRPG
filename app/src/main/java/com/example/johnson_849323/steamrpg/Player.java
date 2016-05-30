package com.example.johnson_849323.steamrpg;

/**
 * Created by Eddie on 4/15/2016.
 */



public class Player {

    int health = 100;

    public double getSkillMod(String sk){

        return 1.5;
    }

    public void setHealth(int h){
        health = h;
    }

    public int getHealth(){
        return health;
    }
}
