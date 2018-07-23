package com.example.android.bakingapp.ui.recipesteps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

public class RecipeStepsActivity extends AppCompatActivity {

    private final String LOG = RecipeStepsActivity.class.getSimpleName();

    private Step mStep;
    private Recipe mRecipe;
    int mStepPosition;
    int mStepListSize;
    public Button mNextButton;
    public FrameLayout mExoContainer;
    public FrameLayout mStepsDescriptionContainer;
    public FragmentStepsDescription mStepsDescriptionFragment;
    public FragmentExoPlayer mExoPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        mExoContainer = findViewById(R.id.exo_player_container);
        mStepsDescriptionContainer = findViewById(R.id.steps_description_container);
        mNextButton = findViewById(R.id.next_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStep = bundle.getParcelable(Step.STEP_KEY);
            mRecipe = bundle.getParcelable(Recipe.RECIPE_KEY);
            mStepPosition = mStep.getId();
            mStepListSize = mRecipe.getSteps().size() - 1;
        }
        // If on last step,
        // then we hide the next button
        if (mStepPosition == mStepListSize) {
            mNextButton.setVisibility(View.GONE);
        }
        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            if (mStep.getVideoURL().equals("")) {
                beginDescriptionFragment();
                mExoContainer.setVisibility(View.GONE);
            } else {
                beginDescriptionFragment();
                beginExoPlayerFragment();
            }
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

    public void nextStep(View view) {
        mStepPosition = mStepPosition + 1;

        if (mStepPosition <= mStepListSize) {
            mStep = mRecipe.getSteps().get(mStepPosition);

            if (mStep.getVideoURL().equals("")) {
                beginDescriptionFragment();
                beginExoPlayerFragment();
                mExoContainer.setVisibility(View.GONE);
            } else {
                mExoContainer.setVisibility(View.VISIBLE);
                beginDescriptionFragment();
                beginExoPlayerFragment();
            }
            // Hide Next button when on last step
            if (mStepListSize == mStepPosition) {
                mNextButton.setVisibility(View.GONE);
            }
        }
    }


}
