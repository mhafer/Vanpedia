package com.demsmobile.vanpedia.places;

/**
 * Created by Diego on 3/4/2016.
 */

import org.apache.http.client.HttpResponseException;

import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

public class GooglePlaces {

    private final String TAG = "GooglePlaces";
    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    // Google API Key
    private static final String API_KEY = "AIzaSyAY5H67_Nuvb40ISHt21LGHGN60SJcXN4c";

    // Google Places serach url's
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private double _latitude;
    private double _longitude;
    private double _radius;

    /**
     * Searching places
     * @param latitude - latitude of place
     * @params longitude - longitude of place
     * @param radius - radius of searchable area
     * @return list of places
     * */
    public PlacesList search(double latitude, double longitude, double radius, String keywords)
            throws Exception {

        this._latitude = latitude;
        this._longitude = longitude;
        this._radius = radius;

        try {

            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key", API_KEY);
            request.getUrl().put("location", _latitude + "," + _longitude);
//            request.getUrl().put("radius", _radius); // in meters
            request.getUrl().put("rankby", "distance");
            request.getUrl().put("sensor", "true");
            if(keywords != null)
                request.getUrl().put("keyword", keywords);

            Log.d(TAG, request.getUrl().toString());

            HttpResponse response = request.execute();
            PlacesList list = response.parseAs(PlacesList.class);
            // Check log cat for places response status
            Log.d("Places Status", "" + list.status);
            return list;

        } catch (IOException e) {
            Log.e("Error:", e.getMessage());
            return null;
        }

    }

    /**
     * Searching single place full details
     * @param reference - reference id of place
     *                 - which you will get in search api request
     * */
    public PlaceDetails getPlaceDetails(String reference) throws Exception {


            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
        HttpRequest request = null;
        PlaceDetails place = null;

        try {
            request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));

            request.getUrl().put("key", API_KEY);
                request.getUrl().put("reference", reference);
                request.getUrl().put("sensor", "false");


            place = request.execute().parseAs(PlaceDetails.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw e;
        }

        return place;
    }

    /**
     * Creating http request Factory
     * */
    public static HttpRequestFactory createRequestFactory(
            final HttpTransport transport) {
        return transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                HttpHeaders headers = new HttpHeaders();
                headers.setUserAgent("Vanpedia");
                request.setHeaders(headers);
                JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
                request.setParser(parser);
            }
        });
    }

}
