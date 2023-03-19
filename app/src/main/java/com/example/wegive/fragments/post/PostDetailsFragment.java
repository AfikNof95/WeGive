package com.example.wegive.fragments.post;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentPostDetailsBinding;
import com.example.wegive.models.user.UserModel;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.viewModels.CommentsViewModel;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.comment.Comment;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.recyclers.CommentsRecyclerAdapter;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDetailsFragment extends Fragment {

    FragmentPostDetailsBinding binding;
    View view;
    boolean isPostOwner;
    boolean isAttended;

    Post post;

    User user;
    CommentsViewModel viewModel;


    CommentsRecyclerAdapter commentsAdapter;


    public PostDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        binding.commentsRecyclerView.setHasFixedSize(true);
        binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        post = NewPostFragmentArgs.fromBundle(getArguments()).getPost();
        commentsAdapter = new CommentsRecyclerAdapter(getLayoutInflater(), post.getComments(), post);
        binding.commentsRecyclerView.setAdapter(commentsAdapter);
        user = User.getCurrentUser();
        isAttended = post.getAttendants().stream().anyMatch(attendant -> attendant.getUserId().equals(user.getId()));
        isPostOwner = post.getCreatorId().equals(user.getId());


        if (isPostOwner) {
            binding.postDetailsEdit.setVisibility(View.VISIBLE);
            binding.postDetailsDelete.setVisibility(View.VISIBLE);
        }


        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            List<Comment> comments = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                innerLoop:
                for (User user : users) {
                    if (user.getId().equals(comment.getUserId())) {
                        comments.add(new Comment(comment.getId(), comment.getUserId(), comment.getPostId(), comment.getContent(), user.getName(), user.getAvatarUrl()));
                        break innerLoop;
                    }
                }
            }
            commentsAdapter.setData(comments);
        });

        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            UserModel.instance().refreshAllUsers();
        });


        setControlsValues();
        initializeAttendButton();
        setEventListeners();


        return view;
    }

    private void setEventListeners() {
        binding.postDetailsAttend.setOnClickListener(view1 -> {
            handleAttendClick();
        });

        binding.postDetailsEdit.setOnClickListener(view1 -> {
            handleEditButtonClick();
        });

        binding.postDetailsDelete.setOnClickListener(view1 -> {
            handleDeleteClick();
        });

        binding.postAttendantsLabel.setOnClickListener(view1 -> {
            if (post.getAttendants().size() > 0) {
                Navigation.findNavController(view).navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentToAttendantsFragment(post));
            }
        });

        binding.postAddCommentButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentToAddCommentFragment(post));
        });
    }

    private void setControlsValues() {
        binding.postTitle.setText(post.getTitle());
        binding.postContent.setText(post.getContent());
        binding.postCreatorName.setText(post.getCreatorName());
        binding.postCreatedAt.setText(post.getCreatedAt());
        binding.postEventDate.setText(post.getTime());
        binding.postAttendantCount.setText(String.valueOf(post.getAttendants().size()));

        Picasso.get().load(post.getCreatorAvatar()).placeholder(R.drawable.progress_animation).into(binding.postUserAvatar);
        Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.progress_animation).into(binding.postImage);

    }

    private void initializeAttendButton() {
        binding.postDetailsAttend.setText(isAttended ? R.string.leave : R.string.join);
        int attendButtonColor = view.getResources().getColor(isAttended ? R.color.warning : R.color.success);
        Drawable attendButtonIcon = view.getResources().getDrawable(isAttended ? R.drawable.outline_cancel_24 : R.drawable.outline_task_alt_24);
        ((MaterialButton) binding.postDetailsAttend).setIcon(attendButtonIcon);
        binding.postDetailsAttend.setBackgroundColor(attendButtonColor);
    }


    private void handleEditButtonClick() {
        Navigation.findNavController(view).navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentToNewPostFragment(post));
    }

    private void handleAttendClick() {
        ProgressDialogGlobal.getInstance().show(view, getString(R.string.processing_operation));
        String userId = user.getId();
        String userName = user.getName();
        String userAvatarUrl = user.getAvatarUrl();
        binding.postDetailsAttend.setClickable(false);
        List<Attendant> attendantList = post.getAttendants();
        if (isAttended) {
            attendantList = attendantList.stream().filter(attendant -> !attendant.getUserId().equals(userId)).collect(Collectors.toList());
            post.setAttendants(attendantList);
        } else {
            Attendant attendant = new Attendant(null, userId, post.getId(), userName, userAvatarUrl);
            attendantList.add(attendant);
            post.setAttendants(attendantList);
        }
        PostModel.getInstance().updatePost(post, data1 -> {
            binding.postDetailsAttend.setClickable(true);
            Navigation.findNavController(view).navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentSelf(post));
            ProgressDialogGlobal.getInstance().hide();
        });
    }

    private void handleDeleteClick() {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Delete Post")
                .setMessage("Are you sure you want to delete this post?")
                .setPositiveButton("Delete", (dialog, whichButton) -> handlePostDelete())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void handlePostDelete() {
        PostModel.getInstance().deletePost(post.getId(), data1 -> {
            Navigation.findNavController(view).popBackStack();
            SnackBarGlobal.make(view, getString(R.string.post_delete_success), SnackBarGlobal.SEVERITY.ERROR);
        });
    }
}