package com.example.projetfinale.GUI.cookmainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetfinale.GUI.MainActivity;
import com.example.projetfinale.GUI.clientmenu.ClientMenu;
import com.example.projetfinale.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class CookMainPage extends AppCompatActivity {
    //Firebase variables
    FirebaseAuth mAuth;

    //GUI variables
    ImageView myProfileButton;
    ImageView logoutButton;
    TabLayout cookMainPageTabLayout;
    ViewPager2 cookMainPageViewPager;
    CookMainPageViewPagerAdapter cookMainPageViewPagerAdapter;
    FloatingActionButton addMealFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main_page);

        myProfileButton = findViewById(R.id.btn_my_profile);
        logoutButton = findViewById(R.id.btn_logout);
        cookMainPageTabLayout = findViewById(R.id.cook_main_page_tabs_layout);
        cookMainPageViewPager =findViewById(R.id.cook_main_page_view_pager);
        cookMainPageViewPagerAdapter = new CookMainPageViewPagerAdapter(this);
        cookMainPageViewPager.setAdapter(cookMainPageViewPagerAdapter);

        // Callback methods for tabs
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

        // Callback method for ViewPager2 (for using tabs)
        cookMainPageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                cookMainPageTabLayout.getTabAt(position).select();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        myProfileButton.setOnClickListener(view ->{

            // ADD HERE WHAT TO DO FOR BUTTON "MY PROFILE"
            Toast.makeText(getApplicationContext(),"My Profile: to be implemented", Toast.LENGTH_SHORT).show();

        });
        logoutButton.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        // For the floating action button: Add meal
        addMealFAB = findViewById(R.id.add_meal_fab);
        addMealFAB.setOnClickListener(view ->{

            // ADD HERE WHAT TO DO FOR BUTTON "ADD MEAL"
            Toast.makeText(getApplicationContext(),"Add meal: to be implemented", Toast.LENGTH_SHORT).show();

        });
    }
}