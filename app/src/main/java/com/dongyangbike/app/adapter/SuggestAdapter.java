package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.http.ack.ParkDetailAck;

import java.util.List;

/**
 * Created by hasee on 2017/4/19.
 */

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {
    private Context mContext;
    private List<ParkDetailAck.ListBeanX> list;
    private ClickListener mClickListener;
    private int mSelectIndex = -1;

    public interface ClickListener {
        void onItemClick(int position);
    }

    public SuggestAdapter(Context context, List<ParkDetailAck.ListBeanX> list, ClickListener listener) {
        this.mContext = context;
        this.list = list;
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_suggest, parent,
                    false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text1.setText("推荐" + (position + 1));
        holder.text2.setText("车位编号：" + list.get(position).getId());
        holder.text3.setText("车位描述：" + list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectIndex = position;
                mClickListener.onItemClick(position);
                notifyDataSetChanged();
            }
        });

        if(position == mSelectIndex) {
            holder.layout.setBackgroundResource(R.drawable.bg_square_green_not_solid);
        } else {
            holder.layout.setBackgroundResource(R.drawable.bg_square_gray_not_solid);
        }
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

        LinearLayout layout;
        TextView text1;
        TextView text2;
        TextView text3;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.item_suggest);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            text3 = view.findViewById(R.id.text3);
        }
    }
}
