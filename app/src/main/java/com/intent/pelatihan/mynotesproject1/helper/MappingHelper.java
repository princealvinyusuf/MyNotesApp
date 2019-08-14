package com.intent.pelatihan.mynotesproject1.helper;

import android.database.Cursor;

import com.intent.pelatihan.mynotesproject1.entity.Note;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.NoteColumns.DATE;
import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.NoteColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Note> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Note> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DATE));
            notesList.add(new Note(id, title, description, date));
        }

        return notesList;
    }
}
