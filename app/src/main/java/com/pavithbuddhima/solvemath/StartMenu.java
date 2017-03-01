package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartMenu extends AppCompatActivity implements View.OnClickListener{

    Button newGame , btnContinue , about ,exit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        newGame = (Button) findViewById(R.id.btnNewGame);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        about = (Button) findViewById(R.id.btnAbout);
        exit = (Button) findViewById(R.id.btnExit);


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
            case R.id.btnAbout:   ;
                break;
            case R.id.btnExit:   ;
                break;

        }

    }
}
