package com.example.myegineerapplication.company_panel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.FitnessUser;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FitnessMembersManagementAdapter extends FirestoreRecyclerAdapter<FitnessUser, FitnessMembersManagementAdapter.FitnessMembersManagementHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FitnessMembersManagementAdapter(@NonNull FirestoreRecyclerOptions<FitnessUser> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessMembersManagementHolder holder, int position, @NonNull FitnessUser model) {
        holder.fitness_member.setText(model.getUser_name());
        holder.fitness_member_day.setText(model.getDay());
        holder.fitness_member_hour.setText(model.getHour());
        holder.fitness_member_name.setText(model.getActivity_name());
    }

    @NonNull
    @Override
    public FitnessMembersManagementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitness_member,parent,false);
        return new FitnessMembersManagementHolder(view);
    }

    public class FitnessMembersManagementHolder extends RecyclerView.ViewHolder {
        TextView fitness_member_name,fitness_member_day,fitness_member_hour,fitness_member;
        Button fitness_member_delete_btn;

        private void deleteItem(int pos){
            getSnapshots().getSnapshot(pos).getReference().delete();
        }
        public FitnessMembersManagementHolder(@NonNull View itemView) {
            super(itemView);

            fitness_member_name = itemView.findViewById(R.id.fitness_member_activity_name);
            fitness_member = itemView.findViewById(R.id.fitness_member);
            fitness_member_hour = itemView.findViewById(R.id.fitness_member_hour);
            fitness_member_day = itemView.findViewById(R.id.fitness_member_day);
            fitness_member_delete_btn = itemView.findViewById(R.id.delete_fitness_member_btn);

            fitness_member_delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (view.getId() == R.id.delete_fitness_member_btn){
                        deleteItem(pos);
                    }
                }
            });
        }
    }
}
