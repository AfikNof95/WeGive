package com.example.wegive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.models.post.Post;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.List;
import java.util.Objects;

class PostViewHolder extends RecyclerView.ViewHolder {

    List<Post> data;
    TextView postTitle;
    TextView postContent;
    ImageView postImage;

    public PostViewHolder(@NotNull View view, PostsRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(view);
        postTitle = view.findViewById(R.id.post_title);
        postContent = view.findViewById(R.id.post_content);
        postImage = view.findViewById(R.id.post_image);

        this.data = data;
        view.setOnClickListener(view1 -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Post post) {
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
        if (!Objects.equals(post.getImageUrl(), "")) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.undraw_grandma_re_rnv1).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.undraw_grandma_re_rnv1);
        }
    }
}

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Post> data;

    public PostsRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
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
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();

    }


}
