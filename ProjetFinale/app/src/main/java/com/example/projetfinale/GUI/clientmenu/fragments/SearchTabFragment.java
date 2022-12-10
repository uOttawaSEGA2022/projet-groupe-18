package com.example.projetfinale.GUI.clientmenu.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projetfinale.GUI.ClientOrder;
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
    String clientID;
    String clientFullName;

    //GUI variables
    protected ListView list_client_meals;
    Button Order;

    //
    List<String> lstCookID = new ArrayList<>();
    List<String> lstCookRestaurantName = new ArrayList<>();
    List<String> lstMealID = new ArrayList<>();
    List<String> lstMealName = new ArrayList<>();
    List<String> lstMealPrice = new ArrayList<>();
    List<Integer> lstMealAvailable = new ArrayList<>();


    Activity activity = getActivity();
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);
        list_client_meals = view.findViewById(R.id.list_client_meals);
        Order = (Button) view.findViewById(R.id.btnOrder);
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                OnOrders();
            }
        });


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Bundle args = getArguments();
        if (args != null){
            clientID = args.getString("clientID");
            clientFullName = args.getString("clientFullName");
        }

        displayMeal();
        return view;
    }

    public void OnOrders(){
        int position = list_client_meals.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(getActivity().getApplicationContext(), "Please select a meal", Toast.LENGTH_SHORT).show();

        } else {
            String cookID = lstCookID.get(position);
            String cookRestaurantName = lstCookRestaurantName.get(position);
            String mealID = lstMealID.get(position);
            String mealName = lstMealName.get(position);
            String mealPrice = lstMealPrice.get(position);
            Integer mealAvailable = lstMealAvailable.get(position);

            Intent i = new Intent(getActivity().getApplicationContext(), ClientOrder.class);
            i.putExtra("clientID", clientID);
            i.putExtra("clientFullName", clientFullName);
            i.putExtra("cookID", cookID);
            i.putExtra("cookRestaurantName", cookRestaurantName);
            i.putExtra("mealID", mealID);
            i.putExtra("mealName", mealName);
            i.putExtra("mealPrice", mealPrice);
            i.putExtra("mealAvailable", mealAvailable);
            startActivity(i);
        }
    }



    public void displayMeal() {
        //Get the list of all meals based on Firestore database

        db.collection("meal")
                .whereEqualTo("mealAvailable",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstMeal = new ArrayList<>();
                        lstCookID.clear();
                        lstCookRestaurantName.clear();
                        lstMealID.clear();
                        lstMealName.clear();
                        lstMealPrice.clear();
                        lstMealAvailable.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstMeal.add(document.get("cookRestaurantName").toString()+" : "
                                        + document.get("mealName").toString() + " ("
                                        + document.get("mealPrice").toString()
                                        + " $CAD)");
                                lstCookID.add(document.get("cookID").toString());
                                lstCookRestaurantName.add(document.get("cookRestaurantName").toString());
                                lstMealID.add(document.getId());
                                lstMealName.add(document.get("mealName").toString());
                                lstMealPrice.add(document.get("mealPrice").toString());
                                lstMealAvailable.add(Integer.parseInt(document.get("mealAvailable").toString()));

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