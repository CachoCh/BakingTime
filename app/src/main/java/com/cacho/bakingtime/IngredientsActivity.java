package com.cacho.bakingtime;

import android.os.Bundle;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        String strObj = getIntent().getStringExtra("ingredientsObject");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = IngredientsFragment.newInstance(strObj);
        ft.replace(R.id.ingredients_fragment_placeholder, fragment);
        ft.commit();


    }
}