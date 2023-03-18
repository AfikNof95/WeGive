package com.example.wegive.recyclers;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.wegive.R;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.user.User;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AttendantsViewHolder extends RecyclerView.ViewHolder {

    List<Attendant> data;

    ImageView attendantAvatar;
    TextView attendantName;

    View view;
    private Attendant attendant;


    public AttendantsViewHolder(@NotNull View view, AttendantsRecyclerAdapter.OnItemClickListener listener, List<Attendant> data) {
        super(view);

        this.view = view;
        this.data = data;

        attendantAvatar = view.findViewById(R.id.attendant_avatar);
        attendantName = view.findViewById(R.id.attendant_name);


        view.setOnClickListener(view1 -> {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        });
    }

    public void bind(Attendant attendant) {

        this.attendant = attendant;


        initializeViewHolderValues();
        setEventListeners();
    }

    public void initializeViewHolderValues() {
        Picasso.get().load(attendant.getAttendantAvatar()).placeholder(R.drawable.undraw_pic_profile_re_7g2h).into(attendantAvatar);
        attendantName.setText(attendant.getAttendantName());
    }


    private void setEventListeners() {

    }


}

public class AttendantsRecyclerAdapter extends RecyclerView.Adapter<AttendantsViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Attendant> data;

    User currentUser;

    public AttendantsRecyclerAdapter(LayoutInflater inflater, List<Attendant> data) {
        this.inflater = inflater;
        this.data = data;
        this.currentUser = User.getCurrentUser();
    }

    public List<Attendant> getData() {
        return this.data;
    }

    public void setData(List<Attendant> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public AttendantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attendant_row, parent, false);
        return new AttendantsViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendantsViewHolder holder, int position) {
        Attendant attendant = data.get(position);
        holder.bind(attendant);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();

    }


}
