package com.example.myegineerapplication.ui.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.myegineerapplication.DetailsCompany.About;
import com.example.myegineerapplication.DetailsCompany.DetailsActivity;
import com.example.myegineerapplication.R;

import com.example.myegineerapplication.model.CompanyModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragmentAdapter extends FirestoreRecyclerAdapter<CompanyModel, HomeFragmentAdapter.HomeFragmentViewHolder>{
    private static final String DOCUMENT = "gymName";

    public HomeFragmentAdapter(@NonNull FirestoreRecyclerOptions<CompanyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final HomeFragmentViewHolder holder, final int position, @NonNull final CompanyModel model) {

        holder.gym_name_view_holder.setText(model.getName());
        holder.city_view_holder.setText(model.getCity());
        holder.price_view_holder.setText(String.valueOf(model.getPrice()));
        holder.address_view_holder.setText(model.getStreet());
        holder.number_view_holder.setText(model.getNumber());
        holder.id_view_holder.setText(model.getId());

    }

    @NonNull
    @Override
    public HomeFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item,parent,false);
        return new HomeFragmentViewHolder(view);
    }

    class HomeFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gym_name_view_holder, address_view_holder, price_view_holder, city_view_holder, number_view_holder, id_view_holder;
        Button choose_btn;

        HomeFragmentViewHolder(@NonNull final View itemView ) {
            super(itemView);

            address_view_holder = itemView.findViewById(R.id.txtStreet);
            price_view_holder = itemView.findViewById(R.id.txtPrice);
            gym_name_view_holder = itemView.findViewById(R.id.txtName);
            city_view_holder = itemView.findViewById(R.id.txtCity);
            number_view_holder = itemView.findViewById(R.id.txtNumber);
            id_view_holder = itemView.findViewById(R.id.txtId);

            choose_btn = itemView.findViewById(R.id.choose_btn);
            choose_btn.setOnClickListener(this);
        }

            @Override
            public void onClick(View v) {

                Log.d("DETAIL: ",id_view_holder.getText().toString());

                Intent intent = new Intent(v.getContext(),DetailsActivity.class);
                intent.putExtra(DOCUMENT, id_view_holder.getText().toString());
                v.getContext().startActivity(intent);

            }


    }

}
