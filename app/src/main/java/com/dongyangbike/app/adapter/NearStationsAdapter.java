package com.dongyangbike.app.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.http.ack.NearStationsAck;
import com.dongyangbike.app.http.ack.SearchAck;
import com.dongyangbike.app.util.MapUtils;

import java.util.ArrayList;
import java.util.Arrays;

import tech.gujin.toast.ToastUtil;

/**
 * Created by hasee on 2017/4/19.
 */

public class NearStationsAdapter extends RecyclerView.Adapter<NearStationsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<NearStationsAck.Data.Row> list;
    private ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(int position);
    }

    public NearStationsAdapter(Context context, ArrayList<NearStationsAck.Data.Row> list, ClickListener listener) {
        this.mContext = context;
        this.list = list;
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_near_stations, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NearStationsAck.Data.Row data = list.get(position);
        holder.yardName.setText(data.getYardName());
        holder.distance.setText("距离" + data.getDistance());
        holder.count.setText(data.getSurplusCount() + "个车位可预约");
        holder.yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtils.isBaiduMapInstalled()){
                    MapUtils.openBaiDuNavi(mContext, 0, 0, null, data.getLatitude(), data.getLongitude(), data.getAddress());
                } else if (MapUtils.isGdMapInstalled()){
                    MapUtils.openGaoDeNavi(mContext, 0, 0, null, data.getLatitude(), data.getLongitude(), data.getAddress());
                } else {
                    ToastUtil.show("尚未安装百度地图和高德地图");
                }
            }
        });

        if (data.getLabel() != null) {
            String size[] = data.getLabel().split(",");
            TextAdapter adapter = new TextAdapter(mContext, Arrays.asList(size));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.labelRecyclerView.setLayoutManager(layoutManager);
            holder.labelRecyclerView.setFocusable(false);
            holder.labelRecyclerView.setHasFixedSize(true);
            holder.labelRecyclerView.setAdapter(adapter);
        } else {
            holder.labelRecyclerView.setVisibility(View.INVISIBLE);
        }

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
        TextView count;
        RecyclerView labelRecyclerView;
        TextView daohang;
        TextView yuyue;
        TextView distance;

        public ViewHolder(View view) {
            super(view);

            yardName = view.findViewById(R.id.yardName);
            count = view.findViewById(R.id.count);
            labelRecyclerView = view.findViewById(R.id.labelRecyclerView);
            daohang = view.findViewById(R.id.daohang);
            yuyue = view.findViewById(R.id.yuyue);
            distance = view.findViewById(R.id.distance);
        }
    }
}
