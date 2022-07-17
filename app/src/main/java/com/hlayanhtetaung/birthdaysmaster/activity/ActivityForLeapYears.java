package com.hlayanhtetaung.birthdaysmaster.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import java.util.Objects;

public class ActivityForLeapYears extends UtilsActivity {

    TextView leapYearText, resultsValue, leapYearNumber;
    String inputUnits, resultText, totalDay;
    private static final int LIMIT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leap_years);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.leap_checking);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        inputUnits = "";

        resultsValue = findViewById(R.id.resultsValue);
        leapYearText = findViewById(R.id.leapYearText);

        leapYearNumber = findViewById(R.id.leapYearNumber);

        View developer = findViewById(R.id.developer);
        developer.setOnClickListener(v -> visitDev());

    }

    public void onClickPlus(View view) {
        if (inputUnits.equals("")) {
            inputUnits = "0";
        }

        int i = Integer.parseInt(inputUnits);

        inputUnits = String.valueOf(i + 1);

        makeProcess();
    }

    public void onClickMinus(View view) {
        if (inputUnits.equals("")) {
            inputUnits = "0";
        }

        int i = Integer.parseInt(inputUnits);

        if (i > 0) {
            inputUnits = String.valueOf(i - 1);
            makeProcess();
        }
    }

    public void onClickB1(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("1");
        }
    }

    public void onClickB2(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("2");
        }
    }

    public void onClickB3(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("3");
        }
    }

    public void onClickB4(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("4");
        }
    }

    public void onClickB5(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("5");
        }
    }

    public void onClickB6(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("6");
        }
    }

    public void onClickB7(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("7");
        }
    }

    public void onClickB8(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("8");
        }
    }

    public void onClickB9(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("9");
        }
    }

    public void onClickB0(View view) {
        if (inputUnits.length() <= LIMIT) {
            input("0");
        }
    }

    public void onClickBClear(View view) {
        inputUnits = "";
        leapYearText.setText(R.string.result);
        resultsValue.setText("0");
        leapYearNumber.setText("0");
    }

    public void onClickBDelete(View view) {
        removeLastChar(inputUnits);
    }

    void input(String s) {
        if (inputUnits.equals("0")) {
            inputUnits = "";
        }
        inputUnits += s;

        makeProcess();
    }

    void removeLastChar(String str) {

        if (!str.isEmpty()) {
            inputUnits = str.substring(0, str.length() - 1);
        }

        if (inputUnits.isEmpty()) {
            totalDay = "366";
            resultText = "Leap Year";
            resultsValue.setText(totalDay);
            leapYearText.setText(resultText);
            leapYearNumber.setText(R.string.t0);
        } else {
            makeProcess();
        }
    }

    void makeProcess() {

        int value = Integer.parseInt(inputUnits);
        totalDay = "365 Days";

        if (value % 4 == 0) {
            if (value % 100 == 0) {
                if (value % 400 == 0) {
                    resultText = "Leap Year";
                    totalDay = "366 Days";
                } else {
                    resultText = "Not Leap Year";
                }
            } else {
                resultText = "Leap Year";
                totalDay = "366 Days";
            }
        } else {
            resultText = " Not Leap Year";
        }

        resultsValue.setText(totalDay);

        leapYearText.setText(resultText);

        leapYearNumber.setText(inputUnits);
    }
}