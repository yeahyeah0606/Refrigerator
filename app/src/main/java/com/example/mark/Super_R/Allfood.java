package com.example.mark.Super_R;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.example.mark.Super_R.R.drawable.refresh;

/**
 * Created by mark on 2017/1/3.
 */

public class Allfood  extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private ListView listView;

    public static final String GET_IMAGE_URL="http://59.126.175.68/Superman/getAllImages.php";
    public static final String GET_POSITION_URL="http://59.126.175.68/Superman/getPosition.php";
    public static final String DELETE_URL="http://59.126.175.68/Superman/deleteFood.php";
    public static final String DELETE_URL_A="http://59.126.175.68/Superman/delete2.php";
    public static final String TEST="http://59.126.175.68/test.php";

    private String position;

    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";

    private String JSON_STRING;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinformation);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        findViewById(R.id.imageViewRefresh).setOnClickListener(this);
        findViewById(R.id.imageViewSearch).setOnClickListener(this);



        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        getURLs();

    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 loading = ProgressDialog.show(Allfood.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(Allfood.this,GetAlImages.imageURLs,GetAlImages.bitmaps,GetAlImages.name,GetAlImages.place1,GetAlImages.producer,GetAlImages.dateline);
                listView.setAdapter(customList);
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
                loading = ProgressDialog.show(Allfood.this,"Loading...","Please Wait...",true,true);
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
                finish();
                break;
            case R.id.imageViewRefresh:
                update();
                break;
            case R.id.imageViewSearch:
                intent = new Intent(Allfood.this, Search.class);
                startActivity(intent);
                break;
        }

    }


    private void update(){


        class Update extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Allfood.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getURLs();
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler rh = new RequestHandler();

                String s = rh.sendGetRequest(TEST);

                return s;
            }
        }

        Update ue= new Update();
        ue.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        confirmDeleteEmployee(i);
        return true;
    }


    private void callget(){
        getURLs();
    }

    private void deleteEmployee(int i){

        final String r_id = GetAlImages.r_id_id[i];

        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Allfood.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                callget();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                HashMap<String,String> hashMap2 = new HashMap<>();
                hashMap2.put(Config.r_id,r_id);

                String s = rh.sendPostRequest(DELETE_URL, hashMap2);

                RequestHandler rh2 = new RequestHandler();

                HashMap<String,String> hashMap3 = new HashMap<>();
                hashMap3.put(Config.r_id,r_id);

                String s1 = rh2.sendPostRequest( DELETE_URL_A, hashMap3);

                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(int i){

        final int i1 = i;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this food?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee(i1);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }







    /*private void getSearch(){
        class GetSearch extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Allfood.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(Allfood.this,GetAlImages.imageURLs,GetAlImages.bitmaps,GetAlImages.name,GetAlImages.place1,GetAlImages.producer,GetAlImages.dateline);
                listView.setAdapter(customList);
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
        GetSearch getSearch = new GetSearch();
        getSearch.execute();
    }

    private void getSearchURLs() {

        final String r_id = editTextSearch.getText().toString().trim();
        class GetSearchURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Allfood.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getSearch();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]+r_id);
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
        GetSearchURLs gu = new GetSearchURLs();
        gu.execute(GET_SEARCH_URL);
    }*/
}
