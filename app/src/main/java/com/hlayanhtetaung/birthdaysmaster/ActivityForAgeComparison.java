package com.hlayanhtetaung.birthdaysmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.data.DataForExtra;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Date;
import java.util.Objects;

public class ActivityForAgeComparison extends UtilsActivity {

    LocalDate first, second, today;
    DataForExtra ageExtra;
    String cardName1 = "Person One", cardName2 = "Person Two";
    String resultText, resultText2;
    String nowYears, nowMonths, nowDays = "0";
    int userYears1, userMonths1, userDays1, userYears2, userMonths2, userDays2, firstTotalDays, secondTotalDays;
    View person1, person2, difference;
    TextView title1, years1, months1, days1,
            title2, years2, months2, days2,
            title, years, months, days,
            totalYears, totalMonths, totalWeeks, totalDays, totalHours, totalMinutes, totalSeconds,
            result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_age_comparison);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Age Comparison");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        person1 = findViewById(R.id.person1);
        person2 = findViewById(R.id.person2);
        difference = findViewById(R.id.difference);

        title1 = person1.findViewById(R.id.title);
        years1 = person1.findViewById(R.id.currentYears);
        months1 = person1.findViewById(R.id.currentMonths);
        days1 = person1.findViewById(R.id.currentDays);

        title2 = person2.findViewById(R.id.title);
        years2 = person2.findViewById(R.id.currentYears);
        months2 = person2.findViewById(R.id.currentMonths);
        days2 = person2.findViewById(R.id.currentDays);

        Button calculate = findViewById(R.id.save);
        Button clear = findViewById(R.id.cancel);
        clear.setText(R.string.clear);

        title = difference.findViewById(R.id.title);
        title.setText(R.string.difference);
        years = difference.findViewById(R.id.currentYears);
        months = difference.findViewById(R.id.currentMonths);
        days = difference.findViewById(R.id.currentDays);

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

                first = new LocalDate(userYears1, userMonths1, userDays1);
                second = new LocalDate(userYears2, userMonths2, userDays2);

                firstTotalDays = AgeCalculator.calculateTotalDays(userYears1, userMonths1, userDays1);
                secondTotalDays = AgeCalculator.calculateTotalDays(userYears2, userMonths2, userDays2);

                if (firstTotalDays == secondTotalDays) {

                    resultText = "\"" + cardName1 + "\" and \"" + cardName2 + "\" are the same age.";
                    resultText2 = cardName1 + " = " + cardName2;
                    result.setText(resultText2);

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

                } else if (firstTotalDays > secondTotalDays) {

                    period = new Period(second, first, PeriodType.yearMonthDay());
                    ageExtra = AgeCalculator.calculateExtras(period.getYears(), period.getMonths(), period.getDays());

                    nowYears = String.valueOf(period.getYears());
                    nowMonths = String.valueOf(period.getMonths());
                    nowDays = String.valueOf(period.getDays());

                    if (period.getYears() == 0 && period.getMonths() == 0) {
                        resultText = "\"" + cardName2 + "\" is older than \"" + cardName1 + "\" by " + nowDays + " days.";
                    } else if (period.getYears() == 0) {
                        resultText = "\"" + cardName2 + "\" is older than \"" + cardName1 + "\" by " + nowMonths + " months " + nowDays + " days.";
                    } else {
                        resultText = "\"" + cardName2 + "\" is older than \"" + cardName1 + "\" by " + nowYears + " years " + nowMonths + " months " + nowDays + " days.";
                    }

                    resultText2 = cardName2 + " > " + cardName1;
                    result.setText(resultText2);
                    setTextPerson();
                    setTextExtra();

                } else {

                    period = new Period(first, second, PeriodType.yearMonthDay());
                    ageExtra = AgeCalculator.calculateExtras(period.getYears(), period.getMonths(), period.getDays());

                    nowYears = String.valueOf(period.getYears());
                    nowMonths = String.valueOf(period.getMonths());
                    nowDays = String.valueOf(period.getDays());

                    if (period.getYears() == 0 && period.getMonths() == 0) {
                        resultText = "\"" + cardName1 + "\" is older than \"" + cardName2 + "\" by " + nowDays + " days.";
                    } else if (period.getYears() == 0) {
                        resultText = "\"" + cardName1 + "\" is older than \"" + cardName2 + "\" by " + nowMonths + " months " + nowDays + " days.";
                    } else {
                        resultText = "\"" + cardName1 + "\" is older than \"" + cardName2 + "\" by " + nowYears + " years " + nowMonths + " months " + nowDays + " days.";
                    }

                    resultText2 = cardName1 + " > " + cardName2;
                    result.setText(resultText2);
                    setTextPerson();
                    setTextExtra();

                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextClear();
            }
        });

        person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextBirthday = new Intent(getApplicationContext(), ActivityForRecyclerView.class);
                nextBirthday.putExtra("title", "Choose Person");
                nextBirthday.putExtra("result", 1);
                startActivityForResult(nextBirthday, 1);
            }
        });

        person1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupToFillUserInfo(1);
                return true;
            }
        });

        person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextBirthday = new Intent(getApplicationContext(), ActivityForRecyclerView.class);
                nextBirthday.putExtra("title", "Choose Person");
                nextBirthday.putExtra("result", 2);
                startActivityForResult(nextBirthday, 2);
            }
        });

        person2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupToFillUserInfo(2);
                return true;
            }
        });

        difference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Difference", resultText);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                cardName1 = data.getStringExtra("name");
                userYears1 = data.getIntExtra("userYears", userYears1);
                userMonths1 = data.getIntExtra("userMonths", userMonths1);
                userDays1 = data.getIntExtra("userDays", userDays1);

                setTextPerson1();
            } else {
                cardName2 = data.getStringExtra("name");
                userYears2 = data.getIntExtra("userYears", userYears2);
                userMonths2 = data.getIntExtra("userMonths", userMonths2);
                userDays2 = data.getIntExtra("userDays", userDays2);

                setTextPerson2();
            }
        }
    }

    void setTextPerson1() {
        DataClass currentAge = AgeCalculator.calculateAge(userYears1, userMonths1, userDays1);

        title1.setText(cardName1);
        years1.setText(String.valueOf(currentAge.getYears()));
        months1.setText(String.valueOf(currentAge.getMonths()));
        days1.setText(String.valueOf(currentAge.getDays()));
    }

    void setTextPerson2() {
        DataClass currentAge = AgeCalculator.calculateAge(userYears2, userMonths2, userDays2);

        title2.setText(cardName2);
        years2.setText(String.valueOf(currentAge.getYears()));
        months2.setText(String.valueOf(currentAge.getMonths()));
        days2.setText(String.valueOf(currentAge.getDays()));
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

        cardName1 = "Person One";
        cardName2 = "Peron Two";

        title1.setText(cardName1);
        years1.setText("0");
        months1.setText("0");
        days1.setText("0");

        title2.setText(cardName2);
        years2.setText("0");
        months2.setText("0");
        days2.setText("0");

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
            titleTextView.setText(R.string.person_one);
            datePicker.updateDate(userYears1, userMonths1 - 1, userDays1);
        } else {
            titleTextView.setText(R.string.person_two);
            datePicker.updateDate(userYears2, userMonths2 - 1, userDays2);
        }

        save.setText(R.string.confirm);
        cancel.setText(R.string.dismiss);

        datePicker.setMaxDate(new Date().getTime());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (requestCode == 1) {
                    cardName1 = "Person One";
                    userYears1 = datePicker.getYear();
                    userMonths1 = datePicker.getMonth() + 1;
                    userDays1 = datePicker.getDayOfMonth();

                    setTextPerson1();
                } else {
                    cardName2 = "Person Two";
                    userYears2 = datePicker.getYear();
                    userMonths2 = datePicker.getMonth() + 1;
                    userDays2 = datePicker.getDayOfMonth();

                    setTextPerson2();
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