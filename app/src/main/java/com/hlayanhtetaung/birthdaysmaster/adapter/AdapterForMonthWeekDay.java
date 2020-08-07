package com.hlayanhtetaung.birthdaysmaster.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hlayanhtetaung.birthdaysmaster.activity.ActivityForRecyclerView;
import com.hlayanhtetaung.birthdaysmaster.R;

import java.util.Objects;

public class AdapterForMonthWeekDay extends RecyclerView.Adapter<AdapterForMonthWeekDay.ViewHolderForRecyclerView> {

    private int[] arrayList;
    private String[] strings;
    private String checking;

    public AdapterForMonthWeekDay(int[] arrayList, String[] strings, int count) {
        this.arrayList = arrayList;
        this.strings = strings;
        if (count == 0) {
            checking = "month = ?";
        } else if (count == 1) {
            checking = "day = ?";
        } else {
            checking = "days_of_week = ?";
        }
    }

    @NonNull
    @Override
    public ViewHolderForRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        final CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_months_weeks_days, parent, false);
        return new ViewHolderForRecyclerView(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderForRecyclerView holder, final int position) {
        final CardView cardView = holder.cardView;

        TextView title = cardView.findViewById(R.id.title);
        final TextView count = cardView.findViewById(R.id.count);

        title.setText(strings[position]);
        count.setText(String.valueOf(arrayList[position]));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), ActivityForRecyclerView.class);
                intent.putExtra("title", strings[position]);
                intent.putExtra("count", String.valueOf(position + 1));
                intent.putExtra("checking", checking);
                cardView.getContext().startActivity(intent);
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String resultText;
                if (arrayList[position] > 1) {
                    resultText = "It has (" + arrayList[position] + ") persons in " + strings[position];
                } else {
                    resultText = "It has (" + arrayList[position] + ") person in " + strings[position];
                }
                showAlertDialog(strings[position], resultText, cardView.getContext());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.length;
    }

    static class ViewHolderForRecyclerView extends RecyclerView.ViewHolder {

        private CardView cardView;

        ViewHolderForRecyclerView(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public void showAlertDialog(String title, String message, Context context) {
        final Dialog dialog = new Dialog(context);
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

}