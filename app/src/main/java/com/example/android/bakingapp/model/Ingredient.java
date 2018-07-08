package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String component;

    public Ingredient(long quantity, String measure, String component) {
        this.quantity = quantity;
        this.measure = measure;
        this.component = component;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getComponent() {
        return component;
    }
}
