package vn.com.esolutions.es_bienap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import esolutions.com.esdatabaselib.baseSqlite.LazyList;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;
import vn.com.esolutions.es_bienap.database.TABLE_DEVICE;

import static vn.com.esolutions.es_bienap.Common.BUNDLE_MODE;
import static vn.com.esolutions.es_bienap.Common.TIME_DELAY_CLICK_LONG;
import static vn.com.esolutions.es_bienap.Common.TIME_DELAY_CLICK_SHORT;


public class DeviceFragment extends Fragment implements IBaseView {
    private IOnDeviceFragment mListener;
    private Unbinder unbinder;


    /*View*/
    @BindView(R.id.rl_add_device)
    RelativeLayout rlAddDevice;

    @BindView(R.id.btn_add)
    Button btnAddDevice;

    @BindView(R.id.tv_ma_thietbi)
    TextView tvDeviceCode;

    @BindView(R.id.et_nhap_ten_thietbi)
    EditText etDeviceName;

    @BindView(R.id.et_nhap_diachi_thietbi)
    EditText etAddDevice;

    @BindView(R.id.btn_save_thietbi)
    Button btnSaveDevice;


    /*View*/
    @BindView(R.id.rl_search_device)
    RelativeLayout rlSearchDevice;


    @BindView(R.id.btn_search)
    Button btnSearchDevice;

