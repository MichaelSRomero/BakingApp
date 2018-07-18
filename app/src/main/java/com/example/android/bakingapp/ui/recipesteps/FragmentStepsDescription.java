package com.example.android.bakingapp.ui.recipesteps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

public class FragmentStepsDescription extends Fragment {

    private static final String LOG = FragmentStepsDescription.class.getSimpleName();
    private Step mStep;
    private TextView mStepDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_description, container, false);
        mStepDescription = rootView.findViewById(R.id.tv_steps_description);

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(Step.STEP_KEY);
        }

        if (mStep != null) {
            mStepDescription.setText(mStep.getDescription());
        }

        return rootView;
    }

    public void setStep(Step currentStep) {
        mStep = currentStep;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Step.STEP_KEY, mStep);
        super.onSaveInstanceState(outState);
    }
}
