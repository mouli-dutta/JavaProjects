package com.myjavaprojects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());
    }
}