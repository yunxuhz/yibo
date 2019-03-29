package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.http.ack.MessageAck;

import java.util.List;

/**
 * Created by hasee on 2017/4/19.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private List<MessageAck.ListBean> list;

    public MessageAdapter(Context context, List<MessageAck.ListBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent,
                    false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.message.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getCreate_time());
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

        TextView message;
        TextView title;
        TextView time;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
            time = view.findViewById(R.id.time);
        }
    }
}
