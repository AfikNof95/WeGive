package com.example.wegive.fragments;

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

import com.example.wegive.PostsRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentHomePageBinding;
import com.example.wegive.models.post.Post;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    private HomePageViewModel mViewModel;
    private PostsRecyclerAdapter adapter = null;
    private FragmentHomePageBinding binding;


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.postsRecyclerView.setHasFixedSize(true);
        binding.postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), new ArrayList<>());
        binding.postsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(pos -> {
//            Post post = mViewModel.getData().getValue().get(pos);
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        // TODO: Use the ViewModel
    }

}