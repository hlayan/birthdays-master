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

        insertDataToDateOfBirth(db,"Republic Day",4, 1, 1948);
        insertDataToDateOfBirth(db,"Martyrs' Day",19, 7, 1947);
        insertDataToDateOfBirth(db,"Aung San Suu Kyi",19, 6, 1945);
        insertDataToDateOfBirth(db,"Hlayan Htet Aung",15, 11, 2000);

        db.execSQL("CREATE TABLE " + "public_holidays" + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                DAY + " INT, " +
                MONTH + " INT " +
                ")");

        insertDataToPublicHolidays(db,"လွတ်လပ်ရေးနေ့",4,1);
        insertDataToPublicHolidays(db,"အမေများနေ့",20,1);
        insertDataToPublicHolidays(db,"ပြည်ထောင်စုနေ့",12,2);
        insertDataToPublicHolidays(db,"ဗိုလ်ချုပ်မွေးနေ့",13,2);
        insertDataToPublicHolidays(db,"ချစ်သူများနေ့",14,2);
        insertDataToPublicHolidays(db,"တောင်သူလယ်သမားနေ့",2,3);
        insertDataToPublicHolidays(db,"တပ်မတော်နေ့",27,3);
        insertDataToPublicHolidays(db,"အလုပ်သမားနေ့",1,5);
        insertDataToPublicHolidays(db,"အာဇာနည်နေ့",19,7);
        insertDataToPublicHolidays(db,"ကုလသမဂ္ဂနေ့",24,10);
        insertDataToPublicHolidays(db,"သီတင်းကျွတ်",13,10);
        insertDataToPublicHolidays(db,"အမျိုးသားနေ့",21,10);
        insertDataToPublicHolidays(db,"ခရစ္စမတ်နေ့",25,12);
        insertDataToPublicHolidays(db,"တန်ဆောင်တိုင်",11,11);
        insertDataToPublicHolidays(db,"ကမ္ဘာဆရာများနေ့",5,10);
        insertDataToPublicHolidays(db,"ဟောလိုးဝင်း",31,10);

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void insertDataToDateOfBirth(SQLiteDatabase sqLiteDatabase, String name, int days, int months, int years) {

        LocalDate weeks = new LocalDate(years, months, days);
        int daysOfWeek = weeks.getDayOfWeek();

        int totalDays = AgeCalculator.calculateTotalDays(years,months,days);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("day", days);
        contentValues.put("month", months);
        contentValues.put("year", years);
        contentValues.put("days_of_week", daysOfWeek);
        contentValues.put("total_days", totalDays);
        sqLiteDatabase.insert("date_of_birth", null, contentValues);
    }

    private void insertDataToPublicHolidays(SQLiteDatabase sqLiteDatabase, String name, int days, int months) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("day", days);
        contentValues.put("month", months);
        sqLiteDatabase.insert("public_holidays", null, contentValues);
    }

}
