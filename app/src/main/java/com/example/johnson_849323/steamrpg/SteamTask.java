package com.example.johnson_849323.steamrpg;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by jspspike on 5/10/16.
 */
public class SteamTask extends AsyncTask<ArrayList<String>,Void, String> {

    @Override
    protected String doInBackground(ArrayList<String> ... passing) {

        ArrayList<String> passed = passing[0];

        String gameId = passed.get(0);
        String id = passed.get(1);


        String url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=4B359E101474F1C6726AAD3F7C7C0C74&steamid=" + id;

        String stats = "";

        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream(), "UTF-8"));
            StringBuilder responseString = new StringBuilder();

            String response;

            while((response = streamReader.readLine()) != null) {
                responseString.append(response);
            }

            stats = responseString.toString();
            Log.e("SteamStats", "" + stats);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SteamStats", "Error");
        }

        for (int i = 0; i < stats.length(); i++) {

        }

        return stats;
    }
}
