package com.example.sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class monhocadapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<monhoc> list;

    public monhocadapter(MainActivity context, int layout, List<monhoc> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView textView;
        ImageView imgedit, imgdelete;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.textView = (TextView) view.findViewById(R.id.tenmonhoc);
            holder.imgedit = (ImageView) view.findViewById(R.id.repairmh);
            holder.imgdelete = (ImageView) view.findViewById(R.id.removemh);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();

        }
        final monhoc mh = list.get(i);
        holder.textView.setText(mh.getMonhoc());
        // sự kiện xóa và sửa
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.adddiaglogrepair(mh.getMonhoc(), mh.getId());
            }
        });
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.removeconfim(mh.getMonhoc(), mh.getId());
            }
        });
        return view;
    }
}
