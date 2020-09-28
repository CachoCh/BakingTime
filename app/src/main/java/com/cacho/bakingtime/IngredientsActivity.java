package com.cacho.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class IngredientsActivity extends AppCompatActivity {
    String mStepsJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        String strObj = getIntent().getStringExtra("ingredientsObject");
        mStepsJson = getIntent().getStringExtra("stepsJson");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = IngredientsFragment.newInstance(strObj);
        ft.replace(R.id.ingredients_fragment_placeholder, fragment);
        ft.commit();
    }

    public void launchFistStep (View view){
        Intent i = new Intent(this, StepDetailActivity.class);
        i.putExtra("steps", mStepsJson);
        i.putExtra("position", 0);
        this.startActivity(i);
    }
}