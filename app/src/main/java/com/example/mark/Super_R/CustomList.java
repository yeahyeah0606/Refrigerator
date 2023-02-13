package com.example.mark.Super_R;

/**
 * Created by mark on 2017/1/13.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomList extends ArrayAdapter<String> implements Filterable {

    private String[] urls;


    private String[] name;
    private String[] place;
    private String[] producer;
    private String[] dateline;

    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] urls, Bitmap[] bitmaps, String[] name,String[] place,String[] producer,String[] dateline){

        super(context, R.layout.image_list_view, urls);
        this.context = context;
        this.urls= urls;
        this.name= name;
        this.place= place;
        this.producer= producer;
        this.dateline= dateline;
        this.bitmaps= bitmaps;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

        String str = formatter.format(curDate);




        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPla = (TextView) listViewItem.findViewById(R.id.textViewPla);
        TextView textViewPro = (TextView) listViewItem.findViewById(R.id.textViewPro);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        textViewURL.setText(str);
        textViewName.setText("食材名稱:"+name[position]);
        textViewPla.setText("生產地:彰化");
        textViewPro.setText("生產者:"+producer[position]+"農");
        textViewDate.setText("保鮮天數:"+dateline[position]+"天");
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],200,200,false));
        return  listViewItem;

    }
}
