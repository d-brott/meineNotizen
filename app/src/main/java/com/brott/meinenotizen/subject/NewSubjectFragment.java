package com.brott.meinenotizen.subject;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brott.meinenotizen.R;
import com.brott.meinenotizen.database.Subject;

public class NewSubjectFragment extends DialogFragment {

    private String subjectName;
    private String subjectDescription;

    private EditText editTextName;
    private EditText editTextDescription;

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

        Button btnSave = root.findViewById(R.id.button_save);
        btnSave.setOnClickListener(e -> {
            subjectName = editTextName.getText().toString();
            subjectDescription = editTextDescription.getText().toString();

            writeSubjectToDatabase();
            getDialog().dismiss();
        });

        Button btnCancel = root.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(e -> getDialog().dismiss());

        return root;
    }

    private void writeSubjectToDatabase() {
        Subject subject = new Subject(subjectName, subjectDescription);

        SubjectViewModel subjectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SubjectViewModel.class);
        subjectViewModel.insert(subject);
    }
}



