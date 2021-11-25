package com.example.practice_learningmore_cp13;

import android.widget.Toast;

public class ContextTest {
    
    public static void showContextUse(){
        Toast.makeText(NewApplication.getContext(), "context使用成功", Toast.LENGTH_SHORT).show();
    }

}
