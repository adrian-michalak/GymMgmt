package com.example.myegineerapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myegineerapplication.ui.account.AccountFragment;
import com.example.myegineerapplication.ui.searching.SearchingFragment;
import com.example.myegineerapplication.ui.home.HomeFragment;
import com.example.myegineerapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAppActivity extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_app);
        fragment = new HomeFragment();
        loadFragment(fragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                           fragment = new HomeFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_dashboard:
                            fragment = new SearchingFragment();
                            loadFragment(fragment);

                            return true;
                        case R.id.navigation_notifications:
                            fragment = new NotificationsFragment();
                            loadFragment(fragment);

                            return true;
                        case R.id.navigation_account:
                            fragment = new AccountFragment();
                            loadFragment(fragment);

                            return true;
                    }
                    return false;
                }
            };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
