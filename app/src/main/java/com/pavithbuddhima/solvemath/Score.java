package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Score extends AppCompatActivity {


    TextView scoreList;
    Button toGame;
    ArrayList<Integer> pointList = new ArrayList<>();
    int quesNo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent gameplay = getIntent();
        pointList = gameplay.getIntegerArrayListExtra("times");


        toGame = (Button) findViewById(R.id.togame);
        scoreList = (TextView) findViewById(R.id.scorelist);


        calPoint();


        toGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newGame = new Intent(Score.this, LevelMenu.class);
                scoreList.setText("");
                startActivity(newGame);
            }
        });


    }


    public void calPoint() {

        for (int time : pointList) {

            quesNo++;
            if (time == 99) {
//                scoreList.setTextColor(Color.rgb(204, 0, 0));
                scoreList.append("question " + quesNo + ":   0 points");
                scoreList.append(" \n");

            } else {
                int tempScore = 0;

                tempScore = (100 / (10 - time));
                scoreList.setTextColor(Color.rgb(0, 255, 204));
                scoreList.append("question " + quesNo + ": " + tempScore + " points");
                scoreList.append(" \n");

            }

        }


    }

//    public void setContinue(View v){
//        Intent objNewGame = new Intent(Score.this,LevelMenu.class);
//        startActivity(objNewGame);
////        Intent newGame = new Intent(Score.this,LevelMenu.class);
////        startActivity(newGame);
//
//    }


}
