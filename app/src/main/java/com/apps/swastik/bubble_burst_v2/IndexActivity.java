package com.apps.swastik.bubble_burst_v2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class IndexActivity extends AppCompatActivity {

    SharedPreferences sharedpref;
    Button btn;
    TextView tv;
    Switch modeSwitch;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        sharedpref = this.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        sharedpref.edit().putString("lost","0").apply();
    }

    public void startGame(View view) {

        modeSwitch = (Switch)findViewById(R.id.userComputerSwitch);
        if(modeSwitch.isChecked())
            sharedpref.edit().putString("mode","computer").apply();
        else
            sharedpref.edit().putString("mode","user").apply();

        Intent gameIntent=new Intent (getApplicationContext(),MainActivity.class);
        startActivity(gameIntent);
    }

    public void exitGame(View view){

        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getApplicationContext().getSharedPreferences("gameData", 0).edit().clear().commit();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    public void hints(View view) {
        btn = (Button) findViewById(R.id.hintButton);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.modeBtn);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.start);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.exit);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.backBtn);
        btn.setVisibility(View.VISIBLE);
        tv = (TextView) findViewById(R.id.hints_modes);
        tv.setText("Games lost :" + sharedpref.getString("lost",""));
        int lost = Integer.parseInt(sharedpref.getString("lost",""));
        tv.setVisibility(View.VISIBLE);
        tv = (TextView) findViewById(R.id.hint1);
        if(lost>=1)
            tv.setText("If there are 9 bubbles left after Computer's move, you'll loose!!" );
        tv.setVisibility(View.VISIBLE);
        tv = (TextView) findViewById(R.id.hint2);
        if(lost>=2)
            tv.setText("If there are 5 bubbles left after Computer's move, you'll loose!!" );
        tv.setVisibility(View.VISIBLE);
    }

    public void back(View view){
        btn = (Button) findViewById(R.id.hintButton);
        btn.setVisibility(View.VISIBLE);
        btn = (Button) findViewById(R.id.modeBtn);
        btn.setVisibility(View.VISIBLE);
        btn = (Button) findViewById(R.id.start);
        btn.setVisibility(View.VISIBLE);
        btn = (Button) findViewById(R.id.exit);
        btn.setVisibility(View.VISIBLE);
        btn = (Button) findViewById(R.id.backBtn);
        btn.setVisibility(View.INVISIBLE);
        tv = (TextView) findViewById(R.id.hints_modes);
        tv.setVisibility(View.INVISIBLE);
        tv = (TextView) findViewById(R.id.hint1);
        tv.setVisibility(View.INVISIBLE);
        tv = (TextView) findViewById(R.id.hint2);
        tv.setVisibility(View.INVISIBLE);
        modeSwitch = (Switch) findViewById(R.id.userComputerSwitch);
        modeSwitch.setVisibility(View.INVISIBLE);
        img = (ImageView) findViewById(R.id.androidImage);
        img.setVisibility(View.INVISIBLE);
        img = (ImageView) findViewById(R.id.humanImage);
        img.setVisibility(View.INVISIBLE);
    }

    public void mode(View view){

        btn = (Button) findViewById(R.id.hintButton);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.modeBtn);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.start);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.exit);
        btn.setVisibility(View.INVISIBLE);
        btn = (Button) findViewById(R.id.backBtn);
        btn.setVisibility(View.VISIBLE);
        tv = (TextView) findViewById(R.id.hints_modes);
        tv.setText("Game Mode :");
        tv.setVisibility(View.VISIBLE);
        modeSwitch = (Switch) findViewById(R.id.userComputerSwitch);
        modeSwitch.setVisibility(View.VISIBLE);
        img = (ImageView) findViewById(R.id.androidImage);
        img.setVisibility(View.VISIBLE);
        img = (ImageView) findViewById(R.id.humanImage);
        img.setVisibility(View.VISIBLE);
    }
}