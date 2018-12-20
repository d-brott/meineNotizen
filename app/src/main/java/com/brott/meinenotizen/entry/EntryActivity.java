package com.brott.meinenotizen.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.brott.meinenotizen.HelpActivity;
import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EntryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EntryRecyclerViewAdapter recyclerViewAdapter;
    private EntryViewModel entryViewModel;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.entry_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra(SubjectRecyclerViewAdapter.SUBJECT_ID);

        String subjectName = subject.getName();
        setTitle(subjectName);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EntryViewModel.class);
        entryViewModel.getAllEntries(subject).observe(this, entries -> {
            if (entries != null && entries.size() > 0) {
                recyclerViewAdapter = new EntryRecyclerViewAdapter(entryViewModel, entries, EntryActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
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
            NewEntryFragment dialogFragment = NewEntryFragment.newInstance(subject);
            dialogFragment.show(ft, "entry_dialog");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help: {
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }
}
