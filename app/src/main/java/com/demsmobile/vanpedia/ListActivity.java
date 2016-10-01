package com.demsmobile.vanpedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demsmobile.vanpedia.database.MySQLiteHelper;
import com.demsmobile.vanpedia.database.PlacesContract;
import com.demsmobile.vanpedia.places.Place;
import com.demsmobile.vanpedia.service.Globals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListActivity extends Activity {

    Globals g = Globals.getInstance();
    List place_list_array;
    List<String> place_list = new ArrayList<String>();
    List<String> place_list_ref = new ArrayList<String>();
    ListView lv;
    TextView tv;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       setTitle();
       setBackground();

        place_list_array = (List<Place>)getIntent().getSerializableExtra("PlacesArray");
/*
        for(Iterator<Place> i = place_list_array.iterator(); i.hasNext(); ) {
            Place p = i.next();
            place_list.add(p.name);
            place_list_ref.add(p.reference);

        }*/

       // lv = (ListView) findViewById(R.id.listViewSubCategoryPlacesResult);

      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListActivity.this, R.layout.custom_list, place_list_array);
      //  lv.setAdapter(adapter);

       /* lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                g.setSelectedPlaceId(place_list_ref.get(position));
                Intent i = new Intent(ListActivity.this, SinglePlaceActivity.class);
                startActivity(i);
            };
        });*/

        final ListView lv1 = (ListView) findViewById(R.id.listViewSubCategoryPlacesResult);
        lv1.setAdapter(new CustomListAdapter(this, place_list_array));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Place placeItem = (Place) o;
                String reference = placeItem.reference;
                Intent in = new Intent(getApplicationContext(), SinglePlaceActivity.class);
                in.putExtra("reference", reference);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (g.getCategoryName().equals("Mixed") && g.hasDataChanged()) {
            new AsyncTask<String, Void, List<Place>>() {
                @Override
                protected List<Place> doInBackground(String... strings) {
                    PlacesContract placesDbHelper = new PlacesContract(new MySQLiteHelper(getApplicationContext()));

                    return placesDbHelper.findAllPlaces().results;
                }

                @Override
                protected void onPostExecute(List<Place> places) {
                    if (places.isEmpty()) {
                        Toast.makeText(ListActivity.this, "You have no favorite places!", Toast.LENGTH_SHORT).show();
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.listViewSubCategoryPlacesResult);
                    lv1.setAdapter(new CustomListAdapter(ListActivity.this, places));
                    g.setDataHasChanged(false);
                }

            }.execute();
        }
    }

    public void setTitle(){
        tv = (TextView)findViewById(R.id.listViewSubCategoryPlacesResultTitle);
        String categoryName = g.getCategoryName();
        if (categoryName.equals("Mixed")) {
            tv.setText("Your Favourites");
            icon = (ImageView) findViewById(R.id.activityListHolderIcon);
            icon.setImageResource(R.drawable.staryellow);
            //TODO - Improve layout
        } else {
            tv.setText("Let's " + categoryName + " " + g.getSubCategoryName());
            tv.setTextColor(Color.parseColor(g.getCategoryColor()));

            icon = (ImageView) findViewById(R.id.activityListHolderIcon);
            icon.setImageResource(g.getSubCategoryIcon(g.getSubCategoryName()));
        }

    }


    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(){

        LinearLayout layout =(LinearLayout)findViewById(R.id.subCategoryListPlaces);

        final int sdk = android.os.Build.VERSION.SDK_INT;
        String categoryName = g.getCategoryName();
        if (categoryName.equals("eat")) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.saltblurimg) );
            } else {
                layout.setBackground( getResources().getDrawable(R.drawable.saltblurimg));
            }
        } else if (categoryName.equals("explore")) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.mountainsblurimg) );
            } else {
                layout.setBackground( getResources().getDrawable(R.drawable.mountainsblurimg));
            }
        } else if (categoryName.equals("stay")) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.hotelblurimg) );
            } else {
                layout.setBackground( getResources().getDrawable(R.drawable.hotelblurimg));
            }
        } else {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.favoritebackground) );
        }   else {
                layout.setBackground( getResources().getDrawable(R.drawable.favoritebackground));
        }
    }
    }

}
