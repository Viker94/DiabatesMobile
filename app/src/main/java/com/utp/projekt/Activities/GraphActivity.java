package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class GraphActivity extends Fragment {

    LineGraphSeries<DataPoint> potassiumLimit;
    LineGraphSeries<DataPoint> potassium;
    LineGraphSeries<DataPoint> sodiumLimit;
    LineGraphSeries<DataPoint> sodium;
    LineGraphSeries<DataPoint> waterLimit;
    LineGraphSeries<DataPoint> water;
    HashMap<Integer,Double[]> map;

    GraphView graph;


    double Ypotassium = 0;
    double Ysodium = 0;
    double Ywater = 0;
    User user;
    private int getDays(Date date)
    {
        return (int) (date.getTime() / (1000*60*60*24));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_graph, container, false);
        user = getActivity().getIntent().getParcelableExtra("USER");
        Log.i("GRAPH1", user.getConsumptions().get(0).toString());
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
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

        map = new HashMap<Integer,Double[]>();
        double x = user.getConsumptions().get(0).getProduct().getSodium();
        Date today = new Date();
        int day = 0;
        Log.i("asd", user.getClass().toString() + " " + user.getConsumptions().getClass().toString());
        for(Consumption c : user.getConsumptions())
        {
            Log.i("AppendData", c.getProduct().toString());
            Log.i("Dni",getDays(c.getDate())+"");
            day = getDays(today)-getDays(c.getDate());
            if(map.containsKey(day))
            {
                Double[] doubles = new Double[3];
                doubles[0] = map.get(day)[0]+(c.getAmount()*c.getProduct().getPotassium());
                doubles[1] = map.get(day)[1]+(c.getAmount()*c.getProduct().getSodium());
                doubles[2] = map.get(day)[2]+(c.getAmount()*c.getProduct().getWater());
                map.put(day,doubles);
                Log.i("add",day+"P: "+doubles[0]+"S: "+doubles[1]+"W: "+doubles[2]);
            }
            else
            {
                Double[] doubles = new Double[3];
                doubles[0] = c.getAmount()*c.getProduct().getPotassium();
                doubles[1] = c.getAmount()*c.getProduct().getSodium();
                doubles[2] = c.getAmount()*c.getProduct().getWater();
                map.put(day,doubles);
                Log.i("new",day+"P: "+doubles[0]+"S: "+doubles[1]+"W: "+doubles[2]);
            }
            potassium.appendData(new DataPoint(getDays(new Date())-getDays(c.getDate()), c.getProduct().getPotassium()),false,100);
        }
        //potassium.appendData(new DataPoint(1, x),false,100);
        //potassium.appendData(new DataPoint(10, x),false,100);
        graph.addSeries(potassium);
        return rootView;
    }
}
