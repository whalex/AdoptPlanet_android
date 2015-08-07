package adoptplanet.com.adoptplanet.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;

/**
 * Created by Alexeich on 06.08.2015.
 */
public class AlertCreator {

    public static void showFinishReg(Context context){

        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alert_finish_registration, null);


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(layout)
                .create();

        Button continue_lay = (Button) layout.findViewById(R.id.alert_finish_reg_finish_b);

        continue_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CacheHolder.finishRegistration();
            }
        });

        dialog.show();
    }
}
