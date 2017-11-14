package com.example.ngockhanh.custompathcomponent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.ngockhanh.custompathcomponent.Adapter.CustomGridViewAdapter;
import com.example.ngockhanh.custompathcomponent.Components.SliderTimer;
import com.example.ngockhanh.custompathcomponent.Listener.ChangeChanel;
import com.example.ngockhanh.custompathcomponent.Models.Program;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    List<Date> timeStarting;
    List<Date> timeEnding;
    SliderTimer sliderTimer;
    List<Program> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        final List<Program> programList = new ArrayList<Program>();
        list=new ArrayList<Program>();

        loadProgram();

        //get Starting time
        timeStarting = getArrTimeStarting();
        //get Ending time
        timeEnding = getArrTimeEnding();



        Date maxDate = Collections.max(timeEnding);
        Date minDate = Collections.min(timeStarting);

        sliderTimer.setMaxTime(maxDate);
        sliderTimer.setMinTime(minDate);
        final CustomGridViewAdapter customGridViewAdapter = new CustomGridViewAdapter(this, list);
        gridView.setAdapter(customGridViewAdapter);
        customGridViewAdapter.notifyDataSetChanged();


        sliderTimer.listener = new ChangeChanel() {
            @Override
            public void onChangeChanel(String Object) {
                customGridViewAdapter.getFilter().filter(Object);
                return;
            }

        };




    }

    void init() {
        gridView = (GridView) findViewById(R.id.DataGridView);
        sliderTimer = (SliderTimer) findViewById(R.id.sliderTimer);
    }

    List<Date> getArrTimeStarting() {

        List<Date> listTimeStarting = new ArrayList<Date>();
        for(int i=0; i<list.size();i++){
            listTimeStarting.add(list.get(i).getTimeStarting());

        }

        return listTimeStarting;
    }


    List<Date> getArrTimeEnding() {
        //14
        List<Date> listTimeEnding = new ArrayList<Date>();
        for(int i=0; i<list.size();i++){
            listTimeEnding.add(list.get(i).getTimeEnding());

        }

        return listTimeEnding;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("content_page.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void loadProgram() {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.content_page);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        int i=0;
        while (scanner.hasNextLine()) {


            builder.append(scanner.nextLine());

          parseJson(builder.toString());
        }
    }

    private List<Program> parseJson(String json) {


        List<Program> programList = new ArrayList<Program>();
        StringBuilder builder = new StringBuilder();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray datas = root.getJSONArray("datas");


            for (int i = 0; i < datas.length(); i++) {
                JSONObject object = datas.getJSONObject(i);


                JSONArray items = object.getJSONArray("items");
                for (int j = 0; j < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);


                    String title = item.getString("title");
                    String image = item.getString("image");
                    String imgChannel=item.getString("channel_image");
                    JSONObject current_show_slot = item.getJSONObject("current_show_slot");
                    String stimeStarting = current_show_slot.getString("from");
                    String stimeEnding = current_show_slot.getString("to");


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                    try {
                        Date timeStarting = sdf.parse(stimeStarting);
                        Date timeEnding=sdf.parse(stimeEnding);
                        Program program= new Program(title,image,imgChannel,timeStarting,timeEnding);
                        list.add(program);

                        programList.add(program);

                    } catch (ParseException ex) {

                    }

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return programList;
    }


}
