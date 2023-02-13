package com.example.mark.Super_R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mark on 2017/1/3.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextPassword;

    private ImageView img;

    private static final String REGISTER_URL = "http://59.126.175.68/Superman/register.php";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        img = (ImageView)findViewById(R.id.imageViewRegister);

        img.setOnClickListener(this);
    }

    private void registerUser(){
        String account = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();

        register(account,password);
    }

    private void register(String account, String password){
        String urlSuffix = "?account="+account+"&password="+password;
        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(s.equalsIgnoreCase("successfully registered")){
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                }


            }

            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }



        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);

    }
    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.imageViewRegister:
                registerUser();

        }

    }
}
