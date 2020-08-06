package com.hlayanhtetaung.birthdaysmaster;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.hlayanhtetaung.birthdaysmaster.data.DataForExtra;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Date;
import java.util.Objects;

public class ActivityForWorkingExperience extends UtilsActivity {

    LocalDate start, end, today;
    DataForExtra ageExtra;
    String resultText;
    String nowYears, nowMonths, nowDays = "0";
    int userYears1, userMonths1, userDays1, userWeek1, userYears2, userMonths2, userDays2, userWeek2, firstTotalDays, secondTotalDays;
    View startDate, endDate;
    TextView startTitle, startMonths, startDays, startYears, startDaysOfWeek,
            endTitle, endMonths, endDays, endYears, endDaysOfWeek,
            title, years, months, days,
            totalYears, totalMonths, totalWeeks, totalDays, totalHours, totalMinutes, totalSeconds,
            result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_working_experience);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.working_experience);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);

        startTitle = startDate.findViewById(R.id.upcomingBirthday);
        startTitle.setText(R.string.start_date);
        startMonths = startDate.findViewById(R.id.upcomingBirthdayMonths);
        startDays = startDate.findViewById(R.id.upcomingBirthdayDays);
        startYears = startDate.findViewById(R.id.upcomingBirthdayYears);
        startDaysOfWeek = startDate.findViewById(R.id.upcomingBirthdayDaysOfWeek);

        endTitle = endDate.findViewById(R.id.upcomingBirthday);
        endTitle.setText(R.string.end_date);
        endMonths = endDate.findViewById(R.id.upcomingBirthdayMonths);
        endDays = endDate.findViewById(R.id.upcomingBirthdayDays);
        endYears = endDate.findViewById(R.id.upcomingBirthdayYears);
        endDaysOfWeek = endDate.findViewById(R.id.upcomingBirthdayDaysOfWeek);

        Button calculate = findViewById(R.id.save);
        Button clear = findViewById(R.id.cancel);
        clear.setText(R.string.clear);

        title = findViewById(R.id.title);
        title.setText(R.string.experience);
        years = findViewById(R.id.currentYears);
        months = findViewById(R.id.currentMonths);
        days = findViewById(R.id.currentDays);

        result = findViewById(R.id.result);

        totalYears = findViewById(R.id.totalYears);
        totalMonths = findViewById(R.id.totalMonths);
        totalWeeks = findViewById(R.id.totalWeeks);
        totalDays = findViewById(R.id.totalDays);
        totalHours = findViewById(R.id.totalHours);
        totalMinutes = findViewById(R.id.totalMinutes);
        totalSeconds = findViewById(R.id.totalSeconds);

        setTextClear();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Period period;

                start = new LocalDate(userYears1, userMonths1, userDays1);
                end = new LocalDate(userYears2, userMonths2, userDays2);

                firstTotalDays = AgeCalculator.calculateTotalDays(userYears1, userMonths1, userDays1);
                secondTotalDays = AgeCalculator.calculateTotalDays(userYears2, userMonths2, userDays2);

                if (firstTotalDays == secondTotalDays) {

                    resultText = "No Experience";
                    result.setText(resultText);

                    years.setText("0");
                    months.setText("0");
                    days.setText("0");

                    totalYears.setText("0");
                    totalMonths.setText("0");
                    totalWeeks.setText("0");
                    totalDays.setText("0");
                    totalHours.setText("0");
                    totalMinutes.setText("0");
                    totalSeconds.setText("0");

                } else if (firstTotalDays < secondTotalDays) {

                    period = new Period(start, end, PeriodType.yearMonthDay());
                    ageExtra = AgeCalculator.calculateExtras(period.getYears(), period.getMonths(), period.getDays());

                    nowYears = String.valueOf(period.getYears());
                    nowMonths = String.valueOf(period.getMonths());
                    nowDays = String.valueOf(period.getDays());

                    resultText = "";
                    result.setText(resultText);

                    setTextPerson();
                    setTextExtra();

                } else {

                    resultText = "Invalid";
                    result.setText(resultText);

                    years.setText("0");
                    months.setText("0");
                    days.setText("0");

                    totalYears.setText("0");
                    totalMonths.setText("0");
                    totalWeeks.setText("0");
                    totalDays.setText("0");
                    totalHours.setText("0");
                    totalMinutes.setText("0");
                    totalSeconds.setText("0");

                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextClear();
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToFillUserInfo(1);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToFillUserInfo(2);
            }
        });

        View developer = findViewById(R.id.developer);
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitDev();
            }
        });

    }

    void setStartDate() {
        String daysOfWeeks = AgeCalculator.checkDaysOfWeeks(userWeek1);
        String months = AgeCalculator.checkMonths(userMonths1);

        startMonths.setText(months);
        startDays.setText(String.valueOf(userDays1));
        startYears.setText(String.valueOf(userYears1));
        startDaysOfWeek.setText(daysOfWeeks);
    }

    void setEndDate() {
        String daysOfWeeks = AgeCalculator.checkDaysOfWeeks(userWeek2);
        String months = AgeCalculator.checkMonths(userMonths2);

        endMonths.setText(months);
        endDays.setText(String.valueOf(userDays2));
        endYears.setText(String.valueOf(userYears2));
        endDaysOfWeek.setText(daysOfWeeks);
    }

    void setTextPerson() {
        years.setText(nowYears);
        months.setText(nowMonths);
        days.setText(nowDays);
    }

    void setTextExtra() {
        totalYears.setText(String.valueOf(ageExtra.getTotalYears()));
        totalMonths.setText(String.valueOf(ageExtra.getTotalMonths()));
        totalWeeks.setText(String.valueOf(ageExtra.getTotalWeeks()));
        totalDays.setText(String.valueOf(ageExtra.getTotalDays()));
        totalHours.setText(String.valueOf(ageExtra.getTotalHours()));
        totalMinutes.setText(String.valueOf(ageExtra.getTotalMinutes()));
        totalSeconds.setText(String.valueOf(ageExtra.getTotalSeconds()));
    }

    void setTextClear() {

        today = LocalDate.now();
        userYears1 = today.getYear();
        userMonths1 = today.getMonthOfYear();
        userDays1 = today.getDayOfMonth();
        userYears2 = today.getYear();
        userMonths2 = today.getMonthOfYear();
        userDays2 = today.getDayOfMonth();

        startMonths.setText("0");
        startDays.setText("0");
        startYears.setText("0");
        startDaysOfWeek.setText("0");

        endMonths.setText("0");
        endDays.setText("0");
        endYears.setText("0");
        endDaysOfWeek.setText("0");

        years.setText("0");
        months.setText("0");
        days.setText("0");

        result.setText(R.string.result);

        totalYears.setText("0");
        totalMonths.setText("0");
        totalWeeks.setText("0");
        totalDays.setText("0");
        totalHours.setText("0");
        totalMinutes.setText("0");
        totalSeconds.setText("0");
    }

    private void showPopupToFillUserInfo(final int requestCode) {


        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.date_picker_for_date_of_birth, null);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Button save = dialogView.findViewById(R.id.save);
        Button cancel = dialogView.findViewById(R.id.cancel);

        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);

        if (requestCode == 1) {
            titleTextView.setText(R.string.start_date);
            datePicker.updateDate(userYears1, userMonths1 - 1, userDays1);
        } else {
            titleTextView.setText(R.string.end_date);
            datePicker.updateDate(userYears2, userMonths2 - 1, userDays2);
        }

        save.setText(R.string.confirm);
        cancel.setText(R.string.dismiss);

        datePicker.setMaxDate(new Date().getTime());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (requestCode == 1) {
                    userYears1 = datePicker.getYear();
                    userMonths1 = datePicker.getMonth() + 1;
                    userDays1 = datePicker.getDayOfMonth();
                    LocalDate date = new LocalDate(userYears1, userMonths1, userDays1);
                    userWeek1 = date.getDayOfWeek();
                    setStartDate();
                } else {
                    userYears2 = datePicker.getYear();
                    userMonths2 = datePicker.getMonth() + 1;
                    userDays2 = datePicker.getDayOfMonth();
                    LocalDate date = new LocalDate(userYears2, userMonths2, userDays2);
                    userWeek2 = date.getDayOfWeek();
                    setEndDate();
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(dialogView);
        dialog.show();
    }

}