package com.wetravel.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wetravel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderView extends LinearLayout {
    private Context mContext;
    private static int MAX_COLUMN = 42;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);

    private ImageView btnPrevious,btnNext;
    private TextView curDate;
    private GridView gvCalender;
    public DateResponse listener;
    String selectedDate = "";

    List<Date> dayList = new ArrayList<>();
    private CalenderAdapter calenderAdapter;
    TextView tvSun,tvMon,tvTue,tvWed,tvThu,tvFri,tvSat;

    public CalenderView(Context mContext){
        super(mContext);
    }

    public CalenderView(Context mContext, AttributeSet attrs){
        super(mContext,attrs);
        this.mContext = mContext;
        initLayout();
        setAdapter();
    }

    public CalenderView(Context mContext, AttributeSet attrs,int defStyleAttr){
        super(mContext,attrs,defStyleAttr);
    }

    private void initLayout(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calender_view,this);

        curDate = view.findViewById(R.id.curDate);
        curDate.setTextSize(Utility.txtSize_18dp);
        curDate.setTypeface(Utility.font_roboto_regular);

        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH , -1);
                setAdapter();
            }
        });

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH , 1);
                setAdapter();
            }
        });

        gvCalender = view.findViewById(R.id.gvCalender);
        gvCalender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date mDate = dayList.get(position);
                Calendar mCalender = Calendar.getInstance();
                mCalender.setTime(mDate);

                String selectDate = ""+mCalender.get(Calendar.DAY_OF_MONTH);
                int selectMonth = mCalender.get(Calendar.MONTH)+1;
                int selectYear = mCalender.get(Calendar.YEAR);
                int dayOfWeek = mCalender.get(Calendar.DAY_OF_WEEK);

                int currentMonth = cal.get(Calendar.MONTH)+1;
                int currentYear = cal.get(Calendar.YEAR);

                if(!Utility.isBeforeDate(Constant.date_ddmyyyy,""+selectDate+"-"+selectMonth+"-"+selectYear)){
                    if(selectMonth == currentMonth && selectYear == currentYear) {
                        listener.onDateSelected(selectDate + "-" + selectMonth + "-" + selectYear,""+dayOfWeek);
                        calenderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        tvSun = findViewById(R.id.tvSun);
        tvSun.setTextSize(Utility.txtSize_11dp);
        tvSun.setTypeface(Utility.font_roboto_regular);

        tvMon = findViewById(R.id.tvMon);
        tvMon.setTextSize(Utility.txtSize_11dp);
        tvMon.setTypeface(Utility.font_roboto_regular);

        tvTue = findViewById(R.id.tvTue);
        tvTue.setTextSize(Utility.txtSize_11dp);
        tvTue.setTypeface(Utility.font_roboto_regular);

        tvWed = findViewById(R.id.tvWed);
        tvWed.setTextSize(Utility.txtSize_11dp);
        tvWed.setTypeface(Utility.font_roboto_regular);

        tvThu = findViewById(R.id.tvThu);
        tvThu.setTextSize(Utility.txtSize_11dp);
        tvThu.setTypeface(Utility.font_roboto_regular);

        tvFri = findViewById(R.id.tvFri);
        tvFri.setTextSize(Utility.txtSize_11dp);
        tvFri.setTypeface(Utility.font_roboto_regular);

        tvSat = findViewById(R.id.tvSat);
        tvSat.setTextSize(Utility.txtSize_11dp);
        tvSat.setTypeface(Utility.font_roboto_regular);
    }

    private void setAdapter(){
        dayList.clear();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH,1);

        int firstDayofMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayofMonth);
        if(firstDayofMonth < 5){
            MAX_COLUMN = 35;
        }else {
            MAX_COLUMN = 42;
        }

        while (dayList.size() < MAX_COLUMN){
            dayList.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH,1);
        }

        String sDate = sdf.format(cal.getTime());
        curDate.setText(sDate);

        calenderAdapter  = new CalenderAdapter(mContext, dayList, cal, selectedDate);
        gvCalender.setAdapter(calenderAdapter);
    }

    public void setListener(DateResponse response, String str){
        this.listener = response;
        this.selectedDate = str;
        setAdapter();
    }
}
