package com.utp.projekt;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.utp.projekt.Entities.Login;
import com.utp.projekt.Utils.ConnectClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends Activity {

    ConnectClass connectClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectToDB();
    }

    private void connectToDB(){
        connectClass = new ConnectClass();
        DoLogin doLogin = new DoLogin();
        doLogin.execute("");
    }

    public class DoLogin extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            Connection conn = connectClass.connection();
            if(conn == null){
                return "Błąd połączenia z bazą";
            } else {
                String querry = "SELECT * FROM login";
                String login = "";
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(querry);
                    if(rs.next()){
                        login = rs.getString("Login") + " " + rs.getString("Password");
                        Log.i("INFO", login);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return login;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
