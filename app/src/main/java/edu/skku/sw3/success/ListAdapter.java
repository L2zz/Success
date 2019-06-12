package edu.skku.sw3.success;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
    public View getView(int i, View view, final ViewGroup viewGroup){
        if(view == null){
            view = inflater.inflate(R.layout.list_layout, viewGroup, false);
        }

        final ListItem item = items.get(i);

        TextView tv1 = (TextView)view.findViewById(R.id.title_tv);
        TextView tv3 = (TextView)view.findViewById(R.id.date_tv);
        ConstraintLayout bg = view.findViewById(R.id.list_bg);

        tv1.setText(item.getTitle());
        tv3.setText(item.getDate());

        return  view;
    }

    public void addItem(ListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int pos, ListItem item) {
        items.add(pos, item);
        notifyDataSetChanged();
    }
}
