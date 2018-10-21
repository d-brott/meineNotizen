package com.brott.meinenotizen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.brott.meinenotizen.data.Entry;
import com.brott.meinenotizen.data.Subject;
import com.brott.meinenotizen.subject.NewSubjectFragment;
import com.brott.meinenotizen.subject.SubjectViewModel;

import java.util.List;
import java.util.logging.Logger;

public class NotesActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(NotesActivity.class.getName());

    private TextView entryTextView;

    private EntryViewModel entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        entryTextView = findViewById(R.id.notes_text_view);

        Intent intent = getIntent();
        Subject subject = (Subject) intent.getParcelableExtra(RecyclerViewAdapter.SUBJECT_ID);

        LOGGER.info("Subject: "+ subject.getId());

        entryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EntryViewModel.class);
        entryViewModel.getAllEntries(subject).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null && entries.size() > 0) {
                   entryTextView.setText(entries.get(0).getTitle());
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("entry_dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                NewSubjectFragment dialogFragment = new NewSubjectFragment();
                dialogFragment.show(ft, "entry_dialog");
            }
        });
    }
}