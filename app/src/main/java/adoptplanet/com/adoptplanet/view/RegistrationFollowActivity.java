package adoptplanet.com.adoptplanet.view;




import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import adoptplanet.com.adoptplanet.R;

import adoptplanet.com.adoptplanet.model.CurrentUser;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.DataParser;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistrationFollowActivity extends AppCompatActivity {

    @Bind(R.id.registration_follow_toolbar) Toolbar toolbar;
    @Bind(R.id.reg_follow_listview) ListView listview;
    @Bind(R.id.reg_follow_continue_b) Button continue_b;

    ArrayList<Pet> list = new ArrayList<>();

    ArrayList<Boolean> follow_booleans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_follow);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, AddPetActivity.class);
        //intent.setFlags()
        startActivity(intent);


        Log.d("RegFollowActivity", "Getting id by email: " + CurrentUser.email);
        ParseQuery<ParseUser> user_q = ParseQuery.getQuery("User");
        user_q.whereEqualTo("email", CurrentUser.email);
        user_q.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> mList, ParseException e) {
                if (e == null){
                    try{CurrentUser.id = mList.get(0).getString("id");}catch(Exception e2){e2.printStackTrace();}
                }
                else{
                    Log.d("RegFollowActivity", "CODE: " + e.getCode());
                    e.printStackTrace();
                }
            }
        });


        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Follow pets");

        //listview

        ParseQuery<ParseObject> data = ParseQuery.getQuery("Pet");
        data.setLimit(CurrentUser.LOAD_LIMIT);
        data.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> mList, ParseException e) {
                if (e == null){
                    for (ParseObject parse : mList){
                        list.add(DataParser.parsePet(parse));
                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registration_follow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_reg_follow_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleNextActivity(View v){
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);

    }
}
