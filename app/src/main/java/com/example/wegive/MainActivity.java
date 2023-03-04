package com.example.wegive;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar appBar =getSupportActionBar();
        if(appBar != null){
            appBar.setDisplayShowTitleEnabled(false);
        }


    }

    public void hideToolbar(){
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}