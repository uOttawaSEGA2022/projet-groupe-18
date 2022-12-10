package com.example.projetfinale.GUI.clientmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetfinale.GUI.ClientComplaint;
import com.example.projetfinale.GUI.ClientLogin;
import com.example.projetfinale.GUI.ClientProfile;
import com.example.projetfinale.GUI.CookLogin;
import com.example.projetfinale.GUI.CookMeal;
import com.example.projetfinale.GUI.CookOrder;
import com.example.projetfinale.GUI.CookWelcome;
import com.example.projetfinale.GUI.MainActivity;
import com.example.projetfinale.GUI.clientmenu.fragments.OrdersTabFragment;
import com.example.projetfinale.GUI.clientmenu.fragments.SearchTabFragment;
import com.example.projetfinale.GUI.cookProfil;
import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientMenu extends AppCompatActivity  {
    //Firebase variables
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    //GUI variables
    TabLayout tabLayout;
    ViewPager2 clientsMenuViewPager2;
    ClientMenuViewPagerAdapter clientsMenuTabsViewPagerAdapter;
    FragmentContainerView fragCtView;
    String clientEmail;
    String clientFirstName;
    String clientLastName;
    String clientFullName;
    String clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        tabLayout = findViewById(R.id.client_tabs_layout);
        fragCtView = findViewById(R.id.fragment_container_view);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0 & clientID != null)
                {
                    Bundle args = new Bundle();
                    args.putString("clientID", clientID);
                    args.putString("clientFullName", clientFullName);

                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.fragment_container_view, SearchTabFragment.class, args)
                            .commit();

                } else if(clientID != null){
                    Bundle args = new Bundle();
                    args.putString("clientID", clientID);
                    args.putString("clientFullName", clientFullName);
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.fragment_container_view, OrdersTabFragment.class, args)
                            .commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getApplicationContext(), ClientLogin.class ));
        } else
        {
            //Get client email
            clientEmail = user.getEmail().toLowerCase();
           getClientInfo();


        }

    }


    public void OnComplaint(View view){
        Intent i = new Intent(getApplicationContext(), ClientComplaint.class);
        i.putExtra("clientID",clientID);
        i.putExtra("clientFullName", clientFullName);
        startActivity(i);
      }
    public void OnLogout(View view){
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void OnProfileUpdate( View view){
        Intent i = new Intent(getApplicationContext(), ClientProfile.class);
        i.putExtra("clientID",clientID);
        i.putExtra("clientFirstname",clientFirstName);
        i.putExtra("clientLastname",clientLastName);
        i.putExtra("clientEmail",clientEmail);

        startActivity(i);
    }

    public void displayOrder(){
            Bundle args = new Bundle();
            args.putString("clientID", clientID);
            args.putString("clientFullName", clientFullName);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, SearchTabFragment.class, args)
                    .commit();
    }
    public void getClientInfo(){
        db.collection("client")
                .whereEqualTo("clientEmail",clientEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                clientID = document.getId().toString();
                                clientFirstName = document.get("clientFirstname").toString();
                                clientLastName = document.get("clientLastname").toString();
                                clientFullName = document.get("clientFullName").toString();
                                //Display username
                                TextView welcomeMsg = findViewById(R.id.txt_client_welcomeMsg);
                                welcomeMsg.setText("Welcome "+ clientFullName+ " ("+ clientEmail+")");

                            }
                            displayOrder();
                        } else {
                            Toast.makeText(getApplicationContext(), "Database client infos error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
