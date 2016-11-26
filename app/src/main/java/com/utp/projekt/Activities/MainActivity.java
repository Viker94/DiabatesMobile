package com.utp.projekt.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

public class MainActivity extends Activity {

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        User user = getIntent().getParcelableExtra("USER");
        name = (TextView) findViewById(R.id.name);
        name.setText("Witaj: " + user.getFirstName() + " " + user.getLastName());
    }
}
