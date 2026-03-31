package io.cordova.ifb.adapter;



import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.NewCompetitorDisplayMatrixActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<NewCompetitorDisplayMatrixActivity.Category> list;

    public CategoryAdapter(List<NewCompetitorDisplayMatrixActivity.Category> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewCompetitorDisplayMatrixActivity.Category item = list.get(position);
        Context context = holder.itemView.getContext();

        // Title
        holder.txtTitle.setText(item.categoryName);

        // 🔥 Build Capacity Header
        holder.headerCapacityContainer.removeAllViews();

        for (String cap : item.capacities) {

            TextView tv = new TextView(context);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(200,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            tv.setLayoutParams(params);
            tv.setText(cap);
            tv.setGravity(Gravity.CENTER);
            tv.setTypeface(null, Typeface.BOLD);

            holder.headerCapacityContainer.addView(tv);
        }

        // Rows
        holder.recyclerRows.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerRows.setAdapter(new RowAdapter(item, holder.headerScroll));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        RecyclerView recyclerRows;
        HorizontalScrollView headerScroll;
        LinearLayout headerCapacityContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            recyclerRows = itemView.findViewById(R.id.recyclerRows);
            headerScroll = itemView.findViewById(R.id.headerScroll);
            headerCapacityContainer = itemView.findViewById(R.id.headerCapacityContainer);
        }
    }
}
