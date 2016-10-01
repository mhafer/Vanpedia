package com.demsmobile.vanpedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.demsmobile.vanpedia.places.LoadPlaces;
import com.demsmobile.vanpedia.places.Place;
import com.demsmobile.vanpedia.service.Globals;
import com.demsmobile.vanpedia.service.ServiceCallback;
import com.demsmobile.vanpedia.util.AlertManager;

import java.io.Serializable;
import java.util.List;

public class SubcategoryActivity extends Activity implements ServiceCallback<List<Place>> {

    GridView gridView;
    Globals g = Globals.getInstance();
    ImageView bgImg;

    // Alert Dialog Manager
    AlertManager alert = new AlertManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);


        setHeaderName();

        bgImg = (ImageView)findViewById(R.id.imageBg);
        setBackground();
        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setAdapter(new ImageAdapter(this, getSubcategoriesNames()));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String subCategoryName = ((TextView) v.findViewById(R.id.grid_item_label)).getText().toString();
                g.setSubCategoryName(subCategoryName);
                new LoadPlaces(SubcategoryActivity.this, SubcategoryActivity.this).execute(g.getSearchKeys());
            }
        });

    }

    public void setHeaderName(){
        String categoryName = g.getCategoryName();
        TextView text_view = (TextView)findViewById(R.id.subCategoryNmeTextView);
        text_view.setText(categoryName.toString());
    }

    public void setBackground(){
        String categoryName = g.getCategoryName();
        if (categoryName.equals("eat")) {
           bgImg.setImageResource(R.drawable.saltblurimg);
        } else if (categoryName.equals("explore")) {
            bgImg.setImageResource(R.drawable.mountainsblurimg);
        } else if (categoryName.equals("stay")) {
            bgImg.setImageResource(R.drawable.hotelblurimg);
        }
    }

    public String [] getSubcategoriesNames(){
        String categoryName = g.getCategoryName();
        String[] subCategories = {};

        if (categoryName.equals("eat")) {
            subCategories = new String[] {"Fine", "Casual","Pub", "Breakfast", "Bistro", "Coffee" };
        } else if (categoryName.equals("explore")) {
            subCategories = new String[] {"Concert","Night Life", "Beach", "Sport", "Bike", "Hike", "Mountain" };
        } else if (categoryName.equals("stay")) {
            subCategories = new String[] {"Hotel", "B&B","Hostel", "Rent" };
        }
        return subCategories;
    }


    @Override
    public void serviceSuccess(List<Place> places) {
        Intent intent = new Intent(SubcategoryActivity.this, ListActivity.class);
        intent.putExtra("PlacesArray", (Serializable) places);   //(Parcelable) places
        startActivity(intent);
    }

    @Override
    public void serviceFailure(Exception exception) {
        String message = exception.getMessage();
        if (message.equals("ZERO_RESULTS")) {
            // Zero results found
            alert.showAlertDialog(this, "Near Places",
                    "Sorry no places found. Try to change the types of places",
                    false);
        } else if (message.equals("UNKNOWN_ERROR")) {
            alert.showAlertDialog(this, "Places Error",
                    "Sorry unknown error occured.",
                    false);
        } else if (message.equals("OVER_QUERY_LIMIT")) {
            alert.showAlertDialog(this, "Places Error",
                    "Sorry query limit to google places is reached",
                    false);
        } else if (message.equals("REQUEST_DENIED")) {
            alert.showAlertDialog(this, "Places Error",
                    "Sorry error occured. Request is denied",
                    false);
        } else if (message.equals("INVALID_REQUEST")) {
            alert.showAlertDialog(this, "Places Error",
                    "Sorry error occured. Invalid Request",
                    false);
        } else {
            alert.showAlertDialog(this, "Places Error",
                    "Sorry error occured.",
                    false);
        }
    }
}