package com.example.ngockhanh.custompathcomponent.Components;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.ngockhanh.custompathcomponent.Listener.ChangeChanel;
import com.example.ngockhanh.custompathcomponent.R;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ngoc Khanh on 11/7/2017.
 */

public class SliderTimer extends View {

    int slColor;
    float mDy;
    float mDx;
    public ItemTimer itemTimer;

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date maxTime;
    public Date minTime;
    public long diffTime;

    public int sliderMinHeight = 0;
    public int sliderMaxHeight = 0;
    public int sliderContent;
    public String currentTimeSlider;
    //Listener
    public ChangeChanel listener;

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

    void init(@Nullable AttributeSet set) {
        if (set == null) return;
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.SliderTimer);
        slColor = ta.getColor(R.styleable.SliderTimer_sliderColor, Color.RED);


        slText = "";
        mDy = 100;
        mDx = 0;

        itemTimer = new ItemTimer(mDx, mDy, slColor, slText);
        ta.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Log.d("Height", "onMeasure: " + heightMeasureSpec);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        sliderMinHeight = (int) mDy;
        sliderMaxHeight = 1400;

        sliderContent = sliderMaxHeight - sliderMinHeight;
        SimpleDateFormat df = new SimpleDateFormat("EE HH:mm");
        String time = df.format(this.minTime.getTime());

        itemTimer.setSlText(time + "");
        diffTime = (maxTime.getTime() - minTime.getTime()) / 60000;
        setMeasuredDimension(200, height);


    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
            return;
        }

        itemTimer.DrawItemTimer(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDy = (event.getY() - itemTimer.getSlY());

                break;
            case MotionEvent.ACTION_MOVE:
                itemTimer.setSlY(event.getY() - mDy);

                if (itemTimer.getSlY() < sliderMinHeight) {
                    itemTimer.setSlY(sliderMinHeight);
                }
                if (itemTimer.getSlY() > sliderMaxHeight) {
                    itemTimer.setSlY(sliderMaxHeight);
                }


                int mins = (int) roundPercent(this.diffTime, itemTimer.getSlY());
                Date dt = caculateTime(mins);
                currentTimeSlider=convertDateToStringFormat(dt);


                SimpleDateFormat df = new SimpleDateFormat("EE HH:mm");
                String currentTime = df.format(dt.getTime());
                String s=convertDateToStringFormat(dt);
                itemTimer.setSlText(currentTime);
                break;
            case MotionEvent.ACTION_UP:
                listener.onChangeChanel(""+currentTimeSlider);
                break;
        }
        invalidate();

        return true;
    }

    String convertDateToStringFormat(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year=""+calendar.get(Calendar.YEAR);
        String month=convertForFormat(""+(calendar.get(Calendar.MONTH)+1));

        String day=convertForFormat(""+calendar.get(Calendar.DAY_OF_MONTH));
        String hour=convertForFormat(""+calendar.get(Calendar.HOUR_OF_DAY));
        String minutes=convertForFormat(""+calendar.get(Calendar.MINUTE));


        return year+month+day+hour+minutes;
    }
    Date caculateTime(int mins) {

        final java.util.Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(minTime);
        cal.add(Calendar.MINUTE, mins);
        return cal.getTime();
    }

    //covert Datetime to format yyyyMMddhhmm
    String convertForFormat(String time){
        if(time.length()<2){
            return time ="0"+time;
        }
        return time;

    }

    float roundPercent(long diffTime, float y) {


        return (float) ((y - 100) * (diffTime / 1300.0));
    }

    public class ItemTimer {
        private float slX;
        private float slY;
        private Paint paintTriangular;
        private Paint paintRectangle;
        private Paint paintText;


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


        public ItemTimer(float slX, float slY, int slColor, String slText) {
            this.slX = slX;
            this.slY = slY;
            this.slText = slText;
            paintTriangular = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintTriangular.setColor(slColor);
            paintRectangle = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintRectangle.setColor(slColor);
            paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.WHITE);

        }


        void DrawItemTimer(Canvas canvas) {
            int paddingTop=100;
            int width=200;
            canvas.drawRoundRect(new RectF(slX, slY, slX + width, slY + paddingTop), 0, 100, paintRectangle);

            paintText.setTextAlign(Paint.Align.CENTER);
            paintText.setTextSize(35);
            canvas.drawText(this.slText, slX + 80, slY + 60, paintText);
//            CustomAnalogClock clock = null;
//            clock.init(getContext(), R.drawable.default_face, R.drawable.default_hour_hand, R.drawable.default_minute_hand, 0, false, false);
//            clock.setX(0);
//            clock.setY(100);
//            clock.setScale(100);





        }

    }


}
