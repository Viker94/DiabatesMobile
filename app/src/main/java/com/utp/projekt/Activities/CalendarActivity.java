package com.utp.projekt.Activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.utp.projekt.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_calendar, container, false);
        CalendarView calendar = (CalendarView) rootView.findViewById(R.id.calendarView2);
        TextView text = (TextView) rootView.findViewById(R.id.date);
        calendar.setDate(MainActivity.user.getNextVisit().getTime());
        Date today = new Date();
        long diff = MainActivity.user.getNextVisit().getTime() - today.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if(days>0) {
            text.setText("Do następnej wizyty pozostało: " + days + " dni");
        } else if(days == 0){
            text.setText("Dziś masz kolejną wizytę!");
        } else {
            text.setText("Przegapiłeś wizytę!");
        }
        return rootView;
    }
}
