package com.example.johnson_849323.steamrpg;

import java.util.ArrayList;

/**
 * Created by jspspike on 5/10/16.
 */
public class SteamHours {

    public static double getShootingHours(int id) {
        double sum = 0;
        SteamTask hours = new SteamTask();
        ArrayList<String> ids = new ArrayList<>();

        ids.add("730");
        ids.add("" + id);

        sum += Double.parseDouble("" + hours.execute(ids));

        return sum;
    }
}
