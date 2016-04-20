package com.example.johnson_849323.steamrpg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    Enemy enemy;
    Player player = new Player();
    int damage = 5;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private ProgressBar mProgress;
    private ProgressBar mProgress2;
    private ProgressBar mProgress3;
    private ProgressBar mProgress4;
    private ProgressBar mProgress5;
    private ProgressBar mProgress6;
    private ProgressBar mProgress7;

    public int shooting = 276;
    public int driving = 97;
    public int strategy = 342;
    public int survival = 222;
    public int puzzle = 73;
    public Context c;
    private int[] levels = new int[5];

    ArrayList<Enemy> enemies = new ArrayList();
    SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Context context = MainActivity.this;
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int defaultValue = 100;
        shooting = sharedPref.getInt(getString(R.string.shooting_saved), defaultValue);

        levels[0] = shooting;
        levels[1] = driving;
        levels[2] = strategy;
        levels[3] = survival;
        levels[4] = puzzle;

        System.out.println("Intent is:");
        System.out.println(getIntent().hasCategory("android.intent.category.LAUNCHER"));

        if(!getIntent().hasCategory("android.intent.category.LAUNCHER")) {
            levels = getIntent().getIntArrayExtra("levels");

            shooting = levels[0];
            driving = levels[1];
            strategy = levels[2];
            survival = levels[3];
            puzzle = levels[4];
        }

        mDrawerList = (ListView)findViewById(R.id.navList);

        enemies.add(new Enemy(50,12));
        enemies.add(new Enemy(75,24));

        enemy = enemies.get(0);

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setMax(enemy.getHealth());

        update();

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void levelCommit(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.shooting_saved), shooting);
        editor.commit();
    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Shooting", "Driving", "Strategy", "Survival" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(position == 0){
                    Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                    shooting();
                }

                if(position == 1){
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    shooting();
                }
            }
        });
    }
    public void home(){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void shooting(){
        levelCommit();
        levels[0] = shooting;
        levels[1] = driving;
        levels[2] = strategy;
        levels[3] = survival;
        levels[4] = puzzle;

        Intent intent = new Intent(this , shooting.class);
        intent.putExtra("levels",levels);
        startActivity(intent);

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            return true;
        }





        return super.onOptionsItemSelected(item);
    }

    public void attack(View view){
        enemy.setHealth(enemy.getHealth() - (int)(damage * skillMod(enemy.getSkill())));

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());

        if(enemy.getHealth() < 1){
            nextFloor();
        }



        player.setHealth(player.getHealth() - (int)(enemy.getDamage() / skillMod(enemy.getSkill())));

        if(player.getHealth() < 1){
            new AlertDialog.Builder(this)
                    .setTitle("You Died")
                    .setMessage("Get good scrub")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            reset();
                        }
                    })
                    .show();
        }


        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(player.getHealth());


        TextView healthT = (TextView) findViewById(R.id.healthFrac);
        healthT.setText("(" + player.getHealth() + "/100)");

    }

    public void nextFloor(){
        ImageView back = (ImageView) findViewById(R.id.background);
        back.setImageResource(R.drawable.track);

        TextView floor = (TextView) findViewById(R.id.floor);
        floor.setText("Floor 2 - Race");

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(mProgress2.getMax());
        enemy = enemies.get(1);
        update();


    }

    public void reset() {
        enemy.setHealth(100);
        player.setHealth(100);

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(player.getHealth());

        resetFloor();
        update();
    }

    public void resetFloor(){
        ImageView back = (ImageView) findViewById(R.id.background);
        back.setImageResource(R.drawable.dungeon_back);

        TextView floor = (TextView) findViewById(R.id.floor);
        floor.setText("Floor 1 - Archery Tournament");

    }

    public void update(){

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(player.getHealth());

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());

        TextView healthT = (TextView) findViewById(R.id.healthFrac);
        healthT.setText("(" + player.getHealth() + "/100)");

        mProgress3 = (ProgressBar) findViewById(R.id.progressBar2);
        mProgress3.setProgress(shooting%100);

        TextView shootF = (TextView) findViewById(R.id.shootingFrac);
        shootF.setText((int)(shooting/100) +  " (" + (shooting%100) + "/100)");

        mProgress4 = (ProgressBar) findViewById(R.id.progressBar3);
        mProgress4.setProgress(driving%100);

        TextView driveF = (TextView) findViewById(R.id.drivingFrac);
        driveF.setText((int)(driving/100) +  " (" + (driving%100) + "/100)");

        mProgress5 = (ProgressBar) findViewById(R.id.progressBar4);
        mProgress5.setProgress(strategy%100);

        TextView stratF = (TextView) findViewById(R.id.strategyFrac);
        stratF.setText((int)(strategy/100) +  " (" + (strategy%100) + "/100)");

        mProgress6 = (ProgressBar) findViewById(R.id.progressBar5);
        mProgress6.setProgress(survival%100);

        TextView surF = (TextView) findViewById(R.id.strategyFrac);
        surF.setText((int)(survival/100) +  " (" + (survival%100) + "/100)");

        mProgress7 = (ProgressBar) findViewById(R.id.progressBar6);
        mProgress7.setProgress(puzzle%100);

        TextView puzF = (TextView) findViewById(R.id.puzzleFrac);
        puzF.setText((int)(puzzle/100) +  " (" + (puzzle%100) + "/100)");

    }

    public double skillMod(String sk){
        switch(sk){
            case "shooting": return (shooting * .01);
            case "driving": return (driving * .01);
        }
        return 1;

    }

    public void tap(View view){
        shooting++;
        update();
    }


}
