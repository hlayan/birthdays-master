package com.hlayanhtetaung.birthdaysmaster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;

import org.joda.time.LocalDate;

public class SQLiteDatabaseOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "date_of_birth";
    final String TABLE_NAME = "date_of_birth";
    final String ID = "_id";
    final String NAME = "name";
    final String DAY = "day";
    final String MONTH = "month";
    final String YEAR = "year";
    final String DAYS_OF_WEEK = "days_of_week";

    public SQLiteDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        String TOTAL_DAY = "total_days";
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                DAY + " INT, " +
                MONTH + " INT, " +
                YEAR + " INT ," +
                DAYS_OF_WEEK + " INT," +
                TOTAL_DAY + " INT " +
                ")");

        insertDataToDateOfBirth(db);

        db.execSQL("CREATE TABLE " + "public_holidays" + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                DAY + " INT, " +
                MONTH + " INT " +
                ")");

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void insertDataToDateOfBirth(SQLiteDatabase sqLiteDatabase) {

        LocalDate weeks = new LocalDate(2000, 11, 15);
        int daysOfWeek = weeks.getDayOfWeek();

        int totalDays = AgeCalculator.calculateTotalDays(2000, 11, 15);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Hlayan Htet Aung");
        contentValues.put("day", 15);
        contentValues.put("month", 11);
        contentValues.put("year", 2000);
        contentValues.put("days_of_week", daysOfWeek);
        contentValues.put("total_days", totalDays);
        sqLiteDatabase.insert("date_of_birth", null, contentValues);
    }

}
