package com.bat.iron.demosweeper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

public class
CustomSetValues extends AppCompatActivity {

    NumberPicker n,n3,n2;
    Button play,back;
    Integer rows=4,cols=4,mines=4,maxmines=6;
    TextView tv47;
    int check=0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);
        setContentView(R.layout.activity_custom_set_values);

        play=(Button)findViewById(R.id.button8);
        back=(Button)findViewById(R.id.button9);
        n=(NumberPicker)findViewById(R.id.numberPicker);
        n2=(NumberPicker)findViewById(R.id.numberPicker2);
        n3=(NumberPicker)findViewById(R.id.numberPicker3);
        tv47=(TextView)findViewById(R.id.textView47);
        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);

        Typeface ourfont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        Typeface ourfont2 = Typeface.createFromAsset(getAssets(), "primebold.otf");
        tv47.setTypeface(ourfont2);
        play.setTypeface(ourfont);
        check=0;

        n.setMinValue(4);
        n2.setMinValue(4);
        n3.setMinValue(4);
        n.setMaxValue(12);
        n2.setMaxValue(8);
        n3.setMaxValue(maxmines);

        setDividerColor(n, Color.WHITE);
        setDividerColor(n2,Color.WHITE);
        setDividerColor(n3,Color.WHITE);

        setNumberPickerTextColor(null, n, Color.WHITE);
        setNumberPickerTextColor(null,n2, Color.WHITE);
        setNumberPickerTextColor(null,n3, Color.WHITE);
        n.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rows = newVal;
                maxmines = ((rows*cols)/2) - 2;
                n3.setMaxValue(maxmines);
            }
        });

        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                cols=newVal;
                maxmines=((rows*cols)/2)-2;
                n3.setMaxValue(maxmines);
            }
        });

        n3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mines=newVal;
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomSetValues.this,CustomMode.class);
                String sr = rows.toString();
                String sc = cols.toString();
                String sm = mines.toString();
                i.putExtra("Mi",sm);
                i.putExtra("R",sr);
                i.putExtra("C",sc);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    public static void setNumberPickerTextColor(Context context,NumberPicker numberPicker, int color){
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    numberPicker.invalidate();
                    ((EditText)child).setTextColor(color);



                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
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
        Intent i = new Intent(CustomSetValues.this,MainActivity.class);
        startActivity(i);
    }
}
