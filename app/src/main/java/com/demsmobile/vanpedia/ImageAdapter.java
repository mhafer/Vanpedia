package com.demsmobile.vanpedia;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demsmobile.vanpedia.service.Globals;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] subcategoriesNames;

    public ImageAdapter(Context context, String[] subcategoriesNames) {
        this.context = context;
        this.subcategoriesNames = subcategoriesNames;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Globals g = Globals.getInstance();
        String categoryName = g.getCategoryName();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.mobile, null);

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(subcategoriesNames[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

            String subcategoryName = subcategoriesNames[position];

            imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(g.getCategoryColor())));

            imageView.setImageResource(g.getSubCategoryIcon(subcategoryName));

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return subcategoriesNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}