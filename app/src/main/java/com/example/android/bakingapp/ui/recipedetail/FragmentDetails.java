package com.example.android.bakingapp.ui.recipedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utils.DrawableResUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDetails extends Fragment {

    private static final String LOG = FragmentDetails.class.getSimpleName();

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public FragmentDetails() {}

    private Recipe mRecipe;
    private List<Step> mStepList;
    private List<Ingredient> mIngredientList;
    private DetailsAdapter mAdapter;
    private ImageView mBackdrop;
    private CircleImageView mCircleImageView;
    private TextView mTextViewIngredients;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.details_recycler_view);

        mBackdrop = rootView.findViewById(R.id.iv_backdrop);
        mCircleImageView = rootView.findViewById(R.id.iv_recipe_image);
        mTextViewIngredients = rootView.findViewById(R.id.tv_recipe_ingredients);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mRecipe = bundle.getParcelable(Recipe.RECIPE_KEY);
            mStepList = mRecipe.getSteps();
            mIngredientList = mRecipe.getIngredients();
        }

        setUpViews();
        mAdapter = new DetailsAdapter(getActivity(), mStepList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Step currentStep = mStepList.get(position);
//                Intent intent = new Intent(getContext(), RecipeStepsActivity.class);
//                intent.putExtra(Step.STEP_KEY, currentStep);
//                intent.putExtra(Recipe.RECIPE_KEY, mRecipe);
//                startActivity(intent);
                mCallback.onImageSelected(position);
            }
        });

        return rootView;
    }

    private void setUpViews() {

        int recipeId = mRecipe.getId();

        getActivity().setTitle(mRecipe.getName());
        mBackdrop.setImageResource(DrawableResUtils.getBackDropResId(recipeId));
        mCircleImageView.setImageResource(DrawableResUtils.getDrawableResId(recipeId));

        for (Ingredient i: mIngredientList) {
            String bulletUniCode = "\u2022 ";
            String quantity = " " + i.getQuantity() + " ";
            String measure = i.getMeasure() + "  ";
            String component = i.getComponent();

            String ingredientString = bulletUniCode + quantity + measure + component;
            mTextViewIngredients.append(ingredientString + "\n");

        }
    }
}
