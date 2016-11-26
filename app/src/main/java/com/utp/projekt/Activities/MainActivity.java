package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private int waterLimit = 100;
    private int potassiumLimit = 100;
    private int sodiumLimit = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        User user = getIntent().getParcelableExtra("USER");
        Log.i("asd", user.getPotassium() + "");
        name = (TextView) findViewById(R.id.name);
        name.setText("Witaj: " + user.getFirstName() + " " + user.getLastName());
        pp = (ProgressBar) findViewById(R.id.pPotassium);
        pw = (ProgressBar) findViewById(R.id.pWater);
        ps = (ProgressBar) findViewById(R.id.pSodium);
        tp = (TextView) findViewById(R.id.ppPotassium);
        tw = (TextView) findViewById(R.id.ppWater);
        ts = (TextView) findViewById(R.id.ppSodium);
        pp.setProgress((int)((user.getPotassium()/100)*100));
        pw.setProgress((int)((user.getWater()/100)*100));
        ps.setProgress((int)((user.getSodium()/100)*100));
        tp.setText((int)((user.getPotassium()/potassiumLimit)*100) + "%");
        tw.setText((int)((user.getWater()/waterLimit)*100) + "%");
        ts.setText((int)((user.getSodium()/sodiumLimit)*100) + "%");
    }

    public void openEat(View view){
        Intent intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }
}
