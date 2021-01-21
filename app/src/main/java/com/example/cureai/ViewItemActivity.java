package com.example.cureai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewItemActivity extends AppCompatActivity {

    public static final String ITEM_TEXT = "ItemText";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Intent intent = getIntent();
        String presentedText = intent.getStringExtra(ITEM_TEXT);
        TextView textView = findViewById(R.id.content);
        textView.setText(presentedText);
    }
}
