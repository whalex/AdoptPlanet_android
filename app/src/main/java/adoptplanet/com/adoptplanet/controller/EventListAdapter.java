package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Event;
import adoptplanet.com.adoptplanet.model.Pet;

/**
 * Created by Alexeich on 13.07.2015.
 */
public class EventListAdapter extends BaseAdapter {

    LayoutInflater inflater;

    ArrayList<Event> list;

    Context context;

    public EventListAdapter(Context context, ArrayList<Event> list){
        inflater = LayoutInflater.from(context);
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
        Event event = list.get(position);
        Handler handler;
        if (convertView == null){
            handler = new Handler();
            convertView = inflater.inflate(R.layout.z_listview_event, null);
            convertView.setTag(handler);

            handler.photo = (ImageView) convertView.findViewById(R.id.z_listview_event_image);
            handler.text = (TextView) convertView.findViewById(R.id.z_listview_event_text);
            handler.description = (TextView) convertView.findViewById(R.id.z_listview_event_description);
            handler.time = (TextView) convertView.findViewById(R.id.z_listview_event_time);
            handler.place = (TextView) convertView.findViewById(R.id.z_listview_event_place);

        }
        else{
            handler = (Handler) convertView.getTag();
        }

        handler.text.setText(event.name);
        Log.d("PetListAdapter", "P:" + position + " Name: " + event.name);
        //handler.icon_text.setText(pet.);
        if (event.photo_url != null)
            Picasso.with(context)
                    .load(event.photo_url)
                    .into(handler.photo);


        return convertView;
    }

    class Handler {

        ImageView photo;
        TextView text;
        TextView description;
        TextView time;
        TextView place;

    }
}
