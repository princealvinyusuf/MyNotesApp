package com.intent.pelatihan.mynotesproject1.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.intent.pelatihan.mynotesproject1.activity.MainActivity;
import com.intent.pelatihan.mynotesproject1.db.NoteHelper;

import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.AUTHORITY;
import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.intent.pelatihan.mynotesproject1.db.DatabaseContract.NoteColumns.TABLE_NAME;

/**
 * Created by intent on 12/13/2016.
 */

public class NoteProvider extends ContentProvider {

    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     */
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
    misal
    uri com.intent.pelatihan.mynotesproject1 dicocokan dengan integer 1
    uri com.intent.pelatihan.mynotesproject1/# dicocokan dengan integer 2
     */
    static {

        // content://com.intent.pelatihan.mynotesproject1/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);

        // content://com.intent.pelatihan.mynotesproject1/note/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                NOTE_ID);
    }

    private NoteHelper noteHelper;

    @Override
    public boolean onCreate() {
        noteHelper = NoteHelper.getInstance(getContext());

        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        noteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = noteHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = noteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        noteHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = noteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));

        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        noteHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = noteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        noteHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = noteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));

        return deleted;
    }

}
