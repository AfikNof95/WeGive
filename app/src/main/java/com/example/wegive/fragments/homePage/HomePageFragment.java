package com.example.wegive.fragments.homePage;

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

import com.example.wegive.recyclers.PostsRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentHomePageBinding;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;

import com.example.wegive.viewModels.HomePageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageFragment extends Fragment {

    private HomePageViewModel viewModel;
    private PostsRecyclerAdapter adapter = null;
    private FragmentHomePageBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
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
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), viewModel.getPosts().getValue());
        binding.postsRecyclerView.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListener(pos -> {
            Post post = viewModel.getPosts().getValue().get(pos);
            Navigation.findNavController(view).navigate(com.example.wegive.fragments.homePage.HomePageFragmentDirections.actionHomePageFragmentToPostDetailsFragment(post));

        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);

        });

        viewModel.getPostsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefreshLayout.setRefreshing(status == PostModel.LoadingState.LOADING);
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {

            viewModel.refreshUsers();
            viewModel.refreshPosts();
        });

//        viewModel.getUsers().observe(getViewLifecycleOwner(), list -> {
//            viewModel.refreshPosts();
//        });

        binding.addPostButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(HomePageFragmentDirections.actionHomePageFragmentToNewPostFragment(null));
        });


        return view;
    }


}