package adoptplanet.com.adoptplanet.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

import adoptplanet.com.adoptplanet.R;

/**
 * Created by Alexeich on 13.07.2015.
 */
public class Pet implements Serializable{

    public static final String TAG = "Pet";
    // --------- GENDERS -------------
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    // ---------  TYPES ---------------
    public static final int TYPE_DOG = 0;
    public static final int TYPE_CAT = 1;
    public static final int TYPE_HORSE = 2;
    public static final int TYPE_FISH = 3;
    public static final int TYPE_BIRD = 4;
    public static final int TYPE_HAMSTER = 5;
    public static final int TYPE_FERRET = 6;
    public static final int TYPE_RABBIT = 7;
    public static final int TYPE_OTHERS = 8;

    public String name;
    public String id;
    public String owner_id;
    public int age;
    public String photo_url;
    public int breed;
    public String breed_str;
    public int type;
    public int size;
    public int gender;
    public String description;


    public Pet(){
        type = -1;
        size = -1;
        age = -1;
        gender = GENDER_MALE;
        breed = -1;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Pet to_r = new Pet();
        to_r.id = id;
        to_r.name = name;
        to_r.age = age;
        to_r.photo_url = photo_url;
        to_r.type = type;
        to_r.breed = breed;
        to_r.gender = gender;
        to_r.size = size;
        to_r.description = description;
        return to_r;
    }

    public Pet clone_pet(){
        try{return ((Pet)this.clone());}catch(Exception e){return null;}
    }

    public String getBreedStr(){
        if (breed == -1){
            if (breed_str != null)
                return breed_str;
            else
                return "Unknown";
        }
        else{
            return CacheHolder.getListByType(type).get(breed);
        }
    }

    public Pet(ParseObject pet_parse){
        Log.d(TAG, "Pet block {");
        // name
        name = pet_parse.getString("name");
        // owner
        try{
            owner_id = pet_parse.getParseObject("owner").getObjectId();
        }catch(Exception e){
            e.printStackTrace();
            owner_id = null;
        }
        // id
        id = pet_parse.getObjectId();
        // age
        age = pet_parse.getInt("age");
        // size
        size = pet_parse.getInt("size");
        // type
        type = pet_parse.getInt("type");
        //gender
        gender = pet_parse.getInt("gender");
        // breed
        breed = pet_parse.getInt("breed");
        if (breed == -1) {
            breed_str = pet_parse.getString("custom_breed");
        }
        // photo
        try{
            photo_url = pet_parse.getParseFile("photo_f").getUrl();
            //photo = BitmapFactory.decodeByteArray(file, 0, file.length);
            //BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(photo));
            //bo.write(file);
            //bo.flush();
            //bo.close();
        }
        catch(Exception e){
            e.printStackTrace();

            photo_url = null;
        }
        Log.d(TAG, "    Photo: " + photo_url);
        Log.d(TAG, "    Pet parsed. Name: " + name + " ID: " + id +
                " Type: " + type + " Breed: " + breed + " OwnerId: " + owner_id + "\n}");
    }

}
