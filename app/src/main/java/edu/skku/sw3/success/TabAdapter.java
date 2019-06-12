package edu.skku.sw3.success;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {
    private ArrayList<String> tabList;
    private Context context;
    private Integer lastSelectedIndex;
    private CategoryOnClickListener categoryOnClickListener;

    public TabAdapter(Context context, ArrayList<String> tabList, CategoryOnClickListener onClickListener) {
        this.tabList = tabList;
        this.context = context;
        this.categoryOnClickListener = onClickListener;
    }

    public interface CategoryOnClickListener {
        void onCategoryClicked(int position);
    }

    @NonNull
    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_tab, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        String categoryTitle = tabList.get(i);

        viewHolder.tabTitle.setText(categoryTitle);
        viewHolder.tabTitle.setTag(categoryTitle);
        viewHolder.tabTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryOnClickListener.onCategoryClicked(pos);
            }
        });
        if (lastSelectedIndex != null) {
            viewHolder.tabTitle.setSelected(i == lastSelectedIndex);
        }
    }

    @Override
    public int getItemCount() {
        return tabList.size();
    }

    public void setLastSelectedIndex(int pos) {
        lastSelectedIndex = pos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tabTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tabTitle = itemView.findViewById(R.id.tab_title);
        }
    }

    public void removeAll() {
        tabList.clear();
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        tabList.add(item);
        notifyDataSetChanged();
    }
}
