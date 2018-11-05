package com.brott.meinenotizen.entry;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;
import com.brott.meinenotizen.data.Entry;
import com.brott.meinenotizen.data.Subject;

import java.util.List;
import java.util.logging.Logger;

public class EntryActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(EntryActivity.class.getName());

    private RecyclerView recyclerView;
    private EntryRecyclerViewAdapter adapter;
    private EntryViewModel entryViewModel;

    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra(SubjectRecyclerViewAdapter.SUBJECT_ID);

        LOGGER.info("Subject: "+ subject.getId());

        entryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EntryViewModel.class);
        entryViewModel.getAllEntries(subject).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null && entries.size() > 0) {
                    adapter = new EntryRecyclerViewAdapter(entryViewModel, entries);
                    recyclerView.setAdapter(adapter);
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
                NewEntryFragment dialogFragment = NewEntryFragment.newInstance(subject.getId());
                dialogFragment.show(ft, "entry_dialog");
            }
        });

    }
}
