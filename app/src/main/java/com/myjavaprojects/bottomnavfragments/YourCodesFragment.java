package com.myjavaprojects.bottomnavfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myjavaprojects.R;
import com.myjavaprojects.usercodes.CodeEditor;
import com.myjavaprojects.usercodes.CodeDatabase;
import com.myjavaprojects.usercodes.DbHelper;
import com.myjavaprojects.usercodes.UserCode;
import com.myjavaprojects.usercodes.UserCodeAdapter;

import java.util.ArrayList;

public class YourCodesFragment extends Fragment {
    private ImageView noCodeImgView;
    private TextView noCodeTextView;
    private ArrayList<UserCode> userCodes;
    private UserCodeAdapter adapter;

    public YourCodesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_your_codes, container, false);

        noCodeImgView = view.findViewById(R.id.empty_code_image);
        noCodeTextView = view.findViewById(R.id.empty_code_text);

        initFab(view);
        initRecyclerView(view);
        initToolbar(view);

        return view;
    }

    private void initToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.your_codes_toolbar);
        toolbar.inflateMenu(R.menu.custom_project_search_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_search) {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint(Html.fromHtml("<font color = #818795><small>" + getResources().getString(R.string.search) + "</small></font>"));
                searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText);
                        return false;
                    }
                });
            }
            return true;
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_user_codes);

        CodeDatabase db = new CodeDatabase(requireContext());
        DbHelper dbHelper = new DbHelper(db);

        if (dbHelper.isEmptyDb()) {
            noCodeImgView.setVisibility(View.VISIBLE);
            noCodeTextView.setVisibility(View.VISIBLE);
        } else {
            noCodeImgView.setVisibility(View.GONE);
            noCodeTextView.setVisibility(View.GONE);

            userCodes = dbHelper.getUserCodes();
            adapter = new UserCodeAdapter(requireContext(), userCodes);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }

    private void initFab(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(requireContext(), CodeEditor.class)));
    }

    // filters the search query
    private void filter(String codeTitle) {
        ArrayList<UserCode> filteredList = new ArrayList<>();
        for (UserCode title : userCodes) {
            if (title.getTitle().toLowerCase().trim().contains(codeTitle.toLowerCase().trim())) {
                filteredList.add(title);
            }
        }
        adapter.setFilter(filteredList);
    }

}

