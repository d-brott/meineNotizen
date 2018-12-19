package com.brott.meinenotizen.entry;

import android.app.Activity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Entry;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EntryRecyclerViewAdapter extends RecyclerView.Adapter<EntryRecyclerViewAdapter.EntryViewHolder> {
    private List<Entry> entries;
    private EntryViewModel entryViewModel;
    private Activity context;
    private ActionMode currentActionMode;
    private int adapterPosition;

    EntryRecyclerViewAdapter(EntryViewModel entryViewModel, List<Entry> entries, Activity context) {
        this.entries = entries;
        this.entryViewModel = entryViewModel;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_card_view, viewGroup, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        holder.entryTitle.setText(entries.get(position).getTitle());

        String entryText = entries.get(position).getText();
        if (entryText.contains("\n")) {
            String bulletPoints[] = entryText.split("\n");
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

            for (int i = 0; i < bulletPoints.length; i++) {
                String line = bulletPoints[i];
                SpannableString spannableString = new SpannableString(line);
                spannableString.setSpan(new BulletSpan(10), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(spannableString);

                if (i + 1 < bulletPoints.length) {
                    spannableStringBuilder.append("\n");
                }
            }

            holder.entryText.setText(spannableStringBuilder);
        } else {
            holder.entryText.setText(entryText);
        }
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

            entryTitle.setLongClickable(false);
            entryText.setLongClickable(false);

            /*
            cardView.setOnLongClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage(R.string.dialog_delete_entry);

                builder.setNegativeButton(R.string.dialog_delete_entry_cancel, (dialog, id) -> dialog.dismiss());

                builder.setPositiveButton(R.string.dialog_delete_entry_delete, (dialog, id) -> {
                    entryViewModel.delete(entries.get(getAdapterPosition()));
                    entries.remove(getAdapterPosition());
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
            */

            cardView.setOnLongClickListener(view -> {
                adapterPosition = getAdapterPosition();
                if (currentActionMode != null) {
                    return false;
                } else {
                    context.startActionMode(modeCallBack);
                    view.setSelected(true);
                    return true;
                }
            });

        }

        private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Actions");
                mode.getMenuInflater().inflate(R.menu.actions_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        entryViewModel.delete(entries.get(adapterPosition));
                        entries.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        notifyItemRangeChanged(adapterPosition, getItemCount());
                        mode.finish();
                        return true;
                    case R.id.menu_edit:
                        Toast.makeText(context, "Editieren", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                currentActionMode = null;
            }
        };
    }
}
