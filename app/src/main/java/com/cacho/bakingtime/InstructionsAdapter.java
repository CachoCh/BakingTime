package com.cacho.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.databinding.InstructionRowItemBinding;
import com.cacho.bakingtime.databinding.RecipeRowItemBinding;
import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* check these out
https://medium.com/@sanjeevy133/an-idiots-guide-to-android-recyclerview-and-databinding-4ebf8db0daff
https://medium.com/androiddevelopers/android-data-binding-recyclerview-db7c40d9f0e4
        */



public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {
    protected List<Recipe.Steps> mStepsDataSet;
    private int mPosition;
    private Context mContext;


    public InstructionsAdapter(@NonNull Context context, @NonNull List<Recipe.Steps> recipeDataSet) {
        mContext = context;
        mStepsDataSet = recipeDataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InstructionRowItemBinding itemBinding = InstructionRowItemBinding.inflate(layoutInflater, parent, false);
        return new InstructionViewHolder(itemBinding, mContext, mStepsDataSet);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder viewHolder, int position) {
        Recipe.Steps step = mStepsDataSet.get(position);
        viewHolder.bind(step, position);
    }

    @Override
    public int getItemCount() {
        return mStepsDataSet != null ? mStepsDataSet.size() : 0;
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder {
        private InstructionRowItemBinding binding; //auto generated binding class from instruction_row_item.xml

        public InstructionViewHolder(final InstructionRowItemBinding binding, final Context context, List<Recipe.Steps> mStepsDataSet) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, StepDetailActivity.class);
                    i.putExtra("steps", new Gson().toJson(mStepsDataSet));
                    i.putExtra("position", binding.getPosition());
                    context.startActivity(i);
                }
            });
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Recipe.Steps step, int position) {
            binding.setStep(step);
            binding.setPosition(position);
            binding.executePendingBindings();
        }

    }
}
