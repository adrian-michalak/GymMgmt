package com.example.myegineerapplication.company_panel;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myegineerapplication.DetailsCompany.PayAdapter;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.config.FirebaseInit;
import com.example.myegineerapplication.model.PaymentMemberModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class SubmitPaymentMemberAdapter extends FirestoreRecyclerAdapter<PaymentMemberModel, SubmitPaymentMemberAdapter.SubmitPaymentMemberHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SubmitPaymentMemberAdapter(@NonNull FirestoreRecyclerOptions<PaymentMemberModel> options) {
        super(options);
    }
    private String translate( String s){
        if ( s.toLowerCase().equals("concessionary") ){ s =  "Ulgowy"; }
        if (s.toLowerCase().equals("normal")){ s = "Normalny"; }
        if (s.toLowerCase().equals("gym")) { s = "Siłownia"; }
        return s;
    }
    @Override
    protected void onBindViewHolder(@NonNull SubmitPaymentMemberHolder holder, int position, @NonNull PaymentMemberModel model) {
        holder.email_tv.setText(model.getEmail());
        holder.id.setText(model.getUid());
        holder.entries.setText(model.getEntries());
        holder.membership_card_type.setText(translate(model.getMembership_card_type()));
        holder.phone.setText(model.getPhone());
        holder.ticket_type.setText(translate(model.getType()));
        holder.price.setText(model.getPrice());
        holder.user_name.setText(model.getUser_name());
        holder.date.setText(model.getDate());
        holder.gym_address.setText(model.getFull_gym_address());
    }

    @NonNull
    @Override
    public SubmitPaymentMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_pay_item,parent,false);
        return new SubmitPaymentMemberAdapter.SubmitPaymentMemberHolder(view);
      
    }

    public class SubmitPaymentMemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, user_name, email_tv,phone,entries,price,membership_card_type, ticket_type,date, gym_address;
        Button submit_user_payment,delete_member_pay_btn;
       // FirebaseFirestore db = FirebaseFirestore.getInstance();
        private final Map<String,Object> list = new HashMap<>();
        public SubmitPaymentMemberHolder(@NonNull View itemView) {
            super(itemView);
            ActivityCompat.requestPermissions((Activity) itemView.getContext(),new String[]{Manifest.permission.SEND_SMS},1);

            id = itemView.findViewById(R.id.member_pay_id);
            user_name = itemView.findViewById(R.id.member_pay_member);
            email_tv = itemView.findViewById(R.id.member_pay_email);
            phone = itemView.findViewById(R.id.member_pay_phone);
            entries = itemView.findViewById(R.id.member_pay_entries);
            price = itemView.findViewById(R.id.member_pay_price);
            membership_card_type = itemView.findViewById(R.id.member_pay_membership_card_type);
            ticket_type = itemView.findViewById(R.id.member_pay_type);
            date = itemView.findViewById(R.id.member_pay_date);
            gym_address = itemView.findViewById(R.id.member_pay_gym_address);
            submit_user_payment = itemView.findViewById(R.id.submit_member_pay_btn);
            delete_member_pay_btn = itemView.findViewById(R.id.delete_member_pay_btn);

            submit_user_payment.setOnClickListener(this);
            delete_member_pay_btn.setOnClickListener(this);
        }

        private void deleteItem(int pos){
            getSnapshots().getSnapshot(pos).getReference().delete();
        }
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.submit_member_pay_btn){
                String message = "Potwierdziliśmy Twoją chęć kupna karnetu. Karnet jest gotowy do odbioru w recepcji." +
                                 " \n Twoje dane zgłoszenia znajdziesz w sekcji 'Mój profil'" +
                                 " \n Płatność przy odbiorze.";

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{email_tv.getText().toString() });
                email.putExtra(Intent.EXTRA_SUBJECT, "Rezerwacja Twojego karnetu została potwierdzina. TheGyms");
                email.putExtra(Intent.EXTRA_TEXT, message);
                //need this to prompts email client only
                email.setType("message/rfc822");
                try{
                    itemView.getContext().startActivity(Intent.createChooser(email, "Wybierz swój klient email:"));
                }catch(android.content.ActivityNotFoundException e)
                {
                    Toast.makeText(itemView.getContext(), "Brak zainstalowanych klientów email", Toast.LENGTH_SHORT).show();
                }
                list.put("uid", id.getText());
                list.put("entries",  entries.getText());
                list.put("date", date.getText());
                list.put("price", price.getText());
                list.put("membership_card_type",membership_card_type.getText());
                list.put("ticket_type", ticket_type.getText());
                list.put("gym_full_address", gym_address.getText());

                FirebaseInit.db.collection("Users").document(email_tv.getText().toString()).collection("Memberships").document(id.getText().toString().trim())
                        .set(list);


            }
            if ( view.getId() == R.id.delete_member_pay_btn){
                Intent intent = new Intent(itemView.getContext(),CompanyPanel.class);
                String message = "Przepraszamy Twoja chęć kupna karnetu zostałą odrzucona \n Dzięujemy za zainteresowanie";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{email_tv.getText().toString() });
                email.putExtra(Intent.EXTRA_SUBJECT, "Rezerwacja Twojego karnetu została odrzucona. TheGyms");
                email.putExtra(Intent.EXTRA_TEXT, message);
                //need this to prompts email client only
                email.setType("message/rfc822");
                try{
                    itemView.getContext().startActivity(Intent.createChooser(email, "Chcesz wysłać wiadomość o odmowie?"));
                    FirebaseInit.db.collection("Users").document(email_tv.getText().toString().trim()).collection("Memberships")
                            .document(id.getText().toString().trim()).delete();

                }catch(android.content.ActivityNotFoundException e)
                {
                    Toast.makeText(itemView.getContext(), "Brak zainstalowanych klientów email", Toast.LENGTH_SHORT).show();
                }
                int pos = getAdapterPosition();
                deleteItem(pos);
                itemView.getContext().startActivity(intent);
            }
        }
    }


}
