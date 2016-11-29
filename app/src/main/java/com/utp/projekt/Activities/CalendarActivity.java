package com.utp.projekt.Activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.utp.projekt.R;

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
        return rootView;
    }
}
