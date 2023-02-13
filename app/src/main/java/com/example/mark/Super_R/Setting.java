package com.example.mark.Super_R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by mark on 2017/1/3.
 */

public class Setting  extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> lunchList = ArrayAdapter.createFromResource(Setting.this,
                R.array.days,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(lunchList);


        findViewById(R.id.imageViewBack).setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewBack:
                intent = new Intent(Setting.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}
