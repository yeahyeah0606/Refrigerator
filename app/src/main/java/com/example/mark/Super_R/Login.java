package com.example.mark.Super_R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

/**
 * Created by mark on 2017/1/3.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_NAME = "USER_NAME";

    public static final String PASSWORD = "PASSWORD";



    private static final String LOGIN_URL = "http://59.126.175.68/Superman/login.php";

    private EditText editTextUserName;
    private EditText editTextPassword;


    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String userName = "NAME";
    private static final String passWord = "PASSWORD";
    public static File file;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.imageViewRegister).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        file = new File("/data/data/com.example.mark.eatsy_v2/shared_prefs","DATA.xml");
        if(file.exists()){
            settings = getSharedPreferences(data,0);
            editTextUserName.setText(settings.getString(userName, ""));
            editTextPassword.setText(settings.getString(passWord, ""));
            login();
        }

    }

    private void login(){
        String account = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        userLogin(account,password);
    }

    private void userLogin(final String account, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra(USER_NAME,account);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("account",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(account,password);
    }
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewRegister:
                intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                break;
            case R.id.buttonLogin:
                settings = getSharedPreferences(data,MODE_APPEND);
                settings.edit().putString(userName, editTextUserName.getText().toString()).putString(passWord, editTextPassword.getText().toString()).commit();
                login();
                break;
            case R.id.imageViewBack:
                finish();
                break;
        }

    }
}
