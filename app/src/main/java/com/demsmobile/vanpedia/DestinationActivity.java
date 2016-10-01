package com.demsmobile.vanpedia;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.demsmobile.vanpedia.service.Destination;
import com.demsmobile.vanpedia.service.Globals;

import java.util.ArrayList;


public class DestinationActivity extends ListActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String [] attractions = {"Stanley Park", "Science World", "Grouse Mountain", "Capilano Suspension Bridge", "Vancouver Art Gallery"};
        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_destination, R.id.top5,attractions));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setBackgroundResource(R.drawable.vanevening);
        lv.setCacheColorHint(0);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){

        Globals g = Globals.getInstance();
        ArrayList<Destination> dest =  g.getDestList();

        Intent in = new Intent(getApplicationContext(), TopDestination.class);

        g.setTopPlaceToShow(dest.get(position));
        startActivity(in);

    }

}
