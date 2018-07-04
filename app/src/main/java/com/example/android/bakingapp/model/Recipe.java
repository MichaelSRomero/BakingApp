package com.example.android.bakingapp.model;

import com.example.android.bakingapp.utils.RetrofitClient;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;

    public Recipe( int id, String name, int servings) {
        this.id = id;
        this.name = name;
        this.ingredients = RetrofitClient.callIngredients();
        this.steps = RetrofitClient.callSteps();
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }
}
