package adoptplanet.com.adoptplanet.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.Pet;
import adoptplanet.com.adoptplanet.utils.CircleTransform;

/**
 * Created by Alexeich on 13.07.2015.
 */
public class PetGridviewAdapter extends BaseAdapter {

    LayoutInflater inflater;

    ArrayList<Pet> list;

    Context context;

    public PetGridviewAdapter(Context context, ArrayList<Pet> list){
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
            convertView = inflater.inflate(R.layout.z_gridview_pet, null);
            convertView.setTag(handler);

            handler.photo = (ImageView) convertView.findViewById(R.id.z_gridview_photo);
            handler.icon = (ImageView) convertView.findViewById(R.id.z_gridview_icon);
            handler.name = (TextView) convertView.findViewById(R.id.z_gridview_name);
            handler.icon_text = (TextView) convertView.findViewById(R.id.z_gridview_icon_text);
        }
        else{
            handler = (Handler) convertView.getTag();
        }

        handler.name.setText(pet.name);
        Log.d("PetListAdapter", "P:" + position + " Name: " + pet.name);
        //handler.icon_text.setText(pet.);

        if (pet.photo_url != null)
            Picasso.with(context)
                    .load(pet.photo_url)
                    .transform(new CircleTransform())
                    .into(handler.photo);
        else
            Picasso.with(context)
                    .load(R.drawable.z_check_box_checked)
                    .into(handler.photo);


        return convertView;
    }

    class Handler {

        ImageView photo;
        ImageView icon;
        TextView name;
        TextView icon_text;
    }
}
