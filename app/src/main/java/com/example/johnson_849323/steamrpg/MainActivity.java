package com.example.johnson_849323.steamrpg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


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

    public int shooting = 500;
    public int driving = 254;
    public int strategy = 342;
    public int survival = 222;
    public int fantasyC = 73;
    public int floorNum = 1;
    public int days = 0;
    public Context c;
    private int[] levels = new int[5];
    public double playerLapT = 0;
    public double enemyLapT = 0;
    public int floorType = 0;
    public int rando = 0;
    DecimalFormat df = new DecimalFormat();

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
        levels[4] = fantasyC;

        if (!getIntent().hasCategory("android.intent.category.LAUNCHER")) {
            levels = getIntent().getIntArrayExtra("levels");

            shooting = levels[0];
            driving = levels[1];
            strategy = levels[2];
            survival = levels[3];
            fantasyC = levels[4];
        }

        mDrawerList = (ListView) findViewById(R.id.navList);

        enemies.add(new Enemy(50, 5, "golbin", "dungeon_back", "shooting"));
        enemies.add(new Enemy(100, 8, "target", "csgo", "shooting"));
        enemies.add(new Enemy(75, 8, "skellington", "dungeon_back", "shooting"));
        enemies.add(new Enemy(100, 5, "chess", "board", "strategy"));

        enemy = enemies.get(0);

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setMax(enemy.getHealth());

        int resourceID = this.getResources().getIdentifier(enemy.getSrc(), "drawable", this.getPackageName());
        ImageView enemypic = (ImageView) findViewById(R.id.avatar2);
        enemypic.setImageResource(resourceID);

        int resourceBID = this.getResources().getIdentifier(enemy.getBack(), "drawable", this.getPackageName());
        ImageView backimg = (ImageView) findViewById(R.id.background);
        backimg.setImageResource(resourceBID);

        TextView myTextView = (TextView) findViewById(R.id.text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/High Fiber.ttf");
        myTextView.setTypeface(typeFace);

        /*
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.lobby);
        View inflated = stub.inflate();
        */


        update();

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        df.setMaximumFractionDigits(2);


    }

    private void levelCommit() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.shooting_saved), shooting);
        editor.commit();
    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "Shooting", "Driving", "Strategy", "Survival"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                    shooting();
                }

                if (position == 1) {
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    shooting();
                }
            }
        });
    }

    public void home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void shooting() {
        levelCommit();
        levels[0] = shooting;
        levels[1] = driving;
        levels[2] = strategy;
        levels[3] = survival;
        levels[4] = fantasyC;

        Intent intent = new Intent(this, shooting.class);
        intent.putExtra("levels", levels);
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

    public void attack(View view) {
        enemy.setHealth(enemy.getHealth() - (int) (damage * skillMod(enemy.getSkill())));

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());

        if (enemy.getHealth() < 1) {
            nextFloor();
        }

        player.setHealth(player.getHealth() - (int) (enemy.getDamage() / skillMod(enemy.getSkill())));

        if (player.getHealth() < 1) {
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

    public void nextFloor() {

        enemy.reset();
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.lobby);
        floorNum++;

        System.out.println("NextFlooring!");

        rando = randomWithRange(0, 2);
        while(rando == floorType){
            rando = randomWithRange(0, 2);
        }

        switch (rando) {
            case 0:
                floorType = 0;
                vf.setDisplayedChild(0);
                TextView floor = (TextView) findViewById(R.id.floor);
                floor.setText("Floor " + floorNum + " - Ranged Combat");

                int range = (enemies.size());
                enemy = enemies.get((int) (Math.random() * range));

                mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
                mProgress2.setMax(enemy.getHealth());
                mProgress2.setProgress(enemy.getHealth());

                int resourceID = this.getResources().getIdentifier(enemy.getSrc(), "drawable", this.getPackageName());
                ImageView enemypic = (ImageView) findViewById(R.id.avatar2);
                enemypic.setImageResource(resourceID);

                System.out.println("print statement: " + this.getResources().getIdentifier(enemy.getBack(), "drawable", this.getPackageName()));
                System.out.println("print worked");

                int resourceBID = this.getResources().getIdentifier(enemy.getBack(), "drawable", this.getPackageName());
                ImageView backimg = (ImageView) findViewById(R.id.background);
                backimg.setImageResource(resourceBID);

                update();

                break;

            case 1:
                floorType = 1;
                vf.setDisplayedChild(1);

                TextView floorR = (TextView) findViewById(R.id.floorR);
                floorR.setText("Floor " + floorNum + " - Race Track");
                break;

            case 2:
                floorType = 2;
                vf.setDisplayedChild(2);

                TextView floorS = (TextView) findViewById(R.id.floorS);
                floorS.setText("Floor " + floorNum + " - Survival");
                days = 4;
                TextView daysT = (TextView) findViewById(R.id.days);
                daysT.setText("Days till rescue " + days);
                break;

        }
    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public void survive(View view){

        player.setHealth(player.getHealth() - (int) (enemy.getDamage() / skillMod(enemy.getSkill())));

        if (player.getHealth() < 1) {
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

        days--;
        TextView daysT = (TextView) findViewById(R.id.days);
        daysT.setText("Days till rescue " + days);

        if(days < 1){
            nextFloor();
        }

    }

    public void race(View view) {
        //enemy.setHealth(enemy.getHealth() - (int)(damage * skillMod(enemy.getSkill())));

        /*
        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());
        */

        /*
        if(enemy.getHealth() < 1){
            nextFloor();
        }
        */

        player.setHealth(player.getHealth() - (int) (enemy.getDamage() / skillMod(enemy.getSkill())));

        if (player.getHealth() < 1) {
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

        TextView pTime = (TextView) findViewById(R.id.player_time);
        /*
        double range = (Math.pow(Math.E, 2 * (driving / 100)) + Math.pow(Math.E, 2 * (driving / 100))) + 1;

        playerLapT = ((60 - Math.log(1 + (driving / 100)) + ((Math.random() * range) - Math.pow(Math.E, driving / 100))));
        pTime.setText("" + df.format(playerLapT));
         */
        double range = (Math.pow(Math.E, 2 * (driving / 100)) + Math.pow(Math.E, 2 * (driving / 100))) + 1;


        playerLapT = ((60 - Math.log(1 + (driving / 100)) + neg() * randomWithRange(0, (int) Math.pow(Math.E, ((-.25) * (driving / 100)) + 3))));
        pTime.setText("" + df.format(playerLapT));

        TextView eLapTime = (TextView) findViewById(R.id.enemy_time_t);
        double erange = (Math.pow(Math.E, 2 * (enemy.getXp() / 100)) + Math.pow(Math.E, 2 * (enemy.getXp() / 100))) + 1;

        enemyLapT = ((60 - Math.log(1 + (enemy.getXp() / 100)) + ((Math.random() * erange) - Math.pow(Math.E, enemy.getXp() / 100))));
        eLapTime.setText("" + df.format(enemyLapT));

        if (playerLapT < enemyLapT) {
            nextFloor();
        }


    }

    public void reset() {
        player.setHealth(100);
        enemy.reset();

        ViewFlipper vf = (ViewFlipper) findViewById(R.id.lobby);
        vf.setDisplayedChild(0);

        enemy = enemies.get(0);

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setMax(enemy.getHealth());

        int resourceID = this.getResources().getIdentifier(enemy.getSrc(), "drawable", this.getPackageName());
        ImageView enemypic = (ImageView) findViewById(R.id.avatar2);
        enemypic.setImageResource(resourceID);

        int resourceBID = this.getResources().getIdentifier(enemy.getBack(), "drawable", this.getPackageName());
        ImageView backimg = (ImageView) findViewById(R.id.background);
        backimg.setImageResource(resourceBID);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(player.getHealth());

        resetFloor();
        update();
    }

    public void resetFloor() {
        ImageView back = (ImageView) findViewById(R.id.background);
        back.setImageResource(R.drawable.dungeon_back);

        TextView floor = (TextView) findViewById(R.id.floor);
        floor.setText("Floor 1 - Archery Tournament");

    }

    public void update() {

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(player.getHealth());

        mProgress2 = (ProgressBar) findViewById(R.id.enemy_health);
        mProgress2.setProgress(enemy.getHealth());

        TextView healthT = (TextView) findViewById(R.id.healthFrac);
        healthT.setText("(" + player.getHealth() + "/100)");

        mProgress3 = (ProgressBar) findViewById(R.id.progressBar2);
        mProgress3.setProgress(shooting % 100);

        TextView shootF = (TextView) findViewById(R.id.shootingFrac);
        shootF.setText((int) (shooting / 100) + " (" + (shooting % 100) + "/100)");

        mProgress4 = (ProgressBar) findViewById(R.id.progressBar3);
        mProgress4.setProgress(driving % 100);

        TextView driveF = (TextView) findViewById(R.id.drivingFrac);
        driveF.setText((int) (driving / 100) + " (" + (driving % 100) + "/100)");

        mProgress5 = (ProgressBar) findViewById(R.id.progressBar4);
        mProgress5.setProgress(strategy % 100);

        TextView stratF = (TextView) findViewById(R.id.strategyFrac);
        stratF.setText((int) (strategy / 100) + " (" + (strategy % 100) + "/100)");

        mProgress6 = (ProgressBar) findViewById(R.id.progressBar5);
        mProgress6.setProgress(survival % 100);

        TextView surF = (TextView) findViewById(R.id.strategyFrac);
        surF.setText((int) (survival / 100) + " (" + (survival % 100) + "/100)");

        mProgress7 = (ProgressBar) findViewById(R.id.progressBar6);
        mProgress7.setProgress(fantasyC % 100);

        TextView puzF = (TextView) findViewById(R.id.puzzleFrac);
        puzF.setText((int) (fantasyC / 100) + " (" + (fantasyC % 100) + "/100)");

    }

    public double skillMod(String sk) {
        switch (sk) {
            case "shooting":
                return (shooting * .01);
            case "driving":
                return (driving * .01);
            case "strategy":
                return (strategy * .01);
            case "survival":
                return (survival * .01);
            case "puzzle":
                return (fantasyC * .01);
            case "":
                return 1;
        }
        return 1;

    }

    public void tap(View view) {
        shooting++;
        update();
    }


    public int neg() {
        Random randomNum = new Random();
        if (randomNum.nextInt(2) == 0) {
            return -1;
        } else {
            return 1;
        }
    }

}
