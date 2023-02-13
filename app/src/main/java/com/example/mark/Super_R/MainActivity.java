package com.example.mark.Super_R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private  int[] image_friend=new int[]{
            R.drawable.frd,
            R.drawable.frd_long,
    };
    private  int[] image_allfood=new int[]{
            R.drawable.allfood,
            R.drawable.allfood_long,
    };
    private  int[] image_ref=new int[]{
            R.drawable.ref,
            R.drawable.ref_long,
    };
    private  int[] image_set=new int[]{
            R.drawable.set,
            R.drawable.set_long,
    };
    private int currentImg1=0;
    private int currentImg2=0;
    private int currentImg3=0;
    private int currentImg4=0;

    public static File file;

    ImageView img,img2,img3,img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


        findViewById(R.id.imageViewFriend).setOnClickListener(this);
        findViewById(R.id.imageViewAllFood).setOnClickListener(this);
        findViewById(R.id.imageViewRef).setOnClickListener(this);
        findViewById(R.id.imageViewSet).setOnClickListener(this);
        findViewById(R.id.imageView2).setOnClickListener(this);
        //findViewById(R.id.imageViewLogin).setOnClickListener(this);
    }




    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.imageViewFriend:
                ImageView img;
                img = (ImageView) findViewById(R.id.imageViewFriend);
                img.setImageResource(image_friend[0]);
                if(currentImg1>=1){
                    currentImg1=-1;
                    intent = new Intent(MainActivity.this, Friend.class);
                    startActivity(intent);
                }
                img.setImageResource(image_friend[++currentImg1]);
                break;
            case R.id.imageViewAllFood:
                ImageView img2;
                img2 = (ImageView) findViewById(R.id.imageViewAllFood);
                img2.setImageResource(image_allfood[0]);
                if(currentImg2>=1){
                    currentImg2=-1;
                    intent = new Intent(MainActivity.this, Allfood.class);
                    startActivity(intent);
                }
                img2.setImageResource(image_allfood[++currentImg2]);
                break;
            case R.id.imageViewRef:
                ImageView img3;
                img3 = (ImageView) findViewById(R.id.imageViewRef);
                img3.setImageResource(image_ref[0]);
                if(currentImg3>=1){
                    currentImg3=-1;
                    intent = new Intent(MainActivity.this, Refrigerator.class);
                    startActivity(intent);
                }
                img3.setImageResource(image_ref[++currentImg3]);
                break;
            case R.id.imageViewSet:
                ImageView img4;
                img4 = (ImageView) findViewById(R.id.imageViewSet);
                img4.setImageResource(image_set[0]);
                if(currentImg4>=1){
                    currentImg4=-1;
                    intent = new Intent(MainActivity.this, Setting.class);
                    startActivity(intent);
                    break;
                }
                img4.setImageResource(image_set[++currentImg4]);
                break;
            case R.id.imageView2:
                img = (ImageView) findViewById(R.id.imageViewFriend);
                img.setImageResource(image_friend[0]);
                currentImg1 = 0;
                img2 = (ImageView) findViewById(R.id.imageViewAllFood);
                img2.setImageResource(image_allfood[0]);
                currentImg2 = 0;
                img3 = (ImageView) findViewById(R.id.imageViewRef);
                img3.setImageResource(image_ref[0]);
                currentImg3 = 0;
                img4 = (ImageView) findViewById(R.id.imageViewSet);
                img4.setImageResource(image_set[0]);
                currentImg4 = 0;
                break;

            /*case R.id.imageViewLogin:

                intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;*/


        }

    }
}
