package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.Helper;

/**
 * Created by Marcin on 29.11.2016.
 */

public class MainActivity extends Fragment {

    private TextView name; //przywitanie
    private ProgressBar pp; //progress bary dla potasu, wody i sodu
    private ProgressBar pw;
    private ProgressBar ps;
    private TextView tp; //textview dla liczby obok progress bara
    private TextView tw;
    private TextView ts;
    private Space spacer;
    private Button buttonLimit;
    private Button eat;


    public static User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        this.onCreate(null);
    }


    public void startLimitActivity()
    {
        Intent intent = new Intent(getActivity(), LimitActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void openEat(){
        Intent intent = new Intent(getActivity(), EatActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        user = getActivity().getIntent().getParcelableExtra("USER");
        Log.i("MAIN", user.getConsumptions().get(0).toString());
        Log.i("MAIN", user.getNextVisit().toString());
        name = (TextView) rootView.findViewById(R.id.name);
        name.setText("Witaj: " + user.getFirstName() + " " + user.getLastName());
        buttonLimit = (Button) rootView.findViewById(R.id.limit);
        eat = (Button) rootView.findViewById(R.id.eat);
        pp = (ProgressBar) rootView.findViewById(R.id.pPotassium);
        pw = (ProgressBar) rootView.findViewById(R.id.pWater);
        ps = (ProgressBar) rootView.findViewById(R.id.pSodium);
        tp = (TextView) rootView.findViewById(R.id.ppPotassium);
        tw = (TextView) rootView.findViewById(R.id.ppWater);
        ts = (TextView) rootView.findViewById(R.id.ppSodium);
        pp.setProgressTintList(ColorStateList.valueOf(Helper.POTASSIUM_COLOR));
        pw.setProgressTintList(ColorStateList.valueOf(Helper.WATER_COLOR));
        ps.setProgressTintList(ColorStateList.valueOf(Helper.SODIUM_COLOR));

        pp.setProgress((int)((user.getPotassium()/user.getLimitPotassium())*100));
        pw.setProgress((int)((user.getWater()/user.getLimitWater())*100));
        ps.setProgress((int)((user.getSodium()/user.getLimitSodium())*100));
        tp.setText((int)((user.getPotassium()/user.getLimitPotassium())*100) + "%");
        tw.setText((int)((user.getWater()/user.getLimitWater())*100) + "%");
        ts.setText((int)((user.getSodium()/user.getLimitSodium())*100) + "%");

        buttonLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLimitActivity();
            }
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEat();
            }
        });
        return rootView;
    }
}
