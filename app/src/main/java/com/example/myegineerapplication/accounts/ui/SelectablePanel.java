package com.example.myegineerapplication.accounts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myegineerapplication.R;

public class SelectablePanel extends AppCompatActivity implements View.OnClickListener {
    LinearLayout selectable_panel_user_btn, selectable_panel_gym_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectable_panel);

        selectable_panel_gym_btn = findViewById(R.id.selectable_panel_gym);
        selectable_panel_user_btn = findViewById(R.id.selectable_panel_user);

        selectable_panel_user_btn.setOnClickListener(this);
        selectable_panel_gym_btn.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        return false;
        // Disable back button..............
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectable_panel_user:
                Intent userIntent = new Intent(this, UserLogin.class);
                startActivity(userIntent);
                break;
            case R.id.selectable_panel_gym:
                Intent gymIntent = new Intent(this, SignIn.class);
                startActivity(gymIntent);
                break;
        }
    }
}
