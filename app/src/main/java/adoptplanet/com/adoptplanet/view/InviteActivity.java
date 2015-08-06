package adoptplanet.com.adoptplanet.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteActivity extends ActionBarActivity {

    @Bind(R.id.invite_toolbar) Toolbar toolbar;

    @Bind(R.id.invite_facebook_iv) ImageView facebook_ev;
    @Bind(R.id.invite_googlep_iv) ImageView googlep_ev;
    @Bind(R.id.invite_twitter_iv) ImageView twitter_ev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);


        CacheHolder.registration_pool.add(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("SIGN UP");
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_skip) {
            LayoutInflater inflater = getLayoutInflater();

            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alert_created_acc, null);

            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(layout)
                    .setCancelable(false)
                    .create();;

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.create_pet_button){
                        handleAddPet();
                    }
                    else if (id == R.id.skip_lay){
                        handleSkip();
                    }
                    dialog.dismiss();
                }
            };


            LinearLayout skip_lay = (LinearLayout) layout.findViewById(R.id.layout_continue);
            Button button = (Button) layout.findViewById(R.id.create_pet_button);

            skip_lay.setOnClickListener(listener);
            button.setOnClickListener(listener);


            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleAddPet(){
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    private void handleSkip(){
        CacheHolder.finishRegistration();
    }
}
