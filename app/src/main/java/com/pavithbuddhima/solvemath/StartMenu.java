package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartMenu extends AppCompatActivity implements View.OnClickListener{

    Button newGame , btnContinue , about ,exit ;
    PopupWindow popupAbout;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        newGame = (Button) findViewById(R.id.btnNewGame);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        about = (Button) findViewById(R.id.btnAbout);
        exit = (Button) findViewById(R.id.btnExit);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_start_menu);


        newGame.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        about.setOnClickListener(this);
        exit.setOnClickListener(this);


    }

    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnNewGame:
                Intent objNewGame = new Intent(StartMenu.this,LevelMenu.class);
                startActivity(objNewGame);
                break;


            case R.id.btnContinue:   ;
                break;


            case R.id.btnAbout:

                about.setText("Brain Math");
                about.setTextColor(Color.rgb(239,83,80));

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popupabout,null);

                popupAbout = new PopupWindow(container,1080,740,true);
                popupAbout.showAtLocation(relativeLayout, Gravity.CENTER,0,610);


                relativeLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        about.setText("ABOUT");
                        about.setTextColor(Color.rgb(0,188,212));
                    }
                });


                container.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                      //popupAbout.dismiss();

                        return true;

                    }
                });

                break;
            case R.id.btnExit:   ;
                break;

        }

    }
}
