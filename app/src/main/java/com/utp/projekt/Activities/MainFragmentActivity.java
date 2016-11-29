package com.utp.projekt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.utp.projekt.Entities.User;
import com.utp.projekt.R;
import com.utp.projekt.Utils.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentActivity extends FragmentActivity {

    private PagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, MainActivity.class.getName()));
        fragments.add(Fragment.instantiate(this, GraphActivity.class.getName()));
        adapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }
}
