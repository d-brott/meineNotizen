package com.brott.meinenotizen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.brott.meinenotizen.data.Subject;
import com.brott.meinenotizen.subject.NewSubjectFragment;
import com.brott.meinenotizen.subject.SubjectViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private SubjectViewModel subjectViewModel;
    private List<Subject> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                if (subjects != null && subjects.size() > 0) {
                    allSubjects = subjects;
                    adapter = new RecyclerViewAdapter(subjects);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        final SubjectSwipeController swipeController = new SubjectSwipeController(new SubjectSwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                // super.onLeftClicked(position);

                subjectViewModel.deleteSubject(allSubjects.get(position));

                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
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

