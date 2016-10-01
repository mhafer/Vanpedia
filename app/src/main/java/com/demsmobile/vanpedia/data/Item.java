package com.demsmobile.vanpedia.data;

import org.json.JSONObject;

/**
 * Created by Redlive on 2016-02-07.
 */

public class Item implements JSONPopulator{
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
