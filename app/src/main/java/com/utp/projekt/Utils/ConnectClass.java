package com.utp.projekt.Utils;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Marcin on 24.11.2016.
 */

public class ConnectClass {

    String classs = "com.mysql.jdbc.Driver";

    @SuppressLint("NewApi")
    public Connection connection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connUrl = null;
        try{
            Class.forName(classs);
            connUrl = "jdbc:mysql://mysql3.gear.host:3306/dialysisapp";
            conn = DriverManager.getConnection(connUrl, "appuser1", "zaq1@WSX");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
