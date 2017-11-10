package com.example.ngockhanh.custompathcomponent.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.ngockhanh.custompathcomponent.Listener.ChangeChanel;
import com.example.ngockhanh.custompathcomponent.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ngoc Khanh on 11/7/2017.
 */

public class SliderTimer extends View {

    int slColor;
    int slWidth;
    int slHeight;
    float mDy;
    float mDx;
    ItemTimer  itemTimer;
    //Listener
    public   ChangeChanel listener;



    String slText;
    public SliderTimer(Context context) {
        super(context);
        init(null);
    }

    public SliderTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SliderTimer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SliderTimer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    void init(@Nullable AttributeSet set){
        if(set==null) return;
        TypedArray ta= getContext().obtainStyledAttributes(set, R.styleable.SliderTimer);
        slColor= ta.getColor(R.styleable.SliderTimer_sliderColor, Color.RED);

        Date currentDateTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
        String  currentTime = df.format(currentDateTime.getTime());
        slText=currentTime+"";
        mDy=100;
        mDx=0;
        itemTimer=new ItemTimer(mDx,mDy,slColor,slText);


        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isInEditMode()){
            return;
        }
        itemTimer.DrawItemTimer(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float diff;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDy=  (event.getY()- itemTimer.getSlY());
                break;
            case MotionEvent.ACTION_MOVE:
                itemTimer.setSlY(event.getY()-mDy);

                Log.d("Item", "onTouchEvent: " +itemTimer.getSlY());
                SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
                //Date currentTime = null;

                Calendar cal = Calendar.getInstance();
                int AMorPM=cal.get(Calendar.AM_PM);
                String AMPM="";
                if(AMorPM==Calendar.AM){
                    AMPM="AM";
                }
                else {
                    AMPM="PM";
                }



                DateFormat dateFormat=new SimpleDateFormat("hh:mm");
                
                Date currentTime=cal.getTime();
                try {
                    Date sub=dateFormat.parse("00:01");
                    Log.d("Time", "onTouchEvent: "+sub);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                //  cal.add(cal.getM,-5);
//                String currentTime=cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+" "+AMPM;
//                Toast.makeText(getContext(),currentTime,Toast.LENGTH_SHORT).show();



//                try {
//                    Date subTime;
//                    Calendar now = Calendar.getInstance();
//                    int AMorPM = now.get(Calendar.AM_PM);
//                    Log.d("AM/PM", "onTouchEvent: "+ Calendar.AM);
//
//                    if(AMorPM == Calendar.AM){
//                        Toast.makeText(getContext(),"AM",Toast.LENGTH_SHORT).show();
//                        subTime= df.parse("00:01 AM ");
//                    }
//                    else {
//                        Toast.makeText(getContext(),"PM",Toast.LENGTH_SHORT).show();
//                        subTime= df.parse("00:01 PM ");
//                    }
//
//                    Log.d("subtime", "Subtime: "+subTime);
//                    //Log.d("subtime", "Subtime: "+subTime);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                try {
//                 currentTime= df.parse(slText);
//
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                //
                if(itemTimer.getSlY()<mDy){
                    itemTimer.setSlY(mDy);
                }



                break;
            case MotionEvent.ACTION_UP:
                listener.onChangeChanel("b");
                break;
        }
        invalidate();

        return true;
    }

    class ItemTimer{
        private float slX;

        public String getSlText() {
            return slText;
        }

        public void setSlText(String slText) {
            this.slText = slText;
        }

        private String slText;

        public float getSlY() {
            return slY;

        }

        public void setSlY(float slY) {
            this.slY = slY;
            invalidate();
        }

        private float slY;
        private Paint paintTriangular;
        private Paint paintRectangle;
        private Paint paintText;


        public ItemTimer(float slX, float slY,int slColor,String slText){
            this.slX=slX;
            this.slY=slY;
            this.slText=slText;
            paintTriangular = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintTriangular.setColor(slColor);
            paintRectangle  = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintRectangle.setColor(slColor);
            paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.WHITE);

        }



        void DrawItemTimer(Canvas canvas){
        canvas.drawRoundRect(new RectF(slX, slY, slX+200, slY+100),0,100,paintRectangle);

            paintText.setTextAlign(Paint.Align.CENTER);
            paintText.setTextSize(35);
            canvas.drawText(slText,slX+80,slY+60,paintText);


        }



    }
}
