package com.example.fcg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.fcg.R;

public class Loadding extends AppCompatActivity {
    ProgressBar progressBar;
    private int status = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status);
                            if(status == 100){
                                progressBar.setVisibility(View.GONE);
                                ImageView imageView = (ImageView)findViewById(R.id.active);
                                imageView.setImageResource(R.drawable.ic_activate);
                                ScaleAnimation fade_in =  new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                fade_in.setDuration(1000);
                                fade_in.setFillAfter(true);
                                imageView.startAnimation(fade_in);
                                imageView.setBackgroundResource(R.drawable.cricle_image_active);
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                      startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                       finish();
                                   }
                               },4000);
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status++;
                }
            }
        }).start();

    }

    @Override
    public void onBackPressed() {

    }
}
