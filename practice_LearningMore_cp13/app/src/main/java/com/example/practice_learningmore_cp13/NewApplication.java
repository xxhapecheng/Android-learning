package com.example.practice_learningmore_cp13;

import android.app.Application;
import android.content.Context;

public class NewApplication extends Application {
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
