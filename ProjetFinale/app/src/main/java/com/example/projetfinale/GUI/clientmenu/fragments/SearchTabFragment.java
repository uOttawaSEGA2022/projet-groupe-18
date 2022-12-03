package com.example.projetfinale.GUI.clientmenu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchTabFragment extends Fragment {
    //firebase variables
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    //GUI variables
    protected ListView list_client_meals;
    EditText editText;

    Activity activity = getActivity();
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);
        list_client_meals = view.findViewById(R.id.list_client_meals);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        editText = view.findViewById(R.id.txt_client_searchBar);
        displayMeal();
        return view;
    }

    public void displayMeal() {
        //Get the list of all meals based on Firestore database

        db.collection("meal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstMeal = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstMeal.add(document.get("mealName").toString() + " ("
                                        + document.get("mealPrice").toString()
                                        + " $CAD)");
                            }
                            ListView list_client_meals = getView().findViewById(R.id.list_client_meals);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),
                                    android.R.layout.simple_list_item_activated_1,lstMeal);
                            list_client_meals.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}