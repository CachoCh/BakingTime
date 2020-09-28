package com.cacho.bakingtime.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.R;
import com.cacho.bakingtime.databinding.IngredientRowItemBinding;
import com.cacho.bakingtime.model.Recipe;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
   protected List<Recipe.Ingredient> mIngredientsDataset;
   protected boolean mSelected = false;

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
            this.binding.clickableframeFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mSelected){
                        view.setBackgroundResource(R.color.primaryDarkColor);
                        mSelected = true;
                    } else {
                        view.setBackgroundResource(R.color.appBackgroundColor);
                        mSelected = false;
                    }
                }
            });
        }

        /**
         * We will use this function to bind instance of Recipe.Step to the row
         */
        public void bind(Recipe.Ingredient ingredient) {
            binding.setIngredient(ingredient);
            switch (ingredient.getMeasure()){
                case "CUP":
                    binding.iconIv.setBackgroundResource(R.drawable.ic_measuring_cup);
                    break;
                case "TBLSP":
                case "TSP":
                    binding.iconIv.setBackgroundResource(R.drawable.ic_measuring_spoons);
                    break;
                case "K":
                case "G":
                case "OZ":
                    binding.iconIv.setBackgroundResource(R.drawable.ic_scale);
                    break;
                case "UNIT":
                default:
                    binding.iconIv.setBackgroundResource(R.drawable.ic_ingredients);
                    break;
            }
            binding.executePendingBindings();
        }
    }
}
