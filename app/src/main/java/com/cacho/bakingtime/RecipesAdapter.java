package com.cacho.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.databinding.RecipeRowItemBinding;
import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* check these out
https://medium.com/@sanjeevy133/an-idiots-guide-to-android-recyclerview-and-databinding-4ebf8db0daff
https://medium.com/androiddevelopers/android-data-binding-recyclerview-db7c40d9f0e4
        */



public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    protected Recipe[] mRecipeDataSet; //TODO: to generic
    private Context mContext;


    public RecipesAdapter(Context context, Recipe[] recipeDataSet) {
        mContext = context;
        mRecipeDataSet = recipeDataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeRowItemBinding itemBinding = RecipeRowItemBinding.inflate(layoutInflater, parent, false);
        return new RecipeViewHolder(itemBinding, mContext);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder viewHolder, int position) {
        Recipe recipe = mRecipeDataSet[position];
        viewHolder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipeDataSet != null ? mRecipeDataSet.length : 0;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
       private RecipeRowItemBinding binding; //auto generated binding class from recipe_row_item.xml

        public RecipeViewHolder(final RecipeRowItemBinding binding, final Context context) {
            super(binding.getRoot());
            this.binding = binding;
            // Define click listener for the ViewHolder's View.
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();

                    Intent i = new Intent(context, RecipeInstructionsActivity.class);
                    i.putExtra("recipeObject", gson.toJson(binding.getRecipe()));
                    context.startActivity(i);
                }
            });
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }

    }
}
