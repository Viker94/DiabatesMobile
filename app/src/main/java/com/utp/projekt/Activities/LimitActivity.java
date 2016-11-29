package com.utp.projekt.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.Helper;

import cz.msebera.android.httpclient.Header;

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
        textWater.setTextColor(Helper.WATER_COLOR);
        textSodium.setTextColor(Helper.SODIUM_COLOR);
        textPotassium.setTextColor(Helper.POTASSIUM_COLOR);
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
        String[] params = new String[4];
        params[0] = String.valueOf(MainActivity.user.getId());
        params[1] = String.valueOf(MainActivity.user.getLimitPotassium());
        params[2] = String.valueOf(MainActivity.user.getLimitWater());
        params[3] = String.valueOf(MainActivity.user.getLimitSodium());


        Controller.callServicAsync("user",params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Zapisano limity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Controller.failure(statusCode,getApplicationContext());
            }
        });

        onBackPressed();
        //con.update("user",MainActivity.getUser().getId().toString(),MainActivity.getUser().getLimitPotassium(),MainActivity.getUser().getLimitWater(),MainActivity.getUser().getLimitSodium());

    }



}
