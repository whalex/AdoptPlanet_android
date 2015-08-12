package adoptplanet.com.adoptplanet.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.utils.CircleTransform;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddShelterActivity extends ActionBarActivity {

    public static final int ID_GALLERY = 1;
    public static final String TAG = "AddShelter";

    @Bind(R.id.add_shelter_toolbar) Toolbar toolbar;

    @Bind (R.id.add_shelter_photo) ImageView photo_iv;
    @Bind(R.id.add_shelter_name) EditText name_et;
    @Bind(R.id.add_shelter_email) EditText email_et;
    @Bind(R.id.add_shelter_number) EditText number_et;
    @Bind(R.id.add_shelter_submit) Button submit_b;
    @Bind(R.id.add_shelter_zipcode) EditText zip_et;
    @Bind(R.id.add_shelter_address) AutoCompleteTextView address_actv;

    ArrayList<String> city_list = new ArrayList<>();
    ArrayAdapter<String> city_adapter;

    private File photo_file;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);
        ButterKnife.bind(this);

        context = this;

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("CREATE SHELTER ACCOUNT");
        setSupportActionBar(toolbar);

        city_adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, city_list);

        address_actv.setAdapter(city_adapter);

        address_actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                new CityLoader().execute(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }


    public void handleSubmit(View view) {
        //todo submit
        String name = name_et.getText().toString();
        String email = email_et.getText().toString();
        String number = number_et.getText().toString();
        String city = address_actv.getText().toString();
        int zipcode = -1;
        try{
            zipcode = Integer.parseInt(zip_et.getText().toString());
        }
        catch(NumberFormatException e){
            Toast.makeText(this, "Fill zipcode field!", Toast.LENGTH_LONG).show();
            return;
        }

        if (photo_file == null){
            Toast.makeText(this, "Pick a photo!", Toast.LENGTH_LONG).show();
            return;
        }

        if (name.length() == 0){
            Toast.makeText(this, "Fill name field!", Toast.LENGTH_LONG).show();
            return;
        }

        if (email.length() == 0){
            Toast.makeText(this, "Fill email field!", Toast.LENGTH_LONG).show();
            return;
        }

        if (number.length() == 0){
            Toast.makeText(this, "Fill number field!", Toast.LENGTH_LONG).show();
            return;
        }

        if (city.length() == 0){
            Toast.makeText(this, "Fill address field!", Toast.LENGTH_LONG).show();
            return;
        }

        ParseObject shelter = new ParseObject("Shelter");
        shelter.put("name", name);
        shelter.put("city", city);
        shelter.put("zip", zipcode);
        shelter.put("name", name);
        shelter.put("contact_email", email);
        shelter.put("contact_number", number);
        shelter.put("photo_f", new ParseFile(getByteFromFile(photo_file)));

        shelter.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

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
                            Intent intent = new Intent(context, StartingScreenActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    dialog.show();
                }
                else{
                    Toast.makeText(context, "Error code: " + e.getCode(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "CODE: " + e.getCode());
                    e.printStackTrace();
                }
            }
        });
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
            photo_file = image_file;
            Log.d(TAG, "Path: " + filePath);
            Picasso.with(this)
                    .load(image_file)
                    .transform(new CircleTransform())
                    .into(photo_iv);

        }
    }

    private byte[] getByteFromFile(File file){
        byte[] photo_bytes = new byte[(int)file.length()];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(photo_bytes, 0, photo_bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo_bytes;
    }

    class CityLoader extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            ParseQuery<ParseObject> query = new ParseQuery<>("Cities");
            query.whereContains("name", params[0]);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> mList, ParseException e) {
                    if (e == null) {
                        city_adapter.clear();
                        for (ParseObject object : mList)
                            city_adapter.add(object.getString("name"));

                    } else {
                        Log.d(TAG, "Code:" + e.getCode());
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
    }
}
