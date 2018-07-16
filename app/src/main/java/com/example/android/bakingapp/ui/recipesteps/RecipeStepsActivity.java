package com.example.android.bakingapp.ui.recipesteps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

public class RecipeStepsActivity extends AppCompatActivity {

    private final String LOG = RecipeStepsActivity.class.getSimpleName();

    private Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStep = bundle.getParcelable(Step.STEP_KEY);
        }

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            Log.v(LOG, "BEGINNING FRAGMENT TRANSACTION");

            FragmentExoPlayer exoPlayerFragment = new FragmentExoPlayer();
            exoPlayerFragment.setStep(mStep);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.exo_player_container, exoPlayerFragment)
                    .commit();
        }
    }
}
