package com.skharbanda.adoptakid;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShelterAdapter extends BaseAdapter {

    private Context context;
    private String[] names = null;
    private String[] addresses = null;
    private Drawable[] images = null;
    private String[] descriptions;
    private String[] emails;
    private String[] hours;
    private String[] phones;
    private String[] websites;

    public ShelterAdapter(Context context, String[] names, String[] secondaryInfo, Drawable[] images, String[]... args){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.names = names;
        this.addresses = secondaryInfo;
        this.images = images;
        descriptions = args[0];
        emails = args[1];
        hours = args[2];
        phones = args[3];
        websites = args[4];
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = convertView.findViewById(R.id.appIconIV);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(names[position]);
        viewHolder.txtVersion.setText(String.format("Address: %s", addresses[position]));
        viewHolder.icon.setImageDrawable(images[position]);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtVersion;
        ImageView icon;

    }
}
