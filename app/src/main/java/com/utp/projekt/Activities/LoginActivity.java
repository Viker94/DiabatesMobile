package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Controller.OnJSONResponseCallback;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity {

    private TextView login;
    private EditText password;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (TextView)findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        login.setText("login");
        password.setText("passwd");
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToController();
            }
        });
    }

    public void connectToController(){
        final Controller con = new Controller();
        if(!login.getText().toString().equals("") && !password.getText().toString().equals(""))
            con.getSingleData("login", login.getText()+"/"+password.getText(), getApplicationContext(), new OnJSONResponseCallback() {
                @Override
                public void onJSONResponse(boolean success, JSONObject response) {
                    try {
                        con.getSingleData("user", response.getString("id"), getApplicationContext(), new OnJSONResponseCallback() {
                            @Override
                            public void onJSONResponse(boolean success, JSONObject response) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                try {
                                    User user = new User(response.getLong("id"), response.getString("firstName"), response.getString("lastName"), response.getDouble("potassium"), response.getDouble("water"), response.getDouble("sodium"), response.getDouble("limitPotassium"), response.getDouble("limitWater"), response.getDouble("limitSodium"));
                                    intent.putExtra("USER", user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        else Toast.makeText(getApplicationContext(), "Podaj login i hasło", Toast.LENGTH_LONG).show();
    }

}
