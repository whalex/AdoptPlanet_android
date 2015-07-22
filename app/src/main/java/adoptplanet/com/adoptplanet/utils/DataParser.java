package adoptplanet.com.adoptplanet.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.parse.ParseObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.model.Pet;

public class DataParser {

    public static final String TAG = "DataParser";


    public static Pet parsePet(ParseObject parse){
        Pet pet = new Pet();

        pet.name = parse.getString("name");
        pet.gender = parse.getInt("gender");
        pet.breed = parse.getString("breed");
        pet.age = parse.getInt("age");
        pet.photo_url = parse.getString("photo");
        pet.description = parse.getString("description");
        pet.size = parse.getInt("size");
        pet.id = parse.getString("id");

        return pet;
    }


    public static void uploadBreeds(Context context, int id){
        ArrayList<String> support_langs = new ArrayList<>();
        support_langs.add("en");
        support_langs.add("es");

        String language = Locale.getDefault().getDisplayLanguage();
        ArrayList<String> where;
        String file_name;
        if (support_langs.contains(language))
            file_name = "breeds/" + language + "_breeds_";
        else
            file_name = "breeds/en_breeds_";


        switch (id){
            case Pet.TYPE_CAT:
                file_name += "cat";
                where = CacheHolder.breeds_cat;
                break;
            case Pet.TYPE_DOG:
                file_name += "dog";
                where = CacheHolder.breeds_dog;
                break;
            case Pet.TYPE_HORSE:
                file_name += "horse";
                where = CacheHolder.breeds_horse;
                break;
            case Pet.TYPE_BIRD:
                file_name += "bird";
                where = CacheHolder.breeds_bird;
                break;
            case Pet.TYPE_FERRET:
                file_name += "ferret";
                where = CacheHolder.breeds_ferret;
                break;
            case Pet.TYPE_FISH:
                file_name += "fish";
                where = CacheHolder.breeds_fish;
                break;
            case Pet.TYPE_HAMSTER:
                file_name += "hamster";
                where = CacheHolder.breeds_hamster;
                break;
            case Pet.TYPE_OTHERS:
                file_name += "others";
                where = CacheHolder.breeds_others;
                break;
            case Pet.TYPE_RABBIT:
                file_name += "rabbit";
                where = CacheHolder.breeds_rabbit;
                break;
            default:
                where = new ArrayList<>();
                Log.d(TAG, "Something goes wrong! ID:" + id);
        }

        file_name += ".adoptplanet";

        try {
            AssetManager am = context.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(am.open(file_name)));
            String readed;
            int counter = 1;
            Log.d(TAG, "Load data from file: " + file_name);

            while((readed = reader.readLine()) != null){
                Log.d(TAG, counter + ": " + readed);
                where.add(readed);
                counter++;
            }
        }
        catch(IOException e){Log.e(TAG, "File not found. Name: " + file_name);}
        catch(Exception e){e.printStackTrace();}
    }
}
