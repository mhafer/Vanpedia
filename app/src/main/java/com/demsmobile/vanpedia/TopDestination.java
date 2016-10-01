package com.demsmobile.vanpedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demsmobile.vanpedia.service.Destination;
import com.demsmobile.vanpedia.service.Globals;


public class TopDestination extends ActionBarActivity {

    Destination place_list;
    String website;
    String phone;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_destination);
        Globals g = Globals.getInstance();
        place_list = g.getTopPlaceToShow();
        String name = place_list.dest_name().toString();
        String address = place_list.dest_location().toString();
        phone = place_list.phone().toString();
        website = place_list.website().toString();
        String description = place_list.description().toString();
        int images = place_list.images();
        email = place_list.email();

        ImageView lbl_mainImage = (ImageView) findViewById(R.id.imgMain);
        TextView lbl_name = (TextView) findViewById(R.id.name);
        TextView lbl_address = (TextView) findViewById(R.id.address);
        TextView lbl_phone = (TextView) findViewById(R.id.phone);
        TextView lbl_website = (TextView) findViewById(R.id.website);
        TextView lbl_description = (TextView) findViewById(R.id.description);

        lbl_mainImage.setImageResource(images);
        lbl_name.setText(name);
        lbl_address.setText(address);
        lbl_website.setText(website);
        lbl_phone.setText(phone);
        lbl_description.setText(description);
    }

    public void process(View view) {

        if(view.getId() == R.id.imgCall) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone)));
        }
        if(view.getId() == R.id.imgMail){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"+email));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(view.getId() == R.id.imgHomePage){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+website)));
        }
    }

}
