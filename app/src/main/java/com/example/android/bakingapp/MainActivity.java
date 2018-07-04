package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.RetrofitClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView sampleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleTextView = findViewById(R.id.sample_text);
        List<Recipe> recipeList = RetrofitClient.callRecipe();

        if (recipeList != null) {
            for (Recipe r : recipeList) {
                String name = r.getName();
                sampleTextView.append(name + "\n");
            }
        }

    }

}
