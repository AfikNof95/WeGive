package com.example.wegive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wegive.models.user.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    boolean isFirstRun = true;
    private NavController navController;
    private View navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setDisplayShowTitleEnabled(false);
            toolbar.findViewById(R.id.toolbar_title).setOnClickListener(view1 -> {
                navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                        if (navDestination.getId() == R.id.signUpFragment || navDestination.getId() == R.id.loginFragment) {
                            appBar.hide();
                        } else if (!appBar.isShowing()) {
                            appBar.show();
                        }
                    }
                });
                navController.navigate(R.id.homePageFragment);


            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        navHostFragment = findViewById(R.id.nav_host_fragment);
        navController = Navigation.findNavController(navHostFragment);
        if (isFirstRun && User.getCurrentUser() != null) {

            NavController navController = Navigation.findNavController(navHostFragment);
            navController.navigate(R.id.homePageFragment);
            isFirstRun = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = ((MenuBuilder) menu);
            menuBuilder.setOptionalIconsVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        invalidateOptionsMenu();
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                User.signOut();
                navController.navigate(R.id.loginFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onBackPressed() {

        if (navController != null && navController.getBackQueue().size() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure you want to close WeGive?")
                    .setMessage("We are sorry to see you leave.")
                    .setPositiveButton("Exit", (dialog, whichButton) -> finish())
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
        }

    }


}