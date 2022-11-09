package com.myjavaprojects.defaultcodes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myjavaprojects.R;
import com.myjavaprojects.utility.Child;

import java.util.ArrayList;

public class ChildItemsAdapter extends RecyclerView.Adapter<ChildItemsAdapter.ParentViewHolder> {
    private final Context context;
    private final ArrayList<Child> children;

    public ChildItemsAdapter(Context context, ArrayList<Child> children) {
        this.context = context;
        this.children = children;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ParentViewHolder(inflater.inflate(R.layout.code_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        Child child = children.get(position);
        holder.textView.setText(child.getTitle());
        //todo mainCodeRowLayout.setOnClick
        holder.mainCodeRowLayout.setOnClickListener(v -> {
            Intent i = new Intent(context, DisplayChildren.class);
            i.putExtra("title", child.getTitle());
            i.putExtra("task", child.getTask());
            i.putExtra("code", child.getCode());
            i.putExtra("output", child.getOutput());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainCodeRowLayout;
        TextView textView;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);

            mainCodeRowLayout = itemView.findViewById(R.id.main_code_row_layout);
            textView = itemView.findViewById(R.id.title_text);
        }
    }
}
