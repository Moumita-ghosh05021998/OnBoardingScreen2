package com.example.onboardingscreen2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String,String >parseJsonObject(JSONObject object) throws JSONException {
        //initialize hash map
        HashMap<String,String> dataList = new HashMap<>();
        try {
            //get name from object
            String name = object.getString("name");
            //get latitude from object
            String latitude = object.getJSONObject("geomerty")
                    .getJSONObject("location").getString("lat");
            //get lognitude from object
            String lognitude = object.getJSONObject("geomerty")
                    .getJSONObject("location").getString("lng");
            //put all value in hashmap
            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",lognitude);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return hashmap
        return dataList;

    }
    private List<HashMap<String,String >> parseJsonArray(JSONArray jsonArray){
        //initialize hashmap list
        List<HashMap<String ,String>> dataList = new ArrayList<>();
        for (int i =0;i<jsonArray.length(); i++){

            try {
                //initialize hash map
                HashMap<String,String> data= parseJsonObject((JSONObject)jsonArray.get(i));
                //add data into hash map list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return dataList;
    }
    public List<HashMap<String,String>> parseResult(JSONObject object){
        //initialize json array
        JSONArray jsonArray = null;
        //get result array
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return array
        return  parseJsonArray(jsonArray);
    }
}
