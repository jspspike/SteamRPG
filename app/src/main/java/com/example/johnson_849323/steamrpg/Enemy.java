package com.example.johnson_849323.steamrpg;

/**
 * Created by Eddie on 4/15/2016.
 */
public class Enemy {

    int damage;
    int health;
    int healthMax;
    String src;
    String skill;
    String back;
    int xp = 100;
    int midpoint = 60;
    int range = 2;

    public Enemy(){
        damage = 10;
        health = 100;
    }

    public Enemy(int h, int d){
        health = h;
        healthMax = h;
        damage = d;
        src = "golbin";
        back = "dungeon_back";
        skill = "shooting";

    }

    public Enemy(int h, int d, String s, String sk){
        health = h;
        healthMax = h;
        damage = d;
        src = s;
        skill = sk;
    }

    public Enemy(int h, int d, String s, String b,String sk){
        health = h;
        healthMax = h;
        damage = d;
        src = s;
        back = b;
        skill = sk;
    }

    public void setXp(int x){xp = x;}

    public int getXp(){return xp;}

    public String getSkill(){
        return skill;
    }

    public String getSrc(){return src;}

    public String getBack(){return back;}

    public int getHealth(){
        return health;
    }

    public void setHealth(int h){
        health = h;
    }

    public int getDamage(){
        return damage;
    }

    public void reset(){health = healthMax;}
}
