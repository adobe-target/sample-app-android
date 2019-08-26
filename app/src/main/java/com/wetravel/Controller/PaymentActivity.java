package com.wetravel.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.wetravel.R;
import com.wetravel.Utils.AppDialogs;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

public class PaymentActivity extends AppCompatActivity {
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_payment);

        initLayouts();
    }

    //View initialization
    @SuppressLint("JavascriptInterface")
    public void initLayouts(){
       web = findViewById(R.id.web);

       WebSettings webSettings = web.getSettings();
       webSettings.setJavaScriptEnabled(true);
       webSettings.setUseWideViewPort(true);
       webSettings.setLoadWithOverviewMode(true);

       web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
       web.setScrollbarFadingEnabled(false);
       web.setVerticalScrollBarEnabled(false);
       web.addJavascriptInterface(new JavaScriptInterface(this),"Android");

       web.clearHistory();
       web.clearFormData();
       web.clearCache(true);

       web.loadUrl("file:///android_asset/Payments/index.html");
    }

    public void onBackPress(){
        this.finish();
    }

    public void onProceed(){
        AppDialogs.dialogLoaderShow(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppDialogs.dialogLoaderHide();
                startActivity(new Intent(PaymentActivity.this,ThankYouActivity.class));
            }
        },Constant.delay_api);

    }

    class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void onBackPress(){
            ((PaymentActivity)context).onBackPress();
        }

        @JavascriptInterface
        public void onProceed(){
            ((PaymentActivity)context).onProceed();
        }
    }
}
