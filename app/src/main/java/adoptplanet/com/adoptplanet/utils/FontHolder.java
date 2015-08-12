package adoptplanet.com.adoptplanet.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by Alexeich on 11.08.2015.
 */
public class FontHolder {

    public static Typeface getRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(), "RobotoRegular.ttf");
    }

    public static Typeface getBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "RobotoBold.ttf");
    }

    public static Typeface getLight(Context context){
        return Typeface.createFromAsset(context.getAssets(), "RobotoLight.ttf");
    }

}
