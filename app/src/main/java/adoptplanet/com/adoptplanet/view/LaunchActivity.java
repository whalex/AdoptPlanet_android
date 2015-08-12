package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.Timer;
import java.util.TimerTask;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.DataParser;
import io.fabric.sdk.android.Fabric;

public class LaunchActivity extends Activity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        long str_t = System.nanoTime();
        context = this;

        Fabric.with(this, new Crashlytics());
        try {
            Parse.initialize(this, "9maWSgFEvVDhE2S38JjLHWjkrriEXlSf0MGT5BrO", "aZhLgSlogo10qICrHMat9xTaQJiMJpieg1sSikOt");
            ParseUser.enableRevocableSessionInBackground();
        }
        catch(Exception e){}

        // load breeds
        DataParser.uploadBreeds(this, Pet.TYPE_CAT);
        DataParser.uploadBreeds(this, Pet.TYPE_DOG);

        Thread loadingThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(context,
                            MainFeedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        };
        loadingThread.start();
    }
}
