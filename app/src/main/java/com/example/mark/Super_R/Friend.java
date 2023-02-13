package com.example.mark.Super_R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * Created by mark on 2017/1/3.
 */

public class Friend  extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend);
        findViewById(R.id.imageViewBack).setOnClickListener(this);
        findViewById(R.id.imageView7).setOnClickListener(this);
        findViewById(R.id.imageView9).setOnClickListener(this);
        findViewById(R.id.imageView4).setOnClickListener(this);
        findViewById(R.id.imageView10).setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewBack:
                intent = new Intent(Friend.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView7:
                Uri uri= Uri.parse("https://icook.tw/");
                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
                break;
            case R.id.imageView9:
                Uri uri2= Uri.parse("http://www.dodocook.com/");
                Intent i1=new Intent(Intent.ACTION_VIEW,uri2);
                startActivity(i1);
                break;
            case R.id.imageView4:
                Uri uri3= Uri.parse("http://www.jamieoliver.com/recipes/?");
                Intent i2=new Intent(Intent.ACTION_VIEW,uri3);
                startActivity(i2);
                break;
            case R.id.imageView10:
                Uri uri4= Uri.parse("https://www.facebook.com/");
                Intent i3=new Intent(Intent.ACTION_VIEW,uri4);
                startActivity(i3);
                break;
        }

    }
}
