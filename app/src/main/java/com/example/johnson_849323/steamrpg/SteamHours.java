package com.example.johnson_849323.steamrpg;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jspspike on 5/10/16.
 */
public class SteamHours {
    public static void getUserHours(int id, int gameId) throws IOException {
        URL url = new URL("http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid="+ gameId + "&key=4B359E101474F1C6726AAD3F7C7C0C74&steamid=" + id);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
    }
}
