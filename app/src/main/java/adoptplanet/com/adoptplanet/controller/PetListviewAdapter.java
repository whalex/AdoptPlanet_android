package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.CircleTransform;

/**
 * Created by Alexeich on 15.07.2015.
 */
public class PetListviewAdapter extends BaseAdapter implements Filterable {

    public static final String TAG = "PetListviewAdapter";

    LayoutInflater inflater;

    ArrayList<Pet> list;

    Context context;

    String search_text = "";

    public PetListviewAdapter(Context context, ArrayList<Pet> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pet pet = list.get(position);
        Handler handler;

        if (convertView == null){
            handler = new Handler();
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.z_listview_petlist, null);
            convertView.setTag(handler);

            handler.photo = (ImageView) convertView.findViewById(R.id.z_pet_listview_photo);
            handler.name = (TextView) convertView.findViewById(R.id.z_pet_listview_name);
            handler.age_breed = (TextView) convertView.findViewById(R.id.z_pet_listview_age_breed);

        }
        else{
            handler = (Handler) convertView.getTag();
        }

        handler.name.setText(pet.name);

        handler.age_breed.setText("Age: " + pet.age + " Breed: " + pet.breed);

        if (pet.photo_url != null)
            Picasso.with(context)
                    .load(pet.photo_url)
                    .transform(new CircleTransform())
                    .into(handler.photo);


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<Pet>) results.values;
                PetListviewAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                ArrayList<Pet> filteredResults = new ArrayList<>();

                for (Pet pet : list)
                    if (constraint.length() != 0) {
                        if (pet.name.contains(constraint))
                            filteredResults.add(pet);
                    }else
                        filteredResults.add(pet);

                results.values = filteredResults;
                results.count = filteredResults.size();
                return results;
            }

        };
    }

    class Handler {

        ImageView photo;
        TextView age_breed;
        TextView name;
    }

}
