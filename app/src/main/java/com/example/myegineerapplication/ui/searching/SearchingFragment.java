package com.example.myegineerapplication.ui.searching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.myegineerapplication.R;


public class SearchingFragment extends Fragment implements View.OnClickListener {

    private EditText search, fitness_search;
    private ImageButton commit_search_localization, commit_fitness_search;
    private Button descending_btn, ascending_btn;
    private Bundle bundle;
    Fragment fragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        findView(root);

        commit_search_localization.setOnClickListener(this);
        descending_btn.setOnClickListener(this);
        ascending_btn.setOnClickListener(this);
        commit_fitness_search.setOnClickListener(this);
        fragment = new NotifyFragment();
        loadFragment(fragment);
        return root;
    }

    private void findView(View view){
         search = view.findViewById(R.id.search);
         fitness_search = view.findViewById(R.id.search_fitness);
         descending_btn= view.findViewById(R.id.descending_button);
         ascending_btn = view.findViewById(R.id.ascending_button);
         commit_search_localization = view.findViewById(R.id.commit_button_search);
         commit_fitness_search = view.findViewById(R.id.commit_button_search_fitness);
    }

    //===============================================================================================

    //===============================================================================================


    @Override
    public void onClick(View v) {


       if (v.getId() == R.id.descending_button){
            descending_btn.setBackgroundResource(R.drawable.change_btn_color);
            fragment = new DescendingSort();
            loadFragment(fragment);
        } else {
            descending_btn.setBackgroundResource(R.drawable.button_add_style);
        }

        if (v.getId() == R.id.ascending_button){
            ascending_btn.setBackgroundResource(R.drawable.change_btn_color);
            fragment = new AscendingSort();
            loadFragment(fragment);
        } else {
            ascending_btn.setBackgroundResource(R.drawable.button_add_style);
        }
        if (v.getId() == R.id.commit_button_search){
            fragment = new LocationSort();
            bundle = new Bundle();
            String bundleCityName = search.getText().toString().trim();
            bundle.putString("cityName", bundleCityName);
            fragment.setArguments(bundle);
            loadFragment(fragment);
            search.setText("");
        }
        if (v.getId() == R.id.commit_button_search_fitness){
            fragment = new FitnessSort();
            bundle = new Bundle();
            String bundleCityName = fitness_search.getText().toString().trim();
            bundle.putString("fitnessSearch", bundleCityName);
            fragment.setArguments(bundle);
            loadFragment(fragment);
            fitness_search.setText("");
        }
    }

    private void loadFragment(Fragment fragment) {
        // switch fragments layout
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}