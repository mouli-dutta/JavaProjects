package com.myjavaprojects.bottomnavfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myjavaprojects.R;
import com.myjavaprojects.SearchActivity;
import com.myjavaprojects.defaultcodes.ParentItemsAdapter;
import com.myjavaprojects.utility.JavaProjects;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initRecyclerView(view);
        initToolbar(view);

        return view;
    }

    private void initToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.projects_toolbar);
        toolbar.inflateMenu(R.menu.default_code_search_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.default_code_search) {
                startActivity(new Intent(requireContext(), SearchActivity.class));
            }
            return true;
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.code_recycler_view);
        ParentItemsAdapter adapter = new ParentItemsAdapter(requireContext(), JavaProjects.getJavaProjects());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}