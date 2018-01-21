package com.example.shaul.rushquiz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by shaul on 28/10/2017.
 */

public class HighScore extends Activity {

    private DatabaseReference high;

     String category;

    String user;

    int points;

    String date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        final TextView textView = (TextView) findViewById(R.id.text_view_id);

        Bundle bundle = getIntent().getExtras();

        category =bundle.getString("category");

        high = FirebaseDatabase.getInstance().getReference().child("QuestionsDB").child("נושא").child(category);

        high.child("HighScore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.child("user").getValue(String.class);

                points= dataSnapshot.child("result").getValue(Integer.class);

                date = dataSnapshot.child("date").getValue(String.class);

                textView.setText(user + " " + points + " " + date);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
