package com.bat.iron.demosweeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DarkGame extends AppCompatActivity {

    int count=0;
    int flagstop=0;

    /*String s=getIntent().getStringExtra("Mi");*/
    int totalmines;
    int flagcount;

    Button[][] button = new Button[12][8];
    Button pause,b,b5;
    TextView txtMineCount,txtTimer,txt,tv37,tv38,tv39,tv40,tv41,tv42;
    ImageButton imageButton;
    RelativeLayout rl;
    TableLayout milf;

    public double sp=0;
    Handler timer=new Handler();
    boolean isTimerStarted=false, ispauseClickable=false;
    boolean pauseClicked=false, isgamefinished=false;

    int [][]mirror=new int[12][8];
    /*
    0 - is clickable
    1 - not clickable
    2 - has mine
     */
    int [][]matrix=new int[12][8];
    /*
    1 - is flagged
    2 - flagged and mined
   */
    int [][]dummy=new int[12][8];
    /*
    for any other number greater than 0, the number of mines around the button
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

        setContentView(R.layout.activity_dark_game);
        txtMineCount=(TextView)findViewById(R.id.MineCount);
        txtTimer=(TextView)findViewById(R.id.Timer);
        imageButton=(ImageButton)findViewById(R.id.imageButton4);
        rl=(RelativeLayout)findViewById(R.id.relativeLayout);
        pause=(Button)findViewById(R.id.button2);
        txt=(TextView)findViewById(R.id.textView7);
        tv37=(TextView)findViewById(R.id.textView37);
        tv38=(TextView)findViewById(R.id.textView38);
        tv39=(TextView)findViewById(R.id.textView39);
        tv40=(TextView)findViewById(R.id.textView40);
        tv41=(TextView)findViewById(R.id.textView41);
        tv42=(TextView)findViewById(R.id.textView42);
        b5 = (Button) findViewById(R.id.button5);
        b = (Button) findViewById(R.id.button);

        String s=getIntent().getStringExtra("Mi");
        totalmines=Integer.parseInt(s);
        flagcount=totalmines;

        txtMineCount.setText(""+flagcount);

        Typeface lcdFont = Typeface.createFromAsset(getAssets(), "primelight.otf");
        txtMineCount.setTypeface(lcdFont);
        txtTimer.setTypeface(lcdFont);
        txt.setTypeface(lcdFont);
        tv42.setTypeface(lcdFont);
        b5.setTypeface(lcdFont);
        b.setTypeface(lcdFont);
        tv37.setTypeface(lcdFont);
        tv38.setTypeface(lcdFont);
        tv39.setTypeface(lcdFont);
        tv40.setTypeface(lcdFont);
        tv41.setTypeface(lcdFont);
        rl.setBackgroundResource(R.drawable.darkmainbackeasy);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonID = "i" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                button[i][j] = ((Button) findViewById(resID));
                mirror[i][j]=0;
                matrix[i][j]=0;
            }
        }

        for (int i=0;i<12;i++) {
            for (int j=0;j<8;j++) {
                dummy[i][j]=0;
            }
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshActiviy();
                imageButton.setBackgroundResource(R.drawable.smiley);
            }
        });
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {

                final int curr=i;
                final int curc=j;


                button[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mirror[curr][curc] != 1) {
                            if (sp == 0.0) {
                                // Toast.makeText(MainActivity.this, curr+""+curc, Toast.LENGTH_SHORT).show();
                                startTimer();
                                txt.setText("");

                                setMines(curr, curc);
                                setNumbers(curr, curc);

                                showBox(curr, curc);
                                ispauseClickable = true;
                                pause.setAlpha(1);

                                if (mirror[curr][curc] != 2) mirror[curr][curc] = 1;
                            }
                            if (mirror[curr][curc] == 0)
                                showBox(curr, curc);
                            if (mirror[curr][curc] != 2) mirror[curr][curc] = 1;
                            if (mirror[curr][curc] == 2)
                                endGame();

                        }
                    }
                });

                setFlag(curr, curc);
                pausegame();
            }
        }

    }

    public void pausegame()
    {
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isgamefinished==false)
                    pausemethod();
                else
                    aftergame();
            }
        });
    }

    public void wingame() {

        int k = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j] == 2) {

                    //Toast.makeText(getApplicationContext(), "loop"+k, Toast.LENGTH_SHORT).show();
                    k++;
                }
            }
        }
        if (k == totalmines) {
            String str=String.format("%.1f",sp);
            //txtTimer.setText("Score:" + str);
            //txtMineCount.setText("Game Won");
            imageButton.setBackgroundResource(R.drawable.swag);
            txt.setText("YOU WON IN " + txtTimer.getText() + " seconds");
            isgamefinished=true;
            //Toast.makeText(getApplicationContext(), "game won", Toast.LENGTH_LONG).show();
            stopTimer();
            flagstop = 1;
            double pp=Double.parseDouble(str);
            calculatehighscore(pp);
            ispauseClickable=false;
            pauseClicked=false;
            pause.setAlpha(0.5f);
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 8; j++) {
                    mirror[i][j]=1;
                }
            }
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv37.setText("VIEW BOARD");
                    ispauseClickable=true;
                    pauseClicked=false;
                    pause.setAlpha(1);
                    aftergame();
                }
            },1500);
        }

    }

    void calculatehighscore(double gs )
    {
        SQLiteDatabase db;
        db=openOrCreateDatabase("highscore",MODE_PRIVATE,null);
        String mode="easy";
        if(totalmines==10)
        {
            mode="easy";
        }
        else if(totalmines==16)
        {
            mode="medium";
        }
        else if(totalmines==23)
        {
            mode="hard";
        }
        else if(totalmines==30)
        {
            mode="insane";
        }
        String s="select *from "+mode+"";
        Cursor c=db.rawQuery(s,null);
        int i=0;
        double ar[]=new double[4];
        while (c.moveToNext())
        {
            String p=c.getString(1);
            if(p.equals("- - - -"))
            {
                ar[i]=99999;
            }
            else {
                ar[i] = Double.parseDouble(p);
            }
            i++;
        }
        ar[3]=gs;

        double temp;
        i=0;
        while (i < 4)
        {
            for (int j = 0; j < 4; j++)
            {
                if(ar[i] < ar[j])
                {
                    temp = ar[i];
                    ar[i] = ar[j];
                    ar[j] = temp;
                }
            }
            i++;
        }
        String scr[]={"- - - -","- - - -","- - - -"};
        for(i=0;i<3;i++)
        {
            if(ar[i]!=99999)
            {
                scr[i]=""+ar[i]+"";
            }

        }
        String o="update "+mode+" set sc = '"+scr[0]+"' where sno = '"+1+"'";
        String t="update "+mode+" set sc = '"+scr[1]+"' where sno = '"+2+"'";
        String th="update "+mode+" set sc = '"+scr[2]+"' where sno = '"+3+"'";
        db.execSQL(o);
        db.execSQL(t);
        db.execSQL(th);
    }

    public void setFlag(int i,int j)
    {
        final int curr = i;
        final int curc = j;


        button[curr][curc].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (flagstop != 1) {
                    if ((mirror[curr][curc] == 0) || (mirror[curr][curc] == 2)) {
                        button[curr][curc].setBackgroundResource(R.drawable.darkflag);
                        matrix[curr][curc] = 1;
                        if (mirror[curr][curc] == 2) matrix[curr][curc] = 2;
                        mirror[curr][curc] = 1;

                        flagcount--;

                    } else if (matrix[curr][curc] == 1 || matrix[curr][curc] == 2) {
                        button[curr][curc].setBackgroundResource(R.drawable.darkbackeasy);
                        if (mirror[curr][curc] != 2) mirror[curr][curc] = 0;
                        if (matrix[curr][curc] == 2)
                            mirror[curr][curc] = 2;
                        matrix[curr][curc] = 0;
                        flagcount++;
                    }
                    if (flagcount == -1) {
                        Toast.makeText(getApplicationContext(), "Maximum number of flags added, no more flags", Toast.LENGTH_LONG).show();
                        button[curr][curc].setBackgroundResource(R.drawable.darkbackeasy);
                        if (mirror[curr][curc] != 2) mirror[curr][curc] = 0;
                        matrix[curr][curc] = 0;
                        flagcount++;
                    }

                    txtMineCount.setText("" + flagcount);


                }
                if (flagcount == 0) {
                    wingame();
                }
                return true;
            }
        });



    }


    public void setMines(int currentRow,int currentColumn) {
        for (int i = 0; i < totalmines; i++) {
            Random random=new Random();
            int k,l;
            k=random.nextInt(12);
            l=random.nextInt(8);
            while((k==currentRow && l==currentColumn) || mirror[k][l]==2) {
                k = random.nextInt(12);
                l = random.nextInt(8);
            }
            mirror[k][l]=2;
        }


    }
    public void endGame() {

        for (int currentRow = 0; currentRow < 12; currentRow++) {
            for (int currentColumn = 0; currentColumn < 8; currentColumn++) {

                if (mirror[currentRow][currentColumn] == 2 && matrix[currentRow][currentColumn] != 2) {
                    final int curr = currentRow;
                    final int curc = currentColumn;
                    mirror[curr][curc] = 1;
                    button[curr][curc].setBackgroundResource(R.drawable.darkmine);
                }
                if(mirror[currentRow][currentColumn]==0){
                    button[currentRow][currentColumn].setBackgroundResource(R.drawable.darkendgamebackground);
                    endcolor(currentRow,currentColumn);
                }
                if(matrix[currentRow][currentColumn]==1 && mirror[currentRow][currentColumn]!=2)
                {
                    button[currentRow][currentColumn].setBackgroundResource(R.drawable.darkwrongflag);
                }
                mirror[currentRow][currentColumn]=1;
            }
            imageButton.setBackgroundResource(R.drawable.sad);
            flagstop=1;

        }
        stopTimer();
        txt.setText("YOU LOST. BETTER LUCK NEXT TIME");
        isgamefinished=true;
        isTimerStarted=false;
        ispauseClickable=false;
        pauseClicked=false;
        pause.setAlpha(0.5f);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv37.setText("VIEW BOARD");
                ispauseClickable=true;
                pauseClicked=false;
                pause.setAlpha(1);
                aftergame();
            }
        },1500);
    }

    public void refreshActiviy()
    {
        Intent i=new Intent(DarkGame.this,DarkGame.class);
        recreate();
    }

    public void setNumbers(int curr, int curc) {
        for (int i=0;i<12;i++) {
            for (int j=0;j<8;j++) {

                if(mirror[i][j]==2)
                {
                    if(i==0 && j==0)
                    {
                        dummy[i][j+1]++;
                        dummy[i+1][j]++;
                        dummy[i+1][j+1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 1", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==0 &&j!=7)
                    {
                        dummy[i][j-1]++;
                        dummy[i][j+1]++;
                        dummy[i+1][j-1]++;
                        dummy[i+1][j]++;
                        dummy[i+1][j+1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 2", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==0 && j==7)
                    {
                        dummy[i][j-1]++;
                        dummy[i+1][j-1]++;
                        dummy[i+1][j]++;
                        //Toast.makeText(getApplicationContext(), "Condition 3", Toast.LENGTH_SHORT).show();
                    }
                    else if(j==7 && i!=11)
                    {
                        dummy[i-1][j-1]++;
                        dummy[i-1][j]++;
                        dummy[i][j-1]++;
                        dummy[i+1][j-1]++;
                        dummy[i+1][j]++;
                        //Toast.makeText(getApplicationContext(), "Condition 4", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==11 && j==7)
                    {
                        dummy[i-1][j-1]++;
                        dummy[i-1][j]++;
                        dummy[i][j-1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 5", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==11 && j!=0)
                    {
                        dummy[i-1][j-1]++;
                        dummy[i-1][j]++;
                        dummy[i-1][j+1]++;
                        dummy[i][j-1]++;
                        dummy[i][j+1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 6", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==11 && j==0)
                    {
                        dummy[i-1][j]++;
                        dummy[i-1][j+1]++;
                        dummy[i][j+1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 7", Toast.LENGTH_SHORT).show();
                    }
                    else if(j==0 && i!=0)
                    {
                        dummy[i-1][j]++;
                        dummy[i-1][j+1]++;
                        dummy[i][j+1]++;
                        dummy[i+1][j]++;
                        dummy[i+1][j+1]++;
                        //Toast.makeText(getApplicationContext(), "Condition 8", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dummy[i-1][j-1]++;
                        dummy[i-1][j]++;
                        dummy[i-1][j+1]++;
                        dummy[i][j-1]++;
                        dummy[i][j+1]++;
                        dummy[i+1][j-1]++;
                        dummy[i+1][j]++;
                        dummy[i+1][j+1]++;

                        //Toast.makeText(getApplicationContext(), "Condition 8"+(x++), Toast.LENGTH_SHORT).show();
                    }
                }
                if (dummy[curr][curc]!=0 && mirror[curr][curc]==0)
                {
                    switch (dummy[curr][curc])

                    {

                        case 1:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("1");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.CYAN);
                            break;

                        case 2:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("2");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(0, 204, 102));
                            break;

                        case 3:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("3");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.RED);
                            break;

                        case 4:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("4");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(145, 86, 198));
                            break;

                        case 5:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("5");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(139, 28, 98));
                            break;

                        case 6:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("6");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(255, 188, 0));
                            break;

                        case 7:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("7");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(47, 79, 79));
                            break;

                        case 8:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("8");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(188, 188, 188));
                            break;

                        case 9:
                            button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                            button[curr][curc].setText("9");
                            button[curr][curc].setTextSize(28);
                            button[curr][curc].setTextColor(Color.rgb(204, 102, 0));
                            break;
                    }
                }
            }
        }
        for (int i=0;i<12;i++) {
            for (int j=0;j<8;j++) {
                if(mirror[i][j]!=2)
                {
                    if (dummy[i][j]!=0)
                    {

                        final int M=i;
                        final int N=j;
                        button[M][N].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (matrix[M][N] != 1 && matrix[M][N]!=2 && mirror[M][N]==0)
                                {
                                    mirror[M][N] = 1;
                                    switch (dummy[M][N])

                                    {

                                        case 1:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("1");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.CYAN);
                                            break;

                                        case 2:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("2");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(0, 204, 102));
                                            break;

                                        case 3:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("3");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.RED);
                                            break;

                                        case 4:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("4");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(145, 86, 198));
                                            break;

                                        case 5:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("5");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(139, 28, 98));
                                            break;

                                        case 6:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("6");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(255, 188, 0));
                                            break;

                                        case 7:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("7");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(47, 79, 79));
                                            break;

                                        case 8:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("8");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(188, 188, 188));
                                            break;

                                        case 9:
                                            button[M][N].setBackgroundResource(R.drawable.darkopenbackground);
                                            button[M][N].setText("9");
                                            button[M][N].setTextSize(28);
                                            button[M][N].setTextColor(Color.rgb(204, 102, 0));
                                            break;
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }

    }


    public void showBox(int curr,int curc) {
        final int i = curr;
        final int j = curc;
        if (mirror[i][j] != 2 && dummy[i][j] == 0 ) {

            //Toast.makeText(getApplicationContext(), "hgfjh", Toast.LENGTH_SHORT).show();
            mirror[i][j] = 1;
            count++;
            button[i][j].setBackgroundResource(R.drawable.darkopenbackground);

            if (i == 0 && j == 0) {
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }
                if (mirror[i + 1][j + 1] == 0) {
                    showBox(i + 1, j + 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 1", Toast.LENGTH_SHORT).show();
            } else if (i == 0 && j != 7) {
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }
                if (mirror[i + 1][j - 1] == 0) {
                    showBox(i + 1, j - 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }
                if (mirror[i + 1][j + 1] == 0) {
                    showBox(i + 1, j + 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 2", Toast.LENGTH_SHORT).show();
            } else if (i == 0 && j == 7) {
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }
                if (mirror[i + 1][j - 1] == 0) {
                    showBox(i + 1, j - 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }

                //Toast.makeText(getApplicationContext(), "Condition 3", Toast.LENGTH_SHORT).show();
            } else if (j == 7 && i != 11) {
                if (mirror[i - 1][j - 1] == 0) {
                    showBox(i - 1, j - 1);
                }
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }
                if (mirror[i + 1][j - 1] == 0) {
                    showBox(i + 1, j - 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }

                //Toast.makeText(getApplicationContext(), "Condition 4", Toast.LENGTH_SHORT).show();
            } else if (i == 11 && j == 7) {
                if (mirror[i - 1][j - 1] == 0) {
                    showBox(i - 1, j - 1);
                }
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 5", Toast.LENGTH_SHORT).show();
            } else if (i == 11 && j != 0) {
                if (mirror[i - 1][j - 1] == 0) {
                    showBox(i - 1, j - 1);
                }
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i - 1][j + 1] == 0) {
                    showBox(i - 1, j + 1);
                }
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 6", Toast.LENGTH_SHORT).show();
            } else if (i == 11 && j == 0) {
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i - 1][j + 1] == 0) {
                    showBox(i - 1, j + 1);
                }
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 7", Toast.LENGTH_SHORT).show();
            } else if (j == 0 && i != 0) {
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i - 1][j + 1] == 0) {
                    showBox(i - 1, j + 1);
                }
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }
                if (mirror[i + 1][j + 1] == 0) {
                    showBox(i + 1, j + 1);
                }

                //Toast.makeText(getApplicationContext(), "Condition 8", Toast.LENGTH_SHORT).show();
            } else {
                if (mirror[i - 1][j - 1] == 0) {
                    showBox(i - 1, j - 1);
                }
                if (mirror[i - 1][j] == 0) {
                    showBox(i - 1, j);
                }
                if (mirror[i - 1][j + 1] == 0) {
                    showBox(i - 1, j + 1);
                }
                if (mirror[i][j - 1] == 0) {
                    showBox(i, j - 1);
                }
                if (mirror[i][j + 1] == 0) {
                    showBox(i, j + 1);
                }
                if (mirror[i + 1][j - 1] == 0) {
                    showBox(i + 1, j - 1);
                }
                if (mirror[i + 1][j] == 0) {
                    showBox(i + 1, j);
                }
                if (mirror[i + 1][j + 1] == 0) {
                    showBox(i + 1, j + 1);
                }


                //Toast.makeText(getApplicationContext(), "Condition 9"+(x++), Toast.LENGTH_SHORT).show();
            }
        }

        else if (dummy[curr][curc]!=0)
        {
            color(curr,curc);
            if (mirror[curr][curc]!=2)  mirror[curr][curc] = 1;
        }


    }

    public void color(int i, int j) {
        int curr=i;
        int curc=j;
        switch (dummy[curr][curc])

        {

            case 1:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("1");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.CYAN);
                break;

            case 2:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("2");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(0, 204, 102));
                break;

            case 3:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("3");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.RED);
                break;

            case 4:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("4");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(145, 86, 198));
                break;

            case 5:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("5");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(139, 28, 98));
                break;

            case 6:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("6");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(255, 188, 0));
                break;

            case 7:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("7");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(47, 79, 79));
                break;

            case 8:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("8");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(188, 188, 188));
                break;

            case 9:
                button[curr][curc].setBackgroundResource(R.drawable.darkopenbackground);
                button[curr][curc].setText("9");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(204, 102, 0));
                break;
        }
    }


    public void endcolor(int i, int j) {
        int curr=i;
        int curc=j;
        switch (dummy[curr][curc])

        {

            case 1:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("1");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.CYAN);
                break;

            case 2:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("2");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(0, 204, 102));
                break;

            case 3:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("3");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.RED);
                break;

            case 4:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("4");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(145, 86, 198));
                break;

            case 5:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("5");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(139, 28, 98));
                break;

            case 6:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("6");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(255, 188, 0));
                break;

            case 7:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("7");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(47, 79, 79));
                break;

            case 8:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("8");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(188, 188, 188));
                break;

            case 9:
                button[curr][curc].setBackgroundResource(R.drawable.darkendgamebackground);
                button[curr][curc].setText("9");
                button[curr][curc].setTextSize(28);
                button[curr][curc].setTextColor(Color.rgb(204, 102, 0));
                break;
        }
    }

    public Runnable ute=new Runnable() {
        @Override
        public void run() {
            long currentMilliseconds = System.currentTimeMillis();
            sp=sp+0.1;
            String str=String.format("%.1f",sp);
            txtTimer.setText(str);
            timer.postAtTime(this, currentMilliseconds);
            timer.postDelayed(ute, 100);
        }
    };

    public void startTimer() {
        if (isTimerStarted==false)
        {
            timer.removeCallbacks(ute);
            timer.postDelayed(ute, 100);
            isTimerStarted=true;
            pauseClicked=false;
            ispauseClickable=true;
            //Toast.makeText(getApplicationContext(), "start timer called", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopTimer() {
        timer.removeCallbacks(ute);
        isTimerStarted=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View dv=getWindow().getDecorView();
        int ui=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        dv.setSystemUiVisibility(ui);

    }

    @Override
    public void onBackPressed() {
        if (ispauseClickable == true && pauseClicked == false && isgamefinished==false) {
            stopTimer();
            pauseClicked=true;
            pausemethod();
        }
        else if(ispauseClickable == false && pauseClicked == true && isgamefinished==false){
            resumemethod();
        }
        else if(ispauseClickable == false && pauseClicked == true && isgamefinished==true){
            viewboard();
        }
        else if(ispauseClickable == true && pauseClicked == false && isgamefinished==true){
            aftergame();
            pauseClicked=true;
        }
        else {
            finish();
        }
    }

    public void jumpdialog()
    {
        txt.setVisibility(View.GONE);
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        tv42.setVisibility(View.VISIBLE);
        tv42.setText("Are you sure you want to exit to Main Menu? All the unsaved progress will be lost.");
        b5.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        tv42.startAnimation(a);
        b5.startAnimation(a);
        b.startAnimation(a);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DarkGame.this, DarkMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
                tv42.startAnimation(a2);
                b5.startAnimation(a2);
                b.startAnimation(a2);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        tv42.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        b.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                    }
                }, 300);
            }
        });
    }

    public void exitdialog()
    {
        txt.setVisibility(View.GONE);
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        tv42.setVisibility(View.VISIBLE);
        tv42.setText("Are you sure you want to exit the Game? All the unsaved progress will be lost.");
        b5.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        tv42.startAnimation(a);
        b5.startAnimation(a);
        b.startAnimation(a);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                finishAffinity();
                //System.exit(1);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
                tv42.startAnimation(a2);
                b5.startAnimation(a2);
                b.startAnimation(a2);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        tv42.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        b.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                    }
                }, 300);
            }
        });
    }

    public void resumemethod()
    {
        pauseClicked=false;
        ispauseClickable=true;
        if (tv42.getVisibility() == View.VISIBLE && b5.getVisibility() == View.VISIBLE && b.getVisibility() == View.VISIBLE) {
            Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
            tv42.startAnimation(a2);
            b5.startAnimation(a2);
            b.startAnimation(a2);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    tv42.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                }
            }, 300);
        }

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j].setVisibility(View.VISIBLE);
            }
        }
        Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        milf.startAnimation(a2);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {

                startTimer();
            }
        }, 300);
        tv37.setVisibility(View.GONE);
        tv38.setVisibility(View.GONE);
        tv39.setVisibility(View.GONE);
        tv40.setVisibility(View.GONE);
        tv41.setVisibility(View.GONE);

    }

    public void pausemethod()
    {
        if (ispauseClickable == true) {
            stopTimer();
            pauseClicked=true;
            ispauseClickable=false;

            milf=(TableLayout)findViewById(R.id.MineField);
            Animation a= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movedown);
            Animation a1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeingame);
            milf.startAnimation(a);
            tv37.setVisibility(View.INVISIBLE);
            tv38.setVisibility(View.INVISIBLE);
            tv39.setVisibility(View.INVISIBLE);
            tv40.setVisibility(View.INVISIBLE);
            tv41.setVisibility(View.INVISIBLE);
            Handler mHandler=new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 12; i++) {
                        for (int j = 0; j < 8; j++) {

                            button[i][j].setVisibility(View.GONE);

                        }
                    }
                    tv37.setVisibility(View.VISIBLE);
                    tv38.setVisibility(View.VISIBLE);
                    tv39.setVisibility(View.VISIBLE);
                    tv40.setVisibility(View.VISIBLE);
                    tv41.setVisibility(View.VISIBLE);
                }
            }, 300);

            tv37.startAnimation(a1);
            tv38.startAnimation(a1);
            tv39.startAnimation(a1);
            tv40.startAnimation(a1);
            tv41.startAnimation(a1);

            tv37.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resumemethod();
                }
            });

            tv38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DarkGame.this, InGameDarkHighScore.class);
                    startActivity(i);
                }
            });

            tv39.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DarkGame.this, InGameDarkHowToPlay.class);
                    startActivity(i);
                }
            });

            tv40.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpdialog();
                }
            });

            tv41.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitdialog();
                }
            });

        }

    }

    void viewboard()
    {
        pauseClicked=false;
        ispauseClickable=true;
        if (tv42.getVisibility() == View.VISIBLE && b5.getVisibility() == View.VISIBLE && b.getVisibility() == View.VISIBLE) {
            Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
            tv42.startAnimation(a2);
            b5.startAnimation(a2);
            b.startAnimation(a2);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    tv42.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                }
            }, 300);
        }

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                button[i][j].setVisibility(View.VISIBLE);
            }
        }
        Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        milf.startAnimation(a2);

        tv37.setVisibility(View.GONE);
        tv38.setVisibility(View.GONE);
        tv39.setVisibility(View.GONE);
        tv40.setVisibility(View.GONE);
        tv41.setVisibility(View.GONE);
    }

    void aftergame()
    {
        stopTimer();
        pauseClicked=true;
        ispauseClickable=false;
        txt.setVisibility(View.VISIBLE);

        milf=(TableLayout)findViewById(R.id.MineField);
        Animation a= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movedown);
        Animation a1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeingame);
        milf.startAnimation(a);
        tv37.setVisibility(View.INVISIBLE);
        tv38.setVisibility(View.INVISIBLE);
        tv39.setVisibility(View.INVISIBLE);
        tv40.setVisibility(View.INVISIBLE);
        tv41.setVisibility(View.INVISIBLE);
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < 12; i++) {
                    for (int j = 0; j < 8; j++) {

                        button[i][j].setVisibility(View.GONE);

                    }
                }
                tv37.setVisibility(View.VISIBLE);
                tv38.setVisibility(View.VISIBLE);
                tv39.setVisibility(View.VISIBLE);
                tv40.setVisibility(View.VISIBLE);
                tv41.setVisibility(View.VISIBLE);
            }
        }, 300);

        tv37.startAnimation(a1);
        tv38.startAnimation(a1);
        tv39.startAnimation(a1);
        tv40.startAnimation(a1);
        tv41.startAnimation(a1);

        tv37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewboard();
            }
        });

        tv38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DarkGame.this, InGameHighScore.class);
                startActivity(i);
            }
        });

        tv39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DarkGame.this, InGameHowToPlay.class);
                startActivity(i);
            }
        });

        tv40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpdialog();
            }
        });

        tv41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitdialog();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isgamefinished==false)
            pausemethod();
        else
            aftergame();
    }
}