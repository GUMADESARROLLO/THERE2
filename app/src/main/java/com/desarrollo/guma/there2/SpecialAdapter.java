package com.desarrollo.guma.there2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by marangelo.php on 27/10/2016.
 */

public class SpecialAdapter extends android.widget.SimpleAdapter {
    private int[] colors = new int[] { 0x30ffffff, 0x30ffffff};
    public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}