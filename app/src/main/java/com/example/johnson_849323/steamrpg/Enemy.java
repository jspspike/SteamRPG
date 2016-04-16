package com.example.johnson_849323.steamrpg;

/**
 * Created by Eddie on 4/15/2016.
 */
public class Enemy {

    int damage;
    int health;

    public Enemy(){
        damage = 10;
        health = 100;
    }

    public Enemy(int h, int d){
        health = h;
        damage = d;
    }

    public String getSkill(){
        return "shooting";
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int h){
        health = h;
    }

    public int getDamage(){
        return damage;
    }
}
