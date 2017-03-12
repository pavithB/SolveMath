package com.pavithbuddhima.solvemath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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

public class StartMenu extends AppCompatActivity implements View.OnClickListener {

    Button newGame, btnContinue, about, exit;
    PopupWindow popupAbout;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;
    String level;
    int questionNo = 55;
    SharedPreferences sharedPref;


//    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        //link xml content with java variables
        newGame = (Button) findViewById(R.id.btnNewGame);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        about = (Button) findViewById(R.id.btnAbout);
        exit = (Button) findViewById(R.id.btnExit);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_start_menu);

        //set buttun click listners
        newGame.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        about.setOnClickListener(this);
        exit.setOnClickListener(this);

        //create intent object and retrive passed values
        Intent getStatus = new Intent();
        level = getStatus.getStringExtra("level");
        questionNo = getStatus.getIntExtra("qNo", 55);

    }

    //button on click
    public void onClick(View v) {

        switch (v.getId()) {
            //create intent object to call activities
            case R.id.btnNewGame:
                Intent objNewGame = new Intent(StartMenu.this, LevelMenu.class);
                startActivity(objNewGame);
                break;

//if click continue
            case R.id.btnContinue:
// retrive sharedpreference
                sharedPref = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
//set saved lavel
                String setLevel = sharedPref.getString("level", "novice");
                //set saved question number
                int quisNo = sharedPref.getInt("question", 1);

                Intent continueGame = new Intent(StartMenu.this, GameScreen.class);
//continue to saved state
                continueGame.putExtra("level", setLevel);
                continueGame.putExtra("qNo", quisNo);
                startActivity(continueGame);

                break;

//if click about
            case R.id.btnAbout:

//                about.setText("Brain Math");
//                about.setTextColor(Color.rgb(63,81,181));
//create pop up window
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popupabout, null);

                popupAbout = new PopupWindow(container, 1080, 1250, true);
                popupAbout.showAtLocation(relativeLayout, Gravity.CENTER, 0, 70);

                Button done = (Button) container.findViewById(R.id.done);

                done.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        popupAbout.dismiss();
//                        about.setText("ABOUT");
//                        about.setTextColor(Color.rgb(0,0,0));
                    }
                });

//to reset the buutton name "about"
                container.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        popupAbout.dismiss();

                        return true;

                    }
                });

                break;
            // when exit click
            case R.id.btnExit:
// create Dialog box to confirm save
                AlertDialog.Builder mbuild = new AlertDialog.Builder(StartMenu.this);
                View mView = getLayoutInflater().inflate(R.layout.exitsave, null);
                final Button save = (Button) mView.findViewById(R.id.save);
                final Button exit = (Button) mView.findViewById(R.id.directexit);
// set action listners for dialog box buttons
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        SharedPreferences sp = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
// set saved instances to shared preferences
//                        SharedPreferences.Editor editor = sharedPref.edit();
////set question no
//                        editor.putInt("question", questionNo);
//
////set level
//                        editor.putString("level", level);
//
//                        editor.apply();
//kill tasks
                        finish();
                        //exit
                        System.exit(1);
                    }
                });


                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (questionNo == 55) {

//                        SharedPreferences sp = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
//if user dont want to save
//                            SharedPreferences.Editor editor = sharedPref.edit();
//
//                            editor.putInt("question", 55);
//
//
//                            editor.putString("level", "");
//
//                            editor.apply();
//kill tasks and exit
                            finish();
                            System.exit(1);
                        }
                    }
                });
//show the dialog box
                mbuild.setView(mView);
                AlertDialog dialog = mbuild.create();
                dialog.show();
//                if(questionNo!=55) {

//                SharedPreferences sp = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
//
//                SharedPreferences.Editor editor = sharedPref.edit();
//
//                editor.putInt("question",questionNo);
//
//
//                editor.putString("level",level);
//
//                editor.apply();
//
//                }
//
//
//
////                finish();
//                finish();
//                System.exit(1);
                break;

        }

    }


    @Override
    public void onBackPressed() {
        // when click back button clear tasks , and pass the current level and question num
        super.onBackPressed();
        finish(); // finish the current activity
        System.exit(0);
    }

}