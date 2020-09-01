package com.cacho.bakingtime;

import android.os.Bundle;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = StepDetailFragment.newInstance(stepsJson, position);
        ft.replace(R.id.step_detail_frag, fragment);
        ft.commit();


    }
}