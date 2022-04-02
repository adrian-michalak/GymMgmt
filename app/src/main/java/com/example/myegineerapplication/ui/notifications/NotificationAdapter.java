package com.example.myegineerapplication.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allyants.notifyme.NotifyMe;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.NotificationModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends FirestoreRecyclerAdapter<NotificationModel, NotificationAdapter.NotificationViewHolder> {


     NotificationAdapter(@NonNull FirestoreRecyclerOptions<NotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull NotificationModel model) {
        holder.notification_body_view_holder.setText(model.getBody());
        holder.notification_date_view_holder.setText(model.getDate());
        //holder.note_hour_view_holder.setText(model.getHour());
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new NotificationViewHolder(view);
    }

     class NotificationViewHolder extends RecyclerView.ViewHolder  {
         Button delete_note_btn;
        TextView notification_body_view_holder,notification_date_view_holder;
         NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
             notification_body_view_holder = itemView.findViewById(R.id.notifications_body_tv);
             notification_date_view_holder = itemView.findViewById(R.id.notifications_date_tv);
             //delete_note_btn = itemView.findViewById(R.id.cancel_btn);

           // note_hour_view_holder = itemView.findViewById(R.id.notification_time);
        }
    }
}
