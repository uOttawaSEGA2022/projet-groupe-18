package com.example.projetfinale.GUI.clientmenu.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetfinale.GUI.CookWelcome;
import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersTabFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    //GUI variables
    protected ListView list_client_orders;

    String clientID;

    Activity activity = getActivity();

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_tab, container, false);
        list_client_orders = view.findViewById(R.id.list_client_orders);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Bundle args = getArguments();
        clientID = args.getString("clientID");
        displayOrders(clientID);
        return view;
    }


    public void displayOrders(String clientID) {
        //Get the list of all orders
        db.collection("orders")
                .whereEqualTo("clientID",clientID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstOrders = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstOrders.add(document.get("mealFullName").toString()
                                        + " X " + document.get("quantity").toString()
                                        + "\n\tStatus: " + document.get("orderStatus"));
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),
                                    android.R.layout.simple_list_item_activated_1,lstOrders);
                            list_client_orders.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(getContext(), "Database error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}