package com.example.johnson_849323.steamrpg;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by jspspike on 5/10/16.
 */
public class SteamHours extends AsyncTask<ArrayList<String>,Void, String> {

    @Override
    protected String doInBackground(ArrayList<String> ... passing) {

        ArrayList<String> passed = passing[0];

        String gameId = passed.get(0);
        String id = passed.get(1);


        String url = "http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=" + gameId + "&key=4B359E101474F1C6726AAD3F7C7C0C74&steamid=" + id;

        Document stats = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            stats = db.parse(new URL(url).openStream());
            Log.e("SteamStats", "" + stats);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return "" + stats;
    }
}
