package com.example.mark.Super_R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mark on 2017/5/1.
 */

public class Search extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    public static final String GET_IMAGE_URL="http://59.126.175.68/Superman/search.php?food=";

    private ListView listViewSearch;

    public GetAlImages getAlImages;

    private EditText editTextSearch;

    private TextView textViewTest;

    private String JSON_STRING;

    public static final String TAG_ID = "food_name";
    public static final String TAG_NAME = "food_place";


    private String food;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        findViewById(R.id.imageViewDo).setOnClickListener(this);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        listViewSearch = (ListView) findViewById(R.id.listviewSearch);


    }
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.imageViewDo:
                food = editTextSearch.getText().toString();
                getEmployee();
                break;
        }

    }

    /*private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Search.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(GET_IMAGE_URL,food);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }




    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_SEARCH);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String food_name = jo.getString(TAG_ID);
                String food_place = jo.getString(TAG_NAME);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(TAG_ID,food_name);
                employees.put(TAG_NAME,food_place);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Search.this, list, R.layout.list_item,
                new String[]{TAG_ID,TAG_NAME},
                new int[]{R.id.food_name, R.id.food_place});

        listViewSearch.setAdapter(adapter);

    }*/

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Search.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(Search.this,GetAlImages.imageURLs,GetAlImages.bitmaps,GetAlImages.name,GetAlImages.place1,GetAlImages.producer,GetAlImages.dateline);
                listViewSearch.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    /*private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Search.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL,food);
    }*/

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Search.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(GET_IMAGE_URL,food);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
