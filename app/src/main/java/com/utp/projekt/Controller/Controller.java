package com.utp.projekt.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by Marcin on 27.11.2016.
 */

public class Controller {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String ip = "http://dialisys.azurewebsites.net/"; //ip serwera


    //funkcje odpowiedzialne za łączenie się z serwerem z podanymi parametrami
    public static void callServiceJSON(String db, String [] params, JsonHttpResponseHandler handler){
        String url = ip + db;
        for(String param : params){
            url+="/" + param;
        }
        client.get(url, handler);
    }

    public static void callServicAsync(String db, String [] params, AsyncHttpResponseHandler handler){
        String url = ip + db;
        for(String param : params){
            url+="/" + param;
        }
        client.get(url, handler);
    }

    //funkcja wyświetlająca napisy niepowodzeń
    public static void failure(int statusCode, Context context){
        if(statusCode == 404){
            Toast.makeText(context, "Nie znaleziono źródła", Toast.LENGTH_LONG).show();
        } else if(statusCode == 500){
            Toast.makeText(context, "Błąd serwera", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Nieoczekiwany błąd", Toast.LENGTH_LONG).show();
        }
    }
}
