package com.example.wegive.fragments.post;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentPostDetailsBinding;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class PostDetailsFragment extends Fragment {

    FragmentPostDetailsBinding binding;
    View view;
    boolean isPostOwner;
    boolean isAttended;

    Post post;

    User user;


    public PostDetailsFragment() {
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
        post = NewPostFragmentArgs.fromBundle(getArguments()).getPost();
        user = User.getCurrentUser();
        isAttended = post.getAttendants().stream().anyMatch(attendant -> attendant.getUserId().equals(user.getId()));
        isPostOwner = post.getCreatorId().equals(user.getId());


        if (isPostOwner) {
            binding.postDetailsEdit.setVisibility(View.VISIBLE);
            binding.postDetailsDelete.setVisibility(View.VISIBLE);
        }


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
        });
    }
}