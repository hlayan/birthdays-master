package com.hlayanhtetaung.birthdaysmaster.adapter;

import android.app.Dialog;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hlayanhtetaung.birthdaysmaster.activity.ActivityForDetailsOfBirthday;
import com.hlayanhtetaung.birthdaysmaster.database.SQLiteDatabaseOpenHelper;
import com.hlayanhtetaung.birthdaysmaster.R;
import com.hlayanhtetaung.birthdaysmaster.data.DataClass;
import com.hlayanhtetaung.birthdaysmaster.logic.AgeCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterForMainFragment extends RecyclerView.Adapter<AdapterForMainFragment.ViewHolderForRecyclerView> implements Filterable {

    private ArrayList<DataClass> arrayList;
    public ArrayList<DataClass> arrayListFull;
    public android.database.sqlite.SQLiteDatabase sqLiteDatabase;
    private ReadData readData;
    int result;

    public interface ReadData {
        void refresh(int result, int id, String name, int userYears, int userMonths, int userDays);
    }

    public void setReadData(ReadData readData) {
        this.readData = readData;
    }

    public AdapterForMainFragment(ArrayList<DataClass> arrayList, int result) {
        this.arrayList = arrayList;
        this.result = result;
        arrayListFull = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public ViewHolderForRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_current_age, parent, false);
        return new ViewHolderForRecyclerView(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderForRecyclerView holder, final int position) {
        final CardView cardView = holder.cardView;

        setAnimation(holder.cardView, position, cardView.getContext());

        SQLiteDatabaseOpenHelper SQLiteDatabaseOpenHelper = new SQLiteDatabaseOpenHelper(cardView.getContext());
        sqLiteDatabase = SQLiteDatabaseOpenHelper.getReadableDatabase();

        TextView title = cardView.findViewById(R.id.title);
        TextView years = cardView.findViewById(R.id.currentYears);
        TextView months = cardView.findViewById(R.id.currentMonths);
        TextView days = cardView.findViewById(R.id.currentDays);

        DataClass dataClass = AgeCalculator.calculateAge(arrayList.get(position).getYears(), arrayList.get(position).getMonths(), arrayList.get(position).getDays());

        title.setText(arrayList.get(position).getName());
        years.setText(String.valueOf(dataClass.getYears()));
        months.setText(String.valueOf(dataClass.getMonths()));
        days.setText(String.valueOf(dataClass.getDays()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result == 0) {
                    Intent intent = new Intent(cardView.getContext(), ActivityForDetailsOfBirthday.class);

                    intent.putExtra("id", arrayList.get(position).getId());
                    intent.putExtra("name", arrayList.get(position).getName());
                    intent.putExtra("userYears", arrayList.get(position).getYears());
                    intent.putExtra("userMonths", arrayList.get(position).getMonths());
                    intent.putExtra("userDays", arrayList.get(position).getDays());

                    cardView.getContext().startActivity(intent);
                } else {
                    readData.refresh(result, arrayList.get(position).getId(), arrayList.get(position).getName(), arrayList.get(position).getYears(), arrayList.get(position).getMonths(), arrayList.get(position).getDays());
                }

            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (result == 0) {

                    showPopupToDeleteItem(cardView.getContext(), position);

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

    public void showPopupToDeleteItem(Context context, final int position) {

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
                sqLiteDatabase.delete("date_of_birth", "_id = ?", new String[]{String.valueOf(arrayList.get(position).getId())});
                readData.refresh(0, arrayList.get(position).getId(), arrayList.get(position).getName(), arrayList.get(position).getYears(), arrayList.get(position).getMonths(), arrayList.get(position).getDays());
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