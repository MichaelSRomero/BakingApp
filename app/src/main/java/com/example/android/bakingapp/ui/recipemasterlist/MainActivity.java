package com.example.android.bakingapp.ui.recipemasterlist;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipedetail.DetailsActivity;
import com.example.android.bakingapp.viewmodel.RecipeViewModel;
import com.example.android.bakingapp.widget.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private List<Recipe> mRecipeList;
    private MasterListAdapter mAdapter;
    private RecipeViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.master_recycler_view);

        mRecipeList = new ArrayList<>();
        mAdapter = new MasterListAdapter(this, mRecipeList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        setupViewModel();
        mAdapter.setOnItemClickListener(new MasterListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Recipe currentRecipe = mRecipeList.get(position);
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra(Recipe.RECIPE_KEY, currentRecipe);

                //WidgetUpdateService.startActionUpdateWidget(getBaseContext(), currentRecipe);
                sendBroadcastIntent(currentRecipe);
                startActivity(intent);
            }
        });
    }

    private void setupViewModel() {
        mModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mModel.getRecipeLists().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mAdapter.addData(recipes);
            }
        });
    }

    private void sendBroadcastIntent(Recipe recipe) {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);
        sendBroadcast(intent);
    }
}
