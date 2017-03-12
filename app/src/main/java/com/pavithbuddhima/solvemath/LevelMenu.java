package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelMenu extends AppCompatActivity implements View.OnClickListener {

    Button novice, easy, medium, guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        novice = (Button) findViewById(R.id.btnNovice);
        easy = (Button) findViewById(R.id.btnEasy);
        medium = (Button) findViewById(R.id.btnMedium);
        guru = (Button) findViewById(R.id.btnGuru);


        novice.setOnClickListener(this);
        easy.setOnClickListener(this);
        medium.setOnClickListener(this);
        guru.setOnClickListener(this);

    }

    ///  create intent and put extras accoding to which level user select
    public void onClick(View v) {

        Intent objNewGame = new Intent(this, GameScreen.class);

        switch (v.getId()) {
//novice
            case (R.id.btnNovice):
                objNewGame.putExtra("level", "novice");
                objNewGame.putExtra("qNo", 0);
                startActivity(objNewGame);
                break;
//easy
            case (R.id.btnEasy):
                objNewGame.putExtra("level", "easy");
                objNewGame.putExtra("qNo", 0);
                startActivity(objNewGame);
                break;
//medium
            case (R.id.btnMedium):
                objNewGame.putExtra("level", "medium");
                objNewGame.putExtra("qNo", 0);
                startActivity(objNewGame);
                break;
//guru
            case (R.id.btnGuru):
                objNewGame.putExtra("level", "guru");
                objNewGame.putExtra("qNo", 0);
                startActivity(objNewGame);
                break;

        }

    }


    @Override
    public void onBackPressed() {
        // when click back button clear tasks , and pass the current level and question num
        super.onBackPressed();
        Intent backMenu = new Intent(this, StartMenu.class);
        backMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backMenu);
        finish(); // finish the current activity
    }

}
