package com.myjavaprojects.defaultcodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.myjavaprojects.R;
import com.myjavaprojects.utility.SyntaxHighlight;

public class DisplayChildren extends AppCompatActivity {
    private String title, task, code, output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_children);

        getAndSetIntentData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        TextView taskView = findViewById(R.id.task);
        taskView.setText(task);

        TextView codeView = findViewById(R.id.code);
        SyntaxHighlight sh = new SyntaxHighlight(this);
        codeView.setText(sh.setHighLight(code));

        TextView outputView = findViewById(R.id.output);
        outputView.setText(output);
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("title") &&
                getIntent().hasExtra("task") &&
                getIntent().hasExtra("code") &&
                getIntent().hasExtra("output")) {

            title = getIntent().getStringExtra("title");
            task = getIntent().getStringExtra("task");
            code = getIntent().getStringExtra("code");
            output = getIntent().getStringExtra("output");
        }
    }
}