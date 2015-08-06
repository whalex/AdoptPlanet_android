package adoptplanet.com.adoptplanet.utils;

/**
 * Created by Alexeich on 05.08.2015.
 */
public class CodeHandler {

    public static String getByCode(int code){

        String r = null;
        switch (code){
            case -1:
                r += "You are already logged in.";
            case 1:
                r += "Server error.";
            case 100:
                r += "Connection to servers failed.";
            case 124:
                r += "Server timed out.";
            case 125:
                r += "Invalid email address.";
            case 202:
                r += "Email is already taken.";
            case 209:
                r += "Invalid session token.";
        }

        return r;

    }
}
