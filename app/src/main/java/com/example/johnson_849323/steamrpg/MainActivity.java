package com.example.johnson_849323.steamrpg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        SharedPreferences sharedPreferences = getSharedPreferences("special", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("player_id", "-2").equals("-2")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Log.e("PlayerID", sharedPreferences.getString("player_id", "-2"));
        }

        try {
            SteamHours.updateData(sharedPreferences.getString("player_id", "-2"));
            System.out.println(SteamHours.getShootingHours());
            System.out.println(SteamHours.getDrivingHours());
            System.out.println(SteamHours.getStrategyHours());
            System.out.println(SteamHours.getSurvivalHours());
            System.out.println(SteamHours.getFanasyHours());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("SteamStats", "JSON ERROR");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("SteamStats", "JSON ERROR");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
