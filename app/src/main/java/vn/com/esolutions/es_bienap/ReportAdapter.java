package vn.com.esolutions.es_bienap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.com.esolutions.es_bienap.database.TABLE_DEVICE;

/**
 * Created by VINH-PC on 2/5/2018.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportAdapterViewHolder> {



    public ReportAdapter() {
    }

    @Override
    public ReportAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyler_search, null, false);
        ReportAdapterViewHolder viewHolder = new ReportAdapterViewHolder(viewRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReportAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class ReportAdapterViewHolder extends RecyclerView.ViewHolder {

        public ReportAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }


}
