package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CacheHolder;


/**
 * Created by Alexeich on 23.07.2015.
 */
public class BreedListviewAdapter extends BaseAdapter implements Filterable{

    public static final String TAG = "BreedListviewAdapter";

    LayoutInflater inflater;

    ArrayList<String> original_list;
    ArrayList<String> filtered_list;
    ArrayList<Integer> filtered_ints;
    TextView choosen;
    int choosen_int = -1;

    ItemFilter filter = new ItemFilter();

    Context context;

    public BreedListviewAdapter(Context context, ArrayList<String> list, TextView choosen){
        this.original_list = list;
        this.filtered_list = list;
        filtered_ints = new ArrayList<>();
        for (int i = 0; i < original_list.size(); i++){
            filtered_ints.add(i);
        }
        this.choosen = choosen;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filtered_list.size();
    }

    @Override
    public Object getItem(int position) {
        return original_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String string = filtered_list.get(position);
        Handler handler;

        if (convertView == null){
            handler = new Handler();
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.z_listview_breed_string, null);
            convertView.setTag(handler);

            handler.name = (TextView) convertView.findViewById(R.id.breed_text_tv);


            handler.real_position = position;

        }
        else{
            handler = (Handler) convertView.getTag();
        }

        handler.name.setText(string);

        handler.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosen_int = filtered_ints.get(position);
                Log.d(TAG, "Pos: " + position + " LIST: " + choosen_int);
                choosen.setText("Current: " + original_list.get(choosen_int));
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class Handler {

        int real_position;
        TextView name;
    }

    private class ItemFilter extends Filter {

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_list = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filter_string = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<String> list = original_list;
            filtered_ints.clear();

            ArrayList<String> filteredResults = new ArrayList<>();

            for (int i = 0; i < list.size(); i++){
                String breed = list.get(i);
                if (breed.toLowerCase().contains(filter_string)) {
                    filteredResults.add(breed);
                    filtered_ints.add(i);
                }
            }


            results.values = filteredResults;
            results.count = filteredResults.size();
            return results;
        }


    }

    public int getBread(){
        return choosen_int;
    }

}
