package io.cordova.ifb.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.NewCompetitorDisplayMatrixActivity;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {

    NewCompetitorDisplayMatrixActivity.Category category;
    HorizontalScrollView headerScroll;

    List<HorizontalScrollView> scrollViews = new ArrayList<>();

    public RowAdapter(NewCompetitorDisplayMatrixActivity.Category category, HorizontalScrollView headerScroll) {
        this.category = category;
        this.headerScroll = headerScroll;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_dynamic, parent, false);
        return new RowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

        NewCompetitorDisplayMatrixActivity.BrandRow row = category.brands.get(position);
        Context context = holder.itemView.getContext();

        // ✅ Set Brand Name
        holder.txtBrand.setText(row.brandName);

        // ✅ Clear old views (VERY IMPORTANT)
        holder.containerCapacity.removeAllViews();

        int total = 0;

        for (int i = 0; i < category.capacities.size(); i++) {

            int value = row.values.get(i);
            total += value;

            EditText et = new EditText(context);

            // 🔥 IMPORTANT: width must match header
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(4, 0, 4, 0);

            et.setLayoutParams(params);

            // ✅ Apply your border design
            et.setBackgroundResource(R.drawable.lldesign24);
            et.setBackgroundTintList(null);

            et.setText(String.valueOf(value));
            et.setGravity(Gravity.CENTER);
            et.setTextSize(14);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setPadding(8, 8, 8, 8);

            int index = i;

            et.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
                @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}

                @Override
                public void afterTextChanged(Editable s) {

                    int val = 0;
                    if (!s.toString().isEmpty()) {
                        val = Integer.parseInt(s.toString());
                    }

                    // ✅ Save value in model
                    row.values.set(index, val);

                    // ✅ Update total WITHOUT refreshing row
                    int sum = 0;
                    for (int v : row.values) {
                        sum += v;
                    }

                    holder.txtTotal.setText(String.valueOf(sum));
                }
            });

            holder.containerCapacity.addView(et);
        }

        // ✅ Set total initially
        holder.txtTotal.setText(String.valueOf(total));

        // 🔥 OPTIONAL: SCROLL SYNC (if you already implemented)
        if (headerScroll != null) {
            holder.rowScroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
                headerScroll.scrollTo(holder.rowScroll.getScrollX(), 0);
            });

            headerScroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
                holder.rowScroll.scrollTo(headerScroll.getScrollX(), 0);
            });
        }
    }

    @Override
    public int getItemCount() {
        return category.brands.size();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {

        TextView txtBrand;
        TextView txtTotal;

        HorizontalScrollView rowScroll;
        LinearLayout containerCapacity;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBrand = itemView.findViewById(R.id.txtBrand);
            txtTotal = itemView.findViewById(R.id.txtTotal);

            rowScroll = itemView.findViewById(R.id.rowScroll);
            containerCapacity = itemView.findViewById(R.id.containerCapacity);
        }
    }
}