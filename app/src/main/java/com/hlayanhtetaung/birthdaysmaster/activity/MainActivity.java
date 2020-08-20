package com.hlayanhtetaung.birthdaysmaster.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForMainFragment;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForMonthWeekDay;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForRemainingBirthday;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForUpcomingBirthday;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends UtilsActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AdapterForMonthWeekDay adapterForMonthWeekDay;
    private Dialog dialog;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.recyclerView);
        if (arrayList.size() > 1) {
            toolbar.setTitle(arrayList.size() + " Persons");
        } else {
            toolbar.setTitle(arrayList.size() + " Person");
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRefresh() {

                readDataFromDatabase(0);

                if (id == 1) {
                    adapterForMainFragment.notifyDataSetChanged();
                } else if (id == 2) {
                    adapterForRemainingBirthday.notifyDataSetChanged();
                } else {
                    adapterForUpcomingBirthday.notifyDataSetChanged();
                }

                if (arrayList.size() > 1) {
                    toolbar.setTitle(arrayList.size() + " Persons");
                } else {
                    toolbar.setTitle(arrayList.size() + " Person");
                }

            }
        });

        swipeContainer.setColorSchemeColors(getThemeColor());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        readDataFromDatabase(0);

        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        id = sharedPref.getInt("id", 1);

        if (id == 1) {
            adapterForMainFragment = new AdapterForMainFragment(arrayList, 0);
        } else if (id == 2) {
            adapterForRemainingBirthday = new AdapterForRemainingBirthday(arrayList, 0);
        } else if (id == 3) {
            adapterForUpcomingBirthday = new AdapterForUpcomingBirthday(arrayList, 2, 0);
        } else {
            adapterForUpcomingBirthday = new AdapterForUpcomingBirthday(arrayList, 1, 0);
        }

        showFragments(id);

        if (id == 1) {
            adapterForMainFragment.setReadData(new AdapterForMainFragment.ReadData() {
                @Override
                public void refresh(int i, int id, String name, int userYears, int userMonths, int userDays) {
                    readDataFromDatabase(0);
                    if (arrayList.size() > 1) {
                        toolbar.setTitle(arrayList.size() + " Persons");
                    } else {
                        toolbar.setTitle(arrayList.size() + " Person");
                    }
                }
            });
        } else if (id == 2) {
            adapterForRemainingBirthday.setReadData(new AdapterForRemainingBirthday.ReadData() {
                @Override
                public void refresh(int i, int id, String name, int userYears, int userMonths, int userDays) {
                    readDataFromDatabase(0);
                    if (arrayList.size() > 1) {
                        toolbar.setTitle(arrayList.size() + " Persons");
                    } else {
                        toolbar.setTitle(arrayList.size() + " Person");
                    }
                }
            });
        } else {
            adapterForUpcomingBirthday.setReadData(new AdapterForUpcomingBirthday.ReadData() {
                @Override
                public void refresh(int i, int id, String name, int userYears, int userMonths, int userDays) {
                    readDataFromDatabase(0);
                    if (arrayList.size() > 1) {
                        toolbar.setTitle(arrayList.size() + " Persons");
                    } else {
                        toolbar.setTitle(arrayList.size() + " Person");
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        swipeContainer = findViewById(R.id.swipe_refresh);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderBy = "_id DESC";
        itemSelected = 5;

        openHelper = new SQLiteDatabaseOpenHelper(this);
        sqLiteDatabase = openHelper.getReadableDatabase();

        arrayList = new ArrayList<>();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (menu instanceof MenuBuilder) {

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        MenuItem item = menu.findItem(R.id.search_items);

        SearchView searchView = (SearchView) item.getActionView();

        customSearchView(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (id == 1) {
                    adapterForMainFragment.getFilter().filter(newText);
                } else if (id == 2) {
                    adapterForRemainingBirthday.getFilter().filter(newText);
                } else {
                    adapterForUpcomingBirthday.getFilter().filter(newText);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_items:
                showSorting(id);
                return true;

            case R.id.add_person:
                showPopupToAddPerson();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (Objects.requireNonNull(item).getItemId()) {

            case R.id.upcoming_birthday:
                editor.putInt("id", 3);
                editor.apply();
                onStart();
                break;

            case R.id.next_birthdays:
                editor.putInt("id", 2);
                editor.apply();
                onStart();
                break;

            case R.id.date_of_birth:
                editor.putInt("id", 4);
                editor.apply();
                onStart();
                break;

            case R.id.current_age:
                editor.putInt("id", 1);
                editor.apply();
                onStart();
                break;

            case R.id.add_person:
                showPopupToAddPerson();
                break;

            case R.id.compare_to:
                Intent compareTo = new Intent(this, ActivityForAgeComparison.class);
                startActivity(compareTo);
                break;

            case R.id.check_leap:
                Intent checkLeap = new Intent(this, ActivityForLeapYears.class);
                startActivity(checkLeap);
                break;

            case R.id.working_experience:
                Intent workingExperience = new Intent(this, ActivityForWorkingExperience.class);
                startActivity(workingExperience);
                break;

            case R.id.delete_everything:
                showPopupToDeleteAllItems();
                break;

            case R.id.change_theme:
                View dialogView;
                dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_for_app_theme, null);
                BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetMenuTheme); // Style here
                dialog.setContentView(dialogView);
                dialog.show();
                break;

            case R.id.month_by:
                showPopupMonthWeekDay(1);
                return true;

            case R.id.week_by:
                showPopupMonthWeekDay(2);
                return true;

            case R.id.day_by:
                showPopupMonthWeekDay(3);
                return true;

            case R.id.show_facebook_profile:
                visitDev();
                break;

            case R.id.send_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:hlayanhtetaung@gmail.com"));
                startActivity(emailIntent);
                break;

            default:
                showToast("This is default");
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (recyclerView.computeVerticalScrollOffset() != 0) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            super.onBackPressed();
        }
    }

    private void showPopupToAddPerson() {

        dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogbox_for_adding_person, null);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        final EditText editText = dialogView.findViewById(R.id.editText);
        Button save = dialogView.findViewById(R.id.save);
        Button cancel = dialogView.findViewById(R.id.cancel);

        save.setText(R.string.save);

        datePicker.setMaxDate(new Date().getTime());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int years = datePicker.getYear();
                int months = datePicker.getMonth() + 1;
                int days = datePicker.getDayOfMonth();
                String name = editText.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    editText.setError("Enter name please!");
                    return;
                }

                LocalDate weeks = new LocalDate(years, months, days);
                int daysOfWeek = weeks.getDayOfWeek();

                int totalDays = AgeCalculator.calculateTotalDays(years, months, days);

                insertDataToDatabase(name, days, months, years, daysOfWeek, totalDays);
                readDataFromDatabase(0);
                if (id == 1) {
                    adapterForMainFragment.notifyDataSetChanged();
                } else if (id == 2) {
                    adapterForRemainingBirthday.notifyDataSetChanged();
                } else {
                    adapterForUpcomingBirthday.notifyDataSetChanged();
                }
                showToast("Added successfully");
                if (arrayList.size() > 1) {
                    toolbar.setTitle(arrayList.size() + " Persons");
                } else {
                    toolbar.setTitle(arrayList.size() + " Person");
                }
                dialog.cancel();

                if (recyclerView.computeVerticalScrollOffset() != 0) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void showPopupToCalculateAge() {

        dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogbox_for_date_picker, null);
        dialog.setCancelable(true);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Button save = dialogView.findViewById(R.id.save);
        Button cancel = dialogView.findViewById(R.id.cancel);

        datePicker.setMaxDate(new Date().getTime());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int years = datePicker.getYear();
                final int months = datePicker.getMonth() + 1;
                final int days = datePicker.getDayOfMonth();

                Intent intent = new Intent(getApplicationContext(), ActivityForDetailsOfBirthday.class);
                intent.putExtra("name", "Anonymous");
                intent.putExtra("userYears", years);
                intent.putExtra("userMonths", months);
                intent.putExtra("userDays", days);
                startActivity(intent);

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

    private void showPopupToDeleteAllItems() {

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialogbox_for_delete);
        Button yes = dialog.findViewById(R.id.save);
        Button no = dialog.findViewById(R.id.cancel);
        yes.setText(R.string.delete);
        TextView deleteItem = dialog.findViewById(R.id.warning_text);
        deleteItem.setText(R.string.delete_all_item);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase.delete("date_of_birth", null, null);
                readDataFromDatabase(0);
                adapterForMainFragment.notifyDataSetChanged();
                showToast("Deleted Everything");
                if (arrayList.size() > 1) {
                    toolbar.setTitle(arrayList.size() + " Persons");
                } else {
                    toolbar.setTitle(arrayList.size() + " Person");
                }
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    public void onClickAddFloatAction(View view) {
        showPopupToCalculateAge();
    }

    private void insertDataToDatabase(String name, int days, int months, int years, int daysOfWeek, int totalDays) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("day", days);
        contentValues.put("month", months);
        contentValues.put("year", years);
        contentValues.put("days_of_week", daysOfWeek);
        contentValues.put("total_days", totalDays);
        sqLiteDatabase.insert("date_of_birth", null, contentValues);
    }

    public void showPopupMonthWeekDay(int id) {
        loadAdapterForMonthWeekDay(id);
        View dialogView;
        dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_month_week_day, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetMenuTheme); // Style here
        dialog.setContentView(dialogView);
        dialog.show();

        TextView textView = dialogView.findViewById(R.id.button_sheet_title);

        if (id != 1) {
            if (id == 2) {
                textView.setText(R.string.WEEKS);
            } else {
                textView.setText(R.string.DAYS);
            }
        }

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setAdapter(adapterForMonthWeekDay);

    }

    void loadAdapterForMonthWeekDay(int id) {
        int[] arrayCount;
        if (id == 1) {
            arrayCount = resultsForTwelveMonths("month", id);
            adapterForMonthWeekDay = new AdapterForMonthWeekDay(arrayCount, AgeCalculator.MONTH12, 0);
        } else if (id == 2) {
            arrayCount = resultsForTwelveMonths("days_of_week", id);
            adapterForMonthWeekDay = new AdapterForMonthWeekDay(arrayCount, AgeCalculator.WEEK7, 2);
        } else {
            arrayCount = resultsForTwelveMonths("day", id);
            adapterForMonthWeekDay = new AdapterForMonthWeekDay(arrayCount, AgeCalculator.DAY31, 1);
        }
    }

    private int[] resultsForTwelveMonths(String month, int id) {

        ArrayList<Integer> arrayListForMonths = new ArrayList<>();

        cursor = sqLiteDatabase.query(
                "date_of_birth",
                new String[]{month},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                arrayListForMonths.add(cursor.getInt(0));
            } while (cursor.moveToNext());

        }

        AgeCalculator ageCalculator = new AgeCalculator();

        if (id == 1) {
            return ageCalculator.calculateTwelveMonths(arrayListForMonths);
        } else if (id == 2) {
            return ageCalculator.calculateDaysOfWeek(arrayListForMonths);
        } else {
            return ageCalculator.calculate31Days(arrayListForMonths);
        }
    }

    public void onClickButton1(View view) {
        ThemeUtils.changeToTheme(this, 1);
    }

    public void onClickButton2(View view) {
        ThemeUtils.changeToTheme(this, 2);
    }

    public void onClickButton3(View view) {
        ThemeUtils.changeToTheme(this, 3);
    }

    public void onClickButton4(View view) {
        ThemeUtils.changeToTheme(this, 4);
    }

    public void onClickButton5(View view) {
        ThemeUtils.changeToTheme(this, 5);
    }

    public void onClickButton6(View view) {
        ThemeUtils.changeToTheme(this, 6);
    }

    public void onClickButton7(View view) {
        ThemeUtils.changeToTheme(this, 7);
    }

    public void onClickButton8(View view) {
        ThemeUtils.changeToTheme(this, 8);
    }

    public void onClickButton9(View view) {
        ThemeUtils.changeToTheme(this, 9);
    }

    public void onClickButton10(View view) {
        ThemeUtils.changeToTheme(this, 10);
    }

    public void onClickButton11(View view) {
        ThemeUtils.changeToTheme(this, 11);
    }

    public void onClickButton12(View view) {
        ThemeUtils.changeToTheme(this, 12);
    }

    public void onClickButton13(View view) {
        ThemeUtils.changeToTheme(this, 13);
    }

    public void onClickButton14(View view) {
        ThemeUtils.changeToTheme(this, 14);
    }

    public void onClickButton15(View view) {
        ThemeUtils.changeToTheme(this, 15);
    }

    public void onClickButton16(View view) {
        ThemeUtils.changeToTheme(this, 16);
    }

    public void onClickButton17(View view) {
        ThemeUtils.changeToTheme(this, 17);
    }

    public void onClickButton18(View view) {
        ThemeUtils.changeToTheme(this, 18);
    }

    public void onClickButton19(View view) {
        ThemeUtils.changeToTheme(this, 19);
    }

    public void onClickButton20(View view) {
        ThemeUtils.changeToTheme(this, 20);
    }
}
