package com.pavithbuddhima.solvemath;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ToggleButton;



import java.util.ArrayList;
import java.util.Random;


public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnenter, btndel, btnminus;
    ImageView resultImg;

    ToggleButton hint;
    int pair1, pair2, pair3, pair4, pair5, questionNo;
    boolean minusValue, enterSwitch;
    int hintNo = 0;

    ArrayList<Integer> times = new ArrayList<>();


    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 3,
            DIVIDE_OPERATOR = 2;

    int answer = 0, minTerms = 0, maxTerms = 0;
    String level;
    final String[] operators = {"+", "-", "/", "*"};
    Random random = new Random();

    CountDownTimer countDownTimer;

    final long startCountdown = 11 * 1000;
    final long intervalCountdown = 1 * 1000;
    long timeRemain;


    TextView displayQuestion, displayAnswer, displayTime, displayResult/*,debug*/, displayHintText, displayHintNo;

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
//        debug = (TextView) findViewById(R.id.debug);
        resultImg = (ImageView) findViewById(R.id.resultImg);
        hint = (ToggleButton) findViewById(R.id.hint);
        displayHintText = (TextView) findViewById(R.id.hinttext);
        displayHintNo = (TextView) findViewById(R.id.hintNo);


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


//        countDownTimer = new MyCountDownTimer(startCountdown, intervalCountdown);
//        displayTime.setText("Time left:" + String.valueOf(startCountdown / 1000));
//        countDownTimer.start();

        genQuestion();


    }


    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.btndel):

                switch (displayAnswer.getText().toString()) {


                    case "":

                        break;
                    case "=- ":
                        displayAnswer.setText("= ");
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
                if ((displayAnswer.getText().toString().endsWith("?")) || (displayAnswer.getText().toString().endsWith("=")) || (displayAnswer.getText().toString().endsWith(" ")))
                    displayAnswer.setText("= -");
                minusValue = true;

                break;


            case (R.id.btnenter):

                if (enterSwitch) {
                    displayAnswer.setText("= ?");
                    displayResult.setText("");
//                    debug.setText("");
                    enterSwitch = false;
                    resultImg.setVisibility(View.INVISIBLE);
                    hintNo = 0;
                    genQuestion();


                } else {


                    if ((displayAnswer.getText().toString().endsWith("-")) || (displayAnswer.getText().toString().endsWith("?")) || (displayAnswer.getText().toString().endsWith(" "))) {

                    } else {


                        int subString = 2;
                        if (minusValue) {
                            subString = 3;
                        }

                        String answerview = displayAnswer.getText().toString();
                        //check we have an answer
                        if (!answerview.endsWith("?")) {
                            //get number
                            int userAnswer = Integer.parseInt(answerview.substring(subString));
                            //get score
                            if (minusValue) {
                                userAnswer = userAnswer * -1;
                                minusValue = false;
                            }
//                    int exScore = getScore();
                            //check answer

                            if ((hint.getText().toString().equals("Hint Off")) || (hintNo >= 3)) {


                                countDownTimer.cancel();


                                resultImg.setVisibility(View.VISIBLE);
                                if (userAnswer == answer) {
//                        displayResult.append(String.valueOf(answer));
                                    displayResult.setText("Correct");
                                    displayResult.setTextColor(Color.rgb(0,230,118));
                                    resultImg.setImageResource(R.drawable.correct);
                                    times.add((int)timeRemain);
//                                debug.append(String.valueOf(answer) + " p1- " + String.valueOf(pair1) + " p2- " + String.valueOf(pair2) + " p3- " + String.valueOf(pair3) + " p4- " + String.valueOf(pair4) + " p5- " + String.valueOf(pair5));

                                    //correct
//                        scoreTxt.setText("Score: "+(exScore+1));
//                        response.setImageResource(R.drawable.tick);
//                        response.setVisibility(View.VISIBLE);

                                } else {
                                    displayResult.setText("Wrong");
                                    displayResult.setTextColor(Color.rgb(244,67,54));
                                    times.add(99);
                                    resultImg.setImageResource(R.drawable.wrong);
//                                debug.append(String.valueOf(answer) + " p1- " + String.valueOf(pair1) + " p2- " + String.valueOf(pair2) + " p3- " + String.valueOf(pair3) + " p4- " + String.valueOf(pair4) + " p5- " + String.valueOf(pair5));
                                }
                                //set high score
//                        setHighScore();
                                //incorrect
//                        scoreTxt.setText("Score: 0");
//                        response.setImageResource(R.drawable.cross);
//                        response.setVisibility(View.VISIBLE);


                                pair1 = 0;
                                pair2 = 0;
                                pair3 = 0;
                                pair4 = 0;
                                pair5 = 0;
                                answer = 0;


                                enterSwitch = true;

                            } else if ((hint.getText().toString().equals("Hint On")) || hintNo < 3) {
                                displayHintText.setVisibility(View.VISIBLE);
                                displayHintNo.setVisibility(View.VISIBLE);


                                resultImg.setVisibility(View.VISIBLE);
                                if (userAnswer == answer) {
                                    displayResult.setText("Correct");
                                    displayResult.setTextColor(Color.rgb(0,230,118));
                                    resultImg.setImageResource(R.drawable.correct);
                                    times.add((int)timeRemain);
                                    pair1 = 0;
                                    pair2 = 0;
                                    pair3 = 0;
                                    pair4 = 0;
                                    pair5 = 0;
                                    answer = 0;
                                    countDownTimer.cancel();


                                    enterSwitch = true;


                                } else if (userAnswer < answer) {


                                    hintNo++;
                                    displayHintNo.setText(String.valueOf(4 - hintNo));
                                    displayResult.setText("Greater");
//                                    displayResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    displayResult.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    resultImg.setImageResource(R.drawable.up);
                                    resultImg.setVisibility(View.VISIBLE);
                                    displayResult.setTextColor(Color.rgb(255,152,0));
                                    displayAnswer.setText("= ?");

//                                    displayResult.setTextColor(Color.RED);
//                                    resultImg.setImageResource(R.drawable.wrong);

                                } else if (userAnswer > answer) {
                                    hintNo++;
                                    displayHintNo.setText(String.valueOf(4 - hintNo));
                                    displayResult.setTextColor(Color.rgb(255,152,0));
                                    resultImg.setImageResource(R.drawable.down);
                                    resultImg.setVisibility(View.VISIBLE);
                                    displayResult.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    displayResult.setText("less");
                                    displayAnswer.setText("= ?");
                                }


                            }
                        }


                    }
                }

                break;

            default:
                if(!enterSwitch) {
                    //get number from tag
                    int enteredNum = Integer.parseInt(v.getTag().toString());
                    //either first or subsequent digit
                    if (displayAnswer.getText().toString().endsWith("?"))
                        displayAnswer.setText("= " + enteredNum);
                    else
                        displayAnswer.append("" + enteredNum);
                }

                break;

        }

    }


    public void genQuestion() {
        displayResult.setText("");

        if (questionNo == 10) {

            questionNo=0;

            Intent score = new Intent(GameScreen.this,Score.class);
            score.putExtra("times", times);
            startActivity(score);

        } else {

            questionNo++;

            displayHintText.setVisibility(View.INVISIBLE);
            displayHintNo.setVisibility(View.INVISIBLE);
            resultImg.setVisibility(View.INVISIBLE);
            hint.setChecked(false);


            countDownTimer = new MyCountDownTimer(startCountdown, intervalCountdown);
            displayTime.setText("Time left:" + String.valueOf(startCountdown / 1000));
            countDownTimer.start();

            displayQuestion.setText("");
            displayAnswer.setText("= ?");


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
    }


    public void calculation(int pair, int value1, int value2, int oper) {
//        displayResult.setText("calcula");
        int result = 0;
        switch (oper) {
            case ADD_OPERATOR:
                result = (value1 + value2);
//                displayTime.setText("+");
                break;
            case SUBTRACT_OPERATOR:
                result = (value1 - value2);
//                displayTime.setText("-");
                break;
            case DIVIDE_OPERATOR:
                result = (value1 / value2);
//                displayTime.setText("/");
                break;
            case MULTIPLY_OPERATOR:
                result = (value1 * value2);
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

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

            displayTime.setText("Time's up!");
            times.add(99);
            genQuestion();

        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeRemain = (millisUntilFinished / 1000);
            displayTime.setText("Time Left:" + timeRemain);
            displayTime.setTextColor(Color.rgb(3,169,244));

            if ((timeRemain <= 5) && (timeRemain > 3)) {
//                displayTime.setTextColor(Color.YELLOW);
                displayTime.setTextColor(Color.rgb(255,235,59));

            }
            if (timeRemain <= 3) {
                displayTime.setTextColor(Color.rgb(244,67,54));
            }
        }
    }

}
