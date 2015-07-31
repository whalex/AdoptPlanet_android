package adoptplanet.com.adoptplanet.model;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;

import adoptplanet.com.adoptplanet.R;

/**
 * Created by Alexeich on 13.07.2015.
 */
public class Pet implements Serializable{

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
    public String photo_local;
    public int breed;
    public int type;
    public int size;
    public int gender;
    public String description;
    public File photo;


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
}
