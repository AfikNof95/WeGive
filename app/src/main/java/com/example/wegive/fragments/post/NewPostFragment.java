package com.example.wegive.fragments.post;

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
import android.widget.ImageView;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentNewPostBinding;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;


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

    ImageView uploadPostImage;

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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

        titleInput = binding.postTitleInput;
        titleLayout = binding.postTitleLayout;
        contentInput = binding.postContentInput;
        contentLayout = binding.postContentLayout;
        uploadPostImage = binding.uploadPostImage;

        binding.addPostCancelButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(NewPostFragmentDirections.actionNewPostFragmentToHomePageFragment());
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

        return view;
    }

    private void handlePostUpload() {
        if (validateForm()) {
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();
            Bitmap postImage = ((BitmapDrawable) uploadPostImage.getDrawable()).getBitmap();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(currentDate);
            User currentUser = User.getCurrentUser();
            Post post = new Post(title, content, formattedDate, null, currentUser.getName(), currentUser.getId(), null);
            ProgressDialogGlobal pg = ProgressDialogGlobal.getInstance();
            pg.show(view, getString(R.string.add_post_loading_message));
            PostModel.getInstance().uploadPostImage(post.getId(), postImage, data -> {
                if (data != null) {
                    post.setImageUrl(data);
                }

                PostModel.getInstance().addPost(post, unused -> {
                    SnackBarGlobal.make(view, getString(R.string.add_post_success), SnackBarGlobal.SEVERITY.SUCCESS);
                    pg.hide();
                    Navigation.findNavController(view).navigate(NewPostFragmentDirections.actionNewPostFragmentToHomePageFragment());
                });
            });


        }
    }

    private boolean validateForm() {
        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();
        Drawable postImage = uploadPostImage.getDrawable();
        boolean isValid = true;
        if (title.trim().equals("")) {
            titleLayout.setError(getString(R.string.empty_post_title_error));
            isValid = false;
        }
        if (content.trim().equals("")) {
            contentLayout.setError(getString(R.string.empty_post_content_error));
            isValid = false;
        }

        if (isValid && (postImage instanceof VectorDrawable || postImage == null)) {
            uploadPostImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.new_post_default_image, null));
        }
        return isValid;
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