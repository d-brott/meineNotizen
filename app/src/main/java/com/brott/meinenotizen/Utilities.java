package com.brott.meinenotizen;

import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    private static final DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public static String getCurrentDate() {
        return sdf.format(new Date());
    }

    public static boolean isEmpty(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }
}
