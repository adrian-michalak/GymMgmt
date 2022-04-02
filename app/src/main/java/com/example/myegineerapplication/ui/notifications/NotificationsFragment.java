package com.example.myegineerapplication.ui.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allyants.notifyme.NotifyMe;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.NotificationModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection("Notification");

    ListView listView;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private Button btn_submit, btn_cancel;
    private TimePicker timePicker;
    private EditText etContent;
    private static final String TITLE = "Training";

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private CalendarView calendarView;
    private final Calendar calendar = Calendar.getInstance();

    private  Map<String,Object> notification = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        findView(root);
         btn_submit.setOnClickListener(this);
         chooseDay();
        return root;
    }
//=======================================================================
 /* private void setDate(){

         new TimePickerDialog(getContext(),timeSetListener,
              calendar.get(Calendar.HOUR_OF_DAY),
              calendar.get(Calendar.MINUTE),true).show();
         new DatePickerDialog(getContext(),dateSetListener,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();


    }*/
//=======================================================================
    private void cancelNotification(){
        NotifyMe.cancel(getContext(),"TEST");
    }
//=======================================================================


    /*  private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String title = "Remember";
                String body = etContent.getText().toString();
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND,0);

                //notification.put("hour", hourOfDay);
               // notification.put("minute", minute);
                System.out.println("GET TIME: "+ calendar.getTime());
                NotifyMe notifyMe = new NotifyMe.Builder(getContext())
                        .title(title)
                        .content(body)
                        .color(255,0,0,255)
                        .led_color(255,255,255,255)
                        .time(calendar.getTime())
                        .addAction(new Intent(),"Snooze", false)
                        .key("TEST")
                        .addAction(new Intent(),"Dismiss",true,false)
                        .addAction(new Intent(),"Done")
                        .large_icon(R.drawable.ic_info_24dp)
                        .build();
                notifyMe.getBuilder();

            }
        };
        private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };
*/
//=======================================================================

//    private void setNotificationContent(){
//        collection.document().set(notification);
//       // String note = etContent.getText().toString();
//        //collection.document().set(note);
//    }


    // FETCHING NOTIFICATIONS FROM FIREBASE
//    private void fetchNotification(){
//        Query query = collection.orderBy("date");
//        FirestoreRecyclerOptions<NotificationModel> options = new FirestoreRecyclerOptions.Builder<NotificationModel>()
//                .setQuery(query,NotificationModel.class).build();
//        options.getSnapshots();
//        adapter = new NotificationAdapter(options);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//    }

//=======================================================================
    private void findView(View view){
        etContent = view.findViewById(R.id.enter_notification_et);
        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        calendarView = view.findViewById(R.id.calendar_view);
        btn_submit = view.findViewById(R.id.submit_date_hour);
    }
    private void chooseDay(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                System.out.println("CALENDAR VIEW: " + month+1 + "."+ dayOfMonth+"."+year);

            }
        });
    }

//    private void getDateFromFirebase(){
//        DocumentReference documentReference = db.collection("Notification").document();
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                  int day = (int) documentSnapshot.get("dayOfMonth");
//                  int month = (int) documentSnapshot.get("month");
//                  int year = (int) documentSnapshot.get("year");
//
//                  System.out.println(day +"/"+month+"/"+year);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//    private void displayHours(){
//         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,hoursList);
//         listView.setAdapter(adapter);
//    }
//====================================================================================================================

//    private Notification setNotification(){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), default_notification_channel_id);
//        builder.setContentText(etContent.getText());
//        builder.setContentTitle("GymStyle");
//        builder.setSmallIcon(R.drawable.ic_check_circle);
//        builder.setAutoCancel(true);
//        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
//        return builder.build();
//    }

    private void setDate(){

       // Date date ;
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm",Locale.getDefault());
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.DAY_OF_MONTH);

        String title = "Powiadomienie";
        String body = etContent.getText().toString();

        int h =  timePicker.getHour();
        int m = timePicker.getMinute();
        calendar.set(Calendar.HOUR_OF_DAY,h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, 0);
       // date = calendar.getTime();
       // NotificationModel notificationModel = new NotificationModel(simpleDateFormat.format(date),body);
        //collection.document().set(notificationModel);

        if (body.isEmpty()){
            etContent.setError("Proszę podać treść powiadomienia");
            etContent.requestFocus();
        } else {
            NotifyMe notifyMe = new NotifyMe.Builder(getContext())
                    .small_icon(R.drawable.ic_fitness_center_black_24dp)
                    .title(title)
                    .content(body)
                    .color(255,0,0,255)
                    .led_color(255,255,255,255)
                    .time(calendar.getTime())
                    .addAction(new Intent(),"Snooze", false)
                    .key("TEST")
                    .addAction(new Intent(),"Dismiss",true,false)
                    .addAction(new Intent(),"Done")
                    .large_icon(R.drawable.ic_fitness_center_black_24dp)
                    .build();
            notifyMe.getBuilder();
            etContent.setText("");
            Toast.makeText(getContext(), "Powiadomienie dodano", Toast.LENGTH_SHORT).show();
        }

    }


//====================================================================================================================
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_date_hour) {
            setDate();
        }
    }


}