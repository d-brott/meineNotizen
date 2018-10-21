package com.brott.meinenotizen;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brott.meinenotizen.data.Entry;

import java.util.List;
import java.util.logging.Logger;

public class EntryRecyclerViewAdapter extends RecyclerView.Adapter<EntryRecyclerViewAdapter.EntryViewHolder> {
    public static final String SUBJECT_ID = "com.brott.meinenotizen.SUBJECT_ID";
    private static final Logger LOGGER = Logger.getLogger(RecyclerViewAdapter.class.getName());

    List<Entry> entries;

    EntryRecyclerViewAdapter(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_card_view, viewGroup, false);
        EntryViewHolder svh = new EntryViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        holder.entryTitle.setText(entries.get(position).getTitle());
        holder.entryText.setText(entries.get(position).getText());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView entryTitle;
        TextView entryText;

        EntryViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            entryTitle = itemView.findViewById(R.id.text_view_title);
            entryText = itemView.findViewById(R.id.text_view_text);
        }
    }
}
