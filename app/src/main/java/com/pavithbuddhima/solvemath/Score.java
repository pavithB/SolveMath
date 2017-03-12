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
    int totalScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent gameplay = getIntent();
        pointList = gameplay.getIntegerArrayListExtra("times");


        toGame = (Button) findViewById(R.id.togame);
        scoreList = (TextView) findViewById(R.id.scorelist);


        calPoint();

//continue button function
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

        // get remaining times of each questiona and generate points , and append them
        for (int time : pointList) {
//increment question no
            quesNo++;
            ///if user unable to answer the question or wroong answer gives 0 points
            if (time == 99) {
//                scoreList.setTextColor(Color.rgb(204, 0, 0));
                scoreList.append("question " + quesNo + " points:   0");
                scoreList.append(" \n");
                scoreList.append(" \n");

            } else {
                int tempScore = 0;
//if user answer is correct
                //  calculate the points using remainig time  100/(10-remaining time)
                tempScore = (100 / (10 - time));
                totalScore = totalScore + tempScore;
                scoreList.setTextColor(Color.rgb(0, 255, 204));
                scoreList.append("question " + quesNo + " points:   " + tempScore);
                scoreList.append(" \n");
                scoreList.append(" \n");

            }

        }
//        scoreList.append(" \n");
        scoreList.append("\t\t\t Total points: " + totalScore);
        totalScore = 0;


    }

//    public void setContinue(View v){
//        Intent objNewGame = new Intent(Score.this,LevelMenu.class);
//        startActivity(objNewGame);
////        Intent newGame = new Intent(Score.this,LevelMenu.class);
////        startActivity(newGame);
//
//    }


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
