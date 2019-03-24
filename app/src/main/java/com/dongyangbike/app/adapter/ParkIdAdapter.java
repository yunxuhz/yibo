package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.http.ack.GetRecommendedAck;
import com.dongyangbike.app.http.ack.ParkDetailAck;

import java.util.List;

/**
 * Created by hasee on 2017/4/19.
 */

public class ParkIdAdapter extends RecyclerView.Adapter<ParkIdAdapter.ViewHolder> {
    private Context mContext;
    private List<GetRecommendedAck.ListBeanX.ListBean> list;
    private ClickListener mClickListener;
    private int mSelectIndex = -1;

    public interface ClickListener {
        void onItemClick(int position);
    }

    public ParkIdAdapter(Context context, List<GetRecommendedAck.ListBeanX.ListBean> list, int selectIndex, ClickListener listener) {
        this.mContext = context;
        this.list = list;
        this.mSelectIndex = selectIndex;
        this.mClickListener = listener;
    }

    public void updateData(List<GetRecommendedAck.ListBeanX.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void resetSelected() {
        mSelectIndex = -1;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_park_id, parent,
                    false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text1.setText("车位号：" + list.get(position).getLocation_number());

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
        if(list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout;
        TextView text1;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.item_suggest);
            text1 = view.findViewById(R.id.text1);
        }
    }
}
