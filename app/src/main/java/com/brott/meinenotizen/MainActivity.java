package com.brott.meinenotizen;

import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.NewSubjectFragment;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;
import com.brott.meinenotizen.subject.SubjectViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubjectRecyclerViewAdapter adapter;

    private List<Subject> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}

