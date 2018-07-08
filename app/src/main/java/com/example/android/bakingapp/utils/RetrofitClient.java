package com.example.android.bakingapp.utils;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private RetrofitClient() {}
    public static final String LOG_TAG = RetrofitClient.class.getSimpleName();

    public static BakingApi generateBakingApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BakingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BakingApi.class);
    }

    public static MutableLiveData<List<Recipe>> callRecipe() {
        final MutableLiveData<List<Recipe>> mutableLiveData = new MutableLiveData<>();

        Call<List<Recipe>> call = generateBakingApi().getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                mutableLiveData.setValue(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(LOG_TAG, "Failed to Retrieve data for " + t);
            }
        });

        return mutableLiveData;
    }

    public static List<Ingredient> callIngredients() {
        List<Ingredient> ingredientList;
        Call<List<Ingredient>> call = generateBakingApi().getIngredients();

        try {
            Response<List<Ingredient>> response = call.execute();
            if (response != null && response.isSuccessful()) {
                ingredientList = response.body();
                return ingredientList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Step> callSteps() {
        List<Step> stepList;
        Call<List<Step>> call = generateBakingApi().getSteps();

        try {
            Response<List<Step>> response = call.execute();
            if (response != null && response.isSuccessful()) {
                stepList = response.body();
                return stepList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
