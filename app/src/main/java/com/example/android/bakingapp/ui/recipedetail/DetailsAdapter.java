package com.example.android.bakingapp.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipesteps.RecipeStepsActivity;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Step> mStepList;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView recipeStepId, stepShortDescription;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeStepId = itemView.findViewById(R.id.tv_steps_id);
            stepShortDescription = itemView.findViewById(R.id.tv_steps_short_desrciption);
            linearLayout = itemView.findViewById(R.id.linear_layout_steps_item);
        }
    }

    public DetailsAdapter(Context context, List<Step> stepList) {
        mContext = context;
        mStepList = stepList;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        //TODO: Decide to use {@recipe_steps_item} or {@recipe_steps_item_test}

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_item_test, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Step currentStep = mStepList.get(position);

        holder.recipeStepId.setText(String.valueOf(currentStep.getId()));
        holder.stepShortDescription.setText(currentStep.getShortDescription());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), RecipeStepsActivity.class);
                intent.putExtra(Step.STEP_KEY, currentStep);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public void ClearData(List<Step> steps) {
        mStepList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Step> steps) {
        mStepList.addAll(steps);
        notifyDataSetChanged();
    }

}
