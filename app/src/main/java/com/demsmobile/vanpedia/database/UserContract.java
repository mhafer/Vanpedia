package com.demsmobile.vanpedia.database;

import android.content.ContentValues;

/**
 * Created by Diego on 2/3/2016.
 */
public class UserContract implements DataAccessObject {

    private static final String TAG = UserContract.class.getSimpleName();

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public MySQLiteHelper dbHelper;

    public UserContract(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long createUser(String name, String email, String uid, String created_at) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CREATED_AT, created_at);

        long result = dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        dbHelper.close();
        return result;
    }
}
