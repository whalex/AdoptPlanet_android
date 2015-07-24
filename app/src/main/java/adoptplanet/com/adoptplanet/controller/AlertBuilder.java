package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.model.CurrentUser;
import adoptplanet.com.adoptplanet.model.Pet;

/**
 * Created by Alexeich on 22.07.2015.
 */
public class AlertBuilder {

    public void showBread(final Context context, final int type, final TextView tv, final Pet temp_pet){

        LayoutInflater inflater = LayoutInflater.from(context);

        ArrayList<String> breeds_to_choose = CacheHolder.getListByType(type);



        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alert_breed_choose, null);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(layout)
                .create();

        Button confirm_b = (Button)layout.findViewById(R.id.alert_breed_choose_button);
        TextView top_text = (TextView)layout.findViewById(R.id.alert_breed_choose_top_text);
        TextView chosen_text = (TextView)layout.findViewById(R.id.alert_breed_choose_choose);
        ListView list_view = (ListView)layout.findViewById(R.id.alert_breed_choose_listview);
        EditText edit_text = (EditText) layout.findViewById(R.id.alert_breed_choose_search);

        final BreedListviewAdapter adapter = new BreedListviewAdapter(context, breeds_to_choose,chosen_text);
        list_view.setAdapter(adapter);

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null)
                    adapter.getFilter().filter(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        top_text.setText("Choose " + CacheHolder.getNameByType(context, type) + " Breed.");
        confirm_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getBread() != -1) {
                    temp_pet.breed = adapter.getBread();
                    tv.setText(CacheHolder.getListByType(type).get(temp_pet.breed));
                    dialog.dismiss();
                }

            }
        });
        chosen_text.setText("Current: none");

        dialog.show();
    }
}
