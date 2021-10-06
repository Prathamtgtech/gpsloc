package com.example.latlang.MyAdapeter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latlang.ModelClass.MapModel;
import com.example.latlang.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.Holder> {
    ArrayList<MapModel> mapModels;

    public MapAdapter(ArrayList<MapModel> dataView, Context applicationContext) {
        this.mapModels = dataView;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MapAdapter.Holder holder, int position) {
        MapModel mapModel = mapModels.get(position);
        holder.id.setText(String.valueOf(mapModel.getId()));
        holder.g_lat.setText(String.valueOf(mapModel.getG_lat()));
        holder.g_lang.setText(String.valueOf(mapModel.getG_lang()));
        holder.o_lat.setText(String.valueOf(mapModel.getM_lat()));
        holder.o_lang.setText(String.valueOf(mapModel.getM_lang()));
        holder.diff.setText(String.valueOf(mapModel.getDiff()));
    }

    @Override
    public int getItemCount() {
        return mapModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView id, g_lat, g_lang, o_lat, o_lang, diff;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.Ids);
            g_lat = itemView.findViewById(R.id.g_lats);
            g_lang = itemView.findViewById(R.id.g_langs);
            o_lat = itemView.findViewById(R.id.o_lats);
            o_lang = itemView.findViewById(R.id.o_langs);
            diff = itemView.findViewById(R.id.diffs);
        }
    }
}
