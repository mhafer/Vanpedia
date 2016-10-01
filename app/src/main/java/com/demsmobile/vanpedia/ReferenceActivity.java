package com.demsmobile.vanpedia;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReferenceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_reference);

        TextView imgReferences = (TextView)findViewById(R.id.txtImages);
        imgReferences.setText(Html.fromHtml("<big><big><big><big>Discover <i>Vancouver</i> with <i>Vanpedia</i></big></big></big></big><br /><br /><br /><br />" +
                "<big><big>We offer our gratitude to the following sources that helped this app become what it is...</big></big><br /><br /><br />" +
                "<big>Api's by:</big><br />Google Places<br />Google Maps<br />Yahoo Weather<br /><br />" +
                "<big>Icons by:</big><br />Icons8.com<br /><br />" +
                "<big>Images by:</big><br />" +
                "Hotel photo courtesy of www.where.ca<br />" +
                "About photo courtesy of fitamerica.com<br />" +
                "Restaurant photo courtesy of YEW Restaurant+Bar<br />" +
                "Explore photo courtesy of whutdoyouexpect.tumblr.com<br />" +
                "Splash photo courtesy of blackslatestudios.com<br />" +
                "Science World photo courtesy of youthareawesome.com<br />" +
                "Top 5 photo courtesy of tourismvancouver.com<br />" +
                "Destination details provided by Wikapedia.com<br /><br /><br /><br /><big>Thank You</big><br /><br />&#9786<br />" ));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}
