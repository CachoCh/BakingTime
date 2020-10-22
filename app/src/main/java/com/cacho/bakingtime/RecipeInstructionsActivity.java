package com.cacho.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction ;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

public class RecipeInstructionsActivity extends AppCompatActivity {
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("recipeObject");
        mRecipe = gson.fromJson(strObj, Recipe.class);

        Configuration config = getResources().getConfiguration();

        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_recipes);

            //start with ingredients fragment
            launchIngredientsFragment(null);

        } else {
            setContentView(R.layout.activity_recipes);
        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = RecipeInstructionsFragment.newInstance(gson.toJson(mRecipe));
        ft.replace(R.id.recipe_instructions_placeholder, fragment);
        ft.commit();


    }

    public void launchIngredientsFragment(View view){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = IngredientsFragment.newInstance(new Gson().toJson(mRecipe.getIngredients()));
        ft.replace(R.id.detail_instructions_placeholder, fragment);
        ft.commit();
    }

    public void launchIngredientsActivity (View view){
        Intent i = new Intent(getApplicationContext(), IngredientsActivity.class);
        i.putExtra("ingredientsObject", new Gson().toJson(mRecipe.getIngredients()));
        i.putExtra("stepsJson", new Gson().toJson(mRecipe.getSteps()));
        this.startActivity(i);
    }
}