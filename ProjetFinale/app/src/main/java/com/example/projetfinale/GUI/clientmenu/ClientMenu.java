package com.example.projetfinale.GUI.clientmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetfinale.GUI.ClientComplaint;
import com.example.projetfinale.GUI.ClientLogin;
import com.example.projetfinale.GUI.MainActivity;
import com.example.projetfinale.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button Logout;
    Button Complaint;
    FirebaseAuth mAuth;

    TabLayout tabLayout;
    ViewPager2 clientsMenuViewPager2;
    ClientMenuViewPagerAdapter clientsMenuTabsViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        tabLayout = findViewById(R.id.client_tabs_layout);
        clientsMenuViewPager2 = findViewById(R.id.clients_menu_view_pager);
        clientsMenuTabsViewPagerAdapter = new ClientMenuViewPagerAdapter(this);
        clientsMenuViewPager2.setAdapter(clientsMenuTabsViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                clientsMenuViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        clientsMenuViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        // Logout = findViewById(R.id.btn_logout);
        // Complaint = findViewById(R.id.btn_ComplaintPage);
        mAuth = FirebaseAuth.getInstance();
        Logout.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(ClientMenu.this, MainActivity.class));
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(ClientMenu.this, ClientLogin.class ));
        }
    }
    public void OnComplaint(View view){
        startActivity(new Intent(getApplicationContext(), ClientComplaint.class));
    }
    public void OnLogout(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), text+" functionality coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
