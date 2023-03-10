package com.example.wegive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class PostViewHolder extends RecyclerView.ViewHolder {

    List<Post> data;
    TextView postTitle;
    TextView postContent;
    TextView postUserName;
    ImageView postImage;
    ImageView userAvatar;
    Chip editButton;

    Button attendButton;

    Boolean isAttended = false;

    public PostViewHolder(@NotNull View view, PostsRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(view);

        postTitle = view.findViewById(R.id.post_title);
        postContent = view.findViewById(R.id.post_content);
        postUserName = view.findViewById(R.id.post_user_name);
        postImage = view.findViewById(R.id.post_image);
        editButton = view.findViewById(R.id.post_edit_button);
        userAvatar = view.findViewById(R.id.post_user_avatar);
        attendButton = view.findViewById(R.id.post_attend);
        this.data = data;


        view.setOnClickListener(view1 -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Post post, User currentUser) {

        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
        postUserName.setText(post.getCreatorName());
        Picasso.get().load(currentUser.getAvatarUrl()).placeholder(R.drawable.undraw_pic_profile_re_7g2h).into(userAvatar);
        String participants = post.getParticipants();
        if (!Objects.equals(post.getImageUrl(), "")) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.progress_animation).into(postImage);
        } else {
            postImage.setVisibility(View.GONE);
        }

        if (post.getCreatorId().equals(currentUser.getId())) {
            editButton.setVisibility(View.VISIBLE);
        }

        String[] participantsArray = participants.split(",");

        for (String id : participantsArray) {
            if (id.equals(currentUser.getId())) {
                isAttended = true;
                attendButton.setText(R.string.cancel_attend);
                break;
            }

        }

        attendButton.setOnClickListener(view -> {
            String newParticipants = "";
            if (isAttended) {
                Stream<String> newParticipantsStream = Arrays.stream(participantsArray).filter(participant -> !participant.equals(currentUser.getId()));
                String[] arr = (newParticipantsStream.toArray(String[]::new));
                newParticipants =String.join(",",arr);
            } else {
                newParticipants = participants.trim().equals("") ? currentUser.getId() : participants + "," + currentUser.getId();
            }
            post.setParticipants(newParticipants);
            PostModel.getInstance().updatePost(post, data1 -> {

            });
        });

    }
}

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Post> data;

    User currentUser;

    public PostsRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
        this.currentUser = User.getCurrentUser();
    }

    public List<Post> getData() {
        return this.data;
    }

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homepage_post_row, parent, false);
        return new PostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, currentUser);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();

    }


}
