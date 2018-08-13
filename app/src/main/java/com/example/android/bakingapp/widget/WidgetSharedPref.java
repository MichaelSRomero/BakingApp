package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WidgetSharedPref {

    private static final String WIDGET_DETAILS = "sharedPreferenceWidgetDetails";

    public static void saveRecipe(Context context, Recipe recipe) {
        String recipeJson= new Gson().toJson(recipe);

//        SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(WIDGET_DETAILS, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Recipe.RECIPE_KEY, recipeJson)
                .apply();
    }

    public static Recipe getRecipe(Context context) {
        Gson gson = new Gson();
//        SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(WIDGET_DETAILS, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(Recipe.RECIPE_KEY, null);
        Type type = new TypeToken<Recipe>() {}.getType();

        return gson.fromJson(json, type);
    }
}
