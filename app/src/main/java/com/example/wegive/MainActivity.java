package com.example.wegive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wegive.fragments.HomePageFragmentDirections;
import com.example.wegive.fragments.auth.LoginFragmentDirections;
import com.example.wegive.models.user.User;


public class MainActivity extends AppCompatActivity {

    boolean isFirstRun = true;
    private NavController navController;

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
//                navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//                    @Override
//                    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//                        if (navDestination.getId() == R.id.signUpFragment || navDestination.getId() == R.id.loginFragment) {
//                            appBar.hide();
//                        } else if (!appBar.isShowing()) {
//                            appBar.show();
//                        }
//                    }
//                });
                navController.navigate(R.id.homePageFragment);


            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        View navHostFragment = findViewById(R.id.nav_host_fragment);
        navController = Navigation.findNavController(navHostFragment);
        if (isFirstRun && User.getCurrentUser() != null) {
            NavController navController = Navigation.findNavController(navHostFragment);
            navController.navigate(com.example.wegive.fragments.auth.LoginFragmentDirections.actionLoginFragmentToHomePageFragment());

        }
        isFirstRun = false;

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
                navController.navigate(com.example.wegive.fragments.HomePageFragmentDirections.actionHomePageFragmentToLoginFragment());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FragmentManager manager = navHost.getChildFragmentManager();
        if (manager.getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure you want to close WeGive?")
                    .setMessage("We are sorry to see you leave.")
                    .setPositiveButton("Exit", (dialog, whichButton) -> finish())
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            super.onBackPressed();
        }

    }


}