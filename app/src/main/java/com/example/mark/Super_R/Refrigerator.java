package com.example.mark.Super_R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * Created by mark on 2017/1/3.
 */

public class Refrigerator  extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_refrigerator);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        findViewById(R.id.imageView29).setOnClickListener(this);
        findViewById(R.id.imageView30).setOnClickListener(this);
        findViewById(R.id.imageView31).setOnClickListener(this);
        findViewById(R.id.imageView27).setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewBack:
                intent = new Intent(Refrigerator.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView29:
                intent = new Intent(Refrigerator.this, Refrigerator_RightTop.class);
                startActivity(intent);
                break;
            case R.id.imageView27:
                intent = new Intent(Refrigerator.this, Refrigerator_RightBot.class);
                startActivity(intent);
                break;
            case R.id.imageView30:
                intent = new Intent(Refrigerator.this, Refrigerator_LeftTop.class);
                startActivity(intent);
                break;
            case R.id.imageView31:
                intent = new Intent(Refrigerator.this, Refrigerator_LeftBot.class);
                startActivity(intent);
                break;
        }

    }
}
