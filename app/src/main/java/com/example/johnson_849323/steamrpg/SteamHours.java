package com.example.johnson_849323.steamrpg;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jspspike on 5/10/16.
 */
public class SteamHours {

    static ArrayList<Integer> gameId = new ArrayList<>();
    static ArrayList<Integer> gameHours = new ArrayList<>();

    static int[] shootingGames = {220, 7670, 19900, 9480, 6060, 8190, 22380, 21090, 24240, 94200, 200900, 91310, 730, 236390, 209870, 234190, 222880, 304930, 271590};
    static int[] drivingGames = {24740, 8190};
    static int[] strategyGames = {4000, 10500, 24800, 1500, 1510, 1520, 1530, 17410, 42960, 8930, 620, 7600, 49600, 99700, 41800, 200900, 205790, 18700, 26500, 26900, 70300, 38740, 91200, 22000, 38700, 207570, 110800, 200390, 111800, 205910, 207750, 206500,234190, };
    static int[] survivalGames = {22380, 21100, 95300,19900, 105600, 42160, 21090, 102600, 6120, 40800, 63710, 41100, 29180, 104900, 200260, 111800, 91310, 201790, 35140, 204300,40300, 304930,};
    static int[] fantasyGames = {56400, 105600, 32800, 107100, 65800, 102600, 42910, 203810, 205790, 41100, 200710,205350, 201790, 40300, 40390, 247950};

    public static void updateData(String id) throws ExecutionException, InterruptedException {
        gameId = new ArrayList<>();
        gameHours = new ArrayList<>();

        SteamTask hours = new SteamTask();

        Document response = hours.execute(id).get();
        NodeList games = response.getElementsByTagName("message");

        for (int i = 0; i < games.getLength(); i++) {
            NodeList info = games.item(i).getChildNodes();
            gameId.add(Integer.parseInt(info.item(1).getTextContent()));
            gameHours.add(Integer.parseInt(info.item(3).getTextContent()));
        }
    }

    public static int getShootingHours() {
        int sum = 0;

        for (int i = 0; i < shootingGames.length; i++) {
            for (int j = 0; j < gameId.size(); j++) {
                if (shootingGames[i] == gameId.get(j)) {
                    sum += gameHours.get(j);
                    System.out.println("Hours" + gameHours.get(j));
                }
            }
        }

        return sum;
    }

    public static int getDrivingHours() {
        int sum = 0;

        for (int i = 0; i < drivingGames.length; i++) {
            for (int j = 0; j < gameId.size(); j++) {
                if (drivingGames[i] == gameId.get(j)) {
                    sum += gameHours.get(j);
                }
            }
        }

        return sum;
    }

    public static int getStrategyHours() {
        int sum = 0;

        for (int i = 0; i < strategyGames.length; i++) {
            for (int j = 0; j < gameId.size(); j++) {
                if (strategyGames[i] == gameId.get(j)) {
                    sum += gameHours.get(j);
                }
            }
        }

        return sum;
    }

    public static int getSurvivalHours() {
        int sum = 0;

        for (int i = 0; i < survivalGames.length; i++) {
            for (int j = 0; j < gameId.size(); j++) {
                if (survivalGames[i] == gameId.get(j)) {
                    sum += gameHours.get(j);
                }
            }
        }

        return sum;
    }

    public static int getFanasyHours() {
        int sum = 0;

        for (int i = 0; i < fantasyGames.length; i++) {
            for (int j = 0; j < gameId.size(); j++) {
                if (fantasyGames[i] == gameId.get(j)) {
                    sum += gameHours.get(j);
                }
            }
        }

        return sum;
    }


}
