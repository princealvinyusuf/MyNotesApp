package com.intent.pelatihan.mynotesproject1.activity;

import android.database.Cursor;

interface LoadNotesCallback {
    void preExecute();

    void postExecute(Cursor notes);
}

