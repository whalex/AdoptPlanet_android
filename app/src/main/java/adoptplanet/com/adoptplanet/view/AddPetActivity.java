package adoptplanet.com.adoptplanet.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.CircleTransform;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPetActivity extends ActionBarActivity {

    @Bind(R.id.add_pet_toolbar) Toolbar toolbar;
    @Bind(R.id.add_pet_age_l) LinearLayout age_l;
    @Bind(R.id.add_pet_age_et) EditText age_et;
    @Bind(R.id.add_pet_breed_l) LinearLayout breed_l;
    @Bind(R.id.add_pet_type_l) LinearLayout type_l;
    @Bind(R.id.add_pet_size_l) LinearLayout size_l;
    @Bind(R.id.add_pet_pet_size_et) EditText size_et;
    @Bind(R.id.add_pet_male_b) Button male_b;
    @Bind(R.id.add_pet_female_b) Button female_b;
    @Bind(R.id.add_pet_submit_b) LinearLayout submit_l;
    @Bind(R.id.add_pet_name_et) EditText name_et;
    @Bind(R.id.add_pet_photo_iv) ImageView photo_iv;

    private Pet pet = null;
    private Pet temp_pet = new Pet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar action_bar = getSupportActionBar();

        Intent this_intent = getIntent();
        pet = (Pet) this_intent.getSerializableExtra("pet");
        if (pet != null){
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
        ParseObject pet_p = new ParseObject("Pet");
        pet_p.put("name", pet.name);
    }

    public void handlePhoto(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == requestCode){
            Uri image = data.getData();

            pet.photo_uri = image;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                //.setView()
                .create();
    }

    public void handleBreed(View view) {
        if (temp_pet.type != -1){
            
        }
        else{
            Toast.makeText(this, R.string.toast_chose_type_first, Toast.LENGTH_LONG).show();
        }
    }
}
