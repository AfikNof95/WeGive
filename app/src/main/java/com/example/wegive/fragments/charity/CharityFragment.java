package com.example.wegive.fragments.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.recyclers.CharitiesRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentCharityBinding;
import com.example.wegive.models.charity.Charity;

import com.example.wegive.viewModels.CharityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CharityFragment extends Fragment {

    private CharityViewModel viewModel;

    private CharitiesRecyclerAdapter adapter;

    private FragmentCharityBinding binding;

    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CharityViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCharityBinding.inflate(inflater, container, false);
        binding.swipeRefreshLayout.setRefreshing(true);
        view = binding.getRoot();
        binding.charityRecyclerView.setHasFixedSize(true);
        binding.charityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CharitiesRecyclerAdapter(getLayoutInflater(), viewModel.getCharities().getValue());
        binding.charityRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        adapter.setOnItemClickListener(pos -> {
            Charity charity = viewModel.getCharities().getValue().get(pos);

        });

        viewModel.getCharities().observe(getViewLifecycleOwner(), list -> {
            binding.swipeRefreshLayout.setRefreshing(list == null);
            adapter.setData(list);
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshCharities();
        });

        return view;
    }


}