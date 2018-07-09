package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.R;

public class DrawableResUtils {

    private DrawableResUtils() {}

    public static int getDrawableResId(int recipeId) {
        switch (recipeId) {
            case 1:
                return R.drawable.nutella_pie;
            case 2:
                return R.drawable.brownies;
            case 3:
                return R.drawable.yellow_cake;
            default:
                return R.drawable.cheesecake;
        }
    }

    public static int getBackDropResId(int recipeId) {
        switch (recipeId) {
            case 1:
                return R.drawable.nutella_pie_blur;
            case 2:
                return R.drawable.brownies_blur;
            case 3:
                return R.drawable.yellow_cake_blur;
            default:
                return R.drawable.cheesecake_blur;
        }
    }
}
