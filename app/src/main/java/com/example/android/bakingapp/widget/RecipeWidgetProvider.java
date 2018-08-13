package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipedetail.DetailsActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetName = "Recipe Name";
        Recipe recipe = WidgetSharedPref.getRecipe(context);
        if (recipe != null) {
            widgetName = recipe.getName();
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_recipe_name, widgetName);

        Intent intentService = new Intent(context, BakingViewsService.class);
        views.setRemoteAdapter(R.id.remote_list_view, intentService);

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (!widgetName.equals("Recipe Name")) {
            views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);
        }

        Intent appIntent = new Intent(context, DetailsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.remote_list_view, appPendingIntent);

        views.setEmptyView(R.id.remote_list_view, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionUpdateWidget(context, null);
    }

    public static void updateMultipleWidgets(Context context, AppWidgetManager appWidgetManager,
                                             int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}
