package com.example.wegive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.models.charity.Charity;
import com.example.wegive.models.user.User;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


class CharityViewHolder extends RecyclerView.ViewHolder {

    List<Charity> data;
    TextView charityTitle;
    TextView charityContent;
    TextView charityCreatorName;
    TextView charityCreatorEmail;

    TextView charityDate;


    ImageView charityImage;


    View parentView;

    Charity charity;
    User user;

    public CharityViewHolder(@NotNull View view, CharitiesRecyclerAdapter.OnItemClickListener listener, List<Charity> data) {
        super(view);

        charityTitle = view.findViewById(R.id.charity_title);
        charityContent = view.findViewById(R.id.charity_content);
        charityCreatorName = view.findViewById(R.id.charity_user_name);
        charityCreatorEmail = view.findViewById(R.id.charity_creator_email);
        charityDate = view.findViewById(R.id.charity_event_date);
        charityImage = view.findViewById(R.id.charity_image);
        parentView = view;
        this.data = data;


        view.setOnClickListener(view1 -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Charity charity, User currentUser) {

        this.charity = charity;
        this.user = currentUser;


        initializeViewHolderValues();
    }

    public void initializeViewHolderValues() {
        charityTitle.setText(charity.getTitle());
        charityContent.setText(charity.getContent());
        charityCreatorName.setText(charity.getCreatorName());
        charityCreatorEmail.setText(charity.getCreatorEmail());
        Date date = new Date();
        try {
            date = DateFormat.getDateInstance().parse(charity.getDate());
        } catch (Exception ex) {
        }

        charityDate.setText(date.toString());

        if (!Objects.equals(charity.getImageUrl(), "")) {
            Picasso.get().load(charity.getImageUrl()).placeholder(R.drawable.progress_animation).into(charityImage);
        } else {
            charityImage.setVisibility(View.GONE);
        }
    }
}

public class CharitiesRecyclerAdapter extends RecyclerView.Adapter<CharityViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Charity> data;

    User currentUser;

    public CharitiesRecyclerAdapter(LayoutInflater inflater, List<Charity> data) {
        this.inflater = inflater;
        this.data = data;
        this.currentUser = User.getCurrentUser();
    }

    public List<Charity> getData() {
        return this.data;
    }

    public void setData(List<Charity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public CharityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.charity_row, parent, false);
        return new CharityViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull CharityViewHolder holder, int position) {
        Charity charity = data.get(position);
        holder.bind(charity, currentUser);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();

    }


}
