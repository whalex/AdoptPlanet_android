package adoptplanet.com.adoptplanet.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

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

    public static final int ID_GALLERY = 10;
    public static final int ID_PHOTO = 11;

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

    AlertDialog dialog_type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        ButterKnife.bind(this);

        context = this;
        this_activity = this;

        CacheHolder.registration_pool.add(this);

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
        try{temp_pet.age = Integer.parseInt(age_et.getText().toString());}catch(Exception e){temp_pet.age = 0;}
        //temp_pet.breed
        //temp_pet.type
        //temp_pet.gender
        //temp_pet.uri
        if (size_et.getText()!=null && size_et.getText().length() != 0)
            temp_pet.size = Integer.parseInt(size_et.getText().toString());

        String alert_str = "";
//        if (temp_pet.photo_uri == null){
//            alert_str += "\n Pick photo";
//        }
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
                            pet_updt.put("owner", ParseObject.createWithoutData(ParseUser.class, CurrentUser.id));
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
                pet_p.put("owner", ParseObject.createWithoutData(ParseUser.class, CurrentUser.id));

                if (temp_pet.photo_local != null)
                    pet_p.put("photo_f", new File(temp_pet.photo_local));

                pet_p.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                            LinearLayout layout = (LinearLayout)
                                    getLayoutInflater().inflate(R.layout.alert_finish_registration, null);

                            Button finish_b = (Button)layout.findViewById(R.id.alert_finish_reg_finish_b);

                            final AlertDialog dialog = new AlertDialog.Builder(context)
                                    .setView(layout)
                                    .create();

                            finish_b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    CacheHolder.finishRegistration();
                                }
                            });

                            dialog.show();
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
        Intent pickPhoto =
                new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, ID_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Request: " + requestCode + " Result: " + resultCode);

        if (resultCode == RESULT_OK && data != null) {
            Log.d(TAG, "DATA: " + data + " GET_DATA: " + data.getData());

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();


            File image_file = new File(filePath);
            temp_pet.photo = image_file;
            Log.d(TAG, "Path: " + filePath);
            Picasso.with(this)
                    .load(image_file)
                    .transform(new CircleTransform())
                    .into(photo_iv);

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
        LayoutInflater inflater = getLayoutInflater();

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final String[] types = getResources().getStringArray(R.array.pet_types);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = ((TextView)((LinearLayout) v).getChildAt(0)).getText().toString();
                int index = -1;
                for (int i = 0; i < types.length; i++){
                    String type = types[i];
                    if (curr.equals(type)){
                        index = i;
                        break;
                    }
                }

                if (temp_pet.type != index) {
                    temp_pet.type = index;

                    if (index != -1)
                        type_tv.setText(context.getResources().getStringArray(R.array.pet_types)[index]);
                    else {
                        type_tv.setText(context.getResources().getString(R.string.choose_type));
                    }

                    temp_pet.breed = -1;
                    breed_tv.setText(context.getResources().getString(R.string.choose_breed));
                }
                dialog_type.dismiss();
            }
        };

        for (String type : types){
            LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.z_listview_pettype, null);
            lay.setOnClickListener(listener);
            ((TextView)lay.findViewById(R.id.z_listview_pettype)).setText(type);
            layout.addView(lay);
        }

        dialog_type = new AlertDialog.Builder(this)
                .setView(layout)
                .setCancelable(true)
                .create();

        temp_pet.type = -1;
        dialog_type.show();
    }

    public void handleBreed(View view) {
        if (temp_pet.type != -1){
            new AlertBuilder().showBread(this, temp_pet.type, breed_tv, temp_pet);
        }
        else{
            Toast.makeText(this, R.string.toast_chose_type_first, Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
