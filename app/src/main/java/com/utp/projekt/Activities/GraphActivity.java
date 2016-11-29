package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class GraphActivity extends Activity {
    LineGraphSeries<DataPoint> potassiumLimit;
    LineGraphSeries<DataPoint> potassium;
    LineGraphSeries<DataPoint> sodiumLimit;
    LineGraphSeries<DataPoint> sodium;
    LineGraphSeries<DataPoint> waterLimit;
    LineGraphSeries<DataPoint> water;




    double Ypotassium = 0;
    double Ysodium = 0;
    double Ywater = 0;
    User user;
    private int getDays(Date date)
    {
        return (int) (date.getTime() / (1000*60*60*24));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        user =  getIntent().getParcelableExtra("USER");
        Log.i("GRAPH1", user.getConsumptions().get(0).toString());
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Log.i("GRAPH2", user.getConsumptions().get(0).toString());

        /*Potas - kolor czerwony*/
        potassiumLimit = new LineGraphSeries<DataPoint>();
        potassiumLimit.setColor(Color.RED);

        potassium = new LineGraphSeries<DataPoint>();
        potassium.setColor(Color.RED);
        potassium.setThickness(2);

        /*Sod - kolor zielony*/
        sodiumLimit = new LineGraphSeries<DataPoint>();
        sodiumLimit.setColor(Color.GREEN);

        sodium = new LineGraphSeries<DataPoint>();
        sodium.setColor(Color.GREEN);
        sodium.setThickness(2);

         /*Woda - kolor niebieski*/
        waterLimit = new LineGraphSeries<DataPoint>();
        waterLimit.setColor(Color.BLUE);

        water = new LineGraphSeries<DataPoint>();
        water.setColor(Color.BLUE);
        water.setThickness(2);
        /* Koniec DataPointow */
        Log.i("GRAPH3", user.getConsumptions().get(0).toString());


        double x = user.getConsumptions().get(0).getProduct().getSodium();

        Log.i("asd", user.getClass().toString() + " " + user.getConsumptions().getClass().toString());
        potassium.appendData(new DataPoint(1, x),false,100);
        graph.addSeries(potassium);



    }
}
