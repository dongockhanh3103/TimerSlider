package com.example.ngockhanh.custompathcomponent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    int[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.GREEN, Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.GREEN, Color.WHITE};
    String[] titles = {"BLACK", "RED", "BLUE", "YELLOW", "GRAY", "GREEN", "WHITE", "BLACK", "RED", "BLUE", "YELLOW", "GRAY", "GREEN", "WHITE"};
    List<Date> timeStarting;

    List<Date> timeEnding;
    SliderTimer sliderTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        final List<Program> programList = new ArrayList<Program>();

        //get Starting time
        timeStarting = getArrTimeStarting();
        //get Ending time
        timeEnding = getArrTimeEnding();

        for (int i = 0; i < colors.length; i++) {
            Program program = new Program(titles[i], colors[i], timeStarting.get(i), timeEnding.get(i));
            programList.add(program);
        }

        Date maxDate = Collections.max(timeEnding);
        Date minDate = Collections.min(timeStarting);


        sliderTimer.setMaxTime(maxDate);
        sliderTimer.setMinTime(minDate);
        final CustomGridViewAdapter customGridViewAdapter = new CustomGridViewAdapter(this, programList);
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

        //yyyyMMddHHmm
        String[] arrStringDateTime =
                {"201711110730", "201711110745", "201711110845",
                "201711110900","201711110845", "201711110830", "201711111100",
                "201711111130", "201711111130", "201711111330", "201711111430",
                "201711111515", "201711111700", "201711111730"};


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

        for (int i = 0; i < arrStringDateTime.length; i++) {
            try {
                Date d = sdf.parse(arrStringDateTime[i]);
                //  Log.d("Date", "getArrTimeStarting: "+d.toString());

                listTimeStarting.add(d);

            } catch (ParseException ex) {
            }
        }

        return listTimeStarting;
    }


    List<Date> getArrTimeEnding() {
        //14
        List<Date> listTimeEnding = new ArrayList<Date>();
        //yyyyMMddHHmm
        String[] arrStringDateTime =
                {"201711110830", "201711110830", "201711111000",
                "201711111030", "201711111045", "201711111030", "201711111200",
                "201711111400", "201711111330", "201711111415", "201711111500",
                "201711111645", "201711111800", "201711111900"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

        for (int i = 0; i < arrStringDateTime.length; i++) {
            try {
                Date d = sdf.parse(arrStringDateTime[i]);

                listTimeEnding.add(d);
            } catch (ParseException ex) {

            }
        }
        return listTimeEnding;
    }


}
