package com.myjavaprojects.usercodes;

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

import java.util.ArrayList;

public class UserCodeAdapter extends RecyclerView.Adapter<UserCodeAdapter.UserViewHolder> {
    private final Context context;
    private ArrayList<UserCode> userCodes;

    public UserCodeAdapter(Context context, ArrayList<UserCode> userCodes) {
        this.context = context;
        this.userCodes = userCodes;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new UserViewHolder(inflater.inflate(R.layout.code_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserCode userCode = userCodes.get(position);
        holder.textView.setText(userCode.getTitle());
        holder.mainCodeRowLayout.setOnClickListener(v -> {
            Intent i = new Intent(context, CodeEditor.class);
            i.putExtra("user_code_id", userCode.getId());
            i.putExtra("user_code_title", userCode.getTitle());
            i.putExtra("user_code", userCode.getCode());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return userCodes.size();
    }

    public void setFilter(ArrayList<UserCode> list) {
        userCodes = new ArrayList<>();
        userCodes.addAll(list);
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainCodeRowLayout;
        TextView textView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            mainCodeRowLayout = itemView.findViewById(R.id.main_code_row_layout);
            textView = itemView.findViewById(R.id.title_text);
        }
    }
}
