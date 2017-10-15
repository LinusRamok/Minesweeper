package com.bat.iron.demosweeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InGameDarkHighScore extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t20,t21,t25,t29,t33;
    SQLiteDatabase db;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dark_high_score);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

        t1=(TextView)findViewById(R.id.textView22);
        t2=(TextView)findViewById(R.id.textView23);
        t3=(TextView)findViewById(R.id.textView24);
        t4=(TextView)findViewById(R.id.textView26);
        t5=(TextView)findViewById(R.id.textView27);
        t6=(TextView)findViewById(R.id.textView28);
        t7=(TextView)findViewById(R.id.textView30);
        t8=(TextView)findViewById(R.id.textView31);
        t9=(TextView)findViewById(R.id.textView32);
        t10=(TextView)findViewById(R.id.textView34);
        t11=(TextView)findViewById(R.id.textView35);
        t12=(TextView)findViewById(R.id.textView36);
        t20=(TextView)findViewById(R.id.textView20);
        t21=(TextView)findViewById(R.id.textView21);
        t25=(TextView)findViewById(R.id.textView25);
        t29=(TextView)findViewById(R.id.textView29);
        t33=(TextView)findViewById(R.id.textView33);

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        t20.setTypeface(ourfont2);
        t21.setTypeface(ourfont2);
        t25.setTypeface(ourfont2);
        t29.setTypeface(ourfont2);
        t33.setTypeface(ourfont2);
        t1.setTypeface(ourfont);
        t2.setTypeface(ourfont);
        t3.setTypeface(ourfont);
        t4.setTypeface(ourfont);
        t5.setTypeface(ourfont);
        t6.setTypeface(ourfont);
        t7.setTypeface(ourfont);
        t8.setTypeface(ourfont);
        t9.setTypeface(ourfont);
        t10.setTypeface(ourfont);
        t11.setTypeface(ourfont);
        t12.setTypeface(ourfont);
        check=0;

        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);
         /*   String data="";
                for (int h=0;h<4;h++)
                {
                    String s="select *from "+modes[h]+"";
                    Cursor c=db.rawQuery(s,null);
                    data= data+modes[h]+"\n";
                    while (c.moveToNext())
                    {
                        String n=c.getString(0);
                        String p=c.getString(1);
                        data =data+n+" .  "+p+"\n";
                    }

                }
                AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
                b.setTitle("High Scores");
                b.setMessage(data);
                b.setPositiveButton("Ok", null);
                AlertDialog d=b.create();
                d.show();*/
        String m1="select *from easy";
        String score1="- - - -";
        Cursor c1=db.rawQuery(m1,null);
        c1.moveToNext();
        score1=c1.getString(1);
        t1.setText("1. "+score1+"");
        c1.moveToNext();
        score1=c1.getString(1);
        t2.setText("2. "+score1+"");
        c1.moveToNext();
        score1=c1.getString(1);
        t3.setText("3. "+score1+"");

        String m2="select *from medium";
        String score2="- - - -";
        Cursor c2=db.rawQuery(m2,null);
        c2.moveToNext();
        score2=c2.getString(1);
        t4.setText("1. "+score2+"");
        c2.moveToNext();
        score2=c2.getString(1);
        t5.setText("2. "+score2+"");
        c2.moveToNext();
        score2=c2.getString(1);
        t6.setText("3. "+score2+"");

        String m3="select *from hard";
        String score3="- - - -";
        Cursor c3=db.rawQuery(m3,null);
        c3.moveToNext();
        score3=c3.getString(1);
        t7.setText("1. "+score3+"");
        c3.moveToNext();
        score3=c3.getString(1);
        t8.setText("2. "+score3+"");
        c1.moveToNext();
        score3=c3.getString(1);
        t9.setText("3. "+score3+"");

        String m4="select *from insane";
        String score4="- - - -";
        Cursor c4=db.rawQuery(m4,null);
        c4.moveToNext();
        score4=c4.getString(1);
        t10.setText("1. "+score4+"");
        c4.moveToNext();
        score4=c4.getString(1);
        t11.setText("2. "+score4+"");
        c4.moveToNext();
        score4=c4.getString(1);
        t12.setText("3. "+score4+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        check=1;
        finish();
    }
}