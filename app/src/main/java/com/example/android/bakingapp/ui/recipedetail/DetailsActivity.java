package com.example.android.bakingapp.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipesteps.FragmentExoPlayer;
import com.example.android.bakingapp.ui.recipesteps.FragmentStepsDescription;
import com.example.android.bakingapp.ui.recipesteps.RecipeStepsActivity;

public class DetailsActivity extends AppCompatActivity
        implements FragmentDetails.OnImageClickListener {

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
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRecipe = bundle.getParcelable(Recipe.RECIPE_KEY);
        }

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.recipe_steps_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            mSavedInstanceState = savedInstanceState;
            mExoContainer = findViewById(R.id.exo_player_container);
            mStepsDescriptionContainer = findViewById(R.id.steps_description_container);
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }
    }

    public void beginDescriptionFragment() {
        mStepsDescriptionFragment = new FragmentStepsDescription();
        mStepsDescriptionFragment.setStep(mStep);

        if (mStepsDescriptionFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_description_container, mStepsDescriptionFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_description_container, mStepsDescriptionFragment)
                    .commit();
        }
    }

    public void beginExoPlayerFragment() {
        mExoPlayerFragment = new FragmentExoPlayer();
        mExoPlayerFragment.setStep(mStep);

        if (mExoPlayerFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.exo_player_container, mExoPlayerFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.exo_player_container, mExoPlayerFragment)
                    .commit();
        }
    }

    @Override
    public void onImageSelected(int position) {
        mStep = mRecipe.getSteps().get(position);

        if (mTwoPane) {
            // Only create new fragments when there is no previously saved state
            if (mSavedInstanceState == null) {

                if (mStep.getVideoURL().equals("")) {
                    beginDescriptionFragment();
                    beginExoPlayerFragment();
                    mExoContainer.setVisibility(View.GONE);
                } else {
                    beginDescriptionFragment();
                    beginExoPlayerFragment();
                    mExoContainer.setVisibility(View.VISIBLE);
                }
            }
        } else {
             // Handle the single-pane phone case by passing information in a Bundle attached to an Intent
                Intent intent = new Intent(DetailsActivity.this, RecipeStepsActivity.class);
                intent.putExtra(Step.STEP_KEY, mStep);
                intent.putExtra(Recipe.RECIPE_KEY, mRecipe);
                startActivity(intent);
        }
    }
}
