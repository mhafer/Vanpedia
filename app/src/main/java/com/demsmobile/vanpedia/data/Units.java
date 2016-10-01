package com.demsmobile.vanpedia.data;

import org.json.JSONObject;

/**
 * Created by Redlive on 2016-02-07.
 */
public class Units implements JSONPopulator{
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
