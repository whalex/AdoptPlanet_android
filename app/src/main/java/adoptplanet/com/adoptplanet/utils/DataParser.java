package adoptplanet.com.adoptplanet.utils;

import com.parse.ParseObject;

import adoptplanet.com.adoptplanet.model.Pet;

/**
 * Created by Alexeich on 20.07.2015.
 */
public class DataParser {

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
}
