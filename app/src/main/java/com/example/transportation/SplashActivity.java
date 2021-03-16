package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {

   // private static int  SPLASH_SCREEN = 2000;

    //variables

//    Animation topAnim, bottomAnim;
//    ImageView image;
//    TextView logo, slogan;

    private GifImageView gifImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        gifImageView = findViewById(R.id.gifImage);
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        try {
            InputStream inputStream = getAssets().open("splash_screen.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException ex)
        {

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                SplashActivity.this.finish();

            }
        }, 3000);

         //Animations
//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//
//        //Hooks
//        image = findViewById(R.id.imageView);
//        logo = findViewById(R.id.textView);
//
//
//    //      image.setAnimation(topAnim);
//        logo.setAnimation(bottomAnim);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intToHome = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intToHome);
//                finish();
//            }
//        }, SPLASH_SCREEN);





    }
}