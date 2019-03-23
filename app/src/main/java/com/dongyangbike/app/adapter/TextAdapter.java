package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dongyangbike.app.R;

import java.util.List;

/**
 * Created by hasee on 2017/4/19.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    private Context mContext;
    private List<String> list;

    public TextAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_text_bg_rect, parent,
                    false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
            return list.size() ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(View view) {
            super(view);

            text = view.findViewById(R.id.size);
        }
    }
}
