package com.wetravel.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wetravel.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderAdapter extends ArrayAdapter {
    private LayoutInflater layoutInflater;
    private List<Date> dateList;
    private Calendar currentDate;
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    Context mContext;
    String selectDate;

    public CalenderAdapter(Context context, List<Date> dateList, Calendar currentDate, String selectDate){
        super(context, R.layout.calender_view_cell);
        this.mContext = context;
        this.dateList = dateList;
        this.currentDate = currentDate;
        this.selectDate = selectDate;

        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int pos, View mView, ViewGroup viewGroup){
        Date mDate = dateList.get(pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int displayDate = calendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = calendar.get(Calendar.MONTH)+1;
        int displayYear = calendar.get(Calendar.YEAR);

        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if(mView == null){
            mView = layoutInflater.inflate(R.layout.calender_view_cell,viewGroup,false);
        }

        TextView tvDate = mView.findViewById(R.id.tvDate);
        tvDate.getLayoutParams().width = Utility.deviceWidth*10/100;
        tvDate.getLayoutParams().height = Utility.deviceWidth*10/100;
        tvDate.setTextSize(Utility.txtSize_14dp);
        tvDate.setTypeface(Utility.font_roboto_regular);

        if(displayMonth == (cal.get(Calendar.MONTH)+1) && displayYear == cal.get(Calendar.YEAR) && displayDate == cal.get(Calendar.DATE)){
            tvDate.setBackground(mContext.getResources().getDrawable(R.drawable.bg_calender_today_date));
            Drawable drawable = tvDate.getBackground();
            drawable.setAlpha(30);

            tvDate.setTextColor(mContext.getResources().getColor(R.color.color_2C2C2C));
            tvDate.setTypeface(Utility.font_roboto_bold);
        }else{
            mView.setBackgroundColor(mContext.getResources().getColor(R.color.color_ffffff));
            mView.setAlpha(1f);

            tvDate.setTextColor(mContext.getResources().getColor(R.color.color_4B4B4B));
        }

        if(!selectDate.equalsIgnoreCase("")){
            String[] split = selectDate.split("-");
            if(displayMonth == Integer.parseInt(split[1]) && displayYear == Integer.parseInt(split[2]) && displayDate == Integer.parseInt(split[0])){
                tvDate.setBackground(mContext.getResources().getDrawable(R.drawable.border_calender_selected_date));
                tvDate.setTextColor(mContext.getResources().getColor(R.color.color_2C2C2C));
                tvDate.setTypeface(Utility.font_roboto_bold);
            }
        }

        if(displayMonth == currentMonth && displayYear == currentYear){
            tvDate.setText(String.valueOf(displayDate));
        }else{
            tvDate.setText("");
            tvDate.setBackground(mContext.getResources().getDrawable(R.drawable.border_white_round));
            mView.setBackgroundColor(mContext.getResources().getColor(R.color.color_ffffff));
            mView.setAlpha(1f);
        }

        if(Utility.isBeforeDate(Constant.date_ddmyyyy,""+displayDate+"-"+displayMonth+"-"+displayYear)){
                tvDate.setTextColor(mContext.getResources().getColor(R.color.color_B3B3B3));
        }


        return  mView;
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dateList.indexOf(item);
    }
}
