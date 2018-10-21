package com.brott.meinenotizen;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brott.meinenotizen.data.Subject;

import java.util.List;
import java.util.logging.Logger;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SubjectViewHolder> {
    public static final String SUBJECT_ID = "com.brott.meinenotizen.SUBJECT_ID";
    private static final Logger LOGGER = Logger.getLogger(RecyclerViewAdapter.class.getName());

    List<Subject> subjects;

    RecyclerViewAdapter(List<Subject> subjects) {
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
        subjectViewHolder.subjectName.setText(subjects.get(i).getName());
        subjectViewHolder.subjectDescription.setText(subjects.get(i).getDescription());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), NotesActivity.class);
                    intent.putExtra(SUBJECT_ID, subjects.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
