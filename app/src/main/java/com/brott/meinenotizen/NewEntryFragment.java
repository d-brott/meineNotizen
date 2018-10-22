package com.brott.meinenotizen;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.data.Entry;
import com.brott.meinenotizen.data.Subject;
import com.brott.meinenotizen.subject.SubjectViewModel;

public class NewEntryFragment extends DialogFragment {

    private EntryViewModel viewModel;

    private String entryTitle;
    private String entryText;

    private EditText editTextTitle;
    private EditText editTextText;
    private Button btnSave;

    private int subjectId;

    public static NewEntryFragment newInstance(int subjectId){
        NewEntryFragment fragment = new NewEntryFragment();

        Bundle args = new Bundle();
        args.putInt("subjectId", subjectId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subjectId = getArguments().getInt("subjectId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_entry_dialog, container, false);

        editTextTitle = root.findViewById(R.id.edit_text_title);
        editTextText = root.findViewById(R.id.edit_text_text);

        btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryTitle = editTextTitle.getText().toString();
                entryText = editTextText.getText().toString();

                writeEntryToDatabase();
                getDialog().dismiss();
            }
        });

        return root;
    }

    private void writeEntryToDatabase() {
       Entry entry = new Entry(entryTitle, entryText,  subjectId);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EntryViewModel.class);
        viewModel.insert(entry);
    }
}



