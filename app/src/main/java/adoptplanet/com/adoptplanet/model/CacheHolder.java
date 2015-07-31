package adoptplanet.com.adoptplanet.model;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseUser;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;

/**
 * Created by Alexeich on 22.07.2015.
 */
public class CacheHolder {

    public static ArrayList<Activity> registration_pool = new ArrayList<>();


    public static ArrayList<String> breeds_cat = new ArrayList<>();
    public static ArrayList<String> breeds_dog = new ArrayList<>();
    public static ArrayList<String> breeds_fish = new ArrayList<>();
    public static ArrayList<String> breeds_horse = new ArrayList<>();
    public static ArrayList<String> breeds_ferret = new ArrayList<>();
    public static ArrayList<String> breeds_others = new ArrayList<>();
    public static ArrayList<String> breeds_rabbit = new ArrayList<>();
    public static ArrayList<String> breeds_bird = new ArrayList<>();
    public static ArrayList<String> breeds_hamster = new ArrayList<>();


    public static ArrayList<Pet> pets = new ArrayList<>();

    public static ArrayList<ParseUser> users = new ArrayList<>();

    public static void addPet(Pet add){
        for(Pet pet : pets){
            if (pet.id == add.id)return;
        }
        pets.add(add);
    }

    public static void removePet(Pet remove){
        removePet(remove.id);
    }

    public static void removePet(String id){
        for (Pet pet : pets){
            if (pet.id.equals(id)){
                pets.remove(pet);
                return;
            }
        }
    }

    public static Pet getPet(String id){
        for (Pet pet : pets){
            if (pet.id.equals(id))return pet;
        }
        return null;
    }

    public static ArrayList<String> getListByType(int type){
        ArrayList<String> breeds_to_choose = null;
        switch(type){
            case Pet.TYPE_CAT:
                breeds_to_choose = CacheHolder.breeds_cat;
                break;
            case Pet.TYPE_DOG:
                breeds_to_choose = CacheHolder.breeds_dog;
                break;
            case Pet.TYPE_HORSE:
                breeds_to_choose = CacheHolder.breeds_horse;
                break;
            case Pet.TYPE_RABBIT:
                breeds_to_choose = CacheHolder.breeds_rabbit;
                break;
            case Pet.TYPE_HAMSTER:
                breeds_to_choose = CacheHolder.breeds_hamster;
                break;
            case Pet.TYPE_FISH:
                breeds_to_choose = CacheHolder.breeds_fish;
                break;
            case Pet.TYPE_BIRD:
                breeds_to_choose = CacheHolder.breeds_bird;
                break;
            case Pet.TYPE_FERRET:
                breeds_to_choose = CacheHolder.breeds_ferret;
                break;
            case Pet.TYPE_OTHERS:
                breeds_to_choose = CacheHolder.breeds_others;
                break;
            default:
                breeds_to_choose = null;
        }
        return breeds_to_choose;
    }

    public static String getNameByType(Context context, int type){
        return context.getResources().getStringArray(R.array.pet_types)[type];
    }

    public static void finishRegistration(){
        for (Activity ac : registration_pool)
            ac.finish();
    }

}
