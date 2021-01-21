package com.example.cureai;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {
    Workbook workbook;
    AsyncHttpClient asyncHttpClient;
    List<String> storyTitle,storyContent,thumbImages;
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressBar progressBar;
    TextView wait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String text = intent.getStringExtra("Keys");

        List<String> terms = new ArrayList<>();
        if (text != null) {
            String[] keys = text.split(",");

            for (int i = 0; i< keys.length; i++){
                terms.add(keys[i]);
            }
        }


        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        wait = findViewById(R.id.wait);
        wait.setVisibility(View.GONE);

        String URL = "https://bikashthapa01.github.io/excel-reader-android-app/story.xls";
        //String apiURL = "https://bikashthapa01.github.io/excel-reader-android-app/";
        storyContent = new ArrayList<>();

//        // checking if the excel file has new content
//
//        try {
//            URL mURL = new URL(apiURL);
//            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) mURL.openConnection();
//            InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
//            // getting network os exception error
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("noteevents.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();

            for (int i = 1; i < row; i++){
                storyContent.add(sheet.getCell(0, i).getContents());
            }

            showData(terms);

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

//        asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.get(URL, new FileAsyncHttpResponseHandler(this) {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
//                Toast.makeText(MainActivity.this, "Error in Downloading Excel File", Toast.LENGTH_SHORT).show();
//                wait.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, File file) {
//                WorkbookSettings ws = new WorkbookSettings();
//                ws.setGCDisabled(true);
//                if(file != null){
//                    //text.setText("Success, DO something with the file.");
//                    wait.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
//
//                    try {
//                        workbook = Workbook.getWorkbook(file);
//                        Sheet sheet = workbook.getSheet(0);
//                        //Cell[] row = sheet.getRow(1);
//                        //text.setText(row[0].getContents());
//                        for(int i = 0;i< sheet.getRows();i++){
//                            Cell[] row = sheet.getRow(i);
//                            storyTitle.add(row[0].getContents());
//                            storyContent.add( row[1].getContents());
//                            thumbImages.add(row[2].getContents());
//                        }
//
//                        showData();
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (BiffException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    private void showData(List<String> terms) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, storyContent, terms);
        recyclerView.setAdapter(adapter);
    }
}