package adoptplanet.com.adoptplanet.model;

import android.net.Uri;

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
    public int age;
    public String photo_url;
    public Uri photo_uri;
    public int breed;
    public int type = -1;
    public int size;
    public int gender;
    public String description;

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
