package com.pavithbuddhima.solvemath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
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

    boolean iscontinue= false;



    //to store generated numbers
    ArrayList<Integer> intList = new ArrayList<>();
    //            to save randomly  generated operators
    ArrayList<Integer> opratorList = new ArrayList<>();
    //            to  display the expression this array contain the arithmatic expression both integer and operators
    ArrayList<String> displayMath = new ArrayList<>();


    //store nmber of numers
    int termNum ;




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
//get extra values (level chose by the user if continue question no
        Intent gameplay = getIntent();
        level = gameplay.getStringExtra("level");
        switch (level) {
//set maximum and minimum mathematical expressions accoding to level
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
        //get queestion numeber
        questionNo = gameplay.getIntExtra("qNo", 0);

        if(questionNo !=0){
            iscontinue=true;
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

        //to generate random question and answer
        genQuestion();


    }

//add on click listner ttake button clicks
    public void onClick(View v) {

        switch (v.getId()) {
//del button action
            case (R.id.btndel):

                switch (displayAnswer.getText().toString()) {

//do nothing if nothing on text view
                    case "":
//do nothing if - mark at the end on text view
                        break;
                    case "=- ":
                        //if - mark at the end on text view, then delete - mark
                        displayAnswer.setText("= ");
                        break;
                    //do nothing if view ends with ? = " "
                    default:
                        if ((displayAnswer.getText().toString().endsWith("?")) || (displayAnswer.getText().toString().endsWith("=")) || (displayAnswer.getText().toString().endsWith(" "))) {

                        } else {
//delete last character per click
                            String text = displayAnswer.getText().toString();
                            displayAnswer.setText(text.substring(0, text.length() - 1));
                            break;

                        }
                }
                break;
//- button action , if user need to enter minus value
            case (R.id.btnminus):
                if ((displayAnswer.getText().toString().endsWith("?")) || (displayAnswer.getText().toString().endsWith("=")) || (displayAnswer.getText().toString().endsWith(" ")))
                    displayAnswer.setText("= -");
                minusValue = true;

                break;

//to enter the answer and press again to generate next question
            case (R.id.btnenter):
//if user already answer the question then incvoke this and generate next question
                if (enterSwitch) {
                    //reset screen changes before genrate next question
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

//when minus values in the answer
                        int subString = 2;
                        if (minusValue) {
                            // when minus mark in the screen we have to get sub string (3) to get the users answer
                            subString = 3;
                        }

                        String answerview = displayAnswer.getText().toString();
                        // validate the answer
                        if (!answerview.endsWith("?")) {
                            //get number
                            int userAnswer = Integer.parseInt(answerview.substring(subString));
                            //get score
                            //* by -1 if user enter minus mark beginning of the answer
                            if (minusValue) {
                                userAnswer = userAnswer * -1;
                                minusValue = false;
                            }
//                    int exScore = getScore();
                            //check answer
//check whether hint is on or off
                            if ((hint.getText().toString().equals("Hint Off")) || (hintNo >= 3)) {

//stop timer
                                countDownTimer.cancel();

//check answer and set text and image
                                resultImg.setVisibility(View.VISIBLE);
                                //if correct answer
                                if (userAnswer == answer) {
//                        displayResult.append(String.valueOf(answer));
                                    displayResult.setText("Correct");
                                    displayResult.setTextColor(Color.rgb(0, 230, 118));
                                    resultImg.setImageResource(R.drawable.correct);
                                    //add remaining time for each question to time array and calculate points using that
                                    times.add((int) timeRemain);
//                                debug.append(String.valueOf(answer) + " p1- " + String.valueOf(pair1) + " p2- " + String.valueOf(pair2) + " p3- " + String.valueOf(pair3) + " p4- " + String.valueOf(pair4) + " p5- " + String.valueOf(pair5));

                                    //correct
//                        scoreTxt.setText("Score: "+(exScore+1));
//                        response.setImageResource(R.drawable.tick);
//                        response.setVisibility(View.VISIBLE);

                                } else {
                                    displayResult.setText("Wrong");
                                    displayResult.setTextColor(Color.rgb(244, 67, 54));
                                    //if wrong answer add 99, to identify it as a wrong answer
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

//set values that generate answer to 0 genrate new question
                                pair1 = 0;
                                pair2 = 0;
                                pair3 = 0;
                                pair4 = 0;
                                pair5 = 0;
                                answer = 0;

//flag that user gave an anwer ,
                                enterSwitch = true;
//if hint is on , and not exceed the maximum number of hints --->4
                            } else if ((hint.getText().toString().equals("Hint On")) || hintNo < 3) {
                                displayHintText.setVisibility(View.VISIBLE);
                                displayHintNo.setVisibility(View.VISIBLE);

// when answer is correct and hint is on
                                resultImg.setVisibility(View.VISIBLE);
                                if (userAnswer == answer) {
                                    displayResult.setText("Correct");
                                    displayResult.setTextColor(Color.rgb(0, 230, 118));
                                    resultImg.setImageResource(R.drawable.correct);
                                    times.add((int) timeRemain);
                                    pair1 = 0;
                                    pair2 = 0;
                                    pair3 = 0;
                                    pair4 = 0;
                                    pair5 = 0;
                                    answer = 0;
                                    countDownTimer.cancel();

//flag that the  answer for question
                                    enterSwitch = true;

//player get 4 chances to enter correct answer , if not display  greater or less, after 4 chance  user get wrong
                                } else if (userAnswer < answer) {

//playeer get 4 chances to enter coorrect answer when hint is on
                                    hintNo++;
                                    displayHintNo.setText(String.valueOf(4 - hintNo));
                                    displayResult.setText("Greater");
//                                    displayResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    displayResult.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    resultImg.setImageResource(R.drawable.up);
                                    resultImg.setVisibility(View.VISIBLE);
                                    displayResult.setTextColor(Color.rgb(255, 152, 0));
                                    displayAnswer.setText("= ?");

//                                    displayResult.setTextColor(Color.RED);
//                                    resultImg.setImageResource(R.drawable.wrong);
//if user answer is greater than orrect answer also when hint is on
                                } else if (userAnswer > answer) {
                                    hintNo++;
                                    displayHintNo.setText(String.valueOf(4 - hintNo));
                                    displayResult.setTextColor(Color.rgb(255, 152, 0));
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
//set dial pad button actions , by get button tag and append to answer
            default:
                if (!enterSwitch) {
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


    @Override
    public void onBackPressed() {
        // when click back button clear tasks , and pass the current level and question num

        super.onBackPressed();
        // stop timer
        countDownTimer.cancel();
        //clear activities
        finish();

//save current game instance LEVEL AND CURRENT QUESTION NO using Shared preferences.
        SharedPreferences sharedPref = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
//save question no
        editor.putInt("question", questionNo);

//save level
        editor.putString("level", level);
//apply saved
        editor.apply();

//
//        editor.apply();
        // and call go to start menu when  user click back button
        Intent backMenu = new Intent(this, StartMenu.class);
        backMenu.putExtra("level", level);
        backMenu.putExtra("qNo", questionNo);
        backMenu.putExtra("intList", intList);
        backMenu.putExtra("opratorList", opratorList);
        backMenu.putExtra("displayMath", displayMath);
        backMenu.putExtra("termNum", termNum);








        //pass two values too level and question no

        backMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backMenu);
        // finish the current activity
    }


    //generate random questions
    public void genQuestion() {
        displayResult.setText("");
//if user face 10 question then view score
        if (questionNo == 10) {

            questionNo = 0;
//display score activiity and pass time array which contain time's user spent for each questions
            Intent score = new Intent(GameScreen.this, Score.class);
            score.putExtra("times", times);
            startActivity(score);

        } else {

//reset instances
            displayHintText.setVisibility(View.INVISIBLE);
            displayHintNo.setVisibility(View.INVISIBLE);
            resultImg.setVisibility(View.INVISIBLE);
            hint.setChecked(false);




            // set countdown timer
            countDownTimer = new MyCountDownTimer(startCountdown, intervalCountdown);
            displayTime.setText("Time left:" + String.valueOf(startCountdown / 1000));
            countDownTimer.start();

            displayQuestion.setText("");
            displayAnswer.setText("= ?");



            if(iscontinue) {


                /**
                 * this nee to replce with continuee term (previously generated term)
                 */
                termNum = 0 ;




                iscontinue = false ;

            }else{

                //when genrate random question  increment the question no variable

                questionNo++;

//genrate numers
                termNum = random.nextInt((maxTerms + 1) - minTerms) + minTerms;

                for (int i = 1; i <= termNum; i++) {
//generate random no between 1 and 50
                    int randomInt = random.nextInt(50 - 1) + 1;
//            int randomInt = i;
//add to arrays
                    intList.add(randomInt);
                    displayMath.add(String.valueOf(randomInt));
//generate operators in in between two numbers
                    if (i != termNum) {
//generate operators between numbers
                        int rndOp = new Random().nextInt(operators.length);

                        opratorList.add(rndOp);

//                String genoprt = Character.toString(operators[rnd]);

                        displayMath.add(operators[rndOp]);

                    }
//            intList.add(0);

                }


            }




//            Toast.makeText(getBaseContext(),"Question:" + questionNo);
//add a toast
            Toast.makeText(getBaseContext(), "Question:" + (String.valueOf(questionNo + 1)),
                    Toast.LENGTH_SHORT).show();












            //generate random index and get and laod to array that contain operators. (operators = termNum - 1)
//        then create expresions by add a operator in between number ( loop start from the index 1 , and iterat till hasnext()


//        intList.clear();


            int numOfOprtr = opratorList.size();

//        for(int j=1 ;j<=numOfOprtr ; j++ ){


            GameScreen calculate = new GameScreen();

            //this switch case use to identify the no of integers in the generated question and categarised them
            switch (termNum) {

//call calculation method  accoding to number of genrated expression  to find the answer

                //call calculation method , and pass operator , two numbers before and after that operaor, and wich pair is it

                //if expression only contain two integers
                case (2):
                    calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
//                calculate.calculation(1, intList.get(0), intList.get(1), opratorList[]);
                    answer = pair1;

                    break;
                //if expression CONTAIN THREE integers

                case (3):
                    calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                    calculation(2, pair1, intList.get(2), opratorList.get(1));
                    answer = pair2;
                    break;

                //if expression CONTAIN four integers

                case (4):
                    calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                    calculation(2, pair1, intList.get(2), opratorList.get(1));
                    calculation(3, pair2, intList.get(3), opratorList.get(2));
                    answer = pair3;
                    break;

                //if expression CONTAIN five integers

                case (5):
                    calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
                    calculation(2, pair1, intList.get(2), opratorList.get(1));
                    calculation(3, pair2, intList.get(3), opratorList.get(2));
                    calculation(4, pair3, intList.get(4), opratorList.get(3));
                    answer = pair4;

                    break;

                //if expression CONTAIN six integers
                case (6):

                    //if six integers
                    //first pass first integer and second
                    calculation(1, intList.get(0), intList.get(1), opratorList.get(0));
//                    secondly pass  calculation of the firt two integers and third integer
                    calculation(2, pair1, intList.get(2), opratorList.get(1));
                    //calculation of second pair and forth integer
                    calculation(3, pair2, intList.get(3), opratorList.get(2));
                    //calculation of the third pair and fifth integer
                    calculation(4, pair3, intList.get(4), opratorList.get(3));
                    calculation(5, pair4, intList.get(5), opratorList.get(4));
                    //finally answer is set
                    answer = pair5;
                    break;


            }

//use for each loop to display the question in view
            for (String genNumbers : displayMath) {
//            String number = String genNumbers;

                displayQuestion.append("" + String.valueOf(genNumbers));
            }
//        }
        }
    }

    // ask for operator and values on both side then this method return the answer
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
//to make sure what part has been solve
            //wich pair
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

    // to create count down timer
    public class MyCountDownTimer extends CountDownTimer {
        // create timer
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
//code after countdown is over
            displayTime.setText("Time's up!");

            Toast.makeText(getBaseContext(), ("Time's up!"),
                    Toast.LENGTH_SHORT).show();

            times.add(99);
            genQuestion();



            /*final Toast toast = Toast.makeText(getApplicationContext(), "This message will disappear     in half second", Toast.LENGTH_SHORT);
    toast.show();

    Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               toast.cancel();
           }
    }, 500);*/




        }

        @Override
        public void onTick(long millisUntilFinished) {
            //code for each interval
            timeRemain = (millisUntilFinished / 1000);
            displayTime.setText("Time Left:" + timeRemain);
            displayTime.setTextColor(Color.rgb(3, 169, 244));

            if ((timeRemain <= 5) && (timeRemain > 3)) {
//                displayTime.setTextColor(Color.YELLOW);
                displayTime.setTextColor(Color.rgb(255, 235, 59));

            }
            if (timeRemain <= 3) {
                displayTime.setTextColor(Color.rgb(244, 67, 54));
            }
        }
    }

}


