package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

public class MainActivity extends Activity {

    private TextView name; //przywitanie
    private ProgressBar pp; //progress bary dla potasu, wody i sodu
    private ProgressBar pw;
    private ProgressBar ps;
    private TextView tp; //textview dla liczby obok progress bara
    private TextView tw;
    private TextView ts;
    private Space spacer;
    private Button buttonLimit;
    private Button tempButton;


    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user = getIntent().getParcelableExtra("USER");
        Log.i("asd", user.getConsumptions().toString());
        name = (TextView) findViewById(R.id.name);
        name.setText("Witaj: " + user.getFirstName() + " " + user.getLastName());
        buttonLimit = (Button) findViewById(R.id.limit);
        tempButton = (Button) findViewById(R.id.tempGraph);
        pp = (ProgressBar) findViewById(R.id.pPotassium);
        pw = (ProgressBar) findViewById(R.id.pWater);
        ps = (ProgressBar) findViewById(R.id.pSodium);
        tp = (TextView) findViewById(R.id.ppPotassium);
        tw = (TextView) findViewById(R.id.ppWater);
        ts = (TextView) findViewById(R.id.ppSodium);
        pp.setProgress((int)((user.getPotassium()/user.getLimitPotassium())*100));
        pw.setProgress((int)((user.getWater()/user.getLimitWater())*100));
        ps.setProgress((int)((user.getSodium()/user.getLimitSodium())*100));
        tp.setText((int)((user.getPotassium()/user.getLimitPotassium())*100) + "%");
        tw.setText((int)((user.getWater()/user.getLimitWater())*100) + "%");
        ts.setText((int)((user.getSodium()/user.getLimitSodium())*100) + "%");

        buttonLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLimitActivity();
            }
        });

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGraphActivity();
            }
        });

    }
    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }
    public void startGraphActivity()
    {
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }


    public void startLimitActivity()
    {
        Intent intent = new Intent(MainActivity.this, LimitActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openEat(View view){
        Intent intent = new Intent(MainActivity.this, EatActivity.class);
        startActivity(intent);
    }
}
