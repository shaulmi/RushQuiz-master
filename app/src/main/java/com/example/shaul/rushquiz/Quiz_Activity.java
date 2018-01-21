package com.example.shaul.rushquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by shaul on 25/07/2017.
 */

public class Quiz_Activity extends AppCompatActivity {

    private DatabaseReference Quiz;

    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private  int currentQuestionIndex=0;
    private int sum ;
    private int TotalPoints = 0;
    private TextView mQuestion;
    private  Button OptionA;
    private Button OptionB;
    private Button OptionC;
    private Button OptionD;
    private TextView mScore;
    private TextView mTimer;
    private long millis;
    private String category ;
    private CounterClass timer;
    private TextView mCounter;
    private int sumQuestions;
    private ImageView imageView;
    private String Succeed = "הצלחת";
    private String Wrong = "טעית";
    private int points;
    private Toast toast;
    private int flag = 0;
    List <QuizWrapper> QuizList = new ArrayList<QuizWrapper>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity);
        Bundle bundle = getIntent().getExtras();

        imageView = (ImageView)findViewById(R.id.image);

        category =bundle.getString("category");

        mTimer = (TextView)findViewById(R.id.Timer);

        mScore = (TextView)findViewById(R.id.Score);

        mQuestion = (TextView)findViewById(R.id.Question);

        OptionA = (Button) findViewById(R.id.Button1);

        OptionB = (Button) findViewById(R.id.Button2);

        OptionC = (Button) findViewById(R.id.Button3);

        OptionD = (Button) findViewById(R.id.Button4);

         mCounter = (TextView) findViewById(R.id.Counter);

        Quiz = FirebaseDatabase.getInstance().getReference().child("QuestionsDB").child("נושא").child(category).child("Questions");

        Quiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    question = child.child("Question").getValue().toString();
                    a = child.child("A").getValue().toString();
                    b = child.child("B").getValue().toString();
                    c = child.child("C").getValue().toString();
                    d = child.child("D").getValue().toString();
                    answer = child.child("Answer").getValue().toString();
                    QuizList.add(new QuizWrapper(question, a, b, c, d, answer));
                }
                play();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void play(){

        Collections.shuffle(QuizList);
        sumQuestions = QuizList.size();
        // A timer of 2 minutes to play for, with an interval of 1 second (1000 milliseconds)
        timer = new CounterClass(QuizList.size()*7000, 1000);
        SetQuizView();
        OptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check(OptionA.getText().toString());
            }

        });
        OptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check(OptionB.getText().toString());
            }
        });
        OptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(OptionC.getText().toString());
            }
        });
        OptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(OptionD.getText().toString());
            }
        });
    }

    public void SetQuizView(){
        mQuestion.setText(QuizList.get(currentQuestionIndex).getQuestion());
        OptionA.setText(QuizList.get(currentQuestionIndex).getOPTA());
        OptionB.setText(QuizList.get(currentQuestionIndex).getOPTB());
        OptionC.setText(QuizList.get(currentQuestionIndex).getOPTC());
        OptionD.setText(QuizList.get(currentQuestionIndex).getOPTD());
        timer.start();
        currentQuestionIndex++;
        mCounter.setText(currentQuestionIndex+" מתוך "+sumQuestions);
    }

    public void check(String AnswerChosen){
        timer.cancel();
        if(AnswerChosen.equals(QuizList.get(currentQuestionIndex-1).getAnswers())) {
            toast = Toast.makeText(Quiz_Activity.this, Succeed, Toast.LENGTH_SHORT);
            points = 1;
            imageView.setImageResource(R.drawable.success);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
             toast = Toast.makeText(Quiz_Activity.this, Wrong, Toast.LENGTH_SHORT);
            points = 0;
            imageView.setImageResource(R.drawable.pass_time);
            imageView.setVisibility(View.VISIBLE);
        }
            Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TotalPoints +=points;
                    mScore.setText("" + TotalPoints);
                    toast.cancel();
                    imageView.setVisibility(View.INVISIBLE);
                    timer = new CounterClass(millis, 1000);
                    if (currentQuestionIndex<QuizList.size()){
                        SetQuizView();
                    }
                    else{
                        Calculate((int)(TimeUnit.MILLISECONDS.toMinutes(millis))+1);
                    }
                }
            }, 1500);


        }

    public void Calculate(int bonus){
        sum=bonus*TotalPoints;
        finito(sum);

    }
     public void finito(int sum){
         flag = 1;
         Intent intent = new Intent(Quiz_Activity.this,
                 ResultActivity.class);
         Bundle b = new Bundle();
         b.putInt("points", sum);
         b.putString("Type",category);
         intent.putExtras(b);
         startActivity(intent);
         finish();
         startActivity(intent);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     }
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
        }
            public void onTick(long millisUntilFinished) {
            millis = millisUntilFinished;
            String ms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            mTimer.setText(ms);
        }
    public void onFinish() {

        if(flag==0) {

            Calculate(1);
        }
    }
}
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        finish();
    }
}

