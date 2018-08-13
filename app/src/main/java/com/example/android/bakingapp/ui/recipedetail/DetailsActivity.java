package com.example.android.bakingapp.ui.recipedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipesteps.FragmentExoPlayer;
import com.example.android.bakingapp.ui.recipesteps.FragmentStepsDescription;

public class DetailsActivity extends AppCompatActivity {

    private final String LOG = DetailsActivity.class.getSimpleName();

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    private Step mStep;
    private Recipe mRecipe;
    public FrameLayout mExoContainer;
    public FrameLayout mStepsDescriptionContainer;
    public FragmentStepsDescription mStepsDescriptionFragment;
    public FragmentExoPlayer mExoPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            mRecipe = bundle.getParcelable(Recipe.RECIPE_KEY);
//            mStep = bundle.getParcelable(Step.STEP_KEY);
//            Log.v(LOG, "TEST Recipe = " + mRecipe);
//            Log.v(LOG, "TEST Step = " + mStep);
//        }
//
//        // Determine if you're creating a two-pane or single-pane display
//        if (findViewById(R.id.recipe_steps_linear_layout) != null) {
//            // This LinearLayout will only initially exist in the two-pane tablet case
//            mTwoPane = true;
//
//            mExoContainer = findViewById(R.id.exo_player_container);
//            mStepsDescriptionContainer = findViewById(R.id.steps_description_container);
//
//            // Only create new fragments when there is no previously saved state
//            if (savedInstanceState == null) {
//                // Load the Steps & ExoPlayer fragments
//                if (mStep.getVideoURL().equals("") || mStep == null) {
//                    beginDescriptionFragment();
//                    mExoContainer.setVisibility(View.GONE);
//                } else {
//                    beginDescriptionFragment();
//                    beginExoPlayerFragment();
//                }
//            }
//        } else {
//            // We're in single-pane mode and displaying fragments on a phone in separate activities
//            mTwoPane = false;
//        }
    }

//    public void beginDescriptionFragment() {
//        mStepsDescriptionFragment = new FragmentStepsDescription();
//        mStepsDescriptionFragment.setStep(mStep);
//
//        if (mStepsDescriptionFragment == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.steps_description_container, mStepsDescriptionFragment)
//                    .commit();
//        } else {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.steps_description_container, mStepsDescriptionFragment)
//                    .commit();
//        }
//    }
//
//    public void beginExoPlayerFragment() {
//        mExoPlayerFragment = new FragmentExoPlayer();
//        mExoPlayerFragment.setStep(mStep);
//
//        if (mExoPlayerFragment == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.exo_player_container, mExoPlayerFragment)
//                    .commit();
//        } else {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.exo_player_container, mExoPlayerFragment)
//                    .commit();
//        }
//    }
}
