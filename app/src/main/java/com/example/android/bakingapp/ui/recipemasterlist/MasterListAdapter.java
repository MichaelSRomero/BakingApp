package com.example.android.bakingapp.ui.recipemasterlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipedetail.DetailsActivity;
import com.example.android.bakingapp.utils.DrawableResUtils;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MyViewHolder> {

    private static final String LOG = MasterListAdapter.class.getSimpleName();

    private Context mContext;
    private List<Recipe> mRecipeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeName, servings;
        public ImageView recipeImage;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.iv_recipe);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            servings = itemView.findViewById(R.id.tv_recipe_servings);
            cardView = itemView.findViewById(R.id.cv_recipe_item);
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

        final Recipe currentRecipe = mRecipeList.get(position);
        int recipeId = currentRecipe.getId();

        holder.recipeName.setText(currentRecipe.getName());
        holder.servings.setText(String.valueOf(currentRecipe.getServings()));
        holder.recipeImage.setImageResource(DrawableResUtils.getDrawableResId(recipeId));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(Recipe.RECIPE_KEY, currentRecipe);
                view.getContext().startActivity(intent);
            }
        });
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

}
