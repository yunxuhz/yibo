package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.http.ack.SearchAck;

import java.util.ArrayList;

/**
 * Created by hasee on 2017/4/19.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SearchAck.Data> list;
    private ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(int position);
    }

    public SearchResultAdapter(Context context, ArrayList<SearchAck.Data> list, ClickListener listener) {
        this.mContext = context;
        this.list = list;
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SearchAck.Data data = list.get(position);
        holder.yardName.setText(data.getYardName());
        holder.merchantName.setText(data.getMerchantName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(position);
            }
        });
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

        TextView yardName;
        TextView merchantName;

        public ViewHolder(View view) {
            super(view);

            yardName = view.findViewById(R.id.yardName);
            merchantName = view.findViewById(R.id.merchantName);
        }
    }
}
