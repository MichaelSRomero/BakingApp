package com.example.android.bakingapp.ui.recipemasterlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.DrawableResUtils;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MyViewHolder> {

    private static final String LOG = MasterListAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private Context mContext;
    private List<Recipe> mRecipeList;
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView recipeName, servings;
        public ImageView recipeImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.iv_recipe);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            servings = itemView.findViewById(R.id.tv_recipe_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
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
