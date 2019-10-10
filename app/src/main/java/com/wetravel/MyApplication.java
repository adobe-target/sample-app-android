package com.wetravel;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.adobe.mobile.Config;


public class MyApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		Config.setContext(this.getApplicationContext());
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
