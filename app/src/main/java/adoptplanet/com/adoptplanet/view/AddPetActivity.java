package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.controller.AlertBuilder;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.model.CurrentUser;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.CircleTransform;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPetActivity extends AppCompatActivity {

    public static final String TAG = "AddPetActivity";

    public static final int EDIT_PET = 1;
    public static final int ADD_PET = 2;

    public static final int ID_GALLERY = 1;
    public static final int ID_PHOTO = 2;

    @Bind(R.id.add_pet_toolbar) Toolbar toolbar;
    // AGE
    @Bind(R.id.add_pet_age_et) EditText age_et;//
    // BREED
    @Bind(R.id.add_pet_breed_l) LinearLayout breed_l;//
    @Bind(R.id.add_pet_breed_tv) TextView breed_tv;//
    // TYPE
    @Bind(R.id.add_pet_type_l) LinearLayout type_l;//
    @Bind(R.id.add_pet_type_tv) TextView type_tv;//
    // SIZE
    @Bind(R.id.add_pet_size_l) LinearLayout size_l;//
    @Bind(R.id.add_pet_pet_size_et) EditText size_et;//
    // GENDER
    @Bind(R.id.add_pet_male_b) Button male_b;//
    @Bind(R.id.add_pet_female_b) Button female_b;//
    // NAME
    @Bind(R.id.add_pet_name_et) EditText name_et;//
    // PHOTO
    @Bind(R.id.add_pet_photo_iv) ImageView photo_iv;//

    private Pet pet = null;
    private Pet temp_pet = new Pet();

    private int current_action = ADD_PET;

    private Context context;
    private Activity this_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        ButterKnife.bind(this);

        context = this;
        this_activity = this;

        setSupportActionBar(toolbar);
        ActionBar action_bar = getSupportActionBar();

        Intent this_intent = getIntent();
        pet = (Pet) this_intent.getSerializableExtra("pet");
        if (pet != null){

            current_action = EDIT_PET;
            temp_pet = pet.clone_pet();

            action_bar.setTitle("Edit Pet");
            Picasso.with(this)
                    .load(temp_pet.photo_url)
                    .transform(new CircleTransform())
                    .into(photo_iv);
            name_et.setText(temp_pet.name);

            if (temp_pet.gender == Pet.GENDER_FEMALE){
                changeGender(Pet.GENDER_FEMALE);
            }

        }
        else{
            action_bar.setTitle("Add Pet");
            pet = new Pet();
            pet.gender = Pet.GENDER_MALE;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_pet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleSubmit(View view) {
        temp_pet.name = name_et.getText().toString();
        temp_pet.age = Integer.parseInt(age_et.getText().toString());
        //temp_pet.breed
        //temp_pet.type
        //temp_pet.gender
        //temp_pet.uri
        if (size_et.getText()!=null)temp_pet.size = Integer.parseInt(size_et.getText().toString());
        String alert_str = "";
        if (temp_pet.photo_uri == null){
            alert_str += "\n Pick photo";
        }
        if (temp_pet.name == null){
            alert_str += "\n Give pet a name.";
        }
        if (temp_pet.type == -1){
            alert_str += "\n Choose pet type.";
        }


        if (alert_str.length() != 0){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(alert_str)
                    .setTitle("Wrong filled fields")
                    .create();
            dialog.show();
        }
        else{
            if (current_action == EDIT_PET){
                ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");

                query.getInBackground(pet.id, new GetCallback<ParseObject>() {
                    public void done(ParseObject pet_updt, ParseException e) {
                        if (e == null) {
                            pet_updt.put("name", temp_pet.name);
                            pet_updt.put("age", temp_pet.age);
                            pet_updt.put("breed", temp_pet.breed);
                            pet_updt.put("type", temp_pet.type);
                            pet_updt.put("gender", temp_pet.gender);
                            pet_updt.put("size", temp_pet.size);
                            pet_updt.put("owner", CurrentUser.id);
                            pet_updt.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null){
                                        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                                        this_activity.finish();
                                    }
                                    else{
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
            }
            else if (current_action == ADD_PET){
                ParseObject pet_p = new ParseObject("Pet");
                pet_p.put("name", temp_pet.name);
                pet_p.put("age", temp_pet.age);
                pet_p.put("breed", temp_pet.breed);
                pet_p.put("type", temp_pet.type);
                pet_p.put("gender", temp_pet.gender);
                pet_p.put("size", temp_pet.size);
                pet_p.put("owner", CurrentUser.id);

                if (temp_pet.photo_uri != null)
                    pet_p.put("photo_f", new File(temp_pet.photo_uri.getPath()));

                pet_p.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                            this_activity.finish();
                        }
                        else{
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void handlePhoto(View view) {
        Intent pickPhoto = new Intent();
        pickPhoto.setType("image/*");
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickPhoto, "Select Picture"), ID_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Request: " + requestCode + " Result: " + resultCode);

        switch(resultCode){
            case ID_GALLERY:
                temp_pet.photo_uri = data.getData();
                Log.d(TAG, "Photo was picked: " + pet.photo_uri);

                Picasso.with(this)
                        .load(temp_pet.photo_uri)
                        .transform(new CircleTransform())
                        .into(photo_iv);
                break;
            case ID_PHOTO:
                Log.d(TAG, "Camera");
                break;
            default:
                Log.d(TAG, "ERROR!");
        }
    }

    public void  handleGenderB(View v){
        int id = v.getId();
        if (id == R.id.add_pet_male_b){
            changeGender(Pet.GENDER_MALE);
            return;
        }

        if (id == R.id.add_pet_female_b){
            changeGender(Pet.GENDER_FEMALE);
            return;
        }
    }

    public void changeGender(int to){
        if (to == Pet.GENDER_MALE){
            if (temp_pet.gender != Pet.GENDER_MALE){
                makeActive(male_b);
                makeUnactive(female_b);
                temp_pet.gender = Pet.GENDER_MALE;
            }
        }
        else{
            if (temp_pet.gender != Pet.GENDER_FEMALE){
                makeActive(female_b);
                makeUnactive(male_b);
                temp_pet.gender = Pet.GENDER_FEMALE;
            }
        }
    }

    public void makeActive(Button b){
        b.setTextColor(getResources().getColor(R.color.white));
        b.setBackgroundColor(getResources().getColor(R.color.gray_l));
    }

    public void makeUnactive(Button b){
        b.setTextColor(getResources().getColor(R.color.gray));
        b.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void handleType(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setAdapter(new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_single_choice,
                                getResources().getStringArray(R.array.pet_types)),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                temp_pet.type = which;
                                Log.d(TAG, "Choosen: " + which);
                                type_tv.setText(context.getResources().getStringArray(R.array.pet_types)[which]);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        dialog.show();
    }

    public void handleBreed(View view) {
        if (temp_pet.type != -1){
            new AlertBuilder().showBread(this, temp_pet.type, breed_tv, temp_pet);
        }
        else{
            Toast.makeText(this, R.string.toast_chose_type_first, Toast.LENGTH_LONG).show();
        }
    }
}
