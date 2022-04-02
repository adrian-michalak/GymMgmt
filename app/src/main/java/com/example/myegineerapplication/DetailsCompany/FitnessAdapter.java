package com.example.myegineerapplication.DetailsCompany;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.FitnessModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FitnessAdapter extends FirestoreRecyclerAdapter<FitnessModel, FitnessAdapter.FitnessViewHolder> {



    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FitnessAdapter(@NonNull FirestoreRecyclerOptions<FitnessModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessViewHolder holder, int position, @NonNull FitnessModel model) {
        holder.name_view_holder.setText(model.getFitnessName());
        holder.day_view_holder.setText(model.getFitntessDayOfTraining());
        holder.desc_view_holder.setText(model.getFitnessDesc());
        holder.hour_view_holder.setText(model.getFitnesshour());
    }

    @NonNull
    @Override
    public FitnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitness_item,parent,false);
        return new FitnessViewHolder(view);
    }

     class FitnessViewHolder extends RecyclerView.ViewHolder {

        TextView name_view_holder, day_view_holder, desc_view_holder,hour_view_holder;
        Button choose_fitness_btn;

        private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         private FirebaseFirestore db = FirebaseFirestore.getInstance();
         FitnessViewHolder(@NonNull final View itemView) {
            super(itemView);

            choose_fitness_btn = itemView.findViewById(R.id.choose_fitness_btn);

            name_view_holder = itemView.findViewById(R.id.txtfitnessname);
            day_view_holder = itemView.findViewById(R.id.txtdayoftraining);
            desc_view_holder = itemView.findViewById(R.id.txtfitnessdesc);
            hour_view_holder = itemView.findViewById(R.id.txthour);


            choose_fitness_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   String name_activity = name_view_holder.getText().toString();
                   String day = day_view_holder.getText().toString();
                   String hour = hour_view_holder.getText().toString();
                   String id =  user.getUid();
                   String user_name =  user.getDisplayName();
                   String phone = user.getPhoneNumber();
                   String email = user.getEmail();
                    Log.d("USER FITNESS: " , id +"\n"+user_name + "\n"+phone+"\n"+email );
                    Log.d("DETAILS FITNESS: " , name_activity +"\n"+day + "\n"+hour+"\n" );

                    Intent intent = new Intent(view.getContext(), SubmitFitnessRecord.class);

                    intent.putExtra("name_activity", name_activity);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("hour", hour);
                    intent.putExtra("email", email);
                    intent.putExtra("id", id);
                    intent.putExtra("day", day);

                    view.getContext().startActivity(intent);

                }
            });

        }
    }

}
