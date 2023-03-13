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

    TextView postTitle;
    TextView postContent;
    TextView postCreatorName;
    TextView postCreatedDate;
    TextView postEventDate;
    TextView postAttendantCount;
    ImageView postCreatorAvatar;
    ImageView postImage;
    MaterialButton postAttendButton;
    MaterialButton postEditButton;
    MaterialButton postDeleteButton;

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
        postTitle = view.findViewById(R.id.post_title);
        postContent = view.findViewById(R.id.post_content);
        postCreatorName = view.findViewById(R.id.post_creator_name);
        postCreatedDate = view.findViewById(R.id.post_created_at);
        postEventDate = view.findViewById(R.id.post_event_date);
        postAttendantCount = view.findViewById(R.id.post_attendant_count);
        postAttendButton = view.findViewById(R.id.post_details_attend);
        postEditButton = view.findViewById(R.id.post_details_edit);
        postDeleteButton = view.findViewById(R.id.post_details_delete);
        postCreatorAvatar = view.findViewById(R.id.post_user_avatar);
        postImage = view.findViewById(R.id.post_image);


        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
        postCreatorName.setText(post.getCreatorName());
        postCreatedDate.setText(post.getCreatedAt());
        postEventDate.setText(post.getTime());
        postAttendantCount.setText(String.valueOf(post.getAttendants().size()));

        Picasso.get().load(post.getCreatorAvatar()).placeholder(R.drawable.progress_animation).into(postCreatorAvatar);
        Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.progress_animation).into(postImage);

        if (isPostOwner) {
            postEditButton.setVisibility(View.VISIBLE);
            postDeleteButton.setVisibility(View.VISIBLE);
        }

        postAttendButton.setOnClickListener(view1 -> {
            handleAttendClick();
        });

        initializeAttendButton();


        return view;
    }

    private void initializeAttendButton(){
        postAttendButton.setText(isAttended ? R.string.leave : R.string.join);
        int attendButtonColor = view.getResources().getColor(isAttended ? R.color.warning : R.color.success);
        Drawable attendButtonIcon = view.getResources().getDrawable(isAttended ? R.drawable.outline_cancel_24 : R.drawable.outline_task_alt_24);
        postAttendButton.setIcon(attendButtonIcon);
        postAttendButton.setBackgroundColor(attendButtonColor);
    }

    private void handleAttendClick(){
        String userId = user.getId();
        String userName = user.getName();
        String userAvatarUrl = user.getAvatarUrl();
        postAttendButton.setClickable(false);
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
            postAttendButton.setClickable(true);
            Navigation.findNavController(view).navigate(PostDetailsFragmentDirections.actionPostDetailsFragmentSelf(post));
        });
    }
}