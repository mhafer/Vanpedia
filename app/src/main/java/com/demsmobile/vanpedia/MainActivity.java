package com.demsmobile.vanpedia;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demsmobile.vanpedia.data.Channel;
import com.demsmobile.vanpedia.data.Item;
import com.demsmobile.vanpedia.database.MySQLiteHelper;
import com.demsmobile.vanpedia.database.PlacesContract;
import com.demsmobile.vanpedia.places.Place;
import com.demsmobile.vanpedia.service.Destination;
import com.demsmobile.vanpedia.service.DestinationList;
import com.demsmobile.vanpedia.service.Globals;
import com.demsmobile.vanpedia.service.LocationService;
import com.demsmobile.vanpedia.service.ServiceCallback;
import com.demsmobile.vanpedia.service.YahooWeatherService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServiceCallback<Channel> {

    public int categoryId;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionsTextView;
    private TextView locationTextView;

    private YahooWeatherService weatherService;
    private LocationService locationService;
    private Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals g = Globals.getInstance();
        //ArrayList<Destination>
        DestinationList DistList = new DestinationList();
        DistList.createList();
        g.setDestList(DistList.getList());

        ArrayList<Destination> x = g.getDestList();


        weatherIconImageView = (ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)findViewById(R.id.temperatureTextView);
        conditionsTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        // creating GPS Class object
        locationService = LocationService.getInstance(this);


        // check if GPS location can get
        if (locationService.canGetLocation()) {
            Log.d("Your Location", "latitude:" + locationService.getLatitude() + ", longitude: " + locationService.getLongitude());
        } else {
            // Can't get user's current location
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_LONG).show();
            // stop executing code by return
            return;
        }

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(locationService.getLatitude(), locationService.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            weatherService = new YahooWeatherService(this);
            weatherService.refreshWeather(addresses.get(0).getLocality()+", "+ addresses.get(0).getCountryName());
        }

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
    }

    private void addDrawerItems() {
        String[] osArray = { "Sign In", "Liked Places","Top 5 Picks", "About This App", "References"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.simple_white_list_item, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        break;
                    case 1:
                        //startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
                        getFavoritePlaces();
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, DestinationActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, ReferenceActivity.class));
                        break;
                }
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
                    Toast.makeText(MainActivity.this, "You haven't added places yet!", Toast.LENGTH_SHORT).show();
                } else {
                    g.setCategoryName("Mixed");
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("PlacesArray", (Serializable) places);   //(Parcelable) places
                    startActivity(intent);
                }
            }

        }.execute();

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void serviceSuccess(Channel channel) {
//        dialog.hide();
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/weather_icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        locationTextView.setText(weatherService.getLocation());
        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionsTextView.setText(item.getCondition().getDescription());

    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
//        dialog.hide();
    }

    public void openSubcategory(View v){
        String categoryName = getResources().getResourceEntryName(v.getId());
        Globals g = Globals.getInstance();
        g.setCategoryName(categoryName.replace("imageButton","").toLowerCase());
        startActivity(new Intent(MainActivity.this, SubcategoryActivity.class));
    }
}