package com.example.johnson_849323.steamrpg;

/**
 * Created by Eddie on 4/15/2016.
 */
public class Enemy {

    int damage;
    int health;
    String src;
    String skill;
    String back;

    public Enemy(){
        damage = 10;
        health = 100;
    }

    public Enemy(int h, int d){
        health = h;
        damage = d;
        src = "golbin";
        back = "dungeon_back";
        skill = "shooting";

    }

    public Enemy(int h, int d, String s, String sk){
        health = h;
        damage = d;
        src = s;
        skill = sk;
    }

    public Enemy(int h, int d, String s, String b,String sk){
        health = h;
        damage = d;
        src = s;
        back = b;
        skill = sk;
    }

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
}
