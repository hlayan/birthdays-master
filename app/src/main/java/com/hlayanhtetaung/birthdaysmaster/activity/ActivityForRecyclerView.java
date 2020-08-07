package com.hlayanhtetaung.birthdaysmaster.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForMainFragment;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;
import com.hlayanhtetaung.birthdaysmaster.logic.ThemeUtils;
import com.hlayanhtetaung.birthdaysmaster.logic.UtilsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ActivityForRecyclerView extends UtilsActivity {

    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_recycler_view);

        result = Objects.requireNonNull(getIntent().getExtras()).getInt("result");
        String title = Objects.requireNonNull(getIntent().getExtras()).getString("title");
        count = Objects.requireNonNull(getIntent().getExtras()).getString("count");
        checking = Objects.requireNonNull(getIntent().getExtras()).getString("checking");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        openHelper = new SQLiteDatabaseOpenHelper(this);
        sqLiteDatabase = openHelper.getReadableDatabase();

        orderBy = "_id DESC";
        itemSelected = 5;

        arrayList = new ArrayList<>();
        if (result == 0) {
            readDataFromDatabase(1);
        } else {
            readDataFromDatabase(0);
        }

        adapterForMainFragment = new AdapterForMainFragment(arrayList, result);
        showFragments(1);

        adapterForMainFragment.setReadData(new AdapterForMainFragment.ReadData() {
            @Override
            public void refresh(int i, int id, String name, int userYears, int userMonths, int userDays) {
                if (i == 0) {
                    readDataFromDatabase(1);
                } else {
                    returnResult(id, name, userYears, userMonths, userDays, result);
                }
            }
        });

        final SwipeRefreshLayout swipeContainer = findViewById(R.id.swipe_refresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread background = new Thread() {
                    public void run() {
                        try {
                            sleep(500);
                            swipeContainer.setRefreshing(false);
                        } catch (Exception ignored) {
                        }
                    }
                };
                background.start();
                showToast("Refreshed!");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_for_fragments, menu);
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
                adapterForMainFragment.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_items) {
            if (result == 0) {
                showSorting(0);
            } else {
                showSorting(1);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.computeVerticalScrollOffset() != 0) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            super.onBackPressed();
        }
    }

    public void onClickAddFloatAction(View view) {
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogbox_for_date_picker, null);
        dialog.setCancelable(true);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Button save = dialogView.findViewById(R.id.save);
        Button cancel = dialogView.findViewById(R.id.cancel);
        if (result != 0) {
            save.setText(R.string.confirm);
        }
        datePicker.setMaxDate(new Date().getTime());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int years = datePicker.getYear();
                final int months = datePicker.getMonth() + 1;
                final int days = datePicker.getDayOfMonth();
                if (result == 0) {
                    Intent intent = new Intent(getApplicationContext(), ActivityForDetailsOfBirthday.class);
                    intent.putExtra("name", "Details");
                    intent.putExtra("userYears", years);
                    intent.putExtra("userMonths", months);
                    intent.putExtra("userDays", days);
                    startActivity(intent);
                } else if (result == 1) {
                    returnResult(0, "Person One", years, months, days, result);
                } else {
                    returnResult(0, "Person Two", years, months, days, result);
                }
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

    void returnResult(int id, String name, int userYears, int userMonths, int userDays, int resultCode) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("userYears", userYears);
        intent.putExtra("userMonths", userMonths);
        intent.putExtra("userDays", userDays);
        setResult(resultCode, intent);
        finish();
    }

}
