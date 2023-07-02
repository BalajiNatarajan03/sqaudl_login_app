package com.example.sqaudl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private TextView clgName,clgState,clgCountry,clgWeb,clgDomain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent != null) {
            String item = intent.getStringExtra("item");

            try {
            // Do something with the data
            if (item != null) {
                // Use the item data
                //Toast.makeText(this, "Received item: " + item, Toast.LENGTH_SHORT).show();

                    JSONObject js = new JSONObject(item);
                    Log.d("js",js.toString());

                    clgName = findViewById(R.id.detailClgName);
                    clgState = findViewById(R.id.detailClgState);
                    clgCountry = findViewById(R.id.detailClgCountry);
                    clgDomain = findViewById(R.id.detailClgDomains);
                    clgWeb = findViewById(R.id.detailClgWeb);

                    clgName.setText(js.getString("name"));
                    clgState.setText(js.getString("state-province"));
                    clgCountry.setText(js.getString("country"));
                    clgDomain.setText(js.getString("domains").replace('[', ' ').replace(']',','));
                    clgWeb.setText(js.getString("web_pages").replace('[', ' ').replace(']',' '));

            }

            }
            catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}