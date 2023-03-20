package com.example.wegive.fragments.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wegive.recyclers.PostsRecyclerAdapter;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentEventsBinding;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.viewModels.EventsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private EventsViewModel viewModel;
    private FragmentEventsBinding binding;

    PostsRecyclerAdapter adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.eventsRecyclerView.setHasFixedSize(true);
        binding.eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsRecyclerAdapter(getLayoutInflater(), viewModel.getEvents().getValue());
        binding.eventsRecyclerView.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListener(pos -> {
            Post post = adapter.getData().get(pos);
            Navigation.findNavController(view).navigate(EventsFragmentDirections.actionEventsFragmentToPostDetailsFragment(post));
        });

        viewModel.getEvents().observe(getViewLifecycleOwner(), list -> {
            List<Post> events = new ArrayList<>();
            for (Post post : list) {
                innerLoop:
                for (Attendant attendant : post.attendants) {
                    if (attendant.getUserId().equals(User.getCurrentUser().getId())) {
                        events.add(post);
                        break innerLoop;
                    }
                }
            }
            adapter.setData(events);

        });

        viewModel.getEventsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefreshLayout.setRefreshing(status == PostModel.LoadingState.LOADING);
            if (status != PostModel.LoadingState.LOADING) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshEvents();
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        });


        return view;
    }


}