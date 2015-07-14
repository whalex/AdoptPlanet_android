package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import adoptplanet.com.adoptplanet.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends Activity {

    @Bind(R.id.login_email) EditText email_edit;
    @Bind(R.id.login_password) EditText pass_edit;
    @Bind(R.id.login_login) Button login_button;

    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void setUser(ParseUser user){
        this.user = user;
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show();
    }


    public void handle_login(View view) {

        String login = email_edit.getText().toString();
        String password = pass_edit.getText().toString();

        if (login != null && password != null)
            ParseUser.logInInBackground(login, password, new LogInCallback() {
                @Override
                public void done(ParseUser mParseUser, ParseException e) {
                    Log.d("LoginActivity", "Login: " + email_edit + " Pass: " + pass_edit);
                    if (mParseUser != null){
                        setUser(mParseUser);
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
    }
}
