package com.myjavaprojects.defaultcodes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myjavaprojects.R;
import com.myjavaprojects.utility.Parent;

import java.util.ArrayList;

public class ParentItemsAdapter extends RecyclerView.Adapter<ParentItemsAdapter.CodeViewHolder> {
    private final Context context;
    private final ArrayList<Parent> codeRowItems;

    public ParentItemsAdapter(Context context, ArrayList<Parent> codeRowItems) {
        this.context = context;
        this.codeRowItems = codeRowItems;
    }

    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new CodeViewHolder(inflater.inflate(R.layout.code_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        Parent row = codeRowItems.get(position);

        TextView textView = holder.textView;
        textView.setText(row.getHeading());

        holder.mainCodeRowLayout.setOnClickListener(v -> {
            Intent i = new Intent(context, DisplayParentItems.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("heading", row.getChildren());
            i.putExtras(bundle);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return codeRowItems.size();
    }

    static class CodeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainCodeRowLayout;
        TextView textView;

        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);

            mainCodeRowLayout = itemView.findViewById(R.id.main_code_row_layout);
            textView = itemView.findViewById(R.id.title_text);
        }
    }
}
