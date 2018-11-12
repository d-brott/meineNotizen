package com.brott.meinenotizen.entry;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Entry;

public class NewEntryFragment extends DialogFragment {

    private String entryTitle;
    private String entryText;

    private EditText editTextTitle;
    private EditText editTextText;

    private int subjectId;

    public static NewEntryFragment newInstance(int subjectId) {
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

        Button btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(view -> {
            entryTitle = editTextTitle.getText().toString();
            entryText = editTextText.getText().toString();

            writeEntryToDatabase();
            getDialog().dismiss();
        });

        Button btnCancel = root.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(e -> getDialog().dismiss());

        return root;
    }

    private void writeEntryToDatabase() {
        Entry entry = new Entry(entryTitle, entryText, subjectId);

        EntryViewModel viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EntryViewModel.class);
        viewModel.insert(entry);
    }
}



