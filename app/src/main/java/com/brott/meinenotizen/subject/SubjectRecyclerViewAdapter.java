package com.brott.meinenotizen.subject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.entry.EntryActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.SubjectViewHolder> {
    public static final String SUBJECT_ID = "com.brott.meinenotizen.SUBJECT_ID";

    private List<Subject> subjects;
    private View view;
    private SubjectViewModel subjectViewModel;

    public SubjectRecyclerViewAdapter(SubjectViewModel subjectViewModel, List<Subject> subjects) {
        this.subjects = subjects;
        this.subjectViewModel = subjectViewModel;
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);

        return new SubjectViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder subjectViewHolder, int i) {
        String name = subjects.get(i).getName();
        String date = String.format("%s %s", view.getResources().getString(R.string.subject_last_change), subjects.get(i).getDate());

        if (StringUtils.isNotBlank(name)) {
            subjectViewHolder.subjectName.setText(name);
        }

        if (StringUtils.isNotBlank(date)) {
            subjectViewHolder.subjectDate.setText(date);
        }
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView subjectName;
        TextView subjectDate;

        SubjectViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            subjectName = itemView.findViewById(R.id.text_view_name);
            subjectDate = itemView.findViewById(R.id.text_view_date);

            cardView.setOnLongClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.dialog_delete_subject);

                builder.setNegativeButton(R.string.dialog_delete_entry_cancel, (dialog, id) -> dialog.dismiss());

                builder.setPositiveButton(R.string.dialog_delete_entry_delete, (dialog, id) -> {
                    subjectViewModel.deleteSubject(subjects.get(getAdapterPosition()));
                    subjects.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(e -> {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMarginEnd(5);
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(params);
                });

                alertDialog.show();
                return false;
            });

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), EntryActivity.class);
                intent.putExtra(SUBJECT_ID, subjects.get(getAdapterPosition()));
                view.getContext().startActivity(intent);
            });
        }
    }
}
