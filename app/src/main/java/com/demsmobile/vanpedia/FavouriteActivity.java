package com.demsmobile.vanpedia;

import android.app.*;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demsmobile.vanpedia.database.MySQLiteHelper;
import com.demsmobile.vanpedia.database.PlacesContract;
import com.demsmobile.vanpedia.places.Place;
import com.demsmobile.vanpedia.service.Destination;
import com.demsmobile.vanpedia.service.Globals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FavouriteActivity extends android.app.ListActivity {

    private Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setBackgroundDrawableResource(R.drawable.favoritebackground);
//        ListView lv = getListView();
//        lv.setTextFilterEnabled(true);
//        lv.setBackgroundResource(R.drawable.favoritebackground);
//        lv.setCacheColorHint(0);
//
        getFavoritePlaces();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (g.hasDataChanged()) {
           getFavoritePlaces();
        }
    }

    protected void onListItemClick(final ListView l, View v, int position, long id){
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = l.getItemAtPosition(position);
                Place placeItem = (Place) o;
                //TODO unable to get placeItem reference
//                String reference = placeItem.reference;
                Intent in = new Intent(getApplicationContext(), SinglePlaceActivity.class);
//                in.putExtra("reference", reference);
                startActivity(in);
            }
        });

    }

    private void getFavoritePlaces() {
        new AsyncTask<String, Void, List<Place>>() {
            @Override
            protected List<Place> doInBackground(String... strings) {
                PlacesContract placesDbHelper = new PlacesContract(new MySQLiteHelper(getApplicationContext()));

                return placesDbHelper.findAllPlaces().results;
            }

            @Override
            protected void onPostExecute(List<Place> places) {
                if (places.isEmpty()) {
                    Toast.makeText(FavouriteActivity.this, "You haven't added places yet!", Toast.LENGTH_SHORT).show();
                }

                setListAdapter(new ArrayAdapter<String>(FavouriteActivity.this, R.layout.activity_favourite, R.id.favList,getPlacesNames(places)));

            }

        }.execute();

    }

    private String[] getPlacesNames(List<Place> places) {

        String[] names = new String[places.size()];
        for (int i = 0; i < places.size(); i++) {
            names[i] = places.get(i).name;
        }
        return names;
    }
}
