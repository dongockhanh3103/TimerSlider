package com.example.ngockhanh.custompathcomponent.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ngockhanh.custompathcomponent.Models.Program;
import com.example.ngockhanh.custompathcomponent.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ngoc Khanh on 11/8/2017.
 */

public class CustomGridViewAdapter extends BaseAdapter implements Filterable {

    List<Program> listProgram;
    Context context;
    List<Program> filterList;
    CustomFilter filter;

    public CustomGridViewAdapter(Context context, List<Program> listProgram){
        this.context=context;
        this.listProgram=listProgram;
        this.filterList=listProgram;

    }
    @Override
    public int getCount() {
        return listProgram.size();
    }

    @Override
    public Program getItem(int i) {
        return listProgram.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ProgramHolder programHolder;
        if( convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_program, null);
            programHolder = new ProgramHolder(convertView);
            convertView.setTag(programHolder);
        }
        else {
            programHolder=(ProgramHolder) convertView.getTag();
        }

        programHolder.build(getItem(position).getTitle(),getItem(position).getColor(),
                getItem(position).getTimeStarting(),getItem(position).getTimeEnding());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }

    ///INNER CLASS
    class  CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {


                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                try {

                    Date date = sdf.parse(constraint+"");

                    constraint=constraint.toString().toUpperCase();
                    List<Program> filters=new ArrayList<Program>();

                    for(int i=0;i<filterList.size();i++){


                        Date startingTime=filterList.get(i).getTimeStarting();
                        Date endingTime=filterList.get(i).getTimeEnding();
                        if((date.compareTo(startingTime))==1 && date.compareTo(endingTime)==-1){

                            Program program=new Program(filterList.get(i).getTitle(),filterList.get(i).getColor()
                                    ,filterList.get(i).getTimeStarting(),filterList.get(i).getTimeEnding());
                            filters.add(program);
                        }
                    }

                    results.count=filters.size();
                    results.values=filters;

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                //Constrain to upper



            }
            else {
                results.count=filterList.size();
                results.values=filterList;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listProgram=(ArrayList<Program>)results.values;
            notifyDataSetChanged();

        }
    }

    private class ProgramHolder{
        private TextView title;
        private View view;
        private TextView timeStarting;
        private TextView timeEnding;


        private ProgramHolder(View view){
            this.title= (TextView) view.findViewById(R.id.titleProgram);
            this.view = (View) view.findViewById(R.id.bgProgram);
            this.timeStarting=(TextView) view.findViewById(R.id.timeStart);
            this.timeEnding = (TextView) view.findViewById(R.id.timeEnd);


        }

        void build(final String title, final int color, Date timeStarting, Date timeEnding){
            this.title.setText(title);
            this.view.setBackgroundColor(color);

            //set format Starting time
            SimpleDateFormat startTimeFormat = new SimpleDateFormat("EE HH:mm");
            String datetimeStartingTime=startTimeFormat.format(timeStarting);
            //set format Ending time
            SimpleDateFormat endTimeFormat = new SimpleDateFormat("HH:mm");
            String datetimeEndingTime=endTimeFormat.format(timeEnding);

            this.timeStarting.setText(datetimeStartingTime);
            this.timeEnding.setText(datetimeEndingTime);

        }
    }



}
