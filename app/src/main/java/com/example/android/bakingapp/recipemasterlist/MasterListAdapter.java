package com.example.android.bakingapp.recipemasterlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeName, servings;
        public ImageView recipeImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.iv_recipe);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            servings = itemView.findViewById(R.id.tv_recipe_servings);
        }
    }

    public MasterListAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        Recipe currentRecipe = mRecipeList.get(position);
        int recipeId = currentRecipe.getId();

        holder.recipeName.setText(currentRecipe.getName());
        holder.servings.setText(String.valueOf(currentRecipe.getServings()));
        holder.recipeImage.setImageResource(getDrawableResId(recipeId));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void ClearData(List<Recipe> recipes) {
        mRecipeList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Recipe> recipes) {
        mRecipeList.addAll(recipes);
        notifyDataSetChanged();
    }

    private int getDrawableResId(int recipeId) {
        switch (recipeId) {
            case 1:
                return R.drawable.nutella_pie;
            case 2:
                return R.drawable.brownies;
            case 3:
                return R.drawable.yellow_cake;
            default:
                return R.drawable.cheesecake;
        }
    }

}
