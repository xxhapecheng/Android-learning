package com.example.practice_httpuse_cp9;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onErroi(Exception e);

}
