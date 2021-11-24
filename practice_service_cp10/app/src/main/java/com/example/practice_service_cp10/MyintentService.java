package com.example.practice_service_cp10;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

public class MyintentService extends IntentService {

    public MyintentService(){
        super("MyintentService");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService","onDestroy executed");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MyIntentService","Thread id is" + Thread.currentThread().getId());
    }


}
