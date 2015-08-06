package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseUser;

import adoptplanet.com.adoptplanet.R;
import io.fabric.sdk.android.Fabric;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Fabric.with(this, new Crashlytics());
        Parse.initialize(this, "9maWSgFEvVDhE2S38JjLHWjkrriEXlSf0MGT5BrO", "aZhLgSlogo10qICrHMat9xTaQJiMJpieg1sSikOt");
        ParseUser.enableRevocableSessionInBackground();

        Intent intent = new Intent(this, MainFeedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
