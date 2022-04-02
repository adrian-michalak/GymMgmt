package com.example.myegineerapplication.DetailsCompany;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.myegineerapplication.R;

import com.example.myegineerapplication.model.PostModel;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends FirestoreRecyclerAdapter<PostModel, PostAdapter.PostViewHolder> {

    PostAdapter(@NonNull FirestoreRecyclerOptions<PostModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull PostModel model) {

        holder.post_user_name_view_holder.setText(model.getUid());
        holder.post_date_view_holder.setText(model.getDate());
        holder.post_body_view_holder.setText(model.getBody());

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView post_user_name_view_holder, post_date_view_holder, post_body_view_holder;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            post_user_name_view_holder = itemView.findViewById(R.id.post_user_name);
            post_date_view_holder = itemView.findViewById(R.id.post_date);
            post_body_view_holder = itemView.findViewById(R.id.post_body);
        }
    }
}
