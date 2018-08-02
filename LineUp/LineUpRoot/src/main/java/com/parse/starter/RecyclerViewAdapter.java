package com.parse.starter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // variables
    private ArrayList<String> fullNames = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context c, ArrayList<String> fn) {
        fullNames = fn;
        context = c;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fullName.setText(fullNames.get(position));
    }

    @Override
    public int getItemCount() {
        return fullNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;

        // constructor
        public ViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.title_text);
        }
    }

}
