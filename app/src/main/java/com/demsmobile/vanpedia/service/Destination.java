package com.demsmobile.vanpedia.service;

/**
 * Created by Michelle on 2016-03-21.
 */
public class Destination {

    private String dest_name;
    private String dest_location;
    private String phone;
    private String description;
    private int images;
    private String website;
    private String email;

    public Destination(String n, String l, String p, String w, String d, int img, String e){

        dest_name = n;
        dest_location = l;
        phone = p;
        website = w;
        description = d;
        images = img;
        email = e;
    }

    public String dest_name(){return dest_name;}
    public String dest_location(){return dest_location;}
    public String phone(){return phone;}
    public String website(){return website;}
    public String description(){return description;}
    public int images() {return images;}
    public String email(){return email;}

    public void setDest_name(String n){dest_name = n;}
    public void setDest_location(String l){
        dest_location = l;
    }
    public void setPhone(String p){phone = p;}
    public void setWebsite(String w){website = w;}
    public void setDescription(String d){description = d;}
    public void setImages(int img){images = img;}
    public void setEmail(String e){email = e;}

}
