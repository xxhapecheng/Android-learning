package com.example.practice_ui_cp3;

public class Msg {
    public static final int Type_received=0;
    public static final int Type_sent=1;
    private String content;
    private int type;

    public Msg(String content ,int type){
        this.content=content;
        this.type=type;
    }

    public  String getContent(){
        return content;
    }

    public int getType() {
        return type;
    }
}
