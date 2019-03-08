package fr.castorflex.android.verticalviewpager.sample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.QTabView;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.TabAdapter;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.TabView;
import fr.castorflex.android.verticalviewpager.sample.verticaltablayout.VerticalTabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ViewPager vp = findViewById(R.id.vp);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new MainCenterFragment();
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

}
