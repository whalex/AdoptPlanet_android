package adoptplanet.com.adoptplanet.model;

import java.util.ArrayList;

/**
 * Created by Alexeich on 08.07.2015.
 */
public class CurrentUser {

    public static final int LOAD_LIMIT = 30;

    public static String id;
    public static String name;
    public static String email;
    private static String pass;

    public static void setPass(String pass){CurrentUser.pass = pass;}
    public static String getPass(){return CurrentUser.pass;}

}
