package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

public class WidgetUpdateService extends IntentService {

    private static final String LOG = WidgetUpdateService.class.getSimpleName();
    public static final String ACTION_UPDATE_WIDGET = "update_widget";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_WIDGET.equals(action)) {
                Recipe recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
                handleActionUpdateWidget(recipe);
            }
        }
    }

    private void handleActionUpdateWidget(Recipe recipe) {
        if (recipe != null) {
            WidgetSharedPref.saveRecipe(this, recipe);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateMultipleWidgets(this, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.remote_list_view);
    }

    public static void startActionUpdateWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(Recipe.RECIPE_KEY, recipe);

        context.startService(intent);
    }
}
