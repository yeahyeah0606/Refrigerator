package com.example.mark.Super_R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by mark on 2017/1/4.
 */

public class Refrigerator_RightTop extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    public static final String GET_IMAGE_URL="http://59.126.175.68/Superman/getRTYes.php";
    public static final String GET_CHECK="http://59.126.175.68/Superman/getRTCheck.php";
    public static final String UPDATE_URL="http://59.126.175.68/Superman/deleteRT.php";

    private ListView listViewRT;

    public GetAlImages getAlImages;

    public String num;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.refrigerator_righttop);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        findViewById(R.id.imageViewPlus).setOnClickListener(this);

        listViewRT = (ListView) findViewById(R.id.listViewRT);
        listViewRT.setOnItemClickListener(this);
        listViewRT.setOnItemLongClickListener(this);

        getJSON();


        if(num != "0") {
                getURLs();
        }

    }


    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
             num = c.getString("num");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Refrigerator_RightTop.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest( GET_CHECK);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }




    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Refrigerator_RightTop.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(Refrigerator_RightTop.this,GetAlImages.imageURLs,GetAlImages.bitmaps,GetAlImages.name,GetAlImages.place1,GetAlImages.producer,GetAlImages.dateline);
                listViewRT.setAdapter(customList);
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

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Refrigerator_RightTop.this,"Loading...","Please Wait...",true,true);
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
        gu.execute(GET_IMAGE_URL);
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewBack:
                intent = new Intent(Refrigerator_RightTop.this, Refrigerator.class);
                startActivity(intent);
                break;
            case R.id.imageViewPlus:
                intent = new Intent(Refrigerator_RightTop.this, Locat.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        updateEmployee(i);
        getURLs();

        return true;
    }

    private void updateEmployee(int i){

        final String r_id = GetAlImages.r_id_id[i];


        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Refrigerator_RightTop.this,"deleting...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {


                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.r_id,r_id);



                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(UPDATE_URL,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }
}