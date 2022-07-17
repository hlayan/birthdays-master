package com.hlayanhtetaung.birthdaysmaster.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.data.DataForExtra;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ActivityForDetailsOfBirthday extends UtilsActivity {

    private final LocalDate now = new LocalDate();
    private LocalDate birthday;
    int checkYears, id, userYears, userMonths, userDays;
    String cardName;
    Toolbar toolbar;
    private Dialog dialog;
    View upcomingCard, dateOfBirthCard, leap, same, sign;
    TextView title, years, months, days,
            monthsForNext, daysForNext, nextTitle, nextMonths, nextDays,
            upcomingBirthdayYears, upcomingBirthdayMonths, upcomingBirthdayDays, upcomingBirthdayDaysOfWeek,
            birthdayTitle, birthdayYears, birthdayMonths, birthdayDays, birthdayDaysOfWeek,
            totalYears, totalMonths, totalWeeks, totalDays, totalHours, totalMinutes, totalSeconds,
            leapTitle, leapCount, sameTitle, sameCount, signTitle, signCount;

    @Override
    protected void onStart() {
        super.onStart();
        // in following line is not work to set title tools bar
//        toolbar.setTitle("cardName");
        Objects.requireNonNull(getSupportActionBar()).setTitle(cardName);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        DataClass currentAge = AgeCalculator.calculateAge(userYears, userMonths, userDays);

        title.setText(R.string.current_age);
        years.setText(String.valueOf(currentAge.getYears()));
        months.setText(String.valueOf(currentAge.getMonths()));
        days.setText(String.valueOf(currentAge.getDays()));

        birthday = new LocalDate(userYears, userMonths, userDays);
        String daysOfWeek = AgeCalculator.checkDaysOfWeeks(birthday.getDayOfWeek());
        String months = AgeCalculator.checkMonths(userMonths);

        birthdayYears.setText(String.valueOf(userYears));
        birthdayMonths.setText(months);
        birthdayDays.setText(String.valueOf(userDays));
        birthdayDaysOfWeek.setText(daysOfWeek);

        checkYears = now.getYear();

        if (now.getMonthOfYear() > userMonths) {

            checkYears++;
            showNextBirthday(checkYears + 1, userMonths, userDays, nextMonths, nextDays);

        } else if (now.getDayOfMonth() > userDays && now.getMonthOfYear() == userMonths) {

            checkYears++;
            showNextBirthday(checkYears + 1, userMonths, userDays, nextMonths, nextDays);

        } else if (now.getDayOfMonth() == userDays && now.getMonthOfYear() == userMonths) {

            checkYears++;
            nextMonths.setText("0");
            nextDays.setText("0");

        } else {

            showNextBirthday(checkYears, userMonths, userDays, nextMonths, nextDays);

        }

        LocalDate upcomingBirthdayDate = new LocalDate(checkYears, userMonths, userDays);
        String upcomingDaysOfWeek = AgeCalculator.checkDaysOfWeeks(upcomingBirthdayDate.getDayOfWeek());

        upcomingBirthdayYears.setText(String.valueOf(checkYears));
        upcomingBirthdayMonths.setText(months);
        upcomingBirthdayDays.setText(String.valueOf(userDays));
        upcomingBirthdayDaysOfWeek.setText(upcomingDaysOfWeek);

        final ArrayList<String> leapArray = AgeCalculator.leapYearsTime(now.getYear(), userYears);
        leapTitle.setText(R.string.leap);
        leapCount.setText(String.valueOf(leapArray.size()));
        leap.setOnClickListener(v -> showAlertDialog("Leap Year", leapArray.toString()));

        final ArrayList<String> sameYears = AgeCalculator.sameBirthdayTime(checkYears, userYears, userMonths, userDays);
        sameTitle.setText(R.string.same);
        sameCount.setText(String.valueOf(sameYears.size()));
        same.setOnClickListener(v -> showAlertDialog("Same Birth Date", sameYears.toString()));

        final String[] zodiacSign = AgeCalculator.isSign(userDays, userMonths);
        signTitle.setText(R.string.sign);
        signCount.setText(zodiacSign[1]);
        sign.setOnClickListener(v -> showAlertDialog("Zodiac Sign", zodiacSign[0] + " " + zodiacSign[1]));

        DataForExtra ageExtra = AgeCalculator.calculateExtras(currentAge.getYears(), currentAge.getMonths(), currentAge.getDays());
        totalYears.setText(String.valueOf(ageExtra.getTotalYears()));
        totalMonths.setText(String.valueOf(ageExtra.getTotalMonths()));
        totalWeeks.setText(String.valueOf(ageExtra.getTotalWeeks()));
        totalDays.setText(String.valueOf(ageExtra.getTotalDays()));
        totalHours.setText(String.valueOf(ageExtra.getTotalHours()));
        totalMinutes.setText(String.valueOf(ageExtra.getTotalMinutes()));
        totalSeconds.setText(String.valueOf(ageExtra.getTotalSeconds()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_details_birthday);

        dialog = new Dialog(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        upcomingCard = findViewById(R.id.upcoming_card);
        dateOfBirthCard = findViewById(R.id.date_of_birth_card);
        leap = findViewById(R.id.leap);
        same = findViewById(R.id.same);
        sign = findViewById(R.id.zodiac);

        title = findViewById(R.id.title);
        years = findViewById(R.id.currentYears);
        months = findViewById(R.id.currentMonths);
        days = findViewById(R.id.currentDays);

        monthsForNext = findViewById(R.id.monthsForNext);
        daysForNext = findViewById(R.id.daysForNext);
        nextTitle = findViewById(R.id.nextTitle);

        nextMonths = findViewById(R.id.remainingMonths);
        nextDays = findViewById(R.id.remainingDays);

        upcomingBirthdayYears = upcomingCard.findViewById(R.id.upcomingBirthdayYears);
        upcomingBirthdayMonths = upcomingCard.findViewById(R.id.upcomingBirthdayMonths);
        upcomingBirthdayDays = upcomingCard.findViewById(R.id.upcomingBirthdayDays);
        upcomingBirthdayDaysOfWeek = upcomingCard.findViewById(R.id.upcomingBirthdayDaysOfWeek);

        leapTitle = leap.findViewById(R.id.title);
        leapCount = leap.findViewById(R.id.count);

        sameTitle = same.findViewById(R.id.title);
        sameCount = same.findViewById(R.id.count);

        signTitle = sign.findViewById(R.id.title);
        signCount = sign.findViewById(R.id.count);

        birthdayTitle = dateOfBirthCard.findViewById(R.id.upcomingBirthday);
        birthdayTitle.setText(R.string.date_of_birth);
        birthdayYears = dateOfBirthCard.findViewById(R.id.upcomingBirthdayYears);
        birthdayMonths = dateOfBirthCard.findViewById(R.id.upcomingBirthdayMonths);
        birthdayDays = dateOfBirthCard.findViewById(R.id.upcomingBirthdayDays);
        birthdayDaysOfWeek = dateOfBirthCard.findViewById(R.id.upcomingBirthdayDaysOfWeek);

        totalYears = findViewById(R.id.totalYears);
        totalMonths = findViewById(R.id.totalMonths);
        totalWeeks = findViewById(R.id.totalWeeks);
        totalDays = findViewById(R.id.totalDays);
        totalHours = findViewById(R.id.totalHours);
        totalMinutes = findViewById(R.id.totalMinutes);
        totalSeconds = findViewById(R.id.totalSeconds);

        cardName = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        id = getIntent().getExtras().getInt("id");
        userYears = getIntent().getExtras().getInt("userYears");
        userMonths = getIntent().getExtras().getInt("userMonths");
        userDays = getIntent().getExtras().getInt("userDays");

        openHelper = new SQLiteDatabaseOpenHelper(this);
        sqLiteDatabase = openHelper.getReadableDatabase();

        View developer = findViewById(R.id.developer);
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitDev();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_for_details_birthday, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                showPopupToEditUserInfo();
                return true;
            case R.id.delete:
                showPopupToDeleteUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPopupToEditUserInfo() {

        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogbox_for_adding_person, null);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        final EditText editText = dialogView.findViewById(R.id.editText);
        Button save = dialogView.findViewById(R.id.save);
        Button cancel = dialogView.findViewById(R.id.cancel);
        save.setText(R.string.update);

        editText.setText(cardName);
        datePicker.updateDate(userYears, userMonths - 1, userDays);

        datePicker.setMaxDate(new Date().getTime());

        save.setOnClickListener(v -> {

            int years = datePicker.getYear();
            int months = datePicker.getMonth() + 1;
            int days = datePicker.getDayOfMonth();
            String name = editText.getText().toString();

            LocalDate weeks = new LocalDate(years, months, days);
            int daysOfWeek = weeks.getDayOfWeek();

            if (TextUtils.isEmpty(name)) {
                editText.setError("Enter name please!");
                return;
            }

            int totalDays = AgeCalculator.calculateTotalDays(years, months, days);

            if (name.equals(cardName) && years == userYears && months == userMonths && days == userDays) {
                showToast("Unchanged!");
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("day", days);
                contentValues.put("month", months);
                contentValues.put("year", years);
                contentValues.put("days_of_week", daysOfWeek);
                contentValues.put("total_days", totalDays);
                sqLiteDatabase.update("date_of_birth", contentValues, "_id = ?", new String[]{String.valueOf(id)});
                showToast("Edited successfully");
                dialog.dismiss();
                cardName = name;
                userYears = years;
                userMonths = months;
                userDays = days;
                onStart();
            }
        });

        cancel.setOnClickListener(v -> dialog.cancel());

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void showPopupToDeleteUser() {

        dialog.setContentView(R.layout.dialogbox_for_delete);
        Button yes = dialog.findViewById(R.id.save);
        Button no = dialog.findViewById(R.id.cancel);
        yes.setText(R.string.delete);
        yes.setOnClickListener(v -> {
            sqLiteDatabase.delete("date_of_birth", "_id = ?", new String[]{String.valueOf(id)});
            dialog.dismiss();
            showToast("Deleted Successfully");
            finish();
        });

        no.setOnClickListener(v -> dialog.dismiss());

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    private void showNextBirthday(int remainYears, int remainMonths, int remainDays, TextView nextMonths, TextView nextDays) {

        birthday = new LocalDate(remainYears, remainMonths, remainDays);
        Period period = new Period(now, birthday, PeriodType.yearMonthDay());

        nextMonths.setText(String.valueOf(period.getMonths()));
        nextDays.setText(String.valueOf(period.getDays()));
    }
}
