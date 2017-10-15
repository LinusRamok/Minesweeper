package com.bat.iron.demosweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ImageView icon;
    TextView dev,tv52,tv53;
    SharedPreferences prefs=null;
    int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);
        setContentView(R.layout.activity_intro);
        prefs=getSharedPreferences("com.bat.iron.demosweeper", MODE_PRIVATE);

        final String modes[]={"easy","medium","hard","insane"};
        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);
        dev=(TextView)findViewById(R.id.textView44);
        icon=(ImageView)findViewById(R.id.gameicon);
        tv52=(TextView)findViewById(R.id.textView52);
        tv53=(TextView)findViewById(R.id.textView53);

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        dev.setTypeface(ourfont2);
        tv52.setTypeface(ourfont);
        tv53.setTypeface(ourfont);

        if(prefs.getBoolean("firstrun",true))
        {
            String tm="create table if not exists theme (sno varchar(2),m varchar(2))";
            db.execSQL(tm);
            String mo="insert into theme values(1,0)";
            db.execSQL(mo);

            for (int h=0;h<4;h++)
            {
                String s="create table if not exists "+modes[h]+"(sno varchar(2),sc varchar(10))";
                db.execSQL(s);
                String s1="Insert into "+modes[h]+" values(1,'- - - -')";
                String s2="Insert into "+modes[h]+" values(2,'- - - -')";
                String s3="Insert into "+modes[h]+" values(3,'- - - -')";
                db.execSQL(s1);
                db.execSQL(s2);
                db.execSQL(s3);
            }

            String mu="create table if not exists music (sno varchar(2),x varchar(2))";
            db.execSQL(mu);
            String mu1="insert into music values(1,0)";
            db.execSQL(mu1);

            prefs.edit().putBoolean("firstrun", false).commit();
            temp=1;

            Handler mHandler=new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(IntroActivity.this, HowToPlay.class);
                    startActivity(i);
                }
            }, 2500);
        }

        //Animation a= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        Animation a2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        icon.startAnimation(a2);
        dev.startAnimation(a2);
        tv52.startAnimation(a2);
        tv53.startAnimation(a2);
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                String m1 = "select *from theme";
                Cursor c1 = db.rawQuery(m1, null);
                c1.moveToNext();
                String p = c1.getString(1);
                if (p.equals("1") && temp==0)
                {
                    Intent i=new Intent(IntroActivity.this,DarkMainActivity.class);
                    startActivity(i);
                }
                else if (p.equals("0") && temp==0)
                {
                    Intent i=new Intent(IntroActivity.this,MainActivity.class);
                    startActivity(i);
                }

            }
        }, 2500);
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