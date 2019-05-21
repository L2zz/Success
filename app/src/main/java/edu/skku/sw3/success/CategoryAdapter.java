package edu.skku.sw3.success;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<String> categoryList;
    private Context context;
    private View.OnClickListener onClickCategory;

    public CategoryAdapter(Context context, ArrayList<String> categoryList, View.OnClickListener onClickCategory) {
        this.categoryList = categoryList;
        this.context = context;
        this.onClickCategory = onClickCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_category, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {
        String categoryTitle = categoryList.get(i);

        viewHolder.category.setText(categoryTitle);
        viewHolder.category.setTag(categoryTitle);
        viewHolder.category.setOnClickListener(onClickCategory);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_title);
        }
    }
}
