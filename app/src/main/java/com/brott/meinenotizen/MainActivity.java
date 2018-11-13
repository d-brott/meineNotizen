package com.brott.meinenotizen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.NewSubjectFragment;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;
import com.brott.meinenotizen.subject.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubjectRecyclerViewAdapter adapter;

    private List<Subject> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SubjectViewModel subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, subjects -> {
            if (subjects != null && subjects.size() > 0) {
                allSubjects = subjects;
                adapter = new SubjectRecyclerViewAdapter(subjects);
                recyclerView.setAdapter(adapter);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            NewSubjectFragment dialogFragment = new NewSubjectFragment();
            dialogFragment.show(ft, "dialog");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help: {
                // do something
                break;
            }
            case R.id.action_impressum: {
                // do something
                break;
            }
        }
        return true;
    }
}

