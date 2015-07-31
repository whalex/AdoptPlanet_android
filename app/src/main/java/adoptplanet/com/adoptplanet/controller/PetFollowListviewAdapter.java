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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.CircleTransform;

public class PetFollowListviewAdapter extends BaseAdapter implements Filterable {

    public static final String TAG = "PetFollowAdapter";

    LayoutInflater inflater;

    ArrayList<Pet> original_list;
    ArrayList<Pet> filtered_list;
    ArrayList<Pet> follow_list;

    ItemFilter filter = new ItemFilter();

    Context context;

    public PetFollowListviewAdapter(Context context, ArrayList<Pet> list){
        this.original_list = list;
        this.filtered_list = list;
        follow_list = new ArrayList<>();
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
        final Pet pet = filtered_list.get(position);
        final Handler handler;

        if (convertView == null){
            handler = new Handler();
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.z_listview_follow_petlist, null);
            convertView.setTag(handler);

            handler.photo = (ImageView) convertView.findViewById(R.id.z_pet_listview_photo);
            handler.name = (TextView) convertView.findViewById(R.id.z_pet_listview_name);
            handler.age_breed = (TextView) convertView.findViewById(R.id.z_pet_listview_age_breed);
            handler.layout = (LinearLayout) convertView.findViewById(R.id.z_pet_listview_linear_layout);
            handler.check_image = (ImageView) convertView.findViewById(R.id.z_pet_listview_check_image);
        }
        else{
            handler = (Handler) convertView.getTag();
        }

        handler.name.setText(pet.name);

        handler.age_breed.setText("Age: " + pet.age + " Breed: " + pet.breed);

        Log.d(TAG, "PET ID: " + pet.id);

        if (follow_list.contains(pet)){
            handler.check_image.setImageDrawable(context.getResources().getDrawable(R.drawable.z_check_box_checked));
        }
        else{
            handler.check_image.setImageDrawable(context.getResources().getDrawable(R.drawable.z_check_box_passive));
        }


        if (pet.photo_url != null && pet.photo_url.length() != 0) {
            Picasso.with(context)
                    .load(pet.photo_url)
                    .transform(new CircleTransform())
                    .into(handler.photo);
        }

        handler.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow_list.contains(pet)){
                    follow_list.remove(pet);
                    handler.check_image.setImageDrawable(context.getResources().getDrawable(R.drawable.z_check_box_passive));
                }
                else{
                    follow_list.add(pet);
                    handler.check_image.setImageDrawable(context.getResources().getDrawable(R.drawable.z_check_box_checked));
                }
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class Handler {

        LinearLayout layout;
        ImageView photo;
        TextView age_breed;
        TextView name;
        ImageView check_image;
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

    public ArrayList<String> getFollowing(){
        ArrayList<String> following_str = new ArrayList<>();
        for (Pet pet: follow_list){
            following_str.add(pet.id);
        }
        return following_str;
    }
}
