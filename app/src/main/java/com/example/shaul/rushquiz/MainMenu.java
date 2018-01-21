
package com.example.shaul.rushquiz;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



public class MainMenu extends AppCompatActivity {

    final String [] items = {"כללי","מתמטיקה","מחשבים"};
    int checkedItem= -1;
    //Create an Object of AlertDialog
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        final Button NewGame = (Button) findViewById(R.id.buttonNewGame);

        //The button which start a new game
        NewGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("בחר/י נושא");
                builder.setSingleChoiceItems(items ,checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainMenu.this,
                                Quiz_Activity.class);
                        Bundle b = new Bundle();
                        b.putString("category", items[which]);
                        intent.putExtras(b);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

                //AlertDialog dialog; create like this outside onClick
                dialog = builder.create();
                dialog.show();
            }
       });

        final Button HighScore = (Button) findViewById(R.id.bHighScore);

        HighScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("בחר/י נושא");
                builder.setSingleChoiceItems(items ,checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent HighSore = new Intent(MainMenu.this,
                                HighScore.class);
                        Bundle b = new Bundle();
                        b.putString("category", items[which]);
                        HighSore.putExtras(b);

                        startActivity(HighSore);
                        dialog.cancel();
                    }
                });
                //AlertDialog dialog; create like this outside onClick
                dialog = builder.create();
                dialog.show();


            }
        });

    }

}


