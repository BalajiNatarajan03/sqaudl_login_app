package com.example.sqaudl;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {


    private TextView searchedName;
    private EditText search;
    private RecyclerView recyclerView;
    private Adapter adapter;
    ArrayList<JSONObject> array = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new FetchDataAsyncTask().execute();

        search = findViewById(R.id.search);

        searchedName = findViewById(R.id.name);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchName = search.getText().toString();
                if(array.contains(searchName)){
                    searchedName.setVisibility(View.VISIBLE);
                    searchedName.setText(searchName);
                }
            }
        });

    }

    public class FetchDataAsyncTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "FetchDataAsyncTask";

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://universities.hipolabs.com/search?country=India")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.d("msg",response.toString());
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            if (jsonData != null) {
                try {
                    Log.d(TAG, "********************************************");
                    JSONArray universitiesArray = new JSONArray(jsonData);

                    // Process the JSON data as needed
                    for (int i = 0; i < universitiesArray.length(); i++) {
                        JSONObject universityObject = universitiesArray.getJSONObject(i);
                        array.add(universityObject);

                        // Retrieve the university name
                        String name = universityObject.getString("name");

                        // Log the university name
                        Log.d(TAG, "University: " + name);
                    }

                    recyclerView = findViewById(R.id.recyclerView);

                    // Create layout manager
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new Adapter(array);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d(TAG, "**********************");
                    e.printStackTrace();
                }
            } else {
                // Error fetching JSON data
                Log.e(TAG, "Error fetching JSON data");
            }
        }
    }


}