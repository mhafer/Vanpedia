package com.demsmobile.vanpedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demsmobile.vanpedia.places.Place;

import java.util.List;

/**
 * Created by Michelle on 2016-03-08.
 */
public class CustomListAdapter extends BaseAdapter {

    private List<Place> listData;
    private LayoutInflater layoutInflater;
 //   String MY_URL_STRING;

    public CustomListAdapter(Context aContext, List<Place> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_list2, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.title);
            holder.addressView = (TextView) convertView.findViewById(R.id.address);
            holder.ratingImage1 = (ImageView) convertView.findViewById(R.id.ratingImage1);
            holder.ratingImage2 = (ImageView) convertView.findViewById(R.id.ratingImage2);
            holder.ratingImage3 = (ImageView) convertView.findViewById(R.id.ratingImage3);
            holder.ratingImage4 = (ImageView) convertView.findViewById(R.id.ratingImage4);
            holder.ratingImage5 = (ImageView) convertView.findViewById(R.id.ratingImage5);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(listData.get(position).name);
        holder.addressView.setText(listData.get(position).vicinity);

        int ratingImageNum = (int)Math.ceil(listData.get(position).rating);

        holder.ratingImage1.setImageResource(R.drawable.filled_star_gray_dark);
        holder.ratingImage2.setImageResource(R.drawable.filled_star_gray_dark);
        holder.ratingImage3.setImageResource(R.drawable.filled_star_gray_dark);
        holder.ratingImage4.setImageResource(R.drawable.filled_star_gray_dark);
        holder.ratingImage5.setImageResource(R.drawable.filled_star_gray_dark);

        switch (ratingImageNum){
            case 1:
                holder.ratingImage1.setImageResource(R.drawable.filled_star);
                break;
            case 2:
                holder.ratingImage1.setImageResource(R.drawable.filled_star);
                holder.ratingImage2.setImageResource(R.drawable.filled_star);
                break;
            case 3:
                holder.ratingImage1.setImageResource(R.drawable.filled_star);
                holder.ratingImage2.setImageResource(R.drawable.filled_star);
                holder.ratingImage3.setImageResource(R.drawable.filled_star);
                break;
            case 4:
                holder.ratingImage1.setImageResource(R.drawable.filled_star);
                holder.ratingImage2.setImageResource(R.drawable.filled_star);
                holder.ratingImage3.setImageResource(R.drawable.filled_star);
                holder.ratingImage4.setImageResource(R.drawable.filled_star);
                break;
            case 5:
                holder.ratingImage1.setImageResource(R.drawable.filled_star);
                holder.ratingImage2.setImageResource(R.drawable.filled_star);
                holder.ratingImage3.setImageResource(R.drawable.filled_star);
                holder.ratingImage4.setImageResource(R.drawable.filled_star);
                holder.ratingImage5.setImageResource(R.drawable.filled_star);
                break;
        }
        //holder.ratingImage1.setImageResource(R.drawable.outline_star);//.setText(listData.get(position).rating + "");

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView addressView;
        ImageView ratingImage1;
        ImageView ratingImage2;
        ImageView ratingImage3;
        ImageView ratingImage4;
        ImageView ratingImage5;
    }
}

