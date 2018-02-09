package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by VINH-PC on 2/5/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceAdapterViewHolder> {
    private static Drawable ic_done_task, ic_edit_note_menu, ic_recybin;
    private Common.MODE mode;
    private static List<DataDevice> listDevice = new ArrayList<>();
//    private static HashMap<String, Boolean> mapDeviceChoose = new HashMap<>();

    private static IOnDeviceAdapter mIteractor;

    public DeviceAdapter(Context context, List<DataDevice> listDevice, Common.MODE mode, IOnDeviceAdapter mIteractor) {
        this.listDevice.clear();
        this.listDevice = Common.cloneList(listDevice);
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
    public DeviceAdapter.DeviceAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyler_search, null, false);
        DeviceAdapterViewHolder viewHolder = new DeviceAdapterViewHolder(viewRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.DeviceAdapterViewHolder holder, int position) {
        DataDevice data = listDevice.get(position);
        holder.tvTitle.setText(data.getDEVICE_NAME());
        holder.tvDate.setText(Common.convertDateToDate(data.getDEVICE_DATE(), Common.DATE_TIME_TYPE.sqlite2, Common.DATE_TIME_TYPE.type61));

        Common.STATUS status = Common.STATUS.findSTATUS(data.getDEVICE_STATUS());
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
        return this.listDevice.size();
    }

    public void refresh(List<DataDevice> listDevice, Common.MODE mMode) {
        this.listDevice.clear();
        this.listDevice = Common.cloneList(listDevice);
        this.mode = mMode;

        notifyDataSetChanged();
    }

    public static class DeviceAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatus;
        TextView tvDate;
        TextView tvStatus;
        CheckBox rbChoose;
        Button btnEdit;
        Button btnUpload;
        TextView tvTitle;

        public DeviceAdapterViewHolder(View itemView) {
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
                        listDevice.get(pos).setChoose(rbChoose.isChecked());
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

    public List<DataDevice> getListDeviceChoose() {
        List<DataDevice> listDeviceChoose = new ArrayList<>();
        for (DataDevice a : listDevice) {
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

    public List<DataDevice> getListDevice() {
        return listDevice;
    }


    public interface IOnDeviceAdapter {

        void clickUpload(int adapterPosition);

        void clickEdit(int adapterPosition);
    }

    public static class DataDevice implements Cloneable {
        private String DEVICE_CODE;
        private String DEVICE_NAME;
        private String DEVICE_DATE;
        private String DEVICE_STATUS;
        private String DEVICE_ADDRESS;

        private boolean isChoose;

        public String getDEVICE_CODE() {
            return DEVICE_CODE;
        }

        public void setDEVICE_CODE(String DEVICE_CODE) {
            this.DEVICE_CODE = DEVICE_CODE;
        }

        public String getDEVICE_NAME() {
            return DEVICE_NAME;
        }

        public void setDEVICE_NAME(String DEVICE_NAME) {
            this.DEVICE_NAME = DEVICE_NAME;
        }

        public String getDEVICE_DATE() {
            return DEVICE_DATE;
        }

        public void setDEVICE_DATE(String DEVICE_DATE) {
            this.DEVICE_DATE = DEVICE_DATE;
        }

        public String getDEVICE_STATUS() {
            return DEVICE_STATUS;
        }

        public void setDEVICE_STATUS(String DEVICE_STATUS) {
            this.DEVICE_STATUS = DEVICE_STATUS;
        }

        public String getDEVICE_ADDRESS() {
            return DEVICE_ADDRESS;
        }

        public void setDEVICE_ADDRESS(String DEVICE_ADDRESS) {
            this.DEVICE_ADDRESS = DEVICE_ADDRESS;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public DataDevice setChoose(boolean choose) {
            isChoose = choose;
            return this;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
