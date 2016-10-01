package com.demsmobile.vanpedia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demsmobile.vanpedia.R;

import java.util.ArrayList;

/**
 * Created by Michelle on 2016-03-22.
 */
public class SwipeAdapter extends PagerAdapter {

    private Activity _activity;
    private int[] _imagePaths;
    private LayoutInflater inflater;

    public SwipeAdapter(Activity activity,
                                  int[] imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;


        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.activity_top_destination, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgMain);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);

        imgDisplay.setImageResource(position);

//        imgDisplay.setImageBitmap(bitmap);


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
