package com.example.wegive.fragments.attendants;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.databinding.FragmentAttendantsBinding;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;
import com.example.wegive.recyclers.AttendantsRecyclerAdapter;
import com.example.wegive.viewModels.AttendantsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AttendantsFragment extends Fragment {

    AttendantsViewModel viewModel;
    FragmentAttendantsBinding binding;

    View view;
    AttendantsRecyclerAdapter adapter;

    List<Attendant> attendants = new ArrayList<>();

    Post post;

    public AttendantsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAttendantsBinding.inflate(inflater, container, false);
        binding.attendantsRecyclerView.setHasFixedSize(true);
        binding.attendantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AttendantsRecyclerAdapter(getLayoutInflater(), attendants);
        view = binding.getRoot();
        binding.attendantsRecyclerView.setAdapter(adapter);
        post = AttendantsFragmentArgs.fromBundle(getArguments()).getPost();


        adapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Clicked on attendant");
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            UserModel.instance().refreshAllUsers();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshPosts();
        });

        viewModel.getPostsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefreshLayout.setRefreshing(status == PostModel.LoadingState.LOADING);
        });


        viewModel.getData(post).observe(getViewLifecycleOwner(), users -> {
            attendants = new ArrayList<>();
            post.getAttendants().forEach(attendant -> {
                innerLoop:
                for (User user : users) {
                    if (user.getId().equals(attendant.getUserId())) {
                        attendants.add(new Attendant(attendant.getId(), attendant.getUserId(), post.getId(), user.getName(), user.getAvatarUrl()));
                        break innerLoop;
                    }
                }
            });
            adapter.setData(attendants);
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AttendantsViewModel.class);
    }

}