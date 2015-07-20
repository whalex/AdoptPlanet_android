package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
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
public class PetFollowListviewAdapter extends BaseAdapter implements Filterable {

    public static final String TAG = "PetListviewAdapter";

    LayoutInflater inflater;

    ArrayList<Pet> original_list;
    ArrayList<Pet> filtered_list;

    ItemFilter filter = new ItemFilter();

    Context context;

    public PetFollowListviewAdapter(Context context, ArrayList<Pet> list){
        this.original_list = list;
        this.filtered_list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filtered_list.size();
    }

    @Override
    public Object getItem(int position) {
        return filtered_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pet pet = filtered_list.get(position);
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
        return filter;
    }

    class Handler {

        ImageView photo;
        TextView age_breed;
        TextView name;
    }

    private class ItemFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_list = (ArrayList<Pet>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filter_string = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Pet> list = original_list;

            ArrayList<Pet> filteredResults = new ArrayList<>();

            for (Pet pet : list)
                if (pet.name.toLowerCase().contains(filter_string))
                    filteredResults.add(pet);

            results.values = filteredResults;
            results.count = filteredResults.size();
            return results;
        }


    }
}
