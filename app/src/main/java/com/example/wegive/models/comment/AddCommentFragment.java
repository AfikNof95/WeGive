package com.example.wegive.models.comment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentAddCommentBinding;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;

import java.util.List;


public class AddCommentFragment extends Fragment {

    FragmentAddCommentBinding binding;

    View view;

    Post post;


    public AddCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddCommentBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        post = AddCommentFragmentArgs.fromBundle(getArguments()).getPost();
        setEventListeners();
        return view;
    }

    private void setEventListeners() {
        binding.postCommentCancelButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.postCommentSaveButton.setOnClickListener(view1 -> {
            if (validateComment()) {
                handleCommentSave();
            }

        });
    }

    public boolean validateComment() {
        boolean isValid = true;
        if (binding.postCommentInput.getText().toString().trim().equals("")) {
            isValid = false;
            binding.postCommentInputLayout.setError(getString(R.string.empty_comment_error));
        }

        return isValid;
    }


    public void handleCommentSave() {
        List<Comment> comments = post.getComments();
        String userId = User.getCurrentUser().getId();
        String userName = User.getCurrentUser().getName();
        String userAvatar = User.getCurrentUser().getAvatarUrl();
        String content = binding.postCommentInput.getText().toString();
        comments.add(new Comment(null, userId, post.getId(), content, userName, userAvatar));
        PostModel.getInstance().updatePost(post, data -> {
            Navigation.findNavController(view).popBackStack();
        });
    }
}