package com.bat.iron.demosweeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainSettings extends AppCompatActivity {
    ImageButton theme,music;
    Button b,b10,b11;
    SQLiteDatabase db;
    RelativeLayout rl,rl7;
    TextView tv44;
    int check=0;
    int m=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);
        setContentView(R.layout.activity_main_settings);
        theme=(ImageButton)findViewById(R.id.themebuttom);
        b=(Button)findViewById(R.id.button);
        music=(ImageButton)findViewById(R.id.imageButton3);
        b10=(Button)findViewById(R.id.button10);
        b11=(Button)findViewById(R.id.button11);
        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);
        rl=(RelativeLayout)findViewById(R.id.settingsrl);
        rl7=(RelativeLayout)findViewById(R.id.relativeLayout7);
        tv44=(TextView)findViewById(R.id.textView44);

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        tv44.setTypeface(ourfont2);
        b.setTypeface(ourfont);
        b11.setTypeface(ourfont);
        b10.setTypeface(ourfont);

        check=0;
        String m2 = "select *from music";
        Cursor c2 = db.rawQuery(m2, null);
        c2.moveToNext();
        String p2 = c2.getString(1);
        if (p2.equals("1"))
            music.setBackgroundResource(R.drawable.musicoff);
        else
            music.setBackgroundResource(R.drawable.musicon);
        String m1 = "select *from theme";
        Cursor c1 = db.rawQuery(m1, null);
        c1.moveToNext();
        String p = c1.getString(1);
        if (p.equals("1"))
        {
            theme.setBackgroundResource(R.drawable.darkmoonlight);
        }
        else if (p.equals("0")) {
            theme.setBackgroundResource(R.drawable.sunlight);
        }

        theme.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              changetheme();

          }
      });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changetheme();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changemusic();
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changemusic();
            }
        });

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m1 = "select *from theme";
                Cursor c1 = db.rawQuery(m1, null);
                c1.moveToNext();
                String p = c1.getString(1);
                check=1;
                if (p.equals("1"))
                {
                    Intent i = new Intent(MainSettings.this,DarkAboutUs.class);
                    startActivity(i);
                }
                else if (p.equals("0"))
                {
                    Intent i = new Intent(MainSettings.this,AboutUs.class);
                    startActivity(i);
                }
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
        String m1 = "select *from music";
        Cursor c1 = db.rawQuery(m1, null);
        c1.moveToNext();
        String p = c1.getString(1);
        if (p.equals("0"))
        {
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            startService(serv);
        }
    }

    public void changetheme()
    {
        String m1 = "select *from theme";
        String m2 = "select *from music";

        Cursor c1 = db.rawQuery(m1, null);
        Cursor c2 = db.rawQuery(m2, null);
        c1.moveToNext();
        c2.moveToNext();
        String p = c1.getString(1);
        String p2 = c2.getString(1);

        if (p.equals("1"))
        {
            theme.setBackgroundResource(R.drawable.sunlight);
            b.setBackgroundResource(R.drawable.custombuttonbackground);
            b.setTextColor(Color.parseColor("#000000"));
            b11.setBackgroundResource(R.drawable.custombuttonbackground);
            b11.setTextColor(Color.parseColor("#000000"));
            String str="update theme set m = 0 where sno =1 ";
            db.execSQL(str);
            rl.setBackgroundColor(Color.parseColor("#e11e4b"));
            rl7.setBackgroundColor(Color.parseColor("#ffffff"));
            tv44.setTextColor(Color.parseColor("#000000"));
            b10.setBackgroundResource(R.drawable.custombuttonbackground);
            b10.setTextColor(Color.parseColor("#000000"));
            if(p2.equals("1"))
                music.setBackgroundResource(R.drawable.musicoff);
            else if(p2.equals("0"))
                music.setBackgroundResource(R.drawable.musicon);
            m=0;
        }
        else if (p.equals("0"))
        {
            theme.setBackgroundResource(R.drawable.darkmoonlight);
            b.setBackgroundResource(R.drawable.darkcustombuttonbackground);
            b.setTextColor(Color.parseColor("#ffffff"));
            String str="update theme set m = 1 where sno =1 ";
            db.execSQL(str);
            rl.setBackgroundColor(Color.parseColor("#000000"));
            rl7.setBackgroundColor(Color.parseColor("#131313"));
            tv44.setTextColor(Color.parseColor("#ffffff"));
            b10.setBackgroundResource(R.drawable.darkcustombuttonbackground);
            b10.setTextColor(Color.parseColor("#ffffff"));
            b11.setBackgroundResource(R.drawable.darkcustombuttonbackground);
            b11.setTextColor(Color.parseColor("#ffffff"));
            if(p2.equals("1"))
                music.setBackgroundResource(R.drawable.darkmusicoff);
            else if(p2.equals("0"))
                music.setBackgroundResource(R.drawable.darkmusicon);
            m=1;
        }
    }

    void changemusic() {
        String m1 = "select *from music";
        Cursor c1 = db.rawQuery(m1, null);
        c1.moveToNext();
        String p = c1.getString(1);
        if (p.equals("0"))
        {
            String str="update music set x = 1 where sno = 1";
            db.execSQL(str);
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            stopService(serv);
            if(m==0)
                music.setBackgroundResource(R.drawable.musicoff);
            else
                music.setBackgroundResource(R.drawable.darkmusicoff);
        }
        else if (p.equals("1"))
        {
            String str="update music set x = 0 where sno = 1";
            db.execSQL(str);
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            startService(serv);
            if(m==0)
                music.setBackgroundResource(R.drawable.musicon);
            else
                music.setBackgroundResource(R.drawable.darkmusicon);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String m1 = "select *from theme";
        Cursor c1 = db.rawQuery(m1, null);
        c1.moveToNext();
        String p = c1.getString(1);
        check=1;
        if (p.equals("1"))
        {
            Intent i = new Intent(MainSettings.this,DarkMainActivity.class);
            startActivity(i);
            finish();
        }
        else if (p.equals("0"))
        {
            Intent i = new Intent(MainSettings.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(check!=1) {
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            stopService(serv);
        }
    }
}
