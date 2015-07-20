package adoptplanet.com.adoptplanet.view;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CurrentUser;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity {

    @Bind(R.id.sign_up_email) EditText email_edit;
    @Bind(R.id.sign_up_password) EditText password_edit;
    @Bind(R.id.sign_up_username) EditText username_edit;

    public static ParseUser to_register;
    private int type = 1;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        to_register = new ParseUser();

    }


    public void handleSignUp(View view) {
        String username = username_edit.getText().toString();
        String password = password_edit.getText().toString();
        String email = email_edit.getText().toString();

        if (username != null && password != null && email != null){
            // todo normal input check

            to_register.put("username_", username);
            to_register.put("username", email);
            to_register.put("email", email);
            to_register.put("password", password);



            LayoutInflater inflater = getLayoutInflater();
            LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.alert_account_choose, null);

            final ImageView select_pet = (ImageView) lay.findViewById(R.id.choose_type_pet);
            final ImageView select_lover = (ImageView) lay.findViewById(R.id.choose_type_lover);
            final TextView text_view = (TextView) lay.findViewById(R.id.choose_type_text);
            Button accept = (Button) lay.findViewById(R.id.choose_type_accept);

            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(lay)
                    .setCancelable(true)
                    .create();

            View.OnClickListener on_click_choose = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();

                    if (id == R.id.choose_type_accept){
                        to_register.put("type", getType());
                        dialog.dismiss();
                        Toast.makeText(context, "Wait for sending data...", Toast.LENGTH_LONG).show();
                        signUp();
                    }
                    else if(id == R.id.choose_type_pet){
                        if (getType() == 2){
                            text_view.setText("Text for pet account...");
                            setType(1);
                            makeUnactive(select_lover);
                            makeActive(select_pet);
                        }
                    }
                    else if(id == R.id.choose_type_lover){
                        if (getType() == 1){
                            text_view.setText("Text for lover account...");
                            setType(2);
                            makeActive(select_lover);
                            makeUnactive(select_pet);
                        }
                    }
                }
            };

            select_lover.setOnClickListener(on_click_choose);
            select_pet.setOnClickListener(on_click_choose);
            accept.setOnClickListener(on_click_choose);

            dialog.show();
        }
        else{
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Error!\nCheck your input.")
                    .create();
            dialog.show();
        }

    }

    private int getType(){
        return type;
    }

    private void setType(int type){
        this.type = type;
    }

    private void makeUnactive(ImageView iv){
        iv.setBackgroundColor(getResources().getColor(R.color.white));
    }
    private void makeActive(ImageView iv){
        iv.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    public void handleSocial(View view){
        // todo sign up through social networks...
    }

    private void signUp(){
        to_register.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(context, "Registration succesful", Toast.LENGTH_LONG).show();
                    CurrentUser.email = to_register.getString("email");
                    //Intent intent = new Intent(context, RegistrationFollowActivity.class);
                    //startActivity(intent);
                }
                else{
                    Log.d("SignUpActivity", "CODE: " + e.getCode());
                    e.printStackTrace();
                }
            }
        });
    }

}
