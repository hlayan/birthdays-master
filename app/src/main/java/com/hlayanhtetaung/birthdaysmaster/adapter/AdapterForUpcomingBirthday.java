package com.hlayanhtetaung.birthdaysmaster.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hlayanhtetaung.birthdaysmaster.ActivityForDetailsOfBirthday;
import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterForUpcomingBirthday extends RecyclerView.Adapter<AdapterForUpcomingBirthday.ViewHolderForRecyclerView> implements Filterable {

    private ArrayList<DataClass> arrayList;
    private ArrayList<DataClass> arrayListFull;
    private android.database.sqlite.SQLiteDatabase sqLiteDatabase;
    private ReadData readData;
    int checkYears, id;
    int result;
    private LocalDate now = new LocalDate();

    public interface ReadData{
        void refresh(int result, int id, String name, int userYears, int userMonths, int userDays);
    }

    public void setReadData(ReadData readData){
        this.readData = readData;
    }

    public AdapterForUpcomingBirthday(ArrayList<DataClass> arrayList, int id, int result) {
        this.arrayList = arrayList;
        this.id = id;
        this.result=result;
        arrayListFull = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public ViewHolderForRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_upcoming_birthday, parent,false );
        return new ViewHolderForRecyclerView(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderForRecyclerView holder, final int position) {
        final CardView cardView = holder.cardView;

        setAnimation(holder.cardView, position, cardView.getContext());

        SQLiteDatabaseOpenHelper SQLiteDatabaseOpenHelper = new SQLiteDatabaseOpenHelper(cardView.getContext());
        sqLiteDatabase = SQLiteDatabaseOpenHelper.getReadableDatabase();

        TextView name = cardView.findViewById(R.id.upcomingBirthday);
        TextView upcomingBirthdayYears = cardView.findViewById(R.id.upcomingBirthdayYears);
        TextView upcomingBirthdayMonths = cardView.findViewById(R.id.upcomingBirthdayMonths);
        TextView upcomingBirthdayDays = cardView.findViewById(R.id.upcomingBirthdayDays);
        TextView upcomingBirthdayDaysOfWeek = cardView.findViewById(R.id.upcomingBirthdayDaysOfWeek);

        if (id == 1){

            LocalDate birthday = new LocalDate(arrayList.get(position).getYears(), arrayList.get(position).getMonths(), arrayList.get(position).getDays());
            String daysOfWeek = AgeCalculator.checkDaysOfWeeks(birthday.getDayOfWeek());
            String months = AgeCalculator.checkMonths(arrayList.get(position).getMonths());

            name.setText(arrayList.get(position).getName());
            upcomingBirthdayYears.setText(String.valueOf(arrayList.get(position).getYears()));
            upcomingBirthdayMonths.setText(months);
            upcomingBirthdayDays.setText(String.valueOf(arrayList.get(position).getDays()));
            upcomingBirthdayDaysOfWeek.setText(daysOfWeek);

        }else{

            checkYears = now.getYear();

            if (now.getMonthOfYear()>arrayList.get(position).getMonths()){

                checkYears++;

            }else if (now.getDayOfMonth()>arrayList.get(position).getDays() && now.getMonthOfYear()==arrayList.get(position).getMonths()){

                checkYears++;

            }else if (now.getDayOfMonth()==arrayList.get(position).getDays() && now.getMonthOfYear()==arrayList.get(position).getMonths()){

                checkYears++;

            }
            LocalDate upcomingBirthday = new LocalDate(checkYears, arrayList.get(position).getMonths(), arrayList.get(position).getDays());
            String upcomingDaysOfWeek = AgeCalculator.checkDaysOfWeeks(upcomingBirthday.getDayOfWeek());
            String months = AgeCalculator.checkMonths(arrayList.get(position).getMonths());

            name.setText(arrayList.get(position).getName());
            upcomingBirthdayYears.setText(String.valueOf(checkYears));
            upcomingBirthdayMonths.setText(months);
            upcomingBirthdayDays.setText(String.valueOf(arrayList.get(position).getDays()));
            upcomingBirthdayDaysOfWeek.setText(upcomingDaysOfWeek);

        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id != 3){
                    if (result == 0){
                        Intent intent = new Intent(cardView.getContext(), ActivityForDetailsOfBirthday.class);

                        intent.putExtra("id",arrayList.get(position).getId());
                        intent.putExtra("name",arrayList.get(position).getName());
                        intent.putExtra("userYears",arrayList.get(position).getYears());
                        intent.putExtra("userMonths",arrayList.get(position).getMonths());
                        intent.putExtra("userDays",arrayList.get(position).getDays());

                        cardView.getContext().startActivity(intent);
                    }else {
                        readData.refresh(result,arrayList.get(position).getId(),arrayList.get(position).getName(),arrayList.get(position).getYears(),arrayList.get(position).getMonths(),arrayList.get(position).getDays());
                    }
                }else {
                    DataClass dataClass = AgeCalculator.calculateNextBirthday(arrayList.get(position).getMonths(),arrayList.get(position).getDays());
                    String resultText;
                    if (dataClass.getMonths() == 0 && dataClass.getDays() == 0){
                        resultText = "ဒီနေ့သည် \"" + arrayList.get(position).getName() + "\" နေ့ဖြစ်သည်။";
                    }else if (dataClass.getMonths() == 0){
                        resultText = "\"" + arrayList.get(position).getName() + "\" သို့ရောက်ရန် " + dataClass.getDays() + "ရက်သာကျန်ပါတော့သည်။";
                    }else {
                        resultText = "\"" + arrayList.get(position).getName() + "\" သို့ရောက်ရန် " + dataClass.getMonths() + "လ၊ " + dataClass.getDays() + "ရက်ကျန်ပါသေးသည်။";
                    }
                    new AlertDialog.Builder(cardView.getContext())
                            .setTitle("Remaining Time!")
                            .setMessage(resultText)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (id != 3){
                    if (result == 0){
                        showPopupToDeleteAllItems(cardView.getContext(),position);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolderForRecyclerView extends RecyclerView.ViewHolder {

        private CardView cardView;

        ViewHolderForRecyclerView(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataClass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DataClass item : arrayListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private void showPopupToDeleteAllItems(Context context, final int position) {

        final Dialog dialog = new Dialog(context);
        String resultText = "It will delete \"" + arrayList.get(position).getName() + "\" !";

        dialog.setContentView(R.layout.dialogbox_for_delete);
        Button yes = dialog.findViewById(R.id.save);
        Button no = dialog.findViewById(R.id.cancel);
        yes.setText(R.string.delete);
        TextView deleteItem = dialog.findViewById(R.id.warning_text);
        deleteItem.setText(resultText);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase.delete("date_of_birth","_id = ?",new String[]{String.valueOf(arrayList.get(position).getId())});
                readData.refresh(0,arrayList.get(position).getId(),arrayList.get(position).getName(),arrayList.get(position).getYears(),arrayList.get(position).getMonths(),arrayList.get(position).getDays());
                notifyDataSetChanged();
                dialog.cancel();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    private void setAnimation(View viewToAnimate, int position, Context context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > -1) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fall_down);
            viewToAnimate.startAnimation(animation);
        }
    }

}