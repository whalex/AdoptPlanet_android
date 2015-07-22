package adoptplanet.com.adoptplanet.model;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Alexeich on 22.07.2015.
 */
public class CacheHolder {

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

}
