package com.example.practice_ui_cp3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputtext;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        inputtext=(EditText) findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.sendbutton);
        msgRecyclerView=(RecyclerView) findViewById(R.id.msp_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=inputtext.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.Type_sent);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);//有新消息的时候，刷新Listview中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1);//将LV定位到最后一行
                    inputtext.setText("");
                }
            }
        });


    }

    private void initMsgs(){
        Msg msg1=new Msg("hello my friend",Msg.Type_received);
        msgList.add(msg1);
        Msg msg2=new Msg("who are you",Msg.Type_sent);
        msgList.add(msg2);
        Msg msg3=new Msg("this is xxhapecheng,nice talking to you ",Msg.Type_received);

    }
}