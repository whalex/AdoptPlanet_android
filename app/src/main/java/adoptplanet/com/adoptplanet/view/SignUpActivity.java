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

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.model.CurrentUser;
import adoptplanet.com.adoptplanet.utils.CodeHandler;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity {

    public static final String TAG = "SignUpActivity";

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

        CacheHolder.registration_pool.add(this);

        to_register = new ParseUser();

    }


    public void handleSignUp(View view) {
        String username = username_edit.getText().toString();
        String password = password_edit.getText().toString();
        String email = email_edit.getText().toString();

        if (username != null && password != null && email != null){
            // todo normal input check


            to_register.put("username_", username);
            to_register.setUsername(email);
            to_register.setEmail(email);
            to_register.setPassword(password);

            LayoutInflater inflater = getLayoutInflater();

            LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.alert_term, null);

            final TextView tv = (TextView) lay.findViewById(R.id.text);
            final Button but = (Button) lay.findViewById(R.id.accept);

            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(lay)
                    .setCancelable(true)
                    .create();

            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    showChooseAcc();
                }
            });

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

    private void showChooseAcc(){
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.alert_account_choose, null);

        final ImageView select_user_iv = (ImageView) lay.findViewById(R.id.user_iv);
        final ImageView select_shelter_iv = (ImageView) lay.findViewById(R.id.shelter_iv);
        final TextView user_tv = (TextView) lay.findViewById(R.id.user_tv);
        final TextView shelter_tv = (TextView) lay.findViewById(R.id.shelter_tv);
        final LinearLayout user_lay = (LinearLayout) lay.findViewById(R.id.user_lay);
        final LinearLayout shelter_lay = (LinearLayout) lay.findViewById(R.id.shelter_lay);

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
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                signUp();
                            }
                            else{
                                Log.d(TAG, "CODE: " + e.getCode());
                                e.printStackTrace();
                            }
                        }
                    });

                }
                else if(id == R.id.user_lay){
                    if (getType() == 2){
                        text_view.setText("Text for user account...");
                        setType(1, select_user_iv, user_tv, select_shelter_iv, shelter_tv);
                    }
                }
                else if(id == R.id.shelter_lay){
                    if (getType() == 1){
                        text_view.setText("Text for shelter account...");
                        setType(2, select_user_iv, user_tv, select_shelter_iv, shelter_tv);
                    }
                }
            }
        };

        user_lay.setOnClickListener(on_click_choose);
        shelter_lay.setOnClickListener(on_click_choose);
        accept.setOnClickListener(on_click_choose);

        dialog.show();
    }

    private void setType(int type, ImageView iv1, TextView tv1, ImageView iv2, TextView tv2){

        if (type == 1){
            iv1.setImageResource(R.drawable.z_user_acc_active);
            tv1.setTextColor(getResources().getColor(R.color.blue_d));
            iv2.setImageResource(R.drawable.z_shelter_acc);
            tv2.setTextColor(getResources().getColor(R.color.gray));
        }
        else if (type == 2){
            iv1.setImageResource(R.drawable.z_user_acc);
            tv1.setTextColor(getResources().getColor(R.color.gray));
            iv2.setImageResource(R.drawable.z_shelter_acc_active);
            tv2.setTextColor(getResources().getColor(R.color.blue_d));
        }

        this.type = type;
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
                    CurrentUser.email = to_register.getEmail();
                    CurrentUser.id = to_register.getObjectId();
                    Log.d(TAG, "Email: " + CurrentUser.email + " ID: " + CurrentUser.id);

                    Intent intent = new Intent(context, RegistrationFollowActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d("SignUpActivity", "CODE: " + e.getCode());
                    e.printStackTrace();
                    String mess = CodeHandler.getByCode(e.getCode());
                    if (mess != null)
                        Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "Error code: " + e.getCode(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
