package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utp.projekt.Controller.Controller;
import com.utp.projekt.Entities.Consumption;
import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import cz.msebera.android.httpclient.Header;

public class GraphActivity extends Fragment {

    LineGraphSeries<DataPoint> limit;
    LineGraphSeries<DataPoint> potassium;
    LineGraphSeries<DataPoint> sodium;
    LineGraphSeries<DataPoint> water;
    HashMap<Integer,Double[]> map2;

    GraphView graph;


    double Ypotassium = 0;
    double Ysodium = 0;
    double Ywater = 0;
    User user;
    private int getDays(Date date)
    {
        return (int) (date.getTime() / (1000*60*60*24));
    }
    private int getDays(Long ms) { return (int) (ms/(1000*60*60*24));}
    private boolean checkMap()
    {
        int day = getDays((new Date()).getTime());
        double[] params = new double[3];
        params[0] = user.getPotassium();
        params[1] = user.getSodium();
        params[2] = user.getWater();
        if(map2.size()==0) return true;
        Log.e("Exception", "1");
        if(!(map2.containsKey(day))) return true;
        Log.e("Exception", "2");
        if(map2.containsKey(day))
        {
            if(map2.get(day)[0].doubleValue()!=params[0]) return true;
            if(map2.get(day)[1].doubleValue()!=params[1]) return true;
            if(map2.get(day)[2].doubleValue()!=params[2]) return true;
        }
        Log.e("Exception", "3");

        return false;
    }
    private void writeToFile(User user,Context context) {
        if(checkMap()) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("history.diabetes", Context.MODE_APPEND));
                outputStreamWriter.append(new Date().getTime() + "|" + user.getPotassium() + "|" + user.getSodium() + "|" + user.getWater() + "\n");
                Log.e("Exception", "Saved: " + new Date().getTime() + "|" + user.getPotassium() + "|" + user.getSodium() + "|" + user.getWater());
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }
    // DATA | POTASIUM | SODIUM | WATER
    // [0]  |    [1]   |   [2]  |  [3]
    private HashMap<Integer,Double[]> readFromFile(Context context) {
        HashMap<Integer,Double[]> retHashMap = new HashMap<Integer,Double[]>();
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("history.diabetes");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(receiveString!="") {
                        stringBuilder.append(receiveString);
                        Double[] params = new Double[3];
                        String[] stringSplited = new String[4];
                        int day = -1337;

                        try {
                            //Log.e("Exception", "Rec: " + receiveString);
                            stringSplited = receiveString.split("\\|");
                            //Log.e("x", "[day]: " +stringSplited[1]);
                            //Log.e("x", "[day]: " + Long.valueOf(stringSplited[0]).longValue());
                            params[0] = Double.valueOf(stringSplited[1]);
                            //Log.e("x", "[0]: " + params[0]);
                            params[1] = Double.valueOf(stringSplited[2]);
                            //Log.e("x", "[1]: " + params[1]);
                            params[2] = Double.valueOf(stringSplited[3]);
                            //Log.e("x", "[2]: " + params[2]);
                            day = getDays(Long.valueOf(stringSplited[0]));

                            //Log.e("Exception", "Added: " + day + params);
                            retHashMap.put(day, params);
                        } catch (Exception e) {
                            Log.e("Exception", "ErrorParsingString: " + e.toString());
                        }


                    }

                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }

        return retHashMap;
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

        /*Limit 100% - przerywany*/
        limit = new LineGraphSeries<DataPoint>();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        paint.setPathEffect(new DashPathEffect(new float[] { 4, 2, 4, 2 }, 0));
        limit.setCustomPaint(paint);

        /*Potas - kolor zolty*/
        potassium = new LineGraphSeries<DataPoint>();
        potassium.setColor(Helper.POTASSIUM_COLOR);
        potassium.setThickness(10);

        /*Sod - kolor zielony*/
        sodium = new LineGraphSeries<DataPoint>();
        sodium.setColor(Helper.SODIUM_COLOR);
        sodium.setThickness(10);

         /*Woda - kolor niebieski*/
        water = new LineGraphSeries<DataPoint>();
        water.setColor(Helper.WATER_COLOR);
        water.setThickness(10);
        /* Koniec DataPointow */

        map2 = new HashMap<Integer,Double[]>();
        map2 = readFromFile(getContext());
        writeToFile(user,getContext());
        SortedMap<Integer,Double[]> map = new TreeMap<Integer,Double[]>();
        Calendar ca = Calendar.getInstance();
        int day = getDays(ca.getTime().getTime());
        for( Integer i : map2.keySet())
        {
            if(day-i<10)
            {
                map.put(day-i,map2.get(i));
            }
        }
        for(int i = -6 ; i<=0;i++)
        {
            if(map.containsKey(i))
            {
                Ypotassium = map.get(i)[0];
                Ysodium = map.get(i)[1];
                Ywater = map.get(i)[2];

            }
            else
            {
                Ypotassium = 0;
                Ysodium = 0;
                Ywater = 0;
            }
            potassium.appendData(new DataPoint(i, (Ypotassium / user.getLimitPotassium()) * 100), false, 10);
            sodium.appendData(new DataPoint(i, ((Ysodium) / user.getLimitSodium()) * 100), false, 10);
            water.appendData(new DataPoint(i, ((Ywater) / user.getLimitWater()) * 100), false, 10);
        }

        //Stare z produktami moze przyda sie kiedys
        /*
        int day = 0;
        Log.i("asd", user.getClass().toString() + " " + user.getConsumptions().getClass().toString());
        for(Consumption c : user.getConsumptions())
        {
            Log.i("AppendData", c.getProduct().toString());
            Log.i("Dni",getDays(c.getDate().getTime())+""+c.getDate().toString());
            day = getDays(c.getDate())-getDays(today);
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

        }



        int smallest = 10;
        Log.i("Date",new Date(2016,11,29).getTime()+"");
        Log.i("DateNow",new Date().getTime()+"");
        Log.i("Keyset",map.keySet()+"");
        for(Integer i : map.keySet())
        {
            if(i<=0&&i>=-6) {
                Ypotassium += map.get(i)[0];
                Ysodium += map.get(i)[1];
                Ywater += map.get(i)[2];
                potassium.appendData(new DataPoint(i, (Ypotassium / user.getLimitPotassium()) * 100), false, 10);
                sodium.appendData(new DataPoint(i, ((Ysodium) / user.getLimitSodium()) * 100), false, 10);
                water.appendData(new DataPoint(i, ((Ywater) / user.getLimitWater()) * 100), false, 10);
                Log.i("ForAc", i + " " + map.get(i)[0] + "");
                //if (i < smallest) smallest = i;
            }

        }

        */
        limit.appendData(new DataPoint(-6,100),false,10);
        limit.appendData(new DataPoint(1,100),false,10);
        graph.getViewport().setMinX(-6);
        graph.getViewport().setMaxX(1);
        graph.getViewport().setXAxisBoundsManual(true);
        potassium.setDrawDataPoints(true);
        water.setDrawDataPoints(true);
        sodium.setDrawDataPoints(true);
        potassium.setAnimated(true);
        water.setAnimated(true);
        sodium.setAnimated(true);



        potassium.setTitle("Potas");
        water.setTitle("Woda");
        sodium.setTitle("Sód");
        limit.setColor(Color.RED);
        limit.setTitle("Limit");
        graph.setTitle("7 Dniowy wykres zawartości pierwiastków");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Dni wstecz (0 - dzisiaj)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Procent limitu");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.addSeries(limit);
        graph.addSeries(potassium);
        graph.addSeries(sodium);
        graph.addSeries(water);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);


        return rootView;
    }
}
