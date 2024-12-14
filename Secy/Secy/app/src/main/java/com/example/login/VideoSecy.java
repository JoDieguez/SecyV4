package com.example.login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;
public class VideoSecy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videosecy);
        //Config video local
        VideoView videoView = findViewById(R.id.viewSecy);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.mediasecy;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        //controles del video
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

    }
}
