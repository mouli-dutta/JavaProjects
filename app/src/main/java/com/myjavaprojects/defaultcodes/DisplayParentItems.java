package com.myjavaprojects.defaultcodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.myjavaprojects.R;
import com.myjavaprojects.utility.Child;

import java.util.ArrayList;

public class DisplayParentItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_parent_items);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Child> list = bundle.getParcelableArrayList("heading");

        RecyclerView recyclerView = findViewById(R.id.parent_items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChildItemsAdapter adapter = new ChildItemsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}