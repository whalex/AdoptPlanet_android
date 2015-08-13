package adoptplanet.com.adoptplanet.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adoptplanet.com.adoptplanet.R;
import adoptplanet.com.adoptplanet.model.CurrentUser;

public class SidebarAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> mSidebarItems;
    private ArrayList<String> mSidebarIcons;

    public SidebarAdapter(Context context, ArrayList<String> sidebarItems, ArrayList<String> sidebarIcons){
        mContext = context;
        mSidebarItems = sidebarItems;
        mSidebarIcons = sidebarIcons;
    }

    @Override
    public int getCount() {
        return mSidebarItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mSidebarItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(position == 0){
            convertView = layoutInflater.inflate(R.layout.sidebar_header, null);
            ImageView avatar = (ImageView)convertView.findViewById(R.id.avatarImageView);
            ImageView shelterImage = (ImageView)convertView.findViewById(R.id.shelterImageView);
            TextView nameText = (TextView)convertView.findViewById(R.id.nameTextView);
            TextView emailText = (TextView)convertView.findViewById(R.id.emailTextView);

            //todo rewrite with CurrentUser.getPhoto() method
//            Picasso.with(mContext)
//                    .load(CurrentUser.getPhoto())
//                    .resize(80, 80)
//                    .transform(new CircleTransform())
//                    .into(avatar);
//
//            Picasso.with(mContext)
//                    .load(CurrentUser.getShelterPhoto())
//                    .resize(80, 80)
//                    .transform(new CircleTransform())
//                    .into(shelterImage);

            nameText.setText(CurrentUser.name);
            nameText.setText(CurrentUser.email);
        }
        else{
            ImageView image = (ImageView)convertView.findViewById(R.id.itemImageView);
            TextView text = (TextView)convertView.findViewById(R.id.itemTextView);

            //image.setImageResource(mSidebarIcons.get(position));

            text.setText(mSidebarItems.get(position));
        }
        return convertView;
    }

}
