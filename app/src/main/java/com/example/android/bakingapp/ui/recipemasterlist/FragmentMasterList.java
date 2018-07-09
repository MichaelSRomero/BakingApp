package com.example.android.bakingapp.ui.recipemasterlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.viewmodel.RecipeViewModel;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FragmentMasterList extends Fragment {

    public FragmentMasterList() {}

    private List<Recipe> mRecipeList;
    private MasterListAdapter mAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.master_recycler_view);

        mRecipeList = new ArrayList<>();
        mAdapter = new MasterListAdapter(getActivity(), mRecipeList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        setupViewModel();

        return rootView;
    }

    private void setupViewModel() {
        RecipeViewModel model = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
        model.getRecipeLists().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mAdapter.addData(recipes);
            }
        });
    }
}
