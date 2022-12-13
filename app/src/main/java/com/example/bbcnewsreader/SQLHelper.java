package com.example.bbcnewsreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQL Helper to work with build in DB.
 * @author  Kanykei Akmatova
 * @version 1.0
 * @since   2022-12-12
 */
public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BBC_NEWS";
    private static final int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "SAVED_NEWS";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_DESCRIPTION= "description";
    public static final String COLUMN_DATE= "date";
    public static final String COLUMN_LINK_ID= "link_id";

    SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT todo_items_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_LINK_ID + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TITLE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_LINK + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_DESCRIPTION + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_DATE + " varchar(200) NOT NULL\n" +
                "    );";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
         * We are doing nothing here
         * Just dropping and creating the table
         * */
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    /**
     * Crete news
     */
    public Integer add(String title, String link, String description, String date, String linkId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_LINK, link);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_LINK_ID, linkId);
        SQLiteDatabase db = getWritableDatabase();

        return (Integer) (int) db.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Return all news
     */
    public Cursor getAll() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT id, title, link, description, date, link_id FROM " + TABLE_NAME, null);
    }

    /**
     * Delete news by id
     */
    public boolean delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) == 1;
    }

    /**
     * Delete news by link id
     */
    public boolean deleteByLinkId(String linkId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_LINK_ID + "=?",
                new String[]{String.valueOf(linkId)}) == 1;
    }

    /**
     * Return DB Version
     */
    public int getVersion() {
        SQLiteDatabase db = getWritableDatabase();
        return db.getVersion();
    }
}