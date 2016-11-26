package com.utp.projekt.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.utp.projekt.Controller.Controller;
import com.utp.projekt.R;

public class EatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
    }

    public void add(View view){

        Controller con = new Controller();
        String[] tab = {"1", "4", "7", "2"};
        con.update("user", tab, getApplicationContext());
    }
}
