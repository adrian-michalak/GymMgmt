package com.example.myegineerapplication.company_panel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.FitnessModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpdateFitnessAdapter extends FirestoreRecyclerAdapter<FitnessModel, UpdateFitnessAdapter.UpdateFitnessHolder> {
    private static String UPDATE = "UPDATE_FITNESS";
    private static String DELETE = "DELETE_FITNESS";
   // private OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UpdateFitnessAdapter(@NonNull FirestoreRecyclerOptions<FitnessModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UpdateFitnessHolder holder, int position, @NonNull FitnessModel model) {
        holder.update_fitness_name_et.setText(model.getFitnessName());
        holder.update_fitness_day_et.setText(model.getFitntessDayOfTraining());
        holder.update_fitness_hour_et.setText(model.getFitnesshour());
        holder.update_fitness_desc_et.setText(model.getFitnessDesc());
    }

    @NonNull
    @Override
    public UpdateFitnessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitness_update_item,parent,false);
        return new UpdateFitnessHolder(view);
    }


    class UpdateFitnessHolder extends RecyclerView.ViewHolder {
        EditText update_fitness_name_et, update_fitness_day_et, update_fitness_hour_et, update_fitness_desc_et;
        Button update_btn , delete_btn;

        private void deleteItem(int pos){
            getSnapshots().getSnapshot(pos).getReference().delete();
        }
        private void updateItem(int pos){
            getSnapshots().getSnapshot(pos).getReference().update(
                    "fitnessName", update_fitness_name_et.getText().toString() ,
                    "fitnessDesc", update_fitness_desc_et.getText().toString(),
                    "fitntessDayOfTraining" , update_fitness_day_et.getText().toString(),
                    "fitnesshour", update_fitness_hour_et.getText().toString());
        }
        UpdateFitnessHolder(@NonNull final View itemView) {
            super(itemView);
            update_fitness_hour_et = itemView.findViewById(R.id.update_txt_hour);
            update_fitness_name_et = itemView.findViewById(R.id.update_txt_fitness_name);
            update_fitness_day_et = itemView.findViewById(R.id.update_txt_day_of_training);
            update_fitness_desc_et = itemView.findViewById(R.id.update_txt_fitness_desc);

            update_btn = itemView.findViewById(R.id.update_fitness_item_btn);
            delete_btn = itemView.findViewById(R.id.delete_fitness_item_btn);

            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if  (v.getId() == R.id.update_fitness_item_btn ){
                        Log.d(UPDATE,update_fitness_name_et.getText().toString());
                            updateItem(pos);
                    }
                }
            });

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if  (v.getId() == R.id.delete_fitness_item_btn ){
                        Log.d(DELETE,update_fitness_name_et.getText().toString());
                        int pos = getAdapterPosition();
                        deleteItem(pos);
                    }
                }
            });
        }
    }
//
//    public interface OnItemClickListener{
//        void onItemClick(DocumentSnapshot documentSnapshot, int pos);
//    }
//
//    public void setOnItemClickListener( OnItemClickListener listener){
//        this.listener = listener;
//    }
}
