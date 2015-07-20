package adoptplanet.com.adoptplanet.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Alexeich on 13.07.2015.
 */
public class Pet implements Serializable{

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public String name;
    public String id;
    public int age;
    public String photo_url;
    public Uri photo_uri;
    public String breed;
    public int size;
    public int gender;
    public String description;
}
