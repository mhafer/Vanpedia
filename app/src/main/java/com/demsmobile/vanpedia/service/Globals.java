package com.demsmobile.vanpedia.service;

import com.demsmobile.vanpedia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Globals{
    private static Globals instance;

    // Global variable
    private Destination topPlaceToShow;
    private String categoryName;
    private String subCategoryName;
    private String referenceId;
    private ArrayList<Destination> destList;
    private boolean dataHasChanged;

    // Restrict the constructor from being instantiated
    private Globals(){}


    public void setSelectedPlaceId(String ref){ this.referenceId = ref;}
    public String getSelectedPlaceId(){
        return this.referenceId;
    }

    public void setCategoryName(String name){
        this.categoryName=name;
    }
    public String getCategoryName(){
        return this.categoryName;
    }

    public void setSubCategoryName(String subName){
        this.subCategoryName=subName;
    }
    public String getSubCategoryName(){
        return this.subCategoryName;
    }

    public String getSearchKeys(){
        return keywords.get(subCategoryName);
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    static Map<String, String> keywords = new HashMap<String, String>();

    static{
        keywords.put("Hotel", "hotel");
        keywords.put("B&B", "bed and breakfast|B&B");
        keywords.put("Hostel", "hostel");
        keywords.put("Rent", "condo for rent|apartment for rent|bedroom|room");
        keywords.put("Fine", "restaurant fine dinning");
        keywords.put("Casual", "restaurant casual dinning");
        keywords.put("Pub", "restaurant pub");
        keywords.put("Breakfast", "restaurant breakfast food");
        keywords.put("Bistro", "restaurant bistro");
        keywords.put("Coffee", "restaurant coffee shop");
        keywords.put("Concert", "entertainment concert");
        keywords.put("Night Life", "night club");
        keywords.put("Beach", "park|beach");
        keywords.put("Sport", "sporting events|stadium");
        keywords.put("Bike", "sport bike|rental bike");
        keywords.put("Hike", "hike|trail");
        keywords.put("Mountain", "ski|resort");
    }

    public void setDestList(ArrayList<Destination> destinationArr){
        this.destList=destinationArr;
    }
    public ArrayList<Destination> getDestList(){
        return destList;
    }

    public void setTopPlaceToShow(Destination d){
        topPlaceToShow = d;
    }
    public Destination getTopPlaceToShow(){
        return topPlaceToShow;
    }

    public String getCategoryColor(){
        String c;

        if (categoryName.equals("eat")) {
            c = "#F44336";
        } else if (categoryName.equals("explore")) {
            c = "#2196F3";
        } else if (categoryName.equals("stay")) {
            c = "#4CAF50";
        } else {
            c = "#FFFF00";
        }

        return c;
    }

    public Integer getSubCategoryIcon(String subCatName){
        Integer drawableName ;
        switch(subCatName){
            case "Fine":
                drawableName = R.drawable.waiter;
                break;
            case "Casual":
                drawableName = R.drawable.cocktail;
                break;
            case "Pub":
                drawableName = R.drawable.beer;
                break;
            case "Breakfast":
                drawableName = R.drawable.cheese;
                break;
            case "Bistro":
                drawableName = R.drawable.hamburger;
                break;
            case "Coffee":
                drawableName = R.drawable.cafe;
                break;

            case "Hotel":
                drawableName = R.drawable.towel;
                break;
            case "B&B":
                drawableName = R.drawable.electric_teapot;
                break;
            case "Hostel":
                drawableName = R.drawable.caretaker;
                break;
            case "Rent":
                drawableName = R.drawable.cafe;
                break;

            case "Bike":
                drawableName = R.drawable.mountain_biking;
                break;
            case "Sport":
                drawableName = R.drawable.sport;
                break;
            case "Concert":
                drawableName = R.drawable.music_conductor;
                break;
            case "Night Life":
                drawableName = R.drawable.dance_with_devil;
                break;
            case "Beach":
                drawableName = R.drawable.palm_tree;
                break;
            case "Hike":
                drawableName = R.drawable.trekking;
                break;
            case "Mountain":
                drawableName = R.drawable.climbing;
                break;
            default:
                drawableName = R.drawable.abc_btn_rating_star_on_mtrl_alpha;
        }
        return drawableName;
    }

    public boolean hasDataChanged() {
        return dataHasChanged;
    }

    public void setDataHasChanged(boolean dataHasChanged) {
        this.dataHasChanged = dataHasChanged;
    }
}
