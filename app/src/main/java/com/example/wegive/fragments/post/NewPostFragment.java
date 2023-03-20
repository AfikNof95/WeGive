package com.example.wegive.fragments.post;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.wegive.IListener;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentNewPostBinding;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.comment.Comment;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NewPostFragment extends Fragment {


    FragmentNewPostBinding binding;
    View view;
    ActivityResultLauncher<Void> camera;
    ActivityResultLauncher<String> gallery;

    AlertDialog imageUploadDialog;

    TextInputEditText titleInput;
    TextInputLayout titleLayout;
    TextInputEditText contentInput;
    TextInputLayout contentLayout;

    TextInputEditText dateInput;
    TextInputLayout dateLayout;


    ImageView uploadPostImage;

    boolean isEditMode = false;

    Post post = null;

    public NewPostFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        camera = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                uploadPostImage.setImageBitmap(result);
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                uploadPostImage.setImageURI(result);

            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewPostBinding.inflate(inflater, container, false);
        view = binding.getRoot();


        titleInput = binding.postTitleInput;
        titleLayout = binding.postTitleLayout;
        contentInput = binding.postContentInput;
        contentLayout = binding.postContentLayout;
        dateInput = binding.postDateInput;
        dateLayout = binding.postDateLayout;
        uploadPostImage = binding.uploadPostImage;

        post = NewPostFragmentArgs.fromBundle(getArguments()).getPost();
        if (post != null) {
            initializeEditingMode(post);
        }


        setEventListeners();

        return view;
    }

    private void setEventListeners() {
        binding.addPostCancelButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        uploadPostImage.setOnClickListener(view1 -> {
            showUploadImageDialog();
        });

        titleInput.setOnKeyListener((view1, i, keyEvent) -> {
            if (titleLayout.getError() != null) {
                titleLayout.setError(null);
            }
            return false;
        });

        contentInput.setOnKeyListener((view1, i, keyEvent) -> {
            if (contentLayout.getError() != null) {
                contentLayout.setError(null);
            }
            return false;
        });

        binding.addPostSaveButton.setOnClickListener(view1 -> {
            handlePostUpload();
        });

        dateInput.setOnClickListener(view1 -> {
            showDatePickerDialog();
        });
    }


    private void initializeEditingMode(Post post) {
        isEditMode = true;
        titleInput.setText(post.getTitle());
        contentInput.setText(post.getContent());
        dateInput.setText(post.getTime());
        Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.progress_animation).into(uploadPostImage);
    }

    private void handlePostUpload() {
        if (validateForm()) {

            Post newPost = getPost();

            Bitmap postImage = ((BitmapDrawable) uploadPostImage.getDrawable()).getBitmap();

            ProgressDialogGlobal pg = ProgressDialogGlobal.getInstance();
            pg.show(view, getString(R.string.add_post_loading_message));

            PostModel.getInstance().uploadPostImage(newPost.getId(), postImage, data -> {
                if (data != null) {
                    newPost.setImageUrl(data);
                }

                IListener<Void> listener = data1 -> {
                    SnackBarGlobal.make(view, getString(R.string.add_post_success), SnackBarGlobal.SEVERITY.SUCCESS);
                    pg.hide();
                    Navigation.findNavController(view).navigate(NewPostFragmentDirections.actionNewPostFragmentToHomePageFragment());
                };

                PostModel.getInstance().addPost(newPost, listener);

            });


        }
    }

    private Post getPost() {
        String id = post != null ? post.getId() : null;
        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();
        String date = dateInput.getText().toString();
        Long createdAt = post != null ? post.getCreatedAtUnformatted() : (new Date()).getTime();
        User currentUser = User.getCurrentUser();
        List<Attendant> attendants = post != null ? post.getAttendants() : new ArrayList<>();
        List<Comment> comments = post != null ? post.getComments() : new ArrayList<>();

        return new Post(id, title, content, date, null, currentUser.getName(), currentUser.getId(), currentUser.getAvatarUrl(), attendants, comments, createdAt);
    }

    private boolean validateForm() {
        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();
        Drawable postImage = uploadPostImage.getDrawable();
        boolean isValid = true;
        String dateTime = dateInput.getText().toString();
        if (title.trim().equals("")) {
            titleLayout.setError(getString(R.string.empty_post_title_error));
            isValid = false;
        }
        if (content.trim().equals("")) {
            contentLayout.setError(getString(R.string.empty_post_content_error));
            isValid = false;
        }

        if (dateTime.trim().equals("")) {
            dateLayout.setError(getString(R.string.empty_date_error));
            isValid = false;
        }

        if (isValid && (postImage instanceof VectorDrawable || postImage == null)) {
            uploadPostImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.new_post_default_image, null));
        }
        return isValid;
    }

    private void showDatePickerDialog() {
        int year, month, day;
        final Calendar calendar = Calendar.getInstance();
        try {
            Date date = DateFormat.getDateInstance().parse(dateInput.getText().toString());
            calendar.setTime(date);
        } catch (Exception ex) {
        } finally {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                Date selectedDate = calendar.getTime();


                String formattedDate = DateFormat.getDateInstance().format(selectedDate);
                dateInput.setText(formattedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showUploadImageDialog() {
        if (imageUploadDialog != null) {
            imageUploadDialog.show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Upload Profile Picture");
        builder.setPositiveButton("Camera", (dialogInterface, i) -> {
            camera.launch(null);
        });
        builder.setNegativeButton("Gallery", (dialogInterface, i) -> {
            gallery.launch("image/*");
        });
        imageUploadDialog = builder.create();
        imageUploadDialog.show();
    }
}