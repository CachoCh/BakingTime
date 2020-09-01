package com.cacho.bakingtime;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.model.Recipe;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeInstructionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeInstructionsFragment extends Fragment {
    private RecyclerView mInstructionRecyclerView;
    private InstructionsAdapter mInstructionssAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;
    private Recipe mRecipe;

    public RecipeInstructionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeInstructions.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeInstructionsFragment newInstance(String param1) {
        RecipeInstructionsFragment fragment = new RecipeInstructionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            Gson gson = new Gson();
            mRecipe = gson.fromJson(mParam1, Recipe.class);
            mContext = getContext();
            mInstructionssAdapter = new InstructionsAdapter(mContext, mRecipe.getSteps());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_instructions, container, false);
        mContext = getContext();

        mInstructionRecyclerView = (RecyclerView) root.findViewById(R.id.instructions_rv);
        mInstructionRecyclerView.setHasFixedSize(true);
        mInstructionRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));


        if(mInstructionssAdapter != null){
            mInstructionRecyclerView.setAdapter(mInstructionssAdapter);
        }
        return root;
    }
}