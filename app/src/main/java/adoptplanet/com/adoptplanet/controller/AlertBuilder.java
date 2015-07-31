package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;
import adoptplanet.com.adoptplanet.model.CurrentUser;
import adoptplanet.com.adoptplanet.model.Pet;

/**
 * Created by Alexeich on 22.07.2015.
 */
public class AlertBuilder {

    ArrayList<String> breeds_to_choose;

    public void showBread(final Context context, final int type, final TextView tv, final Pet temp_pet){

        final LayoutInflater inflater = LayoutInflater.from(context);

        breeds_to_choose = CacheHolder.getListByType(type);

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alert_breed_choose, null);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(layout)
                .create();

        Button confirm_b = (Button)layout.findViewById(R.id.alert_breed_choose_button);
        TextView top_text = (TextView)layout.findViewById(R.id.alert_breed_choose_top_text);
        TextView chosen_text = (TextView)layout.findViewById(R.id.alert_breed_choose_choose);
        ListView list_view = (ListView)layout.findViewById(R.id.alert_breed_choose_listview);
        EditText edit_text = (EditText) layout.findViewById(R.id.alert_breed_choose_search);
        Button add_b = (Button) layout.findViewById(R.id.alert_breed_add_button);

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

        add_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout add_lay = (LinearLayout) inflater.inflate(R.layout.alert_custom_breed, null);

                final Button add_custom = (Button) add_lay.findViewById(R.id.alert_custom_breed_add_b);
                final EditText add_edit = (EditText) add_lay.findViewById(R.id.alert_custom_breed_et);

                final AlertDialog add_dialog = new AlertDialog.Builder(context)
                        .setView(add_lay)
                        .create();

                add_custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String text = add_edit.getText().toString();

                        if (text != null && text.length() != 0) {
                            boolean is_already = false;
                            for (int i = 0; i < breeds_to_choose.size(); i++) {
                                if (breeds_to_choose.get(i).equals(text)) {
                                    Toast.makeText(context, "Breed is already exsist", Toast.LENGTH_LONG).show();
                                    is_already = true;
                                    break;
                                }
                            }

                            if (!is_already) {
                                breeds_to_choose.add(text);
                                Collections.sort(breeds_to_choose);
                                //todo add on server
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("CustomBreeds");
                                query.whereEqualTo("objectId", CurrentUser.id);
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> mList, ParseException e) {
                                        if (e == null) {
                                            if (mList.size() == 0) {
                                                ParseObject new_custom_array = new ParseObject("CustomBreeds");
                                                new_custom_array.add("breeds", text);
                                                new_custom_array.put("owner", ParseObject.createWithoutData(ParseUser.class, CurrentUser.id));
                                                new_custom_array.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e == null) {
                                                            Toast.makeText(context, "Breed successfully saved", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            e.printStackTrace();
                                                            Toast.makeText(context, "Error, failed to save!", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                mList.get(0).add("breeds", text);
                                                try {
                                                    mList.get(0).save();
                                                    Toast.makeText(context, "Breed successfully saved", Toast.LENGTH_LONG).show();
                                                } catch (Exception e1) {
                                                    e1.printStackTrace();
                                                    Toast.makeText(context, "Error, failed to save!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    }
                                });
                            }

                            add_dialog.dismiss();
                        }
                    }
                });

                add_dialog.show();
            }
        });

        dialog.show();
    }
}
