package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.Products;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                                try {
                                    JSONArray array = response.getJSONArray("consumed");
                                    ArrayList<Consumption> consumed = new ArrayList<Consumption>();
                                    for(int i = 0; i<array.length();i++){
                                        consumed.add(new Consumption(array.getJSONObject(i).getLong("id"), gson.fromJson(array.getJSONObject(i).getString("product").toString(), Products.class), new Date(array.getJSONObject(i).getLong("date")), array.getJSONObject(i).getInt("amount")));
                                    }
                                    Log.i("LISTA", consumed.toString());
                                    user.setConsumptions(consumed);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
