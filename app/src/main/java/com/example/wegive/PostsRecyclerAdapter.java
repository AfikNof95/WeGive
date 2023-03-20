package com.example.wegive;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.fragments.homePage.HomePageFragmentDirections;
import com.example.wegive.fragments.myPosts.MyPostsFragmentDirections;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class PostViewHolder extends RecyclerView.ViewHolder {

    List<Post> data;
    TextView postTitle;
    TextView postContent;
    TextView postUserName;
    TextView postCreatedAt;
    TextView postAttendantCount;
    TextView postEventDate;
    ImageView postImage;
    ImageView userAvatar;
    Chip editButton;
    Chip deleteButton;

    MaterialButton attendButton;

    Boolean isAttended = false;

    View parentView;

    Post post;
    User user;

    public PostViewHolder(@NotNull View view, PostsRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(view);

        postTitle = view.findViewById(R.id.post_title);
        postContent = view.findViewById(R.id.post_content);
        postUserName = view.findViewById(R.id.post_user_name);
        postImage = view.findViewById(R.id.post_image);
        editButton = view.findViewById(R.id.post_edit_button);
        deleteButton = view.findViewById(R.id.post_delete_button);
        userAvatar = view.findViewById(R.id.post_user_avatar);
        attendButton = view.findViewById(R.id.post_attend);
        postCreatedAt = view.findViewById(R.id.post_created_at);
        postAttendantCount = view.findViewById(R.id.post_attendant_count);
        postEventDate = view.findViewById(R.id.post_event_date);
        parentView = view;
        this.data = data;


        view.setOnClickListener(view1 -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Post post, User currentUser) {

        this.post = post;
        this.user = currentUser;


        initializeViewHolderValues();
        setEventListeners();
    }

    public void initializeViewHolderValues() {
        String userId = user.getId();


        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
        postUserName.setText(post.getCreatorName());
        postCreatedAt.setText(post.getCreatedAt());
        postEventDate.setText(post.getTime());
        postAttendantCount.setText(String.valueOf(post.getAttendants().size()) + " Attendants");
        isAttended = post.getAttendants().stream().anyMatch(attendant -> attendant.getUserId().equals(userId));
        attendButton.setText(isAttended ? R.string.leave : R.string.join);
        int attendButtonColor = parentView.getResources().getColor(isAttended ? R.color.error : R.color.success);
        Drawable attendButtonIcon = parentView.getResources().getDrawable(isAttended ? R.drawable.outline_cancel_24 : R.drawable.outline_task_alt_24);
        attendButton.setIconTint(ColorStateList.valueOf(attendButtonColor));
        attendButton.setTextColor(attendButtonColor);
        attendButton.setStrokeColor(ColorStateList.valueOf(attendButtonColor));
        attendButton.setIcon(attendButtonIcon);
        Picasso.get().load(post.getCreatorAvatar()).placeholder(R.drawable.undraw_pic_profile_re_7g2h).into(userAvatar);

        if (!Objects.equals(post.getImageUrl(), "")) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.progress_animation).into(postImage);
        } else {
            postImage.setVisibility(View.GONE);
        }

        Date eventDate = new Date(post.getTime());
        if (eventDate.compareTo(new Date()) < 0) {
            attendButton.setVisibility(View.GONE);
        }


        if (post.getCreatorId().equals(userId)) {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
    }


    private void setEventListeners() {


        attendButton.setOnClickListener(view -> {
            handleAttendClick();
        });

        editButton.setOnClickListener(view -> {
            if (Navigation.findNavController(parentView).getCurrentDestination().getId() == R.id.homePageFragment) {
                Navigation.findNavController(parentView).navigate(HomePageFragmentDirections.actionHomePageFragmentToNewPostFragment(post));
            } else {
                Navigation.findNavController(parentView).navigate(MyPostsFragmentDirections.actionMyPostsFragmentToNewPostFragment(post));
            }

        });


        deleteButton.setOnClickListener(view -> {
            handleDeleteClick();
        });
    }

    private void handleAttendClick() {
        String userId = user.getId();
        String userName = user.getName();
        String userAvatarUrl = user.getAvatarUrl();
        attendButton.setClickable(false);
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
            attendButton.setClickable(true);
        });
    }

    private void handleDeleteClick() {
        new AlertDialog.Builder(parentView.getContext())
                .setTitle("Delete Post")
                .setMessage("Are you sure you want to delete this post?")
                .setPositiveButton("Delete", (dialog, whichButton) -> handlePostDelete())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void handlePostDelete() {
        PostModel.getInstance().deletePost(post.getId(), data1 -> {
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
