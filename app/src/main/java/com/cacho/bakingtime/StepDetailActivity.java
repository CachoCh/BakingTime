package com.cacho.bakingtime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Integer position = getIntent().getIntExtra("position", -1);
        String stepsJson = getIntent().getStringExtra("steps");

        Fragment fragment = StepDetailFragment.newInstance(stepsJson, position);
        getSupportFragmentManager().beginTransaction().add(R.id.your_placeholder, fragment).commit();

    }
}