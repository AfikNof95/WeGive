package com.example.wegive.fragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.PostsRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentHomePageBinding;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    private HomePageViewModel mViewModel;
    private PostsRecyclerAdapter adapter = null;
    private FragmentHomePageBinding binding;


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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        binding.postsRecyclerView.setHasFixedSize(true);
        binding.postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), mViewModel.getData().getValue());
        binding.postsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(pos -> {
            Post post = mViewModel.getData().getValue().get(pos);
        });

        mViewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        PostModel.getInstance().EventPostListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefreshLayout.setRefreshing(status == PostModel.LoadingState.LOADING);
        });

        binding.swipeRefreshLayout.setOnRefreshListener(()->{
            PostModel.getInstance().refreshAllPosts();
        });

        binding.addPostButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(HomePageFragmentDirections.actionHomePageFragmentToNewPostFragment());
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