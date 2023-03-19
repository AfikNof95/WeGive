package com.example.wegive.recyclers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.R;

import com.example.wegive.models.comment.Comment;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

class CommentsViewHolder extends RecyclerView.ViewHolder {

    List<Comment> data;

    ImageView userAvatar;
    TextView userName;

    TextView commentContent;

    Button deleteCommentButton;

    View view;

    Post post;
    private Comment comment;


    public CommentsViewHolder(@NotNull View view, CommentsRecyclerAdapter.OnItemClickListener listener, List<Comment> data, Post post) {
        super(view);

        this.view = view;
        this.data = data;
        this.post = post;

        userAvatar = view.findViewById(R.id.comment_user_avatar);
        userName = view.findViewById(R.id.comment_user_name);
        commentContent = view.findViewById(R.id.comment_content);
        deleteCommentButton = view.findViewById(R.id.comment_delete_button);
    }

    public void bind(Comment comment) {

        this.comment = comment;


        initializeViewHolderValues();
        setEventListeners();
    }

    public void initializeViewHolderValues() {
        Picasso.get().load(comment.getUserAvatar()).placeholder(R.drawable.undraw_pic_profile_re_7g2h).into(userAvatar);
        userName.setText(comment.getUserName());
        commentContent.setText(comment.getContent());
    }


    private void setEventListeners() {

        if (User.getCurrentUser().getId().equals(comment.getUserId())) {
            deleteCommentButton.setOnClickListener(view1 -> {
                ProgressDialogGlobal.getInstance().show(view, view.getRootView().getResources().getString(R.string.processing_operation));
                List<Comment> newComments = data.stream().filter(comm -> !comm.getId().equals(comment.getId())).collect(Collectors.toList());
                post.setComments(newComments);
                PostModel.getInstance().updatePost(post, data1 -> {
                    ProgressDialogGlobal.getInstance().hide();
                });
            });
        } else {
            deleteCommentButton.setVisibility(View.GONE);
        }

    }


}

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Comment> data;

    Post post;

    User currentUser;

    public CommentsRecyclerAdapter(LayoutInflater inflater, List<Comment> data, Post post) {
        this.inflater = inflater;
        this.data = data;
        this.post = post;
        this.currentUser = User.getCurrentUser();
    }

    public List<Comment> getData() {
        return this.data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_row, parent, false);
        return new CommentsViewHolder(view, listener, data, post);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comment comment = data.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();

    }


}
