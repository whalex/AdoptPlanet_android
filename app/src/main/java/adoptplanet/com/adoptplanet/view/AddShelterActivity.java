package adoptplanet.com.adoptplanet.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import adoptplanet.com.adoptplanet.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddShelterActivity extends ActionBarActivity {

    @Bind(R.id.add_shelter_toolbar) Toolbar toolbar;

    @Bind(R.id.add_shelter_name) EditText name_et;
    @Bind(R.id.add_shelter_email) EditText email_et;
    @Bind(R.id.add_shelter_number) EditText number_et;
    @Bind(R.id.add_shelter_address_lay) LinearLayout address_lay;
    @Bind(R.id.add_shelter_address_tv) TextView address_tv;
    @Bind(R.id.add_shelter_submit) Button submit_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("CREATE SHELTER ACCOUNT");
        setSupportActionBar(toolbar);

    }


    public void handleSubmit(View view) {
        //todo submit

    }
}
