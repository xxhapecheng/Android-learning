package com.example.practice_ui_cp3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        EditText ed = (EditText) findViewById(R.id.edtext1);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressbar1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progress 简单使用
//                int progress = pb.getProgress();
//                progress = progress + 10;
//                pb.setProgress(progress);

                //dialog 简单使用
//                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                dialog.setTitle("This is Dialog");
//                dialog.setMessage("somthing important");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.show();
                ProgressDialog pbdialog = new ProgressDialog(MainActivity.this);
                pbdialog.setTitle("this is ProgressDialog");
                pbdialog.setMessage("Loading...");
                pbdialog.setCancelable(true);
                pbdialog.show();
            }
        });


    }
}