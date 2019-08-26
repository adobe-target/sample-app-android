package com.wetravel.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wetravel.BuildConfig;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static int deviceWidth;
    public static int deviceHeight;
    public static int deviceDensity;
    public static String deviceDensityImage;
    public static int txtSize;

    public static int txtSize_7dp;
    public static int txtSize_8dp;
    public static int txtSize_9dp;
    public static int txtSize_10dp;
    public static int txtSize_11dp;
    public static int txtSize_12dp;
    public static int txtSize_14dp;
    public static int txtSize_15dp;
    public static int txtSize_16dp;
    public static int txtSize_18dp;
    public static int txtSize_20dp;

    public static Typeface font_roboto_regular;
    public static Typeface font_roboto_bold;
    public static Typeface font_roboto_medium;
    public static Typeface font_roboto_light;
    public static Typeface font_roboto_regular_italic;

    //Basic info creating function
    public static void init(Context mContext){
        deviceDensity = ((Activity)mContext).getResources().getDisplayMetrics().densityDpi;
        deviceHeight = ((Activity)mContext).getWindowManager().getDefaultDisplay().getHeight();
        deviceWidth = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
        deviceDensityImage = getDeviceDensity(mContext);

        LOG("densitty::::"+deviceDensityImage);
        LOG(""+deviceHeight+":::"+deviceWidth);

        if(deviceHeight>deviceWidth) {
            txtSize = deviceWidth/deviceDensity*6;
        }else{
            txtSize = deviceHeight/deviceDensity*6;
        }

        font_roboto_regular = Typeface.createFromAsset(mContext.getAssets(),"FONT/Roboto-Regular.ttf");
        font_roboto_bold = Typeface.createFromAsset(mContext.getAssets(),"FONT/Roboto-Bold.ttf");
        font_roboto_medium = Typeface.createFromAsset(mContext.getAssets(),"FONT/Roboto-Medium.ttf");
        font_roboto_light = Typeface.createFromAsset(mContext.getAssets(),"FONT/Roboto-Light.ttf");
        font_roboto_regular_italic = Typeface.createFromAsset(mContext.getAssets(),"FONT/Roboto-Italic.ttf");

        txtSize_7dp = txtSize*7/10;
        txtSize_8dp = txtSize*8/10;
        txtSize_9dp = txtSize*9/10;
        txtSize_10dp = txtSize*10/10;
        txtSize_11dp = txtSize*11/10;
        txtSize_12dp = txtSize*12/10;
        txtSize_14dp = txtSize*12/10;
        txtSize_15dp = txtSize*15/10;
        txtSize_16dp = txtSize*16/10;
        txtSize_18dp = txtSize*18/10;
        txtSize_20dp = txtSize*20/10;
    }

    //Print LOG in application
    public static void LOG(String msg){
        if(BuildConfig.DEBUG)
            System.out.println("*******WE TRAVEL LOG********"+msg);
    }

    //Print Toast in application
    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    //Get bitmap from assets folder
    public static Bitmap getBitmapFromAssets(Context context, String path){
        Bitmap finalBitmap = null;
        AssetManager assetManager = context.getAssets();
        InputStream is;
        try {
            is = assetManager.open(path);
            finalBitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e){

        }
        return finalBitmap;
    }

    //Drawable to Bitmap convert
    public static Bitmap drawableToBitmap(Context context, Drawable drawable){
        Bitmap finalBitmap = null;
        if(drawable instanceof BitmapDrawable) {
            finalBitmap = ((BitmapDrawable) drawable).getBitmap();
        }else{
            int w = drawable.getIntrinsicWidth();
            w  = w > 0 ? w : 1;
            int h = drawable.getIntrinsicHeight();
            h  = h > 0 ? h : 1;

            finalBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(finalBitmap);
            drawable.setBounds(0,0,c.getWidth(),c.getHeight());
            drawable.draw(c);
        }
        return finalBitmap;
    }

    //Bitmap to drawable convert
    public static Drawable bitmapToDrawable(Context context,Bitmap bitmap){
        Drawable finalDrawable = null;
        finalDrawable = new BitmapDrawable(context.getResources(),bitmap);
        return finalDrawable;
    }

    //Drawable from assets folder
    public static Drawable getDrawableFromAssets(Context context, String path){
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(context.getAssets().open(path),null);
        }catch (Exception e){

        }
        return drawable;
    }

    //Remove Transparency from bitmap
    public static Bitmap cropBitmapTransparency(Bitmap source){
        Bitmap finalBitmap = null;
        int firstX = 0, firstY = 0;
        int lastX = source.getWidth();
        int lastY = source.getHeight();

        int[] pixels = new int[source.getWidth()*source.getHeight()];
        source.getPixels(pixels,0,source.getWidth(),0,0,source.getWidth(),source.getHeight());

        loop:
        for(int x = 0; x < source.getWidth(); x++){
            for(int y = 0; y < source.getHeight(); y++){
                if(pixels[x + (y * source.getWidth())] != Color.TRANSPARENT){
                    firstX = x;
                    break loop;
                }
            }
        }

        loop:
        for(int y = 0; y < source.getHeight(); y++){
            for(int x = firstX; x < source.getWidth(); x++){
                if(pixels[x + (y * source.getWidth())] != Color.TRANSPARENT){
                    firstY = y;
                    break loop;
                }
            }
        }

        loop:
        for(int x = source.getWidth()-1; x >= firstX; x--){
            for(int y = source.getHeight()-1; y >= firstY; y--){
                if(pixels[x + (y * source.getWidth())] != Color.TRANSPARENT){
                    lastX = x;
                    break loop;
                }
            }
        }

        loop:
        for(int y = source.getHeight()-1; y >= firstY; y--){
            for(int x = source.getWidth()-1; x >= firstX; x--){
                if(pixels[x + (y * source.getWidth())] != Color.TRANSPARENT){
                    lastY = y;
                    break loop;
                }
            }
        }

        finalBitmap = Bitmap.createBitmap(source,firstX,firstY,lastX-firstX,lastY-firstY);
        return finalBitmap;
    }

    public static String changeDateFormat(String currentFormat, String changeFormat, String strDate){
        String convertDate = "";
        SimpleDateFormat sdfCurrent = new SimpleDateFormat(currentFormat);
        SimpleDateFormat sdfChange = new SimpleDateFormat(changeFormat);

        try {
            Date date = sdfCurrent.parse(strDate);
            convertDate = sdfChange.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertDate;
    }

    public static String getDeviceDensity(Context mContext){
        String deviceDensity =""; //0.75,1,1.5,2,3,4

        switch (mContext.getResources().getDisplayMetrics().densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                deviceDensity = "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                deviceDensity = "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                deviceDensity = "hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                deviceDensity = "xhdpi";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                deviceDensity = "xxhdpi";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                deviceDensity = "xxxhdpi";
                break;
                default:
                    deviceDensity = "mdpi";
        }

        return deviceDensity;
    }

    public static boolean isBeforeDate(String dateFormat, String strDate){
        boolean isBefore = false;
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.add(Calendar.DAY_OF_MONTH,-1);

        try {
            if(new SimpleDateFormat(dateFormat).parse(strDate).before(cal.getTime())){
                isBefore = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBefore;
    }

    public static boolean isFutureDate(String pDate){
        boolean status = false;
        try {
            Date date = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(pDate);
            status = new Date().before(date);
        }catch (Exception e){
        }
        LOG("::::Date"+status);
        return status;
    }

    public static void hideKeyboard(Activity mActivity){
        InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = mActivity.getCurrentFocus();
        if(view == null){
            view = new View(mActivity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public static void saveInSharedPreference(Context context, String key ,String value){
        SharedPreferences sp = context.getSharedPreferences(Constant.shared_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key, value);
        e.apply();
    }

    public static String getInSharedPreference(Context context, String key ,String dValue){
        String value = "";
        SharedPreferences sp = context.getSharedPreferences(Constant.shared_pref,Context.MODE_PRIVATE);
        value = sp.getString(key,dValue);
        return value;
    }
}
