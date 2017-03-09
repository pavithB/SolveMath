package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class GameScreen extends AppCompatActivity implements View.OnClickListener {

     Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnenter, btndel, btnminus;
    int pair1, pair2, pair3, pair4, pair5;
    boolean minusValue;

    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 3,
            DIVIDE_OPERATOR = 2;

     int answer = 0, minTerms = 0, maxTerms = 0;
     String level;
     final String[] operators = {"+", "-", "/", "*"};
     Random random = new Random();


    TextView displayQuestion, displayAnswer, displayTime ,displayResult,debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent gameplay = getIntent();
        level = gameplay.getStringExtra("level");
        switch (level) {

            case ("novice"):
                maxTerms = 2;
                minTerms = 2;
                break;

            case ("easy"):
                maxTerms = 3;
                minTerms = 2;
                break;

            case ("medium"):
                maxTerms = 4;
                minTerms = 2;
                break;

            case ("guru"):
                maxTerms = 6;
                minTerms = 4;
                break;

        }


        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnenter = (Button) findViewById(R.id.btnenter);
        btndel = (Button) findViewById(R.id.btndel);
        btnminus = (Button) findViewById(R.id.btnminus);
        displayAnswer = (TextView) findViewById(R.id.displayanswer);
        displayQuestion = (TextView) findViewById(R.id.displayquestion);
        displayTime = (TextView) findViewById(R.id.displaytime);
        displayResult = (TextView) findViewById(R.id.result);
        debug = (TextView) findViewById(R.id.debug);



        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnenter.setOnClickListener(this);
        btndel.setOnClickListener(this);
        btnminus.setOnClickListener(this);

        genQuestion();

    }


    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.btndel):

                switch (displayAnswer.getText().toString()) {


                    case "":

                        break;
                    default:
                        if ((displayAnswer.getText().toString().endsWith("?")) || (displayAnswer.getText().toString().endsWith("=")) || (displayAnswer.getText().toString().endsWith(" "))) {

                        } else {

                            String text = displayAnswer.getText().toString();
                            displayAnswer.setText(text.substring(0, text.length() - 1));
                            break;

                        }
                }
                break;

            case (R.id.btnminus):
                if (displayAnswer.getText().toString().endsWith("?"))
                    displayAnswer.setText("=- ");
                minusValue=true;

                break;


            case (R.id.btnenter):
                    int subString=2;
                if(minusValue){
                    subString=3;
                }

                String answerview = displayAnswer.getText().toString();
                //check we have an answer
                if (!answerview.endsWith("?")) {
                    //get number
                    int userAnswer = Integer.parseInt(answerview.substring(subString));
                    //get score
                    if(minusValue){
                        userAnswer = userAnswer*-1;
                        minusValue = false ;
                    }
//                    int exScore = getScore();
                    //check answer
                    if (userAnswer == answer) {
//                        displayResult.append(String.valueOf(answer));
                        displayResult.setText("Correct");
                        debug.append(String.valueOf(answer)+" p1- "+String.valueOf(pair1)+" p2- "+String.valueOf(pair2)+" p3- "+String.valueOf(pair3)+" p4- "+String.valueOf(pair4)+" p5- "+String.valueOf(pair5));

                        //correct
//                        scoreTxt.setText("Score: "+(exScore+1));
//                        response.setImageResource(R.drawable.tick);
//                        response.setVisibility(View.VISIBLE);

                    } else {
                        displayResult.setText("Incorrect");
                        debug.append(String.valueOf(answer)+" p1- "+String.valueOf(pair1)+" p2- "+String.valueOf(pair2)+" p3- "+String.valueOf(pair3)+" p4- "+String.valueOf(pair4)+" p5- "+String.valueOf(pair5));
                    }
                    //set high score
//                        setHighScore();
                    //incorrect
//                        scoreTxt.setText("Score: 0");
//                        response.setImageResource(R.drawable.cross);
//                        response.setVisibility(View.VISIBLE);
                }

                pair1=0;
                pair2=0;
                pair3=0;
                pair4=0;
                pair5=0;
                answer=0;

                break;

            default:
                //get number from tag
                int enteredNum = Integer.parseInt(v.getTag().toString());
                //either first or subsequent digit
                if (displayAnswer.getText().toString().endsWith("?"))
                    displayAnswer.setText("= " + enteredNum);
                else
                    displayAnswer.append("" + enteredNum);

                break;

        }

    }


    public void genQuestion() {

        displayQuestion.setText("");


        int termNum = random.nextInt((maxTerms + 1) - minTerms) + minTerms;

        ArrayList<Integer> intList = new ArrayList<>();
        ArrayList<Integer> opratorList = new ArrayList<>();
        ArrayList<String> displayMath = new ArrayList<>();

        for (int i = 1; i <= termNum; i++) {

            int randomInt = random.nextInt(50 - 1) + 1;
//            int randomInt = i;

            intList.add(randomInt);
            displayMath.add(String.valueOf(randomInt));

            if (i != termNum) {

                int rndOp = new Random().nextInt(operators.length);

                opratorList.add(rndOp);

//                String genoprt = Character.toString(operators[rnd]);

                displayMath.add(operators[rndOp]);

            }
//            intList.add(0);

        }


        //generate random index and get and laod to array that contain operators. (operators = termNum - 1)
//        then create expresions by add a operator in between number ( loop start from the index 1 , and iterat till hasnext()


//        intList.clear();


        int numOfOprtr = opratorList.size();

//        for(int j=1 ;j<=numOfOprtr ; j++ ){


        GameScreen calculate = new GameScreen();
        switch (termNum) {


            case (2):
                calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
//                calculate.calculation(1, intList.get(0), intList.get(1), opratorList[]);
                answer = pair1;

                break;

            case (3):
                calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                calculation(2, pair1, intList.get(2), opratorList.get(1));
                answer = pair2;
                break;

            case (4):
                calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                calculation(2, pair1, intList.get(2), opratorList.get(1));
                calculation(3, pair2, intList.get(3), opratorList.get(2));
                answer = pair3;
                break;

            case (5):
                calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                calculation(2, pair1, intList.get(2), opratorList.get(1));
                calculation(3, pair2, intList.get(3), opratorList.get(2));
                calculation(4, pair3, intList.get(4), opratorList.get(3));
                answer = pair4;

                break;

            case (6):
                calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                calculation(2, pair1, intList.get(2), opratorList.get(1));
                calculation(3, pair2, intList.get(3), opratorList.get(2));
                calculation(4, pair3, intList.get(4), opratorList.get(3));
                calculation(5, pair4, intList.get(5), opratorList.get(4));
                answer = pair5;
                break;


        }



        for (String genNumbers : displayMath) {
//            String number = String genNumbers;

            displayQuestion.append("" + String.valueOf(genNumbers));
        }
//        }
    }


    public void calculation(int pair, int value1, int value2, int oper) {
//        displayResult.setText("calcula");
        int result = 0;
        switch (oper) {
            case ADD_OPERATOR:
                result =  (value1 + value2);
//                displayTime.setText("+");
                break;
            case SUBTRACT_OPERATOR:
                result =  (value1 - value2);
//                displayTime.setText("-");
                break;
            case DIVIDE_OPERATOR:
                result =  (value1 / value2);
//                displayTime.setText("/");
                break;
            case MULTIPLY_OPERATOR:
                result =  (value1 * value2);
//                displayTime.setText("*");
                break;
            default:
//                displayTime.setText("oper");
                break;

        }

        switch (pair) {

            case 1:
                pair1 = result;
//                displayResult.setText("1");
                break;

            case 2:
                pair2 = result;
//                displayResult.setText("2");
                break;

            case 3:
                pair3 = result;
//                displayResult.setText("3");
                break;

            case 4:
                pair4 = result;
//                displayResult.setText("4");
                break;

            case 5:
                pair5 = result;
//                displayResult.setText("5");
                break;



        }


    }
}
