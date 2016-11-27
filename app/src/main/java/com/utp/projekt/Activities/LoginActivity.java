package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

import org.json.JSONException;
import org.json.JSONObject;

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
        if(!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
            final String[] params = {login.getText().toString(), password.getText().toString()};
            Controller.callServiceJSON("login", params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    String[] params2;
                    try {
                        params2 = new String[]{response.getLong("id") + ""};
                        Controller.callServiceJSON("user", params2, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                Gson gson = new Gson();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                User user = gson.fromJson(response.toString(), User.class);
                                intent.putExtra("USER", user);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Controller.failure(statusCode, getApplicationContext());
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if(statusCode == 200){
                        Toast.makeText(getApplicationContext(), "Niepoprawny login lub hasło", Toast.LENGTH_LONG).show();
                    } else {
                        Controller.failure(statusCode, getApplicationContext());
                    }
                }
            });
        }
        else Toast.makeText(getApplicationContext(), "Podaj login i hasło", Toast.LENGTH_LONG).show();
    }

}
