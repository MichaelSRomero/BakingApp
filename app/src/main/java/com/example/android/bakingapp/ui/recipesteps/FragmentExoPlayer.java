package com.example.android.bakingapp.ui.recipesteps;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class FragmentExoPlayer extends Fragment {

    private static final String LOG = FragmentExoPlayer.class.getSimpleName();
    private final String SELECTED_POSITION = "selected_position";
    private final String PLAY_WHEN_READY = "play_when_ready";

    private Uri mVideoUri;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Step mStep;
    private long mPlayerPosition;
    private Boolean mPlayWhenReady = true;
    private Boolean mReleasePlayer = false;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exo_player, container, false);
        mPlayerView = rootView.findViewById(R.id.player_view);

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(Step.STEP_KEY);
            // Receive the saved position where the player left off at,
            // if there was none, then we set the variable to its default value "C.TIME_UNSET"
            mPlayerPosition = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        if (mStep != null) {
            mVideoUri = Uri.parse(mStep.getVideoURL());
            initializePlayer(mVideoUri);
        }

        return rootView;
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(
                    new AdaptiveVideoTrackSelection.Factory(bandwidthMeter));
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            // If the player position was set,
            // then we resume to where the video left off at
            if (mPlayerPosition != C.TIME_UNSET) {
                mExoPlayer.seekTo(mPlayerPosition);
            }
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /*
     * Set the current step from within {@RecipeStepsActivity} before beginning a fragment
     * transaction
     */
    public void setStep(Step currentStep) {
        mStep = currentStep;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (mVideoUri != null) {
                initializePlayer(mVideoUri);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            if (mVideoUri != null) {
                initializePlayer(mVideoUri);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayWhenReady = mExoPlayer.getPlayWhenReady();
        if (Util.SDK_INT <= 23) {
            if (mExoPlayer != null) {
                mPlayerPosition = mExoPlayer.getCurrentPosition();
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (mExoPlayer != null) {
                mPlayerPosition = mExoPlayer.getCurrentPosition();
                releasePlayer();
            }
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mExoPlayer != null) {
//            releasePlayer();
//        }
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Step.STEP_KEY, mStep);
        outState.putLong(SELECTED_POSITION, mPlayerPosition);
        outState.putBoolean(PLAY_WHEN_READY, mPlayWhenReady);
        super.onSaveInstanceState(outState);
    }
}
