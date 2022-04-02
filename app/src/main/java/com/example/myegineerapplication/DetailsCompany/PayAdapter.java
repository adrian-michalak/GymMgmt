package com.example.myegineerapplication.DetailsCompany;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.PayModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.braintreepayments.api.internal.BraintreeSharedPreferences.getSharedPreferences;

public class PayAdapter extends FirestoreRecyclerAdapter<PayModel, PayAdapter.PayViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PayAdapter(@NonNull FirestoreRecyclerOptions<PayModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PayViewHolder holder, int position, @NonNull PayModel model) {
        holder.pay_duration_tv.setText(model.getDuration());
        holder.pay_price_tv.setText(model.getPrice());

        holder.gym_membership_card_type.setText(model.getGym_membership_card_type());
        holder.type.setText(model.getType());

    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_item,parent,false);
        return new PayViewHolder(view);
    }

    public class PayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pay_price_tv , pay_duration_tv,gym_membership_card_type,type;

        Button pay_btn;
        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            pay_price_tv = itemView.findViewById(R.id.pay_item_price_tv);
            pay_duration_tv = itemView.findViewById(R.id.pay_item_duration_tv);
            type = itemView.findViewById(R.id.pay_type_price_tv);
            gym_membership_card_type = itemView.findViewById(R.id.gym_membership_card_type_tv);
            pay_btn = itemView.findViewById(R.id.pay_buy_item_btn);
            pay_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.pay_buy_item_btn){
                Intent pay_intent = new Intent(v.getContext(), PaymentUserInformation.class);
                pay_intent.putExtra("pay_price", pay_price_tv.getText().toString());
                pay_intent.putExtra("pay_duration", pay_duration_tv.getText().toString());
                pay_intent.putExtra("gym_membership_card_type", gym_membership_card_type.getText().toString());
                pay_intent.putExtra("pay_type", type.getText().toString());

                v.getContext().startActivity(pay_intent);
            }
        }
    }
}
