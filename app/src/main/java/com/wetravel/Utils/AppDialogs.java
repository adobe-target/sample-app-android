package com.wetravel.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wetravel.R;

public class AppDialogs {
    public static Dialog dialogLoader;

    public static void dialogBannerShow(Context mContext,String path){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_banner_show);
        dialog.getWindow().setBackgroundDrawable(null);

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int sWidth = (int) (displayMetrics.widthPixels*1.00);
        int sHeight = (int) (displayMetrics.heightPixels*1.00);

        Window window = dialog.getWindow();
        window.setLayout(sWidth,sHeight);
        dialog.show();

        ImageView imgBanner = dialog.findViewById(R.id.imgBanner);
        Bitmap bitmap = Utility.drawableToBitmap(mContext,(Utility.getDrawableFromAssets(mContext, path)));
        imgBanner.setImageBitmap(bitmap);

        ImageView imgCancel = dialog.findViewById(R.id.imgCancel);
        imgCancel.setPadding(Utility.deviceWidth*10/100,Utility.deviceWidth*11/100,Utility.deviceWidth*10/100,Utility.deviceWidth*10/100);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        RelativeLayout rlItem = dialog.findViewById(R.id.rlItem);
        rlItem.setPadding(Utility.deviceWidth*5/100,0,Utility.deviceWidth*5/100,Utility.deviceWidth*10/100);

    }

    public static void dialogLoaderShow(Context mContext){
        dialogLoader = new Dialog(mContext);
        dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoader.setContentView(R.layout.dialog_loader);
        dialogLoader.getWindow().setBackgroundDrawable(null);

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int sWidth = (int) (displayMetrics.widthPixels*1.00);
        int sHeight = (int) (displayMetrics.heightPixels*1.00);

        Window window = dialogLoader.getWindow();
        window.setLayout(sWidth,sHeight);
        dialogLoader.show();

        ImageView imgLoader = dialogLoader.findViewById(R.id.imgLoader);
        Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.loader_rotate);
        animation.setFillAfter(true);
        imgLoader.startAnimation(animation);

    }

    public static void dialogLoaderHide(){
        if(dialogLoader!=null){
            if(dialogLoader.isShowing()){
                dialogLoader.dismiss();
            }
        }
    }
}
