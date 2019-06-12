package edu.skku.sw3.success;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerSiteAdapter extends BaseAdapter {
    private ArrayList<Site> sites;
    private SparseBooleanArray selectedSites;

    public DrawerSiteAdapter(ArrayList<Site> sites) {
        this.sites = sites;
        this.selectedSites = new SparseBooleanArray();
    }

    public DrawerSiteAdapter(ArrayList<Site> sites, SparseBooleanArray selectedSites) {
        this.sites = sites;
        this.selectedSites = selectedSites;
    }

    @Override
    public int getCount() {
        return sites.size();
    }

    @Override
    public Object getItem(int position) {
        return sites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_drawer, parent, false);
        }

        Site site = sites.get(position);
        TextView siteTitle =  convertView.findViewById(R.id.drawer_site_title);
        siteTitle.setText(site.getTitle());
        LinearLayout siteBG = convertView.findViewById(R.id.drawer_bg);

        siteBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSites.get(pos, false)) {
                    selectedSites.delete(pos);
                    v.setBackgroundColor(context.getResources()
                            .getColor(android.R.color.background_light));
                } else {
                    selectedSites.put(pos, true);
                    v.setBackgroundColor(context.getResources()
                            .getColor(android.R.color.darker_gray));
                }
            }
        });

        if (selectedSites.get(pos, false)) {
            siteBG.setBackgroundColor(context.getResources()
                    .getColor(android.R.color.darker_gray));
        } else {
            siteBG.setBackgroundColor(context.getResources()
                    .getColor(android.R.color.background_light));
        }

        return convertView;
    }

    public SparseBooleanArray getSelected() {
        return selectedSites;
    }
}
