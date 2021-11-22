package com.example.practice_video_cp8;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play=(Button) findViewById(R.id.play);
        Button replay=(Button) findViewById(R.id.replay);
        Button pause=(Button) findViewById(R.id.pause);
        play.setOnClickListener(this);
        replay.setOnClickListener(this);
        pause.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initVideoPlayer();
        }
    }

    private void initVideoPlayer() {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"movie.mp4");
            videoView.setVideoPath(file.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPlayer();
                }else {
                    Toast.makeText(this, "拒绝权限后无法使用", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
}