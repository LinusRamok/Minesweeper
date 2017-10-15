package com.bat.iron.demosweeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    ImageView im,im2,im3,im4,im5,im6,im7,imb6,imb5;
    TextView tv59,tv,tv2,tv3,tv4,tv5,tv6,tv45,tv46,tv48;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);
        setContentView(R.layout.activity_about_us);

        im=(ImageView)findViewById(R.id.imageView);
        im2=(ImageView)findViewById(R.id.imageView2);
        im3=(ImageView)findViewById(R.id.imageView3);
        im4=(ImageView)findViewById(R.id.imageView4);
        im5=(ImageView)findViewById(R.id.imageView5);
        im6=(ImageView)findViewById(R.id.imageView6);
        im7=(ImageView)findViewById(R.id.imageView7);
        tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView5);
        tv6=(TextView)findViewById(R.id.textView6);
        tv59=(TextView)findViewById(R.id.textView59);
        tv45=(TextView)findViewById(R.id.textView45);
        tv46=(TextView)findViewById(R.id.textView46);
        tv48=(TextView)findViewById(R.id.textView48);
        imb5=(ImageView)findViewById(R.id.imageButton5);
        imb6=(ImageView)findViewById(R.id.imageButton6);

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        tv59.setTypeface(ourfont2);
        tv.setTypeface(ourfont2);
        tv3.setTypeface(ourfont2);
        tv5.setTypeface(ourfont2);
        tv2.setTypeface(ourfont);
        tv4.setTypeface(ourfont);
        tv6.setTypeface(ourfont);
        tv46.setTypeface(ourfont);
        tv45.setTypeface(ourfont2);
        tv48.setTypeface(ourfont);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.bat.iron.demosweeper"));
                startActivity(intent);
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                share.putExtra(Intent.EXTRA_SUBJECT,"Share Link!!!");
                share.putExtra(Intent.EXTRA_TEXT,"Download this amazing Minesweeper game with the best graphics in just 1.5MB.\nhttps://play.google.com/store/apps/details?id=com.bat.iron.demosweeper");
                startActivity(Intent.createChooser(share,"Share Link!!!"));
            }
        });

        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send=new Intent(Intent.ACTION_SEND);
                send.setData(Uri.parse("mailto:"));
                send.setType("plain/text");
                send.putExtra(Intent.EXTRA_EMAIL,"nakeddevelopers@gmail.com");
                startActivity(Intent.createChooser(send,"Send Mail"));
            }
        });

        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/gautam.221b/"));
                startActivity(intent);
            }
        });

        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://plus.google.com/102877587482625337161"));
                startActivity(intent);
            }
        });

        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/linus_ramok/"));
                startActivity(intent);
            }
        });

        im7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://plus.google.com/108983472384487136802"));
                startActivity(intent);
            }
        });

        imb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/kunal_v889/"));
                startActivity(intent);
            }
        });

        imb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/arman.siddique5191/"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);
    }
}
