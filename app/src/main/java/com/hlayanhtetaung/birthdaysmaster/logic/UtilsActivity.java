package com.hlayanhtetaung.birthdaysmaster.logic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForMainFragment;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForNextBirthday;
import com.hlayanhtetaung.birthdaysmaster.adapter.AdapterForUpcomingBirthday;
import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.Objects;

public class UtilsActivity extends AppCompatActivity {

    public AdapterForNextBirthday adapterForNextBirthday;
    public AdapterForUpcomingBirthday adapterForUpcomingBirthday;
    public AdapterForMainFragment adapterForMainFragment;
    public SQLiteDatabaseOpenHelper openHelper;
    public android.database.sqlite.SQLiteDatabase sqLiteDatabase;
    public Cursor cursor;
    public ArrayList<DataClass> arrayList;
    public String orderBy, count, checking;
    public int itemSelected, id;
    public static RecyclerView recyclerView;

    @Override
    protected void onDestroy() {

        if (openHelper != null){
            openHelper.close();
        }

        if (sqLiteDatabase != null){
            sqLiteDatabase.close();
        }

        if (cursor != null){
            cursor.close();
        }

        super.onDestroy();
    }

    public void customSearchView(SearchView searchView){

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search");

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.background);

        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(searchSrcTextId);
        searchEditText.setHintTextColor(Color.LTGRAY);
        searchEditText.setTextColor(getResources().getColor(android.R.color.black));

        int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = searchView.findViewById(closeButtonId);
        closeButtonImage.setColorFilter(ContextCompat.getColor(this, android.R.color.black), PorterDuff.Mode.SRC_IN);
    }

    public void visitDev(){
        try
        {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);
            if (appInfo.enabled)
            { Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100016026428007"));
                startActivity(i1);
            } else  {
                Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/HlayanHtetAung"));
                startActivity(i2);
            }
        }
        catch (Exception e) {
            Intent i3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/HlayanHtetAung"));
            startActivity(i3);
        }
    }

    public void showFragments(int id){

        this.id = id;

        recyclerView = findViewById(R.id.recyclerView);
        final TextView emptyView = findViewById(R.id.empty_view);
        isEmptyRecycler(recyclerView,emptyView);

        GridLayoutManager gridLayout = new GridLayoutManager(this,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayout);

        if (id == 1){
            recyclerView.setAdapter(adapterForMainFragment);
            adapterForMainFragment.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    isEmptyRecycler(recyclerView,emptyView);
                }
            });
        }else if (id == 2){
            recyclerView.setAdapter(adapterForNextBirthday);
            adapterForNextBirthday.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    isEmptyRecycler(recyclerView,emptyView);
                }
            });
        }else {
            recyclerView.setAdapter(adapterForUpcomingBirthday);
            adapterForUpcomingBirthday.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    isEmptyRecycler(recyclerView,emptyView);
                }
            });
        }

    }

    public void showToast(String string){
        Toast backToast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
        backToast.show();
    }

    public void showSorting (final int id){
        String[] singleChoiceItems = new String[]{"A to Z", "Z to A", "Age Ascending", "Age Descending", "Time Ascending", "Time Descending",
                "Month Ascending", "Month Descending", "Week Ascending", "Week Descending", "Day Ascending", "Day Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                switch (selectedIndex){
                    case 0:
                        itemSelected = 0;
                        orderBy = "name ASC";
                        break;
                    case 1:
                        itemSelected = 1;
                        orderBy = "name DESC";
                        break;
                    case 2:
                        itemSelected = 2;
                        orderBy = "total_days ASC";
                        break;
                    case 3:
                        itemSelected = 3;
                        orderBy = "total_days DESC";
                        break;
                    case 4:
                        itemSelected = 4;
                        orderBy = "_id ASC";
                        break;
                    case 5:
                        itemSelected = 5;
                        orderBy = "_id DESC";
                        break;
                    case 6:
                        itemSelected = 6;
                        orderBy = "month ASC";
                        break;
                    case 7:
                        itemSelected = 7;
                        orderBy = "month DESC";
                        break;
                    case 8:
                        itemSelected = 8;
                        orderBy = "days_of_week ASC";
                        break;
                    case 9:
                        itemSelected = 9;
                        orderBy = "days_of_week DESC";
                        break;
                    case 10:
                        itemSelected = 10;
                        orderBy = "day ASC";
                        break;
                    case 11:
                        itemSelected = 11;
                        orderBy = "day DESC";
                        break;
                }
                if (id == 0){
                    readDataFromDatabase(1);
                    adapterForMainFragment.notifyDataSetChanged();
                }else {
                    readDataFromDatabase(0);
                    if (id == 1){
                        adapterForMainFragment.notifyDataSetChanged();
                    }else if (id == 2){
                        adapterForNextBirthday.notifyDataSetChanged();
                    }else{
                        adapterForUpcomingBirthday.notifyDataSetChanged();
                    }
                }
                dialogInterface.dismiss();
                if (recyclerView.computeVerticalScrollOffset() != 0){
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    public void readDataFromDatabase(int id) {
        arrayList.clear();
        if (id==0){
            cursor = sqLiteDatabase.query(
                    "date_of_birth",
                    new String[]{"_id", "name", "day", "month", "year"},// The columns to return
                    null,
                    null,
                    null,
                    null,
                    orderBy
            );
        }else {
            cursor = sqLiteDatabase.query(
                    "date_of_birth",
                    new String[]{"_id", "name", "day", "month", "year"},// The columns to return
                    checking,
                    new String[] {count},
                    null,
                    null,
                    orderBy
            );
        }
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(new DataClass(cursor.getInt(0), cursor.getString(1), cursor.getInt(4), cursor.getInt(3), cursor.getInt(2)));
            } while (cursor.moveToNext());

        }
    }

    public void showAlertDialog(String title, String message){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogbox_for_show_info);
        TextView messageText = dialog.findViewById(R.id.warning_text);
        messageText.setText(message);
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    public void isEmptyRecycler(RecyclerView recyclerView, TextView textView){

        int itemCount;

        if (id == 1){
            itemCount = adapterForMainFragment.getItemCount();
        }else if (id == 2){
            itemCount = adapterForNextBirthday.getItemCount();
        }else {
            itemCount = adapterForUpcomingBirthday.getItemCount();
        }

        if(itemCount==0){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

}
