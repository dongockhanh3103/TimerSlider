package com.example.ngockhanh.custompathcomponent;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ngockhanh.custompathcomponent.Adapter.CustomGridViewAdapter;
import com.example.ngockhanh.custompathcomponent.Components.SliderTimer;
import com.example.ngockhanh.custompathcomponent.Listener.ChangeChanel;
import com.example.ngockhanh.custompathcomponent.Models.Program;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    int [] colors={Color.BLACK,Color.RED,Color.BLUE,Color.YELLOW,Color.GRAY,Color.GREEN,Color.WHITE,Color.BLACK,Color.RED,Color.BLUE,Color.YELLOW,Color.GRAY,Color.GREEN,Color.WHITE};
    String [] titles={"BLACK","RED","BLUE","YELLOW","GRAY","GREEN","WHITE","BLACK","RED","BLUE","YELLOW","GRAY","GREEN","WHITE"};
    SliderTimer sliderTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=(GridView) findViewById(R.id.DataGridView);
        sliderTimer=(SliderTimer) findViewById(R.id.sliderTimer);


        final List<Program> programList=new ArrayList<Program>();
        for(int i=0;i<colors.length;i++){
            Program program=new Program(titles[i],colors[i]);
            programList.add(program);
        }
         final CustomGridViewAdapter customGridViewAdapter=new CustomGridViewAdapter(this,programList);
        gridView.setAdapter(customGridViewAdapter);
        customGridViewAdapter.notifyDataSetChanged();

        sliderTimer.listener=new ChangeChanel() {
            @Override
            public void onChangeChanel(String Object) {
                customGridViewAdapter.getFilter().filter(Object);
                Toast.makeText(MainActivity.this,Object,Toast.LENGTH_SHORT).show();
                return;
            }
        };


    }
}
