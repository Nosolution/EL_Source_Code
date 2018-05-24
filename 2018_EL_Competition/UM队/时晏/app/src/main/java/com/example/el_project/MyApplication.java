package com.example.el_project;

import android.app.Application;
import android.content.Context;

/**
 * Created  on 18-4-14.
 */

public class MyApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
