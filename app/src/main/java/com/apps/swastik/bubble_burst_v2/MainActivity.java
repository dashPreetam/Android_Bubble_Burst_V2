package com.apps.swastik.bubble_burst_v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> slots;
    int numberOfSticksLeft = 21;
    ImageView winLoose;
    int userPicks=0, computerPicks=0;
    double thought;
    boolean gameActive = false,win;
    SharedPreferences sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedpref = this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        slots = new ArrayList<Integer>() {};
        winLoose = (ImageView) findViewById(R.id.winLoose);
        new CountDownTimer(5600, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                switch ((int)millisUntilFinished/1000){
                    case 1:winLoose.setImageResource(R.drawable.si1);break;
                    case 2:winLoose.setImageResource(R.drawable.si2);break;
                    case 3:winLoose.setImageResource(R.drawable.si3);break;
                    case 4:winLoose.setImageResource(R.drawable.si4);break;
                    case 5:winLoose.setImageResource(R.drawable.si5);break;
                }
            }

            @Override
            public void onFinish() {
                winLoose.setImageResource(R.drawable.started);
                new CountDownTimer(3600, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        winLoose.setAlpha(0f);
                        gameActive = true;
                        if(sharedpref.getString("mode","").equals("computer"))
                            computerPlay();
                    }
                }.start();
            }
        }.start();
    }

    public void done(View view)
    {
        if(userPicks!=0)
        computerPlay();
    }

    public void userPlay(View view) {
        if (gameActive)
        {
            if (userPicks < 3) {
                ImageView iv = (ImageView) view;
                int tag = Integer.parseInt(iv.getTag().toString());
                slots.add(tag);
                {
                    thought = Math.random();
                    if (thought > 0.0 && thought < 0.25)
                        Toast.makeText(this, "Nice Move!!!", Toast.LENGTH_SHORT).show();
                    else if (thought > 0.75 && thought < 1.0)
                        Toast.makeText(this, "Interesting!!", Toast.LENGTH_SHORT).show();
                }
                iv.animate().alpha(0f).setDuration(500);
                userPicks++;
                numberOfSticksLeft--;
                if (numberOfSticksLeft == 1) {
                    winLoose.setAlpha(1f);
                    winLoose.setImageResource(R.drawable.win);
                    gameActive=false;
                    win = true;
                }
            } else {
                Toast.makeText(this, "Not more than 3", Toast.LENGTH_SHORT).show();
                computerPlay();
            }
        }
    }

    public void computerPlay()
    {
        userPicks=0;
        if (numberOfSticksLeft == 21 || numberOfSticksLeft == 17 || numberOfSticksLeft == 13 || numberOfSticksLeft == 9 || numberOfSticksLeft == 5 )
        {
            numberOfSticksLeft = numberOfSticksLeft - 1 ;
            animate();
        }
        else
        {
            if(numberOfSticksLeft>17)
                computerPicks = numberOfSticksLeft - 17;
            else if(numberOfSticksLeft>13)
                computerPicks = numberOfSticksLeft - 13;
            else if(numberOfSticksLeft>9)
                computerPicks = numberOfSticksLeft - 9;
            else if(numberOfSticksLeft>5)
                computerPicks = numberOfSticksLeft - 5;
            else if(numberOfSticksLeft<5)
                computerPicks = numberOfSticksLeft - 1;


            numberOfSticksLeft = numberOfSticksLeft - computerPicks;

            for (int i = 0; i < computerPicks; i++)
            {
                animate();
            }

            if(numberOfSticksLeft == 1){
                winLoose.setAlpha(1f);
                winLoose.setImageResource(R.drawable.loose);
                gameActive=false;
                win = false;
            }
        }
    }

    public void animate(){
        GridLayout gl = (GridLayout) findViewById(R.id.GridLayout);
        int randNo = randomNumber();
        ImageView iv = (ImageView) gl.findViewWithTag(""+randNo+"");
        iv.animate().alpha(0f).setDuration(500);
    }

    public int randomNumber(){
        double x;int z;
        x = Math.random() * 100;
        z = (int) x;
        if (z>=21 || slots.contains(z)){
            return randomNumber();
        }
        slots.add(z);
        return(z);
    }

    public void back(View view){
        if(!win || gameActive){
        String x = ""+(Integer.parseInt(sharedpref.getString("lost",""))+1);
        sharedpref.edit().putString("lost",x).apply();}
        finish();
    }
}