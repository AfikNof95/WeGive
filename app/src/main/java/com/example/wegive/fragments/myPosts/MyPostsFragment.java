package com.example.wegive.fragments.myPosts;

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
import android.view.WindowManager;

import com.example.wegive.recyclers.PostsRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentMyPostsBinding;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.viewModels.MyPostsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPostsFragment extends Fragment {

    private MyPostsViewModel viewModel;
    private FragmentMyPostsBinding binding;

    PostsRecyclerAdapter adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyPostsViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMyPostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.myPostsRecyclerView.setHasFixedSize(true);
        binding.myPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), viewModel.getMyPosts().getValue());
        binding.myPostsRecyclerView.setAdapter(adapter);



        BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListener(pos -> {
            Post post = adapter.getData().get(pos);
            Navigation.findNavController(view).navigate(MyPostsFragmentDirections.actionMyPostsFragmentToPostDetailsFragment(post));
        });

        viewModel.getMyPosts().observe(getViewLifecycleOwner(), list -> {
            if(list.size() == 0){
                binding.myPostsEmpty.setVisibility(View.VISIBLE);
            }else{
                binding.myPostsEmpty.setVisibility(View.GONE);
            }

            adapter.setData(list);

        });

        viewModel.getMyPostsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefreshLayout.setRefreshing(status == PostModel.LoadingState.LOADING);
            if (status != PostModel.LoadingState.LOADING) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshMyPosts();
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        });


        return view;
    }


}