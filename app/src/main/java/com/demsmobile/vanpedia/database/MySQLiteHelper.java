package com.demsmobile.vanpedia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diego on 2/3/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = MySQLiteHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "vanpedia";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_USER_TABLE = new StringBuilder()
            .append("CREATE TABLE ").append(UserContract.TABLE_NAME).append("(")
            .append(UserContract._ID).append(" TEXT PRIMARY KEY,")
            .append(UserContract.COLUMN_USERNAME).append(" TEXT,")
            .append(UserContract.COLUMN_EMAIL).append(" TEXT UNIQUE,")
            .append(UserContract.COLUMN_CREATED_AT).append(" TEXT, ")
            .append(UserContract.COLUMN_UPDATED_AT).append(" TEXT")
            .append("); ")
            .toString();
    private static final String SQL_PLACE_TABLE = new StringBuilder()
            .append("CREATE TABLE ").append(PlacesContract.TABLE_NAME).append("(")
            .append(PlacesContract._ID).append(" TEXT PRIMARY KEY,")
            .append(PlacesContract.COLUMN_NAME).append(" TEXT,")
            .append(PlacesContract.COLUMN_RATING).append(" TEXT,")
            .append(PlacesContract.COLUMN_REFERENCE).append(" TEXT,")
            .append(PlacesContract.COLUMN_ICON).append(" TEXT,")
            .append(PlacesContract.COLUMN_VICINITY).append(" TEXT,")
            .append(PlacesContract.COLUMN_WEBSITE).append(" TEXT,")
            .append(PlacesContract.COLUMN_LAT).append(" TEXT,")
            .append(PlacesContract.COLUMN_LON).append(" TEXT,")
            .append(PlacesContract.COLUMN_ADDRESS).append(" TEXT,")
            .append(PlacesContract.COLUMN_PHONE).append(" TEXT,")
            .append(PlacesContract.COLUMN_CREATED_AT).append(" TEXT, ")
            .append(PlacesContract.COLUMN_UPDATED_AT).append(" TEXT")
            .append(");")
            .toString();

    private static final String SQL_DELETE_USER_TABLE = new StringBuilder()
            .append("DROP TABLE IF EXISTS ").append(UserContract.TABLE_NAME).append(";").toString();
    private static final String SQL_DELETE_PLACE_TABLE = new StringBuilder()
            .append("DROP TABLE IF EXISTS ").append(PlacesContract.TABLE_NAME).append(";").toString();

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER_TABLE);
        db.execSQL(SQL_PLACE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USER_TABLE);
        db.execSQL(SQL_DELETE_PLACE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
