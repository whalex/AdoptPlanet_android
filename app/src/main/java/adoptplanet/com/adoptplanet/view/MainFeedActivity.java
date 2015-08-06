package adoptplanet.com.adoptplanet.view;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.controller.EventListAdapter;
import adoptplanet.com.adoptplanet.controller.PetGridviewAdapter;
import adoptplanet.com.adoptplanet.model.Event;
import adoptplanet.com.adoptplanet.model.Pet;
import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("deprecation")
public class MainFeedActivity extends AppCompatActivity {

    public static final String TAG = "MainFeedAcivity";

    @Bind(R.id.tabHost) TabHost th;

    @Bind(R.id.main_feed_petlist_grid) GridView pet_grid_view;
    @Bind(R.id.main_feed_eventlist_list) ListView event_list_view;

    @Bind(R.id.main_feed_toolbar) Toolbar toolbar;

    @Bind(R.id.main_feed_fab) FloatingActionMenu fab;

    PetGridviewAdapter pet_adapter;
    EventListAdapter event_adapter;

    ArrayList<Pet> pet_list = new ArrayList<>();
    ArrayList<Event> event_list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        ButterKnife.bind(this);


        th.setup();
        TabHost.TabSpec ts = th.newTabSpec("Pets");
        ts.setContent(R.id.tab1);
        ts.setIndicator("Pets");
        th.addTab(ts);
        ts = th.newTabSpec("Events");
        ts.setContent(R.id.tab2);
        ts.setIndicator("Events");
        th.addTab(ts);

        LayoutInflater inflater = getLayoutInflater();

        LinearLayout bar_lay = (LinearLayout) inflater.inflate(R.layout.bar_main_feed1, null);
        //toolbar.addView(bar_lay);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                return false;
//            }
//        });


        setSupportActionBar(toolbar);
        ActionBar action_bar = getSupportActionBar();
        action_bar.setDefaultDisplayHomeAsUpEnabled(true);
        action_bar.setHomeAsUpIndicator(R.drawable.z_menu_icon);
        action_bar.setDisplayUseLogoEnabled(true);
        action_bar.setLogo(R.drawable.z_action_bar_logo_fixed);
        action_bar.setTitle("");
        action_bar.setDisplayShowHomeEnabled(true);
        //action_bar.setDisplayHomeAsUpEnabled(true);

        pet_adapter = new PetGridviewAdapter(this, pet_list);
        pet_grid_view.setAdapter(pet_adapter);

        event_adapter = new EventListAdapter(this, event_list);
        event_list_view.setAdapter(event_adapter);

        ParseQuery<ParseObject> pets_parse = ParseQuery.getQuery("Pet");
        pets_parse.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> mList, ParseException e) {
                for (ParseObject pet_parse : mList) {
                    Pet temp = new Pet();
                    temp.name = pet_parse.getString("name");
                    temp.id = pet_parse.getString("id");
                    temp.age = pet_parse.getInt("age");
                    temp.size = pet_parse.getInt("size");
                    temp.breed = pet_parse.getInt("breed");
                    temp.description = pet_parse.getString("description");
                    temp.photo_url = pet_parse.getString("photo");
                    //publishProgress(temp);
                    pet_list.add(temp);
                    pet_adapter.notifyDataSetChanged();
                    //Log.d(TAG, "Up Pet! Name:" + temp.name);
                    //Log.d(TAG, "Size: " + pet_list.size());
                }
            }
        });

        ParseQuery<ParseObject> events_parse = ParseQuery.getQuery("Event");
        events_parse.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> mList, ParseException e) {
                for (ParseObject pet_parse : mList) {
                    Event temp = new Event();
                    temp.name = pet_parse.getString("name");
                    temp.id = pet_parse.getString("id");
                    temp.description = pet_parse.getString("description");
                    temp.date = pet_parse.getDate("date");
                    temp.country = pet_parse.getString("country");
                    temp.address = pet_parse.getString("address");
                    temp.city = pet_parse.getString("city");
                    temp.photo_url = pet_parse.getString("image");
                    //publishProgress(temp);
                    event_list.add(temp);
                    event_adapter.notifyDataSetChanged();
                    //Log.d(TAG, "Up Event! Name:" + temp.name);
                    //Log.d(TAG, "Size: " + event_list.size());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_feed, menu);
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

        if (id == R.id.main_feed_menu_search){
            Intent search_intent = new Intent(this, SearchPetActivity.class);
            startActivity(search_intent);
            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleFab(View view) {
        int id = view.getId();

        switch (id){
            case R.id.main_feed_fab_add:
                handleAdd();
                break;
            case R.id.main_feed_fab_favorite:
                handleFavorite();
                break;
            case R.id.main_feed_fab_login:
                handleLogin();
                break;
            case R.id.main_feed_fab_message:
                handleMessage();
                break;
            case R.id.main_feed_fab_news:
                handleNews();
                break;
            case R.id.main_feed_fab_search:
                handleSearch();
                break;
        }

        fab.close(true);
    }

    public void handleAdd(){
        // todo add
    }

    public void handleFavorite(){
        // todo favorite
    }

    public void handleLogin(){
        Intent intent = new Intent(this, StartingScreenActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void handleMessage(){
        // todo message
    }

    public void handleNews(){
        // todo news
    }

    public void handleSearch(){
        // todo search
    }

    class FeedPetLoader extends AsyncTask<Void, Pet, Void> {

        ArrayList<Pet> petlist;
        PetGridviewAdapter adapter;

        public FeedPetLoader(ArrayList<Pet> petlist, PetGridviewAdapter adapter, int offset){
            this.petlist = petlist;
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("MainFeedAcivity", "doInBackground");


            return null;
        }

        @Override
        protected void onProgressUpdate(Pet... values) {
            petlist.add(values[0]);
            adapter.notifyDataSetChanged();
        }
    }

    class FeedEventLoader extends AsyncTask<Void, Event, Void>{
        ArrayList<Event> eventlist;

        public FeedEventLoader(ArrayList<Event> eventlist, int offset){
            this.eventlist = eventlist;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery<ParseObject> pets_parse = ParseQuery.getQuery("Pet");
            pets_parse.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> mList, ParseException e) {
                    for (ParseObject pet_parse : mList) {
                        Event temp = new Event();
                        temp.name = pet_parse.getString("name");
                        temp.id = pet_parse.getString("id");
                        temp.description = pet_parse.getString("description");
                        temp.city = pet_parse.getString("city");
                        temp.address = pet_parse.getString("address");
                        temp.country = pet_parse.getString("country");
                        temp.date = pet_parse.getDate("date");

                        publishProgress(temp);
                    }
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(Event... values) {
            eventlist.add(values[0]);
        }
    }
}