    @BindView(R.id.btn_search_server)
    Button btnSearchDeviceServer;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.ibtn_clear_search)
    ImageButton ibtnClearSearch;

    @BindView(R.id.rv_list_device)
    RecyclerView rvListDevice;


    /*View*/
    @BindView(R.id.rl_upload_device)
    RelativeLayout rlUpload;


    @BindView(R.id.btn_upload)
    Button btnUpload;

    @BindView(R.id.tv_total_device)
    TextView tvTotalDevice;

    @BindView(R.id.tv_count_device_choose)
    TextView tvTotalDeviceChoose;

    @BindView(R.id.pbar_upload)
    ProgressBar pbarUpload;

    @BindView(R.id.tv_count_percent)
    TextView tvCountPercent;

    @BindView(R.id.btn_upload_device)
    Button btnUploadDevice;


    private DAO database;
    private Common.MODE mMode;
    private DeviceAdapter.IOnDeviceAdapter mIteractor;
    private String hasError;

    //region click button menu top
    @OnClick(R.id.btn_upload)
    public void clickBtnUpload(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        if (rvListDevice.getAdapter() == null) {
            mListener.showMessage("Cần chọn trước khi gửi", null, new ISnackbarIteractions() {
                @Override
                public void doIfPressOK() {
                    clickBtnSearch(btnSearchDevice);
                }
            });
            return;
        }

        showHideView(R.id.btn_upload);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTotalDevice.setText(((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().size() + "");
                tvTotalDeviceChoose.setText(((DeviceAdapter) rvListDevice.getAdapter()).getListDeviceChoose().size() + "");

                //init view
                tvCountPercent.setText("0%");
                pbarUpload.setProgress(0);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<DeviceAdapter.DataDevice> listDeviceChoose = ((DeviceAdapter) rvListDevice.getAdapter()).getListDeviceChoose();
                        final int count = listDeviceChoose.size();

                        for (int i = 0; i < count; i++) {
                            final int iI = i;
                            pbarUpload.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        pbarUpload.setProgress(iI * 100 / count);
                                        tvCountPercent.setText((iI * 100 / count) + "%");

                                        //data
                                        //update
                                        //get data if has
                                        String DEVICE_CODE = listDeviceChoose.get(iI).getDEVICE_CODE();
                                        List<TABLE_DEVICE> tableDevices = database.getTABLE_DEVICE(new String[]{
                                                DEVICE_CODE,
                                                mMode.content
                                        });

                                        TABLE_DEVICE tableDeviceOld = new TABLE_DEVICE();
                                        if (tableDevices.size() != 0) {
                                            tableDeviceOld = tableDevices.get(0);
                                            ((LazyList<TABLE_DEVICE>) tableDevices).closeCursor();
                                        }

                                        //save
                                        TABLE_DEVICE tableDeviceNew = (TABLE_DEVICE) tableDeviceOld.clone();
                                        tableDeviceNew.setDEVICE_STATUS(Common.STATUS.PUBLISH.content);
                                        tableDeviceNew.setID_TABLE_DEVICE((int) database.updateORInsertRows(TABLE_DEVICE.class, tableDeviceOld, tableDeviceNew));


                                        if (tableDeviceNew.getID_TABLE_DEVICE() != 0) {
                                            //refresh data
                                        } else {
                                            throw new Exception("Cập nhật thất bại thiết bị DEVICE_CODE = " + tableDeviceNew.getDEVICE_CODE());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        hasError.concat("\nGặp vấn đề khi phát hành thiết bị\nNội dung lỗi: " + e.getMessage());
                                    }

                                }
                            }, Common.TIME_DELAY_CLICK_MORE_SHORT);
                        }


                        //wwhen finish
                        pbarUpload.post(new Runnable() {
                            @Override
                            public void run() {
                                pbarUpload.setProgress(100);
                                tvCountPercent.setText(100 + "%");
                                if (!TextUtils.isEmpty(hasError)) {
                                    tvCountPercent.setText("Có xảy ra lỗi khi phát hành thiết bị!");
                                }
                            }
                        });

                    }
                }).start();

            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }


    @OnClick(R.id.btn_search)
    public void clickBtnSearch(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        showHideView(R.id.btn_search);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<DeviceAdapter.DataDevice> tableDevices = database.getDeviceAdapter(mMode);

                //setup interactor
                if (mIteractor == null)
                    mIteractor = new DeviceAdapter.IOnDeviceAdapter() {
                        @Override
                        public void clickUpload(final int adapterPosition) {
                            //fake ok set up
                            showDialog("Đang tiến hành phát hành thiết bị!", new IOnDismisDialog() {
                                        @Override
                                        public void onDismiss() {
                                            try {
                                                mListener.showMessage("Phát hành thiết bị thành công!", null, null);

                                                //update
                                                //get data if has
                                                String DEVICE_CODE = ((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().get(adapterPosition).getDEVICE_CODE();
                                                List<TABLE_DEVICE> tableDevices = database.getTABLE_DEVICE(new String[]{
                                                        DEVICE_CODE,
                                                        mMode.content
                                                });

                                                TABLE_DEVICE tableDeviceOld = new TABLE_DEVICE();
                                                if (tableDevices.size() != 0) {
                                                    tableDeviceOld = tableDevices.get(0);
                                                    ((LazyList<TABLE_DEVICE>) tableDevices).closeCursor();
                                                }

                                                //save
                                                TABLE_DEVICE tableDeviceNew = (TABLE_DEVICE) tableDeviceOld.clone();
                                                tableDeviceNew.setDEVICE_STATUS(Common.STATUS.PUBLISH.content);
                                                tableDeviceNew.setID_TABLE_DEVICE((int) database.updateORInsertRows(TABLE_DEVICE.class, tableDeviceOld, tableDeviceNew));

                                                if (tableDeviceNew.getID_TABLE_DEVICE() != 0) {
                                                    //refresh data
                                                    DeviceAdapter.DataDevice oldData = (DeviceAdapter.DataDevice) ((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().get(adapterPosition).clone();
                                                    ((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().get(adapterPosition).setDEVICE_STATUS(Common.STATUS.PUBLISH.content);
                                                    DeviceAdapter.DataDevice newData = (DeviceAdapter.DataDevice) ((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().get(adapterPosition).clone();
                                                    ((DeviceAdapter) rvListDevice.getAdapter()).changeData(oldData, newData, true);
                                                    ((DeviceAdapter) rvListDevice.getAdapter()).notifyDataSetChanged();
                                                    rvListDevice.invalidate();

                                                    mListener.showMessage("Phát hành thiết bị thành công!", null, null);
                                                } else {
                                                    throw new Exception("Gặp lỗi khi thêm mới thiết bị\nNội dung lỗi : Phát hành thất bại, kiểm tra kết nối!");
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                mListener.showMessage("Gặp vấn đề khi phát hành thiết bị\nNội dung lỗi: " + e.getMessage(), null, null);
                                            }
                                        }
                                    }
                            );
//                            ((DeviceAdapter) rvListDevice.getAdapter())
                        }

                        @Override
                        public void clickEdit(int adapterPosition) {
                            try {
                                //clear text
                                tvDeviceCode.setText("");
                                etDeviceName.setHint("");
                                etDeviceName.setText("");
                                etAddDevice.setHint("");
                                etAddDevice.setText("");


                                DeviceAdapter.DataDevice dataDevice = ((DeviceAdapter) rvListDevice.getAdapter()).getListDevice().get(adapterPosition);

                                //set text
                                tvDeviceCode.setText(dataDevice.getDEVICE_CODE());
                                etDeviceName.setHint(dataDevice.getDEVICE_NAME());
                                etDeviceName.setText(dataDevice.getDEVICE_NAME());
                                etAddDevice.setHint(dataDevice.getDEVICE_ADDRESS());
                                etAddDevice.setText(dataDevice.getDEVICE_ADDRESS());


                                showHideView(R.id.btn_add);

                            } catch (Exception e) {
                                e.printStackTrace();
                                mListener.showMessage(e.getMessage(), null, null);
                            }
                        }
                    };

                if (rvListDevice.getAdapter() == null) {
                    DeviceAdapter adapter = new DeviceAdapter(getContext(), tableDevices, mMode, mIteractor);
                    rvListDevice.setAdapter(adapter);
                    rvListDevice.invalidate();
                } else {
                    ((DeviceAdapter) rvListDevice.getAdapter()).refresh(tableDevices, null, mMode);
                    rvListDevice.invalidate();
                }

            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }


    @OnClick(R.id.btn_add)
    public void clickBtnAdd(View view) {
        //clear text
        tvDeviceCode.setText("");

        etDeviceName.setHint("");
        etDeviceName.setText("");

        etAddDevice.setHint("");
        etAddDevice.setText("");

        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        showHideView(R.id.btn_add);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                BarcodeScannerDialog dialog = new BarcodeScannerDialog(DeviceFragment.this.getContext(), new BarcodeScannerDialog.OnResultListener() {
                    @Override
                    public void onResult(String text) {
                        text = "CODE" + new Random().nextLong();
                        tvDeviceCode.setText(text);
                        try {
                            if (TextUtils.isEmpty(text)) {
                                clickBtnSearch(btnSearchDevice);
                                return;
                            }

                            //check database
//                            boolean hasDevice = database.isExistRows(
//                                    TABLE_DEVICE.class,
//                                    new String[]{
//                                            TABLE_DEVICE.table.DEVICE_CODE.name(),
//                                            TABLE_DEVICE.table.MODE.name()},
//                                    new String[]{
//                                            tvDeviceCode.getText().toString(),
//                                            mMode.content
//                                    });

                            List<TABLE_DEVICE> tableDevices = database.getTABLE_DEVICE(new String[]{
                                    tvDeviceCode.getText().toString(),
                                    mMode.content
                            });

                            if (tableDevices.size() > 0) {
                                TABLE_DEVICE tableDevice = tableDevices.get(0);
                                ((LazyList<TABLE_DEVICE>) tableDevices).closeCursor();


                                //set text
                                tvDeviceCode.setText(tableDevice.getDEVICE_CODE());

                                etDeviceName.setHint(tableDevice.getDEVICE_NAME());
                                etDeviceName.setText(tableDevice.getDEVICE_NAME());

                                etAddDevice.setHint(tableDevice.getDEVICE_ADDRESS());
                                etAddDevice.setText(tableDevice.getDEVICE_ADDRESS());

                                mListener.showMessage("Đã tồn tại dữ liệu!", null, null);
                            } else {
                                searchOnline(tvDeviceCode.getText().toString(), mMode.content);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            mListener.showMessage(e.getMessage(), null, null);
                        }
                    }
                });
                dialog.show();
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }

    private void searchOnline(final String deviceCode, String content) {
        //fake result from server
        Common.MODE mode = Common.MODE.findMODEbyContent(content);
        switch (mode) {
            case ADMIN:
                //if has, fake ko có case này
                //if no.
                showDialog("Đang kiểm tra trên máy chủ!", new IOnDismisDialog() {
                    @Override
                    public void onDismiss() {
                        tvDeviceCode.setText(deviceCode);
                        mListener.showMessage("Không có dữ liệu thiết bị trên máy chủ!", null, null);
                    }
                });
                break;
            case EMP:
                //không có chức năng này
                break;
        }
    }

    private void showDialog(String message, final IOnDismisDialog iteractorDialog) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_process);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        ((TextView) dialog.findViewById(R.id.tv_message_loadding)).setText(message);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                iteractorDialog.onDismiss();
            }
        });

        dialog.show();

        dialog.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, TIME_DELAY_CLICK_LONG);

    }
    //endregion

    //region click view add new device
    @OnClick(R.id.btn_save_thietbi)
    public void clickBtnSaveDevice(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    //check et
                    if (TextUtils.isEmpty(etDeviceName.getText().toString())) {
                        etDeviceName.setError("Thiết bị cần nhập tên");
                        throw new Exception("Thiết bị cần nhập tên");
                    }


                    //get data if has
                    List<TABLE_DEVICE> tableDevices = database.getTABLE_DEVICE(new String[]{
                            tvDeviceCode.getText().toString(),
                            mMode.content
                    });

                    TABLE_DEVICE tableDeviceOld = new TABLE_DEVICE();
                    if (tableDevices.size() != 0) {
                        tableDeviceOld = tableDevices.get(0);
                        ((LazyList<TABLE_DEVICE>) tableDevices).closeCursor();
                    }


                    //save
                    TABLE_DEVICE tableDeviceNew = new TABLE_DEVICE();
                    tableDeviceNew.setDEVICE_NAME(etDeviceName.getText().toString());
                    tableDeviceNew.setDEVICE_CODE(tvDeviceCode.getText().toString());
                    tableDeviceNew.setDEVICE_ADDRESS(etAddDevice.getText().toString());
                    tableDeviceNew.setDEVICE_DATE(Common.getDateTimeNow(Common.DATE_TIME_TYPE.sqlite2));
                    tableDeviceNew.setDEVICE_STATUS(Common.STATUS.EDIT.content);
                    tableDeviceNew.setMODE(Common.MODE.ADMIN.content);


                    tableDeviceNew.setID_TABLE_DEVICE((int) database.updateORInsertRows(TABLE_DEVICE.class, tableDeviceOld, tableDeviceNew));

                    if (tableDeviceNew.getID_TABLE_DEVICE() != 0) {
                        tvDeviceCode.setText(tableDeviceNew.getDEVICE_CODE());

                        etDeviceName.setHint(tableDeviceNew.getDEVICE_NAME());
                        etDeviceName.setText(tableDeviceNew.getDEVICE_NAME());

                        etAddDevice.setHint(tableDeviceNew.getDEVICE_ADDRESS());
                        etAddDevice.setText(tableDeviceNew.getDEVICE_ADDRESS());

                        mListener.showMessage("Thêm mới thiết bị thành công!", null, null);
                    } else {
                        throw new Exception("Gặp lỗi khi thêm mới thiết bị\nNội dung lỗi : Lưu dữ liệu không thành công!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListener.showMessage(e.getMessage(), null, null);
                }
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }
    //endregion

    //region click view upload device
    @OnClick(R.id.btn_upload_device)
    public void clickBtnUploadDevice(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    //check et
                    if (Integer.parseInt(tvTotalDeviceChoose.getText().toString()) == 0) {
                        throw new Exception("Cần chọn thiết bị để phát hanh");
                    }


                    //get data if has
                    List<TABLE_DEVICE> tableDevices = database.getTABLE_DEVICE(new String[]{
                            tvDeviceCode.getText().toString(),
                            mMode.content
                    });

                    TABLE_DEVICE tableDeviceOld = new TABLE_DEVICE();
                    if (tableDevices.size() != 0) {
                        tableDeviceOld = tableDevices.get(0);
                        ((LazyList<TABLE_DEVICE>) tableDevices).closeCursor();
                    }


                    //save
                    TABLE_DEVICE tableDeviceNew = new TABLE_DEVICE();
                    tableDeviceNew.setDEVICE_NAME(etDeviceName.getText().toString());
                    tableDeviceNew.setDEVICE_CODE(tvDeviceCode.getText().toString());
                    tableDeviceNew.setDEVICE_ADDRESS(etAddDevice.getText().toString());
                    tableDeviceNew.setDEVICE_DATE(Common.getDateTimeNow(Common.DATE_TIME_TYPE.sqlite2));
                    tableDeviceNew.setDEVICE_STATUS(Common.STATUS.EDIT.content);
                    tableDeviceNew.setMODE(Common.MODE.ADMIN.content);


                    tableDeviceNew.setID_TABLE_DEVICE((int) database.updateORInsertRows(TABLE_DEVICE.class, tableDeviceOld, tableDeviceNew));

                    if (tableDeviceNew.getID_TABLE_DEVICE() != 0) {
                        tvDeviceCode.setText(tableDeviceNew.getDEVICE_CODE());

                        etDeviceName.setHint(tableDeviceNew.getDEVICE_NAME());
                        etDeviceName.setText(tableDeviceNew.getDEVICE_NAME());

                        etAddDevice.setHint(tableDeviceNew.getDEVICE_ADDRESS());
                        etAddDevice.setText(tableDeviceNew.getDEVICE_ADDRESS());

                        mListener.showMessage("Thêm mới thiết bị thành công!", null, null);
                    } else {
                        throw new Exception("Gặp lỗi khi thêm mới thiết bị\nNội dung lỗi : Lưu dữ liệu không thành công!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListener.showMessage(e.getMessage(), null, null);
                }
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }
    //endregion


//    @BindView(R.id.tv_ma_thietbi)
//    Button btnAdd;

    public DeviceFragment() {
        // Required empty public constructor
    }

    public static DeviceFragment newInstance(
//            String param1, String param2
            Common.MODE mode) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = (Common.MODE) getArguments().getSerializable(BUNDLE_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_device, container, false);
            unbinder = ButterKnife.bind(DeviceFragment.this, rootView);
            initDataAndView(rootView);
            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            mListener.showMessage(e.getMessage(), null, null);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnDeviceFragment) {
            mListener = (IOnDeviceFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IOnDeviceFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //region IBaseView
    @Override
    public void initDataAndView(View rootView) throws Exception {
        database = DAO.getInstance(SqlHelper.getIntance().openDB(), getContext());

        rvListDevice.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListDevice.setHasFixedSize(true);
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {

        clickBtnSearch(btnSearchDevice);
    }
    //endregion

    public void showHideView(int idButton) {
        rlAddDevice.setVisibility(View.GONE);
        rlSearchDevice.setVisibility(View.GONE);
        rlUpload.setVisibility(View.GONE);

        switch (idButton) {
            case R.id.btn_add:
                rlAddDevice.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_search:
                rlSearchDevice.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_upload:
                rlUpload.setVisibility(View.VISIBLE);
                break;
        }
    }


    public interface IOnDeviceFragment {

        void showMessage(String message, @Nullable String content, @Nullable final ISnackbarIteractions actionOK);
    }

    public interface IOnDismisDialog {

        void onDismiss();
    }

}
