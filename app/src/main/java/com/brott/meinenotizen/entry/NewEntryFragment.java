package com.brott.meinenotizen.entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.Utilities;
import com.brott.meinenotizen.database.Entry;
import com.brott.meinenotizen.database.Subject;
import com.brott.meinenotizen.subject.SubjectViewModel;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class NewEntryFragment extends DialogFragment {
    private String entryTitle;
    private String entryText;

    private EditText editTextTitle;
    private EditText editTextText;

    private Subject subject;

    public static NewEntryFragment newInstance(Subject subject) {
        NewEntryFragment fragment = new NewEntryFragment();

        Bundle args = new Bundle();
        args.putParcelable("subjectId", subject);
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

        subject = getArguments().getParcelable("subjectId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_entry_dialog, container, false);

        editTextTitle = root.findViewById(R.id.edit_text_title);
        editTextText = root.findViewById(R.id.edit_text_text);

        Button btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(view -> {

            if (Utilities.isEmpty(editTextTitle)) {
                editTextTitle.setError(getResources().getString(R.string.dialog_input_error));
                return;

            } else if (Utilities.isEmpty(editTextText)) {
                editTextText.setError(getResources().getString(R.string.dialog_input_error));
                return;

            } else {
                entryTitle = editTextTitle.getText().toString();
                entryText = editTextText.getText().toString();

                writeEntryToDatabase();
                getDialog().dismiss();
            }
        });

        Button btnCancel = root.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(e -> getDialog().dismiss());

        return root;
    }

    private void writeEntryToDatabase() {
        Entry entry = new Entry(entryTitle, entryText, subject.getId());

        EntryViewModel viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(EntryViewModel.class);
        viewModel.insert(entry);

        subject.setDate(Utilities.getCurrentDate());
        SubjectViewModel sViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SubjectViewModel.class);
        sViewModel.update(subject);
    }
}



