package com.example.ngockhanh.custompathcomponent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ngockhanh.custompathcomponent.Models.Program;
import com.example.ngockhanh.custompathcomponent.R;

import java.util.ArrayList;
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

        programHolder.build(getItem(position).getTitle(),getItem(position).getColor());

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
                //Constrain to upper
                constraint=constraint.toString().toUpperCase();
                List<Program> filters=new ArrayList<Program>();

                for(int i=0;i<filterList.size();i++){
                    if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                        Program program=new Program(filterList.get(i).getTitle(),filterList.get(i).getColor());
                        filters.add(program);
                    }
                }

                results.count=filters.size();
                results.values=filters;


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

        private ProgramHolder(View view){
            this.title= (TextView) view.findViewById(R.id.titleProgram);
            this.view = (View) view.findViewById(R.id.bgProgram);
        }

        void build(final String title, final int color){
            this.title.setText(title);
            this.view.setBackgroundColor(color);

        }
    }



}
