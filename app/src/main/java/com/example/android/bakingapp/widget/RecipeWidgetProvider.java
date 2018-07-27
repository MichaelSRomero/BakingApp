package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipemasterlist.MainActivity;

import java.util.List;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Recipe recipe = WidgetSharedPref.getRecipe(context);
        String recipeString = null;
        if (recipe != null) {
            List<Ingredient> ingredientList = recipe.getIngredients();

            for (Ingredient i : ingredientList) {
                String bulletUniCode = "\u2022 ";
                String quantity = " " + i.getQuantity() + " ";
                String measure = i.getMeasure() + "  ";
                String component = i.getComponent();

                String ingredientString = bulletUniCode + quantity + measure + component;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ingredientString + "\n");

                recipeString = stringBuilder.toString();
            }
        } else {
            recipeString = "No Recipe";
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setOnClickPendingIntent(R.id.widget_linear_layout, pendingIntent);
        views.setTextViewText(R.id.widget_textview, recipeString);

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
