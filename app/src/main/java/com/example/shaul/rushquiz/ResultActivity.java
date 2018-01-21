package com.example.shaul.rushquiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shaul on 02/08/2017.
 */

public class ResultActivity extends AppCompatActivity {

    private DatabaseReference database;
    private Button Save;
    private EditText mName;
    private int point=0;
    private AlertDialog dialog;
    private String kind;
    private String stringdate;
    private String FRDate;
    int TopScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Save = (Button) findViewById(R.id.btn);
        Bundle b = getIntent().getExtras();
        mName = (EditText) findViewById(R.id.textResult);
        point = b.getInt("points");
        kind = b.getString("Type");
        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        stringdate= dt.format(newDate);
        database = FirebaseDatabase.getInstance().getReference().child("QuestionsDB").child("נושא").child(kind);
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setMessage (" ציונך המשוקלל " + point);
        dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog.show();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(mName.getText().toString().trim().equals(""))) {
                    database.child("HighScore").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FRDate = dataSnapshot.child("date").getValue(String.class);
                            TopScore = dataSnapshot.child("result").getValue(Integer.class);
                            if ((!(FRDate.equals(stringdate))) || (TopScore < point)) {
                                String user = mName.getText().toString().trim();
                                Score s = new Score(user, point, stringdate);
                                database.child("HighScore").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if (task.isSuccessful()) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                                builder.setMessage(" שברת שיא ");
                                                dialog = builder.create();
                                                dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Intent dashboard = new Intent(ResultActivity.this,
                                                                MainMenu.class);
                                                        startActivity(dashboard);
                                                        // Close Registration Screen

                                                        finish();
                                                    }
                                                });
                                                dialog.show();

                                            }
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                    builder.setMessage(" לא הכנסת את השם ");
                    dialog = builder.create();
                    dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });


        }
    public void onBackPressed() {
        finish();
    }
}

