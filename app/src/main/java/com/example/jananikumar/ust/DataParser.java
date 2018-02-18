package com.example.jananikumar.ust;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jananikumar on 15-02-2018.
 */
public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlacejson)
    {
        HashMap<String, String> googlePlaceMap= new HashMap<>();
        String placename="-NA-";
        String vicnity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";

        try {
        if(!googlePlacejson.isNull("name"))
        {

                placename=googlePlacejson.getString("name");

        }
            if(!googlePlacejson.isNull("vicinty"))
            {
                vicnity=googlePlacejson.getString("vicinty");
            }

            latitude=googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference=googlePlacejson.getString("refernce");

            googlePlaceMap.put("place_name", placename);
            googlePlaceMap.put("vicinty", vicnity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count= jsonArray.length();

        List<HashMap<String, String>> placesList=new ArrayList<>();
        HashMap<String, String> placeMap= null;

        for (int i = 0;i<count;i++)
        {
            try {
            placeMap= getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject =new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  getPlaces(jsonArray);
    }
}
