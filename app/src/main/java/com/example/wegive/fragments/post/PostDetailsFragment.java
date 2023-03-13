package com.example.wegive.fragments.post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentPostDetailsBinding;

public class PostDetailsFragment extends Fragment {

    FragmentPostDetailsBinding binding;
    View view;


    public PostDetailsFragment() {}




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostDetailsBinding.inflate(inflater,container,false);
        view = binding.getRoot();



        
        return view;
    }
}