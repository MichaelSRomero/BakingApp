package com.example.android.bakingapp.ui.recipesteps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

public class RecipeStepsActivity extends AppCompatActivity {

    private final String LOG = RecipeStepsActivity.class.getSimpleName();

    private Step mStep;
    public FrameLayout mExoContainer;
    public FrameLayout mStepsDescriptionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        mExoContainer = findViewById(R.id.exo_player_container);
        mStepsDescriptionContainer = findViewById(R.id.steps_description_container);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStep = bundle.getParcelable(Step.STEP_KEY);
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
        FragmentStepsDescription stepsDescriptionFragment = new FragmentStepsDescription();
        stepsDescriptionFragment.setStep(mStep);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.steps_description_container, stepsDescriptionFragment)
                .commit();
    }

    public void beginExoPlayerFragment() {
        FragmentExoPlayer exoPlayerFragment = new FragmentExoPlayer();
        exoPlayerFragment.setStep(mStep);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exo_player_container, exoPlayerFragment)
                .commit();
    }
}
