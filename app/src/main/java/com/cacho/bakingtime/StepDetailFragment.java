package com.cacho.bakingtime;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.databinding.FragmentStepDetailBinding;
import com.cacho.bakingtime.databinding.RecipeRowItemBinding;
import com.cacho.bakingtime.model.Recipe;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment {
    private List<Recipe.Steps>  mSteps;
    private Recipe.Steps  mCurrentStep;
    private Context mContext;
    private FragmentStepDetailBinding mBinding; //comes from fragment_step_detail.xml

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Integer mParam2;

   // private FragmentS binding; //auto generated binding class from recipe_row_item.xml

    public StepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment StepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepDetailFragment newInstance(String param1, Integer param2) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            Type listType = new TypeToken<List<Recipe.Steps>>() {}.getType();
            try {
                //mParam1 = mParam1.substring(1, mParam1.length()-1);
                mSteps = new Gson().fromJson(mParam1, listType);
            } catch(Exception e){
                e.printStackTrace();
            }
            mCurrentStep = mSteps.get(mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentStepDetailBinding.inflate(inflater, container, false);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_step_detail, container, false);
        mContext = getContext();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(mContext, "ua");
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        if(mCurrentStep != null) {
            ExtractorMediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(mCurrentStep.getVideoURL()),
                    dataSourceFactory,
                    new DefaultExtractorsFactory(),
                    null,
                    null,
                    null);
            player.prepare(mediaSource);
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
            player.setPlayWhenReady(true);
            player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }

        mBinding.playerView.setPlayer(player);
        return root;
    }
}