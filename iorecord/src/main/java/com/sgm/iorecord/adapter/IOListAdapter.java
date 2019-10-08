package com.sgm.iorecord.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgm.iorecord.R;
import com.sgm.iorecord.bean.IOBean;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class IOListAdapter extends RecyclerView.Adapter<IOListAdapter.ViewHolder> {

    private List<IOBean> ioBeans;

    public void setData(List<IOBean> data) {
        this.ioBeans = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.ioban_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IOBean item = ioBeans.get(position);
        holder.mContentTv.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return ioBeans == null ? 0 : ioBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mContentTv = itemView.findViewById(R.id.m_content_tv);
        }
    }
}
