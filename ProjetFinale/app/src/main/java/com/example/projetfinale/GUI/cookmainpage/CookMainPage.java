package com.example.projetfinale.GUI.cookmainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.projetfinale.R;
import com.google.android.material.tabs.TabLayout;

public class CookMainPage extends AppCompatActivity {

    TabLayout cookMainPageTabLayout;
    ViewPager2 cookMainPageViewPager;
    CookMainPageViewPagerAdapter cookMainPageViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main_page);

        cookMainPageTabLayout = findViewById(R.id.cook_main_page_tabs_layout);
        cookMainPageViewPager =findViewById(R.id.cook_main_page_view_pager);
        cookMainPageViewPagerAdapter = new CookMainPageViewPagerAdapter(this);
        cookMainPageViewPager.setAdapter(cookMainPageViewPagerAdapter);

        cookMainPageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cookMainPageViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        cookMainPageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                cookMainPageTabLayout.getTabAt(position).select();
            }
        });
    }
}