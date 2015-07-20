package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;


import adoptplanet.com.adoptplanet.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class StartingScreenActivity extends Activity {

    @Bind(R.id.starting_screen_view_pager) ViewPager pager;
    @Bind(R.id.starting_screen_indicator) CirclePageIndicator indicator;

    private String[] main_ts = {
            "Main Text 1",
            "Main Text 2",
            "Main Text 3",
            "Main Text 4",
    };

    private String[] second_ts = {
            "Second Text 1",
            "Second Text 2",
            "Second Text 3",
            "Second Text 4",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        ButterKnife.bind(this);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "9maWSgFEvVDhE2S38JjLHWjkrriEXlSf0MGT5BrO", "aZhLgSlogo10qICrHMat9xTaQJiMJpieg1sSikOt");
        ParseUser.enableRevocableSessionInBackground();

        TextPagerAdapter adapter = new TextPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setStrokeWidth(0);
        indicator.setViewPager(pager);


    }

    public void handle_login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void handle_signup(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void handle_try(View view) {
        Intent intent = new Intent(this, MainFeedActivity.class);
        startActivity(intent);
    }

    private class TextPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return main_ts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = (LayoutInflater)container.getContext
                    ().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.z_starting_creen_element, null);
            TextView main_t = (TextView) layout.findViewById(R.id.z_starting_screen_element_main_text);
            TextView second_t = (TextView) layout.findViewById(R.id.z_starting_screen_element_second_text);

            main_t.setText(main_ts[position]);
            second_t.setText(second_ts[position]);

            ((ViewPager) container).addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
