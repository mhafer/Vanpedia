package com.demsmobile.vanpedia.places;

/**
 * Created by Diego on 3/4/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.text.Html;

import com.demsmobile.vanpedia.service.LocationService;
import com.demsmobile.vanpedia.service.ServiceCallback;

import java.util.List;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Background Async Task to Load Google places
 * */
public class LoadPlaces extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog pDialog;
    private GooglePlaces googlePlaces;
    private PlacesList nearPlaces;
    private ServiceCallback callback;

    // Radius in meters - increase this value if you don't find any places
    private double RARIUS = 10000; // 1000 meters

    public LoadPlaces(Context c, ServiceCallback<List<Place>> callback){
        this.context = c;
        this.callback = callback;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * getting Places JSON
     * */
    protected String doInBackground(String... args) {
        // creating Places class object
        googlePlaces = new GooglePlaces();

        try {
            Location location = LocationService.getInstance(context).getLocation();

            // get nearest places
            nearPlaces = googlePlaces.search(location.getLatitude(), location.getLongitude(), RARIUS, args[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
    * and show the data in UI
    * Always use runOnUiThread(new Runnable()) to update UI from background
    * thread, otherwise you will get error
    * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
                String status = nearPlaces.status;

                // Check for all possible status
                if (status.equals("OK")) {
                    // Successfully got places details
                    if (nearPlaces.results != null) {
                        // loop through each place
                        callback.serviceSuccess(nearPlaces.results);
                    }
                } else {
                    callback.serviceFailure(new Exception(nearPlaces.status));
                }
            }
        });
    }
}