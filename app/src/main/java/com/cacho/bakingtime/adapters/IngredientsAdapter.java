package com.cacho.bakingtime.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.StepDetailActivity;
import com.cacho.bakingtime.databinding.IngredientRowItemBinding;
import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
   protected List<Recipe.Ingredient> mIngredientsDataset;

   public IngredientsAdapter(@NonNull List<Recipe.Ingredient> ingredients){
       mIngredientsDataset = ingredients;
   }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IngredientRowItemBinding itemBinding = IngredientRowItemBinding.inflate(layoutInflater, parent, false);
        return new IngredientViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
       holder.bind(mIngredientsDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredientsDataset != null ? mIngredientsDataset.size() : 0;
    }

    public class IngredientViewHolder  extends RecyclerView.ViewHolder {
        private IngredientRowItemBinding binding;


        public IngredientViewHolder(final IngredientRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: change colour of the view to indicate you have it now
                }
            });
        }

        /**
         * We will use this function to bind instance of Recipe.Step to the row
         */
        public void bind(Recipe.Ingredient ingredient) {
            binding.setIngredient(ingredient);
            binding.executePendingBindings();
        }
    }
}
