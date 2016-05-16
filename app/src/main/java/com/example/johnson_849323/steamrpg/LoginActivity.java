package com.example.johnson_849323.steamrpg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class LoginActivity extends Activity {


    public static final String WEBSITE = "SteamRPG";


    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setTitle(url);
                Uri Url = Uri.parse(url);

                if(Url.getAuthority().equals(WEBSITE.toLowerCase())) {
                    webView.stopLoading();

                    Uri userAccountUrl = Uri.parse(Url.getQueryParameter("openid.identity"));
                    userID = userAccountUrl.getLastPathSegment();

                    Toast.makeText(LoginActivity.this, userID,Toast.LENGTH_LONG).show();
                    Log.i("UserID", userID);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(getString(R.string.player_id), userID);
                    startActivity(intent);
                }
            }
        });

       setContentView(webView);

        String url = "https://steamcommunity.com/openid/login?openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.mode=checkid_setup" +
                "&openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.realm=https://" + WEBSITE +
                "&openid.return_to=https://" + WEBSITE + "";

        webView.loadUrl(url);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
