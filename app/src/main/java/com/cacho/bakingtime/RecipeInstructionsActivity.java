package com.cacho.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction ;
import android.os.Bundle;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

public class RecipeInstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("recipeObject");
        Recipe recipe = gson.fromJson(strObj, Recipe.class);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = RecipeInstructionsFragment.newInstance(gson.toJson(recipe));
        ft.replace(R.id.recipe_instructions_frag, fragment);
        ft.commit();


    }
}