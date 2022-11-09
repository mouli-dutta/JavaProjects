package com.myjavaprojects.usercodes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.myjavaprojects.R;
import com.myjavaprojects.bottomnavfragments.YourCodesFragment;
import com.myjavaprojects.utility.SyntaxHighlight;

import java.util.Objects;

public class CodeEditor extends AppCompatActivity {
    private EditText title, code;
    private String beforeStr = "";
    private boolean clicked = false;
    private String userCodeId = null, userCodeTitle = null, userCode = null;

    private CodeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_editor);
        db = new CodeDatabase(this);
        getAndSetIntentData();

        initToolbar();
        initTitle();
        initCode();
        initKeyboardHelper();
    }

    private void initKeyboardHelper() {
        View layoutKeyboard = findViewById(R.id.helper_keyboard);
        HelperKeyboard keyboard = new HelperKeyboard(layoutKeyboard, code);
        keyboard.initializeButtons();
    }

    private void initCode() {
        code = findViewById(R.id.code_edit_txt);
        code.addTextChangedListener(setHighlighter());
        if (userCode != null) code.setText(userCode);
    }

    private TextWatcher setHighlighter() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) return;
                code.removeTextChangedListener(this);
                try {

                    int initialCursorPos = start + before;
                    int numberOfCharsRightOfCursor = beforeStr.substring(initialCursorPos).length();

                    SyntaxHighlight sh = new SyntaxHighlight(CodeEditor.this);
                    String original = s.toString();
                    SpannableString formatted = sh.setHighLight(original);

                    code.setText(formatted);
                    code.setSelection(getNewCursorPosition(numberOfCharsRightOfCursor, original));

                } catch (Exception e) {
                    Toast.makeText(CodeEditor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                code.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private int getNewCursorPosition(int numberOfCharsRightOfCursor, String original) {
        int position = 0;
        String reverse = new StringBuilder(original).reverse().toString();
        for (int i = 0; i < reverse.length(); i++) {
            if (numberOfCharsRightOfCursor == 0) break;
            else {
                numberOfCharsRightOfCursor--;
            }
            position++;
        }
        return original.length() - position;
    }

    private void initTitle() {
        title = findViewById(R.id.title_edit_txt);
        if (userCodeTitle != null) title.setText(userCodeTitle);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());
        toolbar.inflateMenu(R.menu.code_editor_menu);
        setMenuTextColor(toolbar.getMenu());
        Objects.requireNonNull(toolbar.getOverflowIcon()).setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        toolbar.setOnMenuItemClickListener(this::setMenuItemClickListener);
    }

    private void setMenuTextColor(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString ss = new SpannableString(menu.getItem(i).getTitle().toString());
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, ss.length(), 0);
            item.setTitle(ss);
        }
    }

    private boolean setMenuItemClickListener(MenuItem item) {
        int id = item.getItemId();
        String titleStr = title.getText().toString();
        String codeStr = code.getText().toString();

        if (id == R.id.save_code) {
            if (userCodeId != null) {
                db.updateData(userCodeId, titleStr, codeStr);
            } else {
                db.addData(titleStr, codeStr);
            }
            // todo set navigation to your codes after saving/updating
            startActivity(new Intent(this, YourCodesFragment.class));

        } else if (id == R.id.copy_code) {
            copyToClipboard(codeStr);
        } else if (id == R.id.share_code){
            shareCode(codeStr);

        } else {
            Toast.makeText(this, "Wrong choice", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void copyToClipboard(String codeStr) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("copy", codeStr);
        cm.setPrimaryClip(cd);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void shareCode(String codeStr) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, codeStr);
        intent.setType("text/plain");
        intent = Intent.createChooser(intent, "Share With");
        startActivity(intent);

    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("user_code_id") &&
                getIntent().hasExtra("user_code_title") &&
                getIntent().hasExtra("user_code")) {
            userCodeId = getIntent().getStringExtra("user_code_id");
            userCodeTitle = getIntent().getStringExtra("user_code_title");
            userCode = getIntent().getStringExtra("user_code");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
