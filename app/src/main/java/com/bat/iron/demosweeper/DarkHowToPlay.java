package com.bat.iron.demosweeper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DarkHowToPlay extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnPrev, btnNext;
    int check=0;
    SQLiteDatabase db;

    //TextView tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnPrev = (Button) findViewById(R.id.btn_prev);
        btnNext = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.darkhtp1,
                R.layout.darkhtp3,
                R.layout.darkhtp5,
                R.layout.darkhtp4};

        addBottomDots(0);

       /* tv10=(TextView)findViewById(R.id.textView10);
        tv11=(TextView)findViewById(R.id.textView11);
        tv12=(TextView)findViewById(R.id.textView12);
        tv13=(TextView)findViewById(R.id.textView13);
        tv14=(TextView)findViewById(R.id.textView14);
        tv15=(TextView)findViewById(R.id.textView15);
        tv16=(TextView)findViewById(R.id.textView16);
        tv17=(TextView)findViewById(R.id.textView17);
        tv18=(TextView)findViewById(R.id.textView18);
        tv19=(TextView)findViewById(R.id.textView19);*/

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);


       /* Typeface ourfont = Typeface.createFromAsset(getAssets(), "moonlight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "moonbold.otf");
        tv10.setTypeface(ourfont2);
        tv11.setTypeface(ourfont);
        tv12.setTypeface(ourfont2);
        tv13.setTypeface(ourfont);
        tv14.setTypeface(ourfont2);
        tv15.setTypeface(ourfont);
        tv16.setTypeface(ourfont2);
        tv17.setTypeface(ourfont);
        tv18.setTypeface(ourfont2);
        tv19.setTypeface(ourfont);*/

        check=0;
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(-1);
                check=1;
                if (current < layouts.length && current >= 0) {
                    viewPager.setCurrentItem(current);
                } else if (current==-1){
                    finish();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    onBackPressed();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        /*int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);*/

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#2d2d2d"));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#131313"));
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("GOT IT");
                btnPrev.setText("PREVIOUS");
            }
            else if (position == 0){
                btnPrev.setText("GO BACK");
            }
            else {
                // still pages are left
                btnNext.setText("NEXT");
                btnPrev.setText("PREVIOUS");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    @Override
    protected void onPause() {
        super.onPause();
        if(check!=1) {
            final Intent serv = new Intent(getBaseContext(), MyService.class);
            stopService(serv);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        check=1;
        Intent i = new Intent(DarkHowToPlay.this,DarkMainActivity.class);
        startActivity(i);
    }
}

