package com.cacho.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cacho.bakingtime.databinding.FragmentStepDetailBinding;
import com.cacho.bakingtime.model.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment {
    private List<Recipe.Steps>  mSteps;
    private FragmentStepDetailBinding mBinding; //comes from fragment_step_detail.xml

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private MediaSource videoSource;
    private DefaultBandwidthMeter bandwidthMeter;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private TrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Integer mStepIndex;

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
            mStepIndex = getArguments().getInt(ARG_PARAM2);
            Type listType = new TypeToken<List<Recipe.Steps>>() {}.getType();
            try {
                mSteps = new Gson().fromJson(mParam1, listType);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentStepDetailBinding.inflate(inflater, container, false);
        mBinding.setLifecycleOwner(this);
        mBinding.setStep(mSteps.get(mStepIndex));
        mBinding.executePendingBindings();
        mPlayerView = mBinding.playerView;

        setupOnClickListeners();
        setButtonsVisibility();

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setupPlayer();
        }
    }

    private void setupPlayer() {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(),
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mPlayerView.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(true);
        }
        if (!mBinding.getStep().getVideoURL().isEmpty()) {
            MediaSource mediaSource = buildMediaSource(Uri.parse(mBinding.getStep().getVideoURL()));
            mPlayer.prepare(mediaSource, true, false);
        } else{
            mPlayer.stop(true);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = "bakingtime";

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ProgressiveMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else {
            DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                    new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
            DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).
                    createMediaSource(uri);
        }
    }

    /**
     * needs to be this way because of the fragment
     */
    private void setupOnClickListeners() {
        mBinding.nextStepBt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                moveStep(v);
            }
        });

        mBinding.previousStepBt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                moveStep(v);
            }
        });
    }

    public void moveStep(@NotNull View view) {
        boolean isNextStep = view.getId() == mBinding.nextStepBt.getId();

        if (isNextStep && (mStepIndex < mSteps.size())) {
            mStepIndex++;
        } else if (mStepIndex > 0) {
            mStepIndex--;
        }
        setButtonsVisibility();
        mBinding.setStep(mSteps.get(mStepIndex));
        setupPlayer();
        mBinding.notifyChange();

    }

    private void setButtonsVisibility(){
        if (mStepIndex <= 0) {
            mBinding.previousStepBt.setText(R.string.ingredients);
            if(mStepIndex == 0){
                mBinding.previousStepBt.setVisibility(View.INVISIBLE);
            }
        } else if (mStepIndex >=  (mSteps.size() - 1)) {
            mBinding.nextStepBt.setVisibility(View.INVISIBLE);
        } else {
            mBinding.nextStepBt.setVisibility(View.VISIBLE);
            mBinding.nextStepBt.setText(R.string.next_step);
            mBinding.previousStepBt.setVisibility(View.VISIBLE);
            mBinding.previousStepBt.setText(R.string.prev_step);
        }
    }




/*    private void getPlayer3() {
        mBinding.playerView.setVisibility(View.VISIBLE);
        if (mPlayer == null) {        // 1. Create a default TrackSelector
            bandwidthMeter = new DefaultBandwidthMeter();
            videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            mPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            // Bind the player to the view.
            mBinding.playerView.setPlayer(mPlayer);

            // Produces DataSource instances through which media data is loaded.
            dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "YOUR_APP_NAME"), bandwidthMeter);

            // This is the MediaSource representing the media to be played.
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mCurrentStep.getVideoURL()));
            // Prepare the player with the source.
            mPlayer.prepare(videoSource);
            mPlayer.setPlayWhenReady(true);
        }
    }*/




    @Override
    public void onResume() {
        super.onResume();
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (null != mPlayer) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}