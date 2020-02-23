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

public class CustomLvAdapter extends BaseAdapter{

    private Context context;
    private String[] names = null;
    private String[] ages = null;
    private Drawable[] images = null;
    String[] allergies;
    String[] bios;
    String[] disabilities;
    String[] genders;
    String[] heights;
    String[] weights;
    String[] medicalHistories;
    String[] races;
    String[] shelter;

    public CustomLvAdapter(Context context, String[] names, String[] secondaryInfo, Drawable[] images, String[]... args){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.names = names;
        this.ages = secondaryInfo;
        this.images = images;
        allergies = args[0];
        bios = args[1];
        disabilities = args[2];
        genders = args[3];
        heights = args[4];
        weights = args[5];
        medicalHistories = args[6];
        races = args[7];
        shelter = args[8];
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
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(names[position]);
        viewHolder.txtVersion.setText(String.format("Age: %s", ages[position]));
        viewHolder.icon.setImageDrawable(images[position]);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtVersion;
        ImageView icon;

    }
}
