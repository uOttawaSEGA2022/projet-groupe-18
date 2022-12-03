package com.example.projetfinale.GUI.clientmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetfinale.GUI.ClientComplaint;
import com.example.projetfinale.GUI.ClientLogin;
import com.example.projetfinale.GUI.CookWelcome;
import com.example.projetfinale.GUI.MainActivity;
import com.example.projetfinale.GUI.clientmenu.fragments.SearchTabFragment;
import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Firebase variables
    FirebaseAuth mAuth;
    //GUI variables
    Button Logout;
    Button Complaint;
    TabLayout tabLayout;
    ViewPager2 clientsMenuViewPager2;
    ClientMenuViewPagerAdapter clientsMenuTabsViewPagerAdapter;
    FragmentContainerView fragCtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        tabLayout = findViewById(R.id.client_tabs_layout);
        clientsMenuViewPager2 = findViewById(R.id.clients_menu_view_pager);
        clientsMenuTabsViewPagerAdapter = new ClientMenuViewPagerAdapter(this);
        clientsMenuViewPager2.setAdapter(clientsMenuTabsViewPagerAdapter);
        fragCtView = findViewById(R.id.fragment_container_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, SearchTabFragment.class, null)
                    .commit();
        }
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
        //Logout.setOnClickListener(view ->{
            //mAuth.signOut();
            //startActivity(new Intent(ClientMenu.this, MainActivity.class));
        //});
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
