package com.brott.meinenotizen.subject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.Utilities;
import com.brott.meinenotizen.database.Subject;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class NewSubjectFragment extends DialogFragment {
    private String subjectName;
    private String subjectDate;

    private EditText editTextName;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_subject_dialog, container, false);

        editTextName = root.findViewById(R.id.edit_text_name);

        Button btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(e -> {
            if (Utilities.isEmpty(editTextName)) {
                editTextName.setError(getResources().getString(R.string.dialog_input_error));
                return;

            } else {
                subjectName = editTextName.getText().toString();
                subjectDate = Utilities.getCurrentDate();

                writeSubjectToDatabase();
                getDialog().dismiss();
            }
        });

        Button btnCancel = root.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(e -> getDialog().dismiss());

        return root;
    }

    private void writeSubjectToDatabase() {
        Subject subject = new Subject(subjectName, subjectDate);

        SubjectViewModel subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SubjectViewModel.class);
        subjectViewModel.insert(subject);
    }
}



