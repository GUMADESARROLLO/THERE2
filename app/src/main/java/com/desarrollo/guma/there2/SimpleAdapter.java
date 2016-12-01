package com.desarrollo.guma.there2;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.desarrollo.guma.core.*;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> implements ItemClickListener
{
    private final Context context;
    private List<Cliente> items;
    public List<Cliente> getFilter(String query){
        List<Cliente> newitems = new ArrayList<>();
        query = query.toLowerCase();
        if (query.isEmpty()){ newitems.addAll(items); }
        else
        {
            for(Cliente obj : items)
            {
                if (obj.getName().toLowerCase().contains(query))
                {
                    newitems.add(new Cliente(obj.getName(),obj.getCod(),obj.getDir()));
                }
            }
            if (newitems.size()>0)
            {
                List<Cliente> newFinal = new ArrayList<>();
                items.addAll(newFinal);
            }
        }
        return newitems;
    }
    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView nombre;
        public ImageView avatar;
        public ItemClickListener listener;
        public SimpleViewHolder(View v, ItemClickListener listener)
        {
            super(v);
            nombre = (TextView) v.findViewById(R.id.list_item_textview);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            this.listener = listener;
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) { listener.onItemClick(v, getAdapterPosition()); }
    }
    public SimpleAdapter(Context context, List<Cliente> items)
    {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getItemCount() { return items.size(); }
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new SimpleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false), this);
    }
    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int i)
    {
        Cliente currentItem = items.get(i);
        viewHolder.nombre.setText(currentItem.getName());
        Glide.with(viewHolder.avatar.getContext()).load(currentItem.getIdDrawable()).centerCrop().into(viewHolder.avatar);
    }
    @Override
    public void onItemClick(View view, int position) {
        DetailActivity.createInstance((Activity) context, items.get(position));
    }
}
interface ItemClickListener { void onItemClick(View view, int position); }