package com.example.android.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.RetrofitClient;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> recipeList;

    public LiveData<List<Recipe>> getRecipeLists() {

        if (recipeList == null) {
            recipeList = RetrofitClient.callRecipe();
        }
        return recipeList;
    }

}
