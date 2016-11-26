package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

public class LimitActivity extends Activity {

    private TextView textWater;
    private TextView textPotassium;
    private TextView textSodium;
    private EditText tbWater;
    private EditText tbPotassium;
    private EditText tbSodium;
    private Button buttonSave;
    private Button buttonCancel;
    private Space polandCanIntoSpace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);
        //User user = getIntent().getParcelableExtra("USER");

        textWater = (TextView) findViewById(R.id.textWater);
        textPotassium = (TextView) findViewById(R.id.textPotassium);
        textSodium = (TextView) findViewById(R.id.textSodium);
        tbWater = (EditText) findViewById(R.id.tbWater);
        tbPotassium = (EditText) findViewById(R.id.tbPotassium);
        tbSodium = (EditText) findViewById(R.id.tbSodium);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        polandCanIntoSpace = (Space) findViewById(R.id.polandCanIntoSpace);
        tbWater.setText(MainActivity.user.getLimitWater()+"");
        tbSodium.setText(MainActivity.user.getLimitSodium()+"");
        tbPotassium.setText(MainActivity.user.getLimitPotassium()+"");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void updateUser()
    {

        User temp = MainActivity.user;
        temp.setLimitPotassium((Double.valueOf(tbPotassium.getText().toString())));
        temp.setLimitWater((Double.valueOf(tbWater.getText().toString())));
        temp.setLimitSodium((Double.valueOf(tbSodium.getText().toString())));
        MainActivity.user = temp;
        Controller con = new Controller();
        String[] params = new String[4];
        params[0] = String.valueOf(MainActivity.user.getId());
        params[1] = String.valueOf(MainActivity.user.getLimitPotassium());
        params[2] = String.valueOf(MainActivity.user.getLimitWater());
        params[3] = String.valueOf(MainActivity.user.getLimitSodium());

        con.update("user",params,getApplicationContext());

        onBackPressed();
        //con.update("user",MainActivity.getUser().getId().toString(),MainActivity.getUser().getLimitPotassium(),MainActivity.getUser().getLimitWater(),MainActivity.getUser().getLimitSodium());

    }



}