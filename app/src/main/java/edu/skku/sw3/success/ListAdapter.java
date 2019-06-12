package edu.skku.sw3.success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<ListItem> items;

    public ListAdapter (Context context, ArrayList<ListItem> lists){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = lists;
    }

    @Override
    public  int getCount(){ return  items.size(); }

    @Override
    public ListItem getItem(int i){ return items.get(i); }

    @Override
    public long getItemId(int i){return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if(view == null){
            view = inflater.inflate(R.layout.list_layout, viewGroup, false);
        }

        ListItem item = items.get(i);

        TextView tv1 = (TextView)view.findViewById(R.id.title_tv);
        TextView tv2 = (TextView)view.findViewById(R.id.category_tv);
        TextView tv3 = (TextView)view.findViewById(R.id.date_tv);

        tv1.setText(item.getTitle());
        tv2.setText(item.getMainCategory());
        tv3.setText(item.getDate());

        return  view;
    }
}
