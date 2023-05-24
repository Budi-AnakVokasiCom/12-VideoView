package com.example.videoview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private final static String LOCAL_VIDEO = "video1";
    private final static String ONLINE_VIDEO = "https://developers.google.com/training/images/tacoma_narrows.mp4";


    private VideoView mVideoView;
    private TextView mBufferingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video_view);
        mBufferingTextView = findViewById(R.id.buffering_textview);

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.seekTo(1);
                mVideoView.start();
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mBufferingTextView.setVisibility(VideoView.INVISIBLE);
                mVideoView.start();
            }
        });
    }
    private Uri getMedia(String mediaName){
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        }
        else{
            return Uri.parse("android.resource://" + getPackageName()
                    + "/raw/" + mediaName);
        }
    }

    private void initializePlayer(){
        Uri videoUri = getMedia(LOCAL_VIDEO);
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }
    private void releasePlayer(){
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            mVideoView.pause();
        }
    }
}