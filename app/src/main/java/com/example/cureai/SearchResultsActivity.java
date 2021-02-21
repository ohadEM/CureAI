package com.example.cureai;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    List<String> storyContent;
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressBar progressBar;
    TextView wait;
    TextView title;
    String[] keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String text = intent.getStringExtra("Keys");
        List<String> filters = new ArrayList<>();
        if (text != null) {
            keys = text.split(",");

            for (int i = 0; i < keys.length; i++) {
                filters.add(keys[i]);
            }
        }

        setContentView(R.layout.activity_search_results);


        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        wait = findViewById(R.id.wait);
        wait.setVisibility(View.GONE);
        title = findViewById(R.id.title);

        title.setText("Search Terms: " + text + "\n" + "Databases: MIMIC\n");

        storyContent = new ArrayList<>();

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("notes.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = reader.readLine()) != null) {
                JSONObject json = new JSONObject(str);
                String entry = json.getString("TEXT");
                if (isMatchingFilters(entry, filters)) {
                    storyContent.add(entry);
                }
            }

            title.append(new Integer(storyContent.size()).toString() + " Cases Found");
co
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter(this, storyContent, new Adapter.ItemClickListener() {
                @Override
                public void onItemClicked(String text) {
                    Intent newIntent = new Intent(SearchResultsActivity.this, ViewItemActivity.class);
                    newIntent.putExtra(ViewItemActivity.ITEM_TEXT, text);
                    newIntent.putExtra("Keys", keys);
                    startActivity(newIntent);
                }
            });
            recyclerView.setAdapter(adapter);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isMatchingFilters(String entry, List<String> filters) {
        for (String filter : filters) {
            if (!entry.contains(filter)) {
                return false;
            }
        }
        return true;
    }
}