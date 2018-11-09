package com.brott.meinenotizen.subject;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.entry.EntryActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.SubjectViewHolder> {
    public static final String SUBJECT_ID = "com.brott.meinenotizen.SUBJECT_ID";

    private List<Subject> subjects;

    public SubjectRecyclerViewAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);
        SubjectViewHolder svh = new SubjectViewHolder(view);
        return svh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder subjectViewHolder, int i) {
        String name = subjects.get(i).getName();
        String description = subjects.get(i).getDescription();

        if (StringUtils.isNotBlank(name)) {
            subjectViewHolder.subjectName.setText(name);
        }

        if (StringUtils.isNotBlank(description)) {
            subjectViewHolder.subjectDescription.setVisibility(View.VISIBLE);
            subjectViewHolder.subjectDescription.setText(subjects.get(i).getDescription());
        } else {
            subjectViewHolder.subjectDescription.setVisibility(View.GONE);
        }
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView subjectName;
        TextView subjectDescription;

        SubjectViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            subjectName = itemView.findViewById(R.id.text_view_name);
            subjectDescription = itemView.findViewById(R.id.text_view_description);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), EntryActivity.class);
                intent.putExtra(SUBJECT_ID, subjects.get(getAdapterPosition()));
                view.getContext().startActivity(intent);
            });
        }
    }
}
