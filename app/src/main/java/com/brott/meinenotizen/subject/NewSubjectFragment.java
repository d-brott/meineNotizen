package com.brott.meinenotizen.subject;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.data.Subject;

public class NewSubjectFragment extends DialogFragment {
    // ViewModel
    private SubjectViewModel subjectViewModel;

    // Data
    private String subjectName;
    private String subjectDescription;

    // Layout
    private EditText editTextName;
    private EditText editTextDescription;
    private Button btnSave;
    private Button btnCancel;

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
        editTextDescription = root.findViewById(R.id.edit_text_description);
        btnCancel = root.findViewById(R.id.button_cancel);

        btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectName = editTextName.getText().toString();
                subjectDescription = editTextDescription.getText().toString();

                writeSubjectToDatabase();
                getDialog().dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return root;
    }

    private void writeSubjectToDatabase() {
        Subject subject = new Subject(subjectName, subjectDescription);

        subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SubjectViewModel.class);
        subjectViewModel.insert(subject);
    }
}



