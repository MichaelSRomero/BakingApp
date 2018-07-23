package com.example.android.bakingapp.ui.recipedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private Context mContext;
    private List<Step> mStepList;
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView recipeStepId, stepShortDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeStepId = itemView.findViewById(R.id.tv_steps_id);
            stepShortDescription = itemView.findViewById(R.id.tv_steps_short_desrciption);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
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
