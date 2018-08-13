package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class BakingViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ViewsFactory(getApplicationContext());
    }

    class ViewsFactory implements RemoteViewsFactory {
        private Context mContext;
        List<Ingredient> mIngredients = new ArrayList<>();
        Recipe mRecipe = null;

        public ViewsFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mRecipe = WidgetSharedPref.getRecipe(mContext);
            mIngredients = mRecipe.getIngredients();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.recipe_list_widget);
            Ingredient currentIngredient = mIngredients.get(position);

            views.setTextViewText(R.id.ingredient_name_widget_tv, currentIngredient.getComponent());
            views.setTextViewText(R.id.ingredient_amount_widget_tv,
                    currentIngredient.getQuantity()
                            + currentIngredient.getMeasure());

            Intent intent = new Intent();
            intent.putExtra(Recipe.RECIPE_KEY, mRecipe);
            views.setOnClickFillInIntent(R.id.parent_view, intent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
