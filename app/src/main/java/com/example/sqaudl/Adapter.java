package com.example.sqaudl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<JSONObject> data;

    public Adapter(List<JSONObject> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.name_lists, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject item = data.get(position);
        try {
            holder.textView.setText(item.getString("name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.clgName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Handle click event for the item
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("item", data.get(position).toString());
                context.startActivity(intent);
            }
        }
    }
}

