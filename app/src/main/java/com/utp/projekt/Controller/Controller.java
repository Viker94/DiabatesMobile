package com.utp.projekt.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Marcin on 26.11.2016.
 */
public class Controller {

    private static JSONObject jsonObject;

    public JSONObject getSingleData(String table, String data , final Context context, final OnJSONResponseCallback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2:8080/" + table + "/" + data, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody, StandardCharsets.UTF_8);
                try {
                    if(string.length()>0) {
                        jsonObject = new JSONObject(string);
                        callback.onJSONResponse(true, jsonObject);
                    } else {
                        Toast.makeText(context, "Złe dane logowania", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 404){
                    Toast.makeText(context, "Nie znaleziono źródła", Toast.LENGTH_LONG).show();
                } else if(statusCode == 500){
                    Toast.makeText(context, "Błąd serwera", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Nieoczekiwany błąd", Toast.LENGTH_LONG).show();
                }
            }
        });
        return jsonObject;
    }

}
