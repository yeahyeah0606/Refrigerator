package com.example.mark.Super_R;

/**
 * Created by mark on 2017/1/13.
 */

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAlImages {

    public static String[] imageURLs;
    public static String[] name;
    public static String[] place1;
    public static String[] producer;
    public static String[] dateline;
    public static String[] r_id_id;
    public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    public static final String NAME_URL = "food_name";
    public static final String PLACE_URL = " food_place";
    public static final String RID_URL = "r_id";
    public static final String PRODUCER_URL = "food_producer";
    public static final String DATELINE_URL = "food_dateline";
    private String json;
    private JSONArray urls;
    private JSONArray food_name;
    private JSONArray r_id;
    private JSONArray food_place;
    private JSONArray food_producer;
    private JSONArray food_dateline;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
            food_name = jsonObject.getJSONArray(JSON_ARRAY);
            r_id = jsonObject.getJSONArray(JSON_ARRAY);
            food_place = jsonObject.getJSONArray(JSON_ARRAY);
            food_producer = jsonObject.getJSONArray(JSON_ARRAY);
            food_dateline = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];
        name = new String[food_name.length()];
        place1 = new String[food_place.length()];
        r_id_id = new String[r_id.length()];
        producer = new String[food_producer.length()];
        dateline = new String[food_dateline.length()];

        for(int i=0;i<urls.length();i++){
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            name[i] = food_name.getJSONObject(i).getString(NAME_URL);
            r_id_id[i] = r_id.getJSONObject(i).getString(RID_URL);
            //place1[i] = food_place.getJSONObject(i).getString(PLACE_URL);
            producer[i] = food_producer.getJSONObject(i).getString(PRODUCER_URL);
            dateline[i] = food_dateline.getJSONObject(i).getString(DATELINE_URL);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
        }
    }
}
