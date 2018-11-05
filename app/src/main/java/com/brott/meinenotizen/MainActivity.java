package com.brott.meinenotizen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.NewSubjectFragment;
import com.brott.meinenotizen.subject.SubjectRecyclerViewAdapter;
import com.brott.meinenotizen.subject.SubjectViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubjectRecyclerViewAdapter adapter;

    private SubjectViewModel subjectViewModel;
    private List<Subject> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                if (subjects != null && subjects.size() > 0) {
                    allSubjects = subjects;
                    adapter = new SubjectRecyclerViewAdapter(subjects);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                NewSubjectFragment dialogFragment = new NewSubjectFragment();
                dialogFragment.show(ft, "dialog");
            }
        });
    }
}

