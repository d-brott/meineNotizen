package com.brott.meinenotizen.entry;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Entry;
import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;

import java.util.List;

public class EntryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntryRecyclerViewAdapter recyclerViewAdapter;
    private EntryViewModel entryViewModel;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra(SubjectRecyclerViewAdapter.SUBJECT_ID);

        String subjectName = subject.getName();
        setTitle(subjectName);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EntryViewModel.class);
        entryViewModel.getAllEntries(subject).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null && entries.size() > 0) {
                    recyclerViewAdapter = new EntryRecyclerViewAdapter(entryViewModel, entries);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("entry_dialog");
            if (prev != null) {
                ft.remove(prev);
            }

            ft.addToBackStack(null);
            NewEntryFragment dialogFragment = NewEntryFragment.newInstance(subject.getId());
            dialogFragment.show(ft, "entry_dialog");
        });
    }
}
