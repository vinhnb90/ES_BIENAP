package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VINH-PC on 2/5/2018.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportAdapterViewHolder> {
    private static Drawable ic_done_task, ic_edit_note_menu, ic_recybin;
    private Common.MODE mode;
    private static List<DataReport> listReport = new ArrayList<>();
//    private static HashMap<String, Boolean> mapDeviceChoose = new HashMap<>();

    private static IOnReportAdapter mIteractor;

    public ReportAdapter(Context context, List<DataReport> listReport, Common.MODE mode, IOnReportAdapter mIteractor) {
        this.listReport.clear();
        this.listReport = Common.cloneList(listReport);
        this.mode = mode;
        this.mIteractor = mIteractor;

//        if (ic_done_task == null)
//            ic_done_task = ContextCompat.getDrawable(context, R.drawable.ic_done_task);
//
//        if (ic_edit_note_menu == null)
//            ic_edit_note_menu = ContextCompat.getDrawable(context, R.drawable.ic_edit_note_menu);
//
//        if (ic_recybin == null)
//            ic_recybin = ContextCompat.getDrawable(context, R.drawable.ic_recybin);
    }

    @Override
    public ReportAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyler_search, null, false);
        ReportAdapterViewHolder viewHolder = new ReportAdapterViewHolder(viewRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReportAdapterViewHolder holder, int position) {
        DataReport data = listReport.get(position);
        holder.tvTitle.setText(data.getREPORT_NAME());
        holder.tvDate.setText(Common.convertDateToDate(data.getREPORT_DATE(), Common.DATE_TIME_TYPE.sqlite2, Common.DATE_TIME_TYPE.type61));

        Common.STATUS status = Common.STATUS.findSTATUS(data.getREPORT_STATUS());
        holder.tvStatus.setText(status.content);

        boolean isChecked = data.isChoose;

        if (mode == Common.MODE.ADMIN) {
            holder.rbChoose.setChecked(isChecked);

            holder.btnEdit.setVisibility(View.INVISIBLE);
            holder.btnUpload.setVisibility(View.INVISIBLE);
            holder.rbChoose.setEnabled(false);

            switch (status) {
                case EDIT:
                    holder.ivStatus.setImageResource(R.drawable.ic_edit_note_menu);
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.btnUpload.setVisibility(View.VISIBLE);
                    holder.rbChoose.setEnabled(true);
                    break;
                case PUBLISH:
                    holder.ivStatus.setImageResource(R.drawable.ic_done_task);
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.btnUpload.setVisibility(View.INVISIBLE);
                    holder.rbChoose.setEnabled(false);
                    break;
                case DELETE:
                    holder.ivStatus.setImageResource(R.drawable.ic_recybin);
                    holder.btnEdit.setVisibility(View.INVISIBLE);
                    holder.btnUpload.setVisibility(View.INVISIBLE);
                    holder.rbChoose.setEnabled(false);
                    break;
            }
        } else {
            holder.ivStatus.setImageResource(R.drawable.ic_done_task);
            holder.btnUpload.setVisibility(View.INVISIBLE);
            holder.rbChoose.setEnabled(false);
            holder.btnEdit.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return this.listReport.size();
    }

    public void refresh(List<DataReport> listDevice, Common.MODE mMode) {
        this.listReport.clear();
        this.listReport = Common.cloneList(listDevice);
        this.mode = mMode;

        notifyDataSetChanged();
    }

    public static class ReportAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatus;
        TextView tvDate;
        TextView tvStatus;
        CheckBox rbChoose;
        Button btnEdit;
        Button btnUpload;
        TextView tvTitle;

        public ReportAdapterViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_row);
            ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_row);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status_row);
            rbChoose = (CheckBox) itemView.findViewById(R.id.cb_choose_row);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit_row);
            btnUpload = (Button) itemView.findViewById(R.id.btn_upload_row);


            /*click*/
            rbChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (rbChoose.isPressed()) {
                        int pos = getAdapterPosition();
                        listReport.get(pos).setChoose(rbChoose.isChecked());
                    }
                }
            });

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIteractor.clickUpload(getAdapterPosition());
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIteractor.clickEdit(getAdapterPosition());
                }
            });
        }
    }

    public List<DataReport> getListDeviceChoose() {
        List<DataReport> listDeviceChoose = new ArrayList<>();
        for (DataReport a : listReport) {
            if (a.isChoose) {
                listDeviceChoose.add(a);
            }
        }

        return listDeviceChoose;
    }

//    public void changeData(DataReport oldKey, DataReport newKey, Boolean value) {
////        hashMap.put("New_Key", hashMap.remove("Old_Key"));
//        mapDeviceChoose.put(newKey, mapDeviceChoose.remove(oldKey));
//    }

    public List<DataReport> getListReport() {
        return listReport;
    }


    public interface IOnReportAdapter {

        void clickUpload(int adapterPosition);

        void clickEdit(int adapterPosition);
    }

    public static class DataReport implements Cloneable {
        private String REPORT_NAME;
        private String REPORT_DATE;
        private String REPORT_STATUS;

        private boolean isChoose;

        public String getREPORT_NAME() {
            return REPORT_NAME;
        }

        public void setREPORT_NAME(String REPORT_NAME) {
            this.REPORT_NAME = REPORT_NAME;
        }

        public String getREPORT_DATE() {
            return REPORT_DATE;
        }

        public void setREPORT_DATE(String REPORT_DATE) {
            this.REPORT_DATE = REPORT_DATE;
        }

        public String getREPORT_STATUS() {
            return REPORT_STATUS;
        }

        public void setREPORT_STATUS(String REPORT_STATUS) {
            this.REPORT_STATUS = REPORT_STATUS;
        }


        public boolean isChoose() {
            return isChoose;
        }

        public DataReport setChoose(boolean choose) {
            isChoose = choose;
            return this;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
