package com.bat.iron.demosweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    SQLiteDatabase db;
    Integer mine=10,mode=1;

    View v2,v3,v4,v5;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv43;
    LinearLayout li;
    ImageButton im,im2;
    Button play,htp,hs,settings,b6,b7;
    RelativeLayout r1,r2;
    int tmp=0,check=0;

    @Override
    public void onBackPressed() {
        if(r1.getVisibility()==View.VISIBLE) {
            exitmethod();
        }
        else {
            final Animation au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
            final Animation ad = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
            r1.setVisibility(View.VISIBLE);
            r2.startAnimation(ad);
            r1.startAnimation(au);
            Handler mHandler=new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    r2.setVisibility(View.GONE);
                    b6.setVisibility(View.GONE);
                    b7.setVisibility(View.GONE);
                }
            }, 300);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

        setContentView(R.layout.activity_main);

        final Intent serv=new Intent(getBaseContext(),MyService.class);

        v2=(View)findViewById(R.id.view2);
        v3=(View)findViewById(R.id.view3);
        v4=(View)findViewById(R.id.view4);
        v5=(View)findViewById(R.id.view5);
        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView5);
        tv6=(TextView)findViewById(R.id.textView6);
        li=(LinearLayout)findViewById(R.id.linear);
        im=(ImageButton)findViewById(R.id.imageButton);
        im2=(ImageButton)findViewById(R.id.imageButton2);
        htp=(Button)findViewById(R.id.htpbutton);
        play=(Button)findViewById(R.id.playbutton);
        hs=(Button)findViewById(R.id.hsbutton);
        settings=(Button)findViewById(R.id.settingsbutton);
        tv43=(TextView)findViewById(R.id.textView43);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);
        r1=(RelativeLayout)findViewById(R.id.rlb);
        r2=(RelativeLayout)findViewById(R.id.rlb2);

        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);

        String m1 = "select *from music";
        Cursor c1 = db.rawQuery(m1, null);
        c1.moveToNext();
        String p = c1.getString(1);
        if (p.equals("0") && tmp==0)
        {
            startService(serv);
            tmp=1;
        }
        check=0;

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        tv1.setTypeface(ourfont);
        tv2.setTypeface(ourfont2);
        tv3.setTypeface(ourfont);
        tv4.setTypeface(ourfont);
        tv5.setTypeface(ourfont);
        tv6.setTypeface(ourfont);
        tv43.setTypeface(ourfont);
        b6.setTypeface(ourfont);
        b7.setTypeface(ourfont);
        play.setTypeface(ourfont);
        final String modes[]={"easy","medium","hard","insane"};

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 2) {
                    tv2.setText("CLASSIC");
                    im2.setVisibility(View.VISIBLE);
                    im.setVisibility(View.INVISIBLE);
                    mode = 1;
                    check=0;
                } else if (mode == 3) {
                    tv2.setText("SURVIVAL");
                    im.setVisibility(View.VISIBLE);
                    im2.setVisibility(View.VISIBLE);
                    mode = 2;
                    li.setBackgroundColor(Color.parseColor("#2196F3"));
                    r1.setBackgroundColor(Color.parseColor("#2196F3"));
                    tv3.setAlpha(1);
                    tv4.setAlpha(0.5f);
                    tv5.setAlpha(0.5f);
                    tv6.setAlpha(0.5f);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    v5.setVisibility(View.INVISIBLE);
                    mine = 10;
                    play.setText("PLAY");
                    check=0;
                }
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1) {
                    tv2.setText("SURVIVAL");
                    im.setVisibility(View.VISIBLE);
                    im2.setVisibility(View.VISIBLE);
                    mode = 2;
                    check=0;
                } else if (mode == 2) {
                    im2.setVisibility(View.INVISIBLE);
                    tv2.setText("CUSTOM");
                    mode = 3;
                    li.setBackgroundColor(Color.parseColor("#7c4dff"));
                    r1.setBackgroundColor(Color.parseColor("#7c4dff"));
                    tv3.setAlpha(0.3f);
                    tv4.setAlpha(0.3f);
                    tv5.setAlpha(0.3f);
                    tv6.setAlpha(0.3f);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    v5.setVisibility(View.INVISIBLE);
                    play.setText("NEXT");
                    check=1;
                }
            }
        });

        htp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
                Intent i = new Intent(MainActivity.this, HowToPlay.class);
                startActivity(i);

            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 3) {
                    li.setBackgroundColor(Color.parseColor("#2196F3"));
                    r1.setBackgroundColor(Color.parseColor("#2196F3"));
                    tv3.setAlpha(1);
                    tv4.setAlpha(0.5f);
                    tv5.setAlpha(0.5f);
                    tv6.setAlpha(0.5f);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    v5.setVisibility(View.INVISIBLE);
                    mine = 10;
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 3) {
                    li.setBackgroundColor(Color.parseColor("#00BFA5"));
                    r1.setBackgroundColor(Color.parseColor("#00BFA5"));
                    tv3.setAlpha(0.5f);
                    tv4.setAlpha(1);
                    tv5.setAlpha(0.5f);
                    tv6.setAlpha(0.5f);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    v5.setVisibility(View.INVISIBLE);
                    mine = 16;
                }
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 3) {
                    li.setBackgroundColor(Color.parseColor("#FF8F00"));
                    r1.setBackgroundColor(Color.parseColor("#FF8F00"));
                    tv3.setAlpha(0.5f);
                    tv4.setAlpha(0.5f);
                    tv5.setAlpha(1);
                    tv6.setAlpha(0.5f);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.VISIBLE);
                    v5.setVisibility(View.INVISIBLE);
                    mine = 23;
                }
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 3) {
                    li.setBackgroundColor(Color.parseColor("#F44336"));
                    r1.setBackgroundColor(Color.parseColor("#F44336"));
                    tv3.setAlpha(0.5f);
                    tv4.setAlpha(0.5f);
                    tv5.setAlpha(0.5f);
                    tv6.setAlpha(1);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    v5.setVisibility(View.VISIBLE);
                    mine = 38;
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mode==1) {
                    stopService(serv);
                    Intent i = new Intent(MainActivity.this, Game.class);
                    String str = mine.toString();
                    i.putExtra("Mi", str);
                    startActivity(i);
                }
                else if(mode==2)
                {
                    stopService(serv);
                    Intent i = new Intent(MainActivity.this, SurvivalMode.class);
                    String str = mine.toString();
                    i.putExtra("Mi", str);
                    startActivity(i);
                }
                else if (mode==3)
                {
                    check=1;
                    Intent i = new Intent(MainActivity.this, CustomSetValues.class);
                    startActivity(i);
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
                Intent i=new Intent(MainActivity.this,MainSettings.class);
                startActivity(i);
            }
        });

        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="";
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
                check=1;
                Intent i=new Intent(MainActivity.this,HighScore.class);
                startActivity(i);
            }
        });

        /*settings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int loc[]=new int[2];
                settings.getLocationOnScreen(loc);
                Toast t=Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP|Gravity.LEFT,settings.getRight()-25,loc[1]-10);
                t.show();
                return true;
            }
        });*/
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
        if (p.equals("0") && tmp==1) {
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            startService(serv);
        }
    }

    public void exitmethod()
    {
        final Animation au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        final Animation ad = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
        r2.setVisibility(View.VISIBLE);
        b6.setVisibility(View.VISIBLE);
        b7.setVisibility(View.VISIBLE);
        r1.startAnimation(ad);
        r2.startAnimation(au);
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                r1.setVisibility(View.GONE);
            }
        }, 300);

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                final Intent serv=new Intent(getBaseContext(),MyService.class);
                stopService(serv);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.VISIBLE);
                r2.startAnimation(ad);
                r1.startAnimation(au);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        r2.setVisibility(View.GONE);
                        b6.setVisibility(View.GONE);
                        b7.setVisibility(View.GONE);
                    }
                }, 300);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (check!=1) {
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            stopService(serv);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}