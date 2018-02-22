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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import esolutions.com.esdatabaselib.baseSqlite.LazyList;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;
import vn.com.esolutions.es_bienap.database.TABLE_REPORT;

import static vn.com.esolutions.es_bienap.Common.BUNDLE_MODE;
import static vn.com.esolutions.es_bienap.Common.TIME_DELAY_CLICK_LONG;
import static vn.com.esolutions.es_bienap.Common.TIME_DELAY_CLICK_SHORT;

public class ReportFragment extends Fragment implements IBaseView {

    private OnIReportFragment mListener;
    private Unbinder unbinder;
    ReportAdapter reportAdapter;


    /*View add*/
    @BindView(R.id.rl_add_report)
    RelativeLayout rlAddReport;

    @BindView(R.id.btn_add_report)
    Button btnAddDevice;

    @BindView(R.id.rl_tool)
    RelativeLayout rlTool;

    @BindView(R.id.ibtn_create_module)
    ImageButton ibtnCreateModule;

    @BindView(R.id.ibtn_create_question)
    ImageButton ibtnCreateQuestion;

    @BindView(R.id.ibtn_create_checkbox)
    ImageButton ibtnCreateCheckbox;

    @BindView(R.id.ibtn_create_text)
    ImageButton ibtnCreateText;

    @BindView(R.id.ibtn_create_image)
    ImageButton ibtnCreateImage;

    @BindView(R.id.ibtn_create_unit)
    ImageButton ibtnCreateUnit;

    @BindView(R.id.btn_save_report)
    Button btnSaveReport;


    @BindView(R.id.tv_report_name)
    EditText etReportname;

    @BindView(R.id.ll_report)
    LinearLayout llReportModule;

    @BindView(R.id.ll_module_include)
    LinearLayout llReport;


//    @BindView(R.id.et_nhap_ten_thietbi)
//    EditText etDeviceName;
//
//    @BindView(R.id.et_nhap_diachi_thietbi)
//    EditText etAddDevice;
//
//    @BindView(R.id.btn_save_thietbi)
//    Button btnSaveDevice;


    /*View search*/
    @BindView(R.id.rv_search_report)
    RelativeLayout rlSearchReport;


    @BindView(R.id.btn_search_report)
    Button btnSearchReport;

    @BindView(R.id.btn_search_report_server)
    Button btnSearchDeviceServer;

    @BindView(R.id.et_search_report)
    EditText etSearch;

    @BindView(R.id.ibtn_clear_search_report)
    ImageButton ibtnClearSearch;

    @BindView(R.id.rv_list_report)
    RecyclerView rvListReport;


    /*View upload*/
    @BindView(R.id.rl_upload_report)
    RelativeLayout rlUploadReport;

    @BindView(R.id.btn_upload_report)
    Button btnUpload;

    @BindView(R.id.tv_total_report)
    TextView tvTotalDevice;

    @BindView(R.id.tv_count_report_choose)
    TextView tvTotalDeviceChoose;

    @BindView(R.id.pbar_upload_report)
    ProgressBar pbarUpload;

    @BindView(R.id.tv_count_percent_report)
    TextView tvCountPercent;

    @BindView(R.id.btn_upload_report_all)
    Button btnUploadDevice;


    private DAO database;
    private Common.MODE mMode;
    private ReportAdapter.IOnReportAdapter mIteractor;
    private String hasError;
    private List<ReportModule> reportViewList = new ArrayList<>();

    /*đang xử lý các tại module nào*/
    private int moduleIndex = -1;
    /*đang xử lý các câu hỏi tại câu hỏi nào*/
    private int questionIndex = -1 ;

    public static ReportFragment newInstance(
//            String param1, String param2
            Common.MODE mode) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    //region click button menu top
    @OnClick(R.id.btn_upload_report)
    public void clickBtnUpload(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        if (rvListReport.getAdapter() == null) {
            mListener.showMessage("Cần chọn trước khi gửi", null, new ISnackbarIteractions() {
                @Override
                public void doIfPressOK() {
                    clickBtnSearch(btnSearchReport);
                }
            });
            return;
        }

        showHideView(R.id.btn_upload_report);

        tvTotalDevice.setText(((ReportAdapter) rvListReport.getAdapter()).getListReport().size() + "");
        tvTotalDeviceChoose.setText(((ReportAdapter) rvListReport.getAdapter()).getListDeviceChoose().size() + "");

        //init view
        tvCountPercent.setText("0%");
        pbarUpload.setProgress(0);

    }

    @OnClick(R.id.btn_search_report)
    public void clickBtnSearch(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        showHideView(R.id.btn_search_report);

        ((Activity) this.getContext()).getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<ReportAdapter.DataReport> dataReports = database.getReportAdapter(mMode);

                //setup interactor
                if (mIteractor == null)
                    mIteractor = new ReportAdapter.IOnReportAdapter() {
                        @Override
                        public void clickUpload(final int adapterPosition) {
                            //fake ok set up
                            showDialog("Đang tiến hành phát hành biên bản!", new DeviceFragment.IOnLoaddingDialog() {
                                        @Override
                                        public void onDismiss() {
                                            try {
                                                mListener.showMessage("Phát hành biên bản thành công!", null, null);

                                                //update
                                                //get data if has
                                                String REPORT_NAME = ((ReportAdapter) rvListReport.getAdapter()).getListReport().get(adapterPosition).getREPORT_NAME();
                                                List<TABLE_REPORT> tableReports = database.getTABLE_REPORT(new String[]{
                                                        REPORT_NAME,
                                                        mMode.content
                                                });

                                                TABLE_REPORT tableReportOld = new TABLE_REPORT();
                                                if (tableReports.size() != 0) {
                                                    tableReportOld = tableReports.get(0);
                                                    ((LazyList<TABLE_REPORT>) tableReports).closeCursor();
                                                }

                                                //save
                                                TABLE_REPORT tableReportNew = (TABLE_REPORT) tableReportOld.clone();
                                                tableReportNew.setREPORT_STATUS(Common.STATUS.PUBLISH.content);
                                                tableReportNew.setID_TABLE_REPORT((int) database.updateORInsertRows(TABLE_REPORT.class, tableReportOld, tableReportNew));

                                                if (tableReportNew.getID_TABLE_REPORT() != 0) {
                                                    //refresh data
                                                    ((ReportAdapter) rvListReport.getAdapter()).getListReport().get(adapterPosition).setREPORT_STATUS(Common.STATUS.PUBLISH.content);
                                                    ((ReportAdapter) rvListReport.getAdapter()).notifyDataSetChanged();
                                                    rvListReport.invalidate();

                                                    mListener.showMessage("Phát hành biên bản thành công!", null, null);
                                                } else {
                                                    throw new Exception("Gặp lỗi khi thêm mới biên bản\nNội dung lỗi : Phát hành thất bại, kiểm tra kết nối!");
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                mListener.showMessage("Gặp vấn đề khi phát hành biên bản\nNội dung lỗi: " + e.getMessage(), null, null);
                                            }
                                        }
                                    }
                            );
//                            ((ReportAdapter) rvListReport.getAdapter())
                        }

                        @Override
                        public void clickEdit(int adapterPosition) {
                            try {
                                //clear text
                                /*tvDeviceCode.setText("");
                                etDeviceName.setHint("");
                                etDeviceName.setText("");
                                etAddDevice.setHint("");
                                etAddDevice.setText("");


                                ReportAdapter.DataDevice dataDevice = ((ReportAdapter) rvListReport.getAdapter()).getListReport().get(adapterPosition);

                                //set text
                                tvDeviceCode.setText(dataDevice.getDEVICE_CODE());
                                etDeviceName.setHint(dataDevice.getDEVICE_NAME());
                                etDeviceName.setText(dataDevice.getDEVICE_NAME());
                                etAddDevice.setHint(dataDevice.getDEVICE_ADDRESS());
                                etAddDevice.setText(dataDevice.getDEVICE_ADDRESS());


                                showHideView(R.id.btn_add);*/

                            } catch (Exception e) {
                                e.printStackTrace();
                                mListener.showMessage(e.getMessage(), null, null);
                            }
                        }
                    };

                if (rvListReport.getAdapter() == null) {
                    ReportAdapter adapter = new ReportAdapter(getContext(), dataReports, mMode, mIteractor);
                    rvListReport.setAdapter(adapter);
                    rvListReport.invalidate();
                } else {
                    ((ReportAdapter) rvListReport.getAdapter()).refresh(dataReports, mMode);
                    rvListReport.invalidate();
                }

            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }

    private void showDialog(String message, final DeviceFragment.IOnLoaddingDialog iteractorDialog) {
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

    private void showDialogInputText(String message, final DeviceFragment.IOnInputFieldDialog iteractorDialog) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_input_text);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        final EditText etInput = ((EditText) dialog.findViewById(R.id.et_name_report));
        ((TextView) dialog.findViewById(R.id.tv_message_input_text)).setText(message);
        ((Button) dialog.findViewById(R.id.btn_create_report)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etInput.getText().toString())) {
                    Toast.makeText(dialog.getContext(), "Không để trống trường nhập!", Toast.LENGTH_SHORT).show();
                    etInput.setError("Không để trống trường nhập!");
                    etInput.requestFocus();
                    return;
                }

                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                iteractorDialog.onDismiss(etInput.getText().toString());
            }
        });
        dialog.show();

//        dialog.getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//            }
//        }, TIME_DELAY_CLICK_LONG);

    }

    @OnClick(R.id.btn_add_report)
    public void clickBtnAdd(View view) {
        //show dialog input name
        showDialogInputText("Nhập tên phiếu", new DeviceFragment.IOnInputFieldDialog() {
            @Override
            public void onDismiss(String result) {
                if (TextUtils.isEmpty(result)) {
                    clickBtnSearch(btnSearchReport);
                    return;
                }

                showHideView(R.id.btn_add);

                showEnableToolsButton(CLICK_TOOLS.CLICK_ADD);

                createReport(result);
            }
        });


        //clear text
        /*tvDeviceCode.setText("");

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
                                clickBtnSearch(btnSearchReport);
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
        }, Common.TIME_DELAY_CLICK_SHORT);*/
    }

    private void createReport(String result) {
        // fill data
        etReportname.setText(result);
        etReportname.setHint(result);

        //setup view
        llReportModule.addView(new ReportView(getContext()));
        llReportModule.invalidate();
    }

    //endregion


    //region click button region add
    @OnClick(R.id.ibtn_create_module)
    public void clickIBtnCreateModule(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_MODULE);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
//                llReportModule.addView(new ReportModule(getContext(),llReportModule));
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                new ReportModule(getContext(), reportView.getLLInclude());
                moduleIndex = reportView.getLLInclude().getChildCount() - 1;
                questionIndex = 0;
            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    @OnClick(R.id.ibtn_create_question)
    public void clickIBtnCreateQuestion(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_QUESTION);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                if (reportView.getLLInclude().getChildCount() <= 0) {
                    mListener.showMessage("Chưa khởi tạo module!", null, new ISnackbarIteractions() {
                        @Override
                        public void doIfPressOK() {
                        }
                    });
                    return;
                }

                ReportModule reportModule = (ReportModule) reportView.getLLInclude().getChildAt(reportView.getChildCount() - 1);
                new ReportAnswer(getContext(), reportModule.getLLInclude(), reportView.getLLInclude().getChildCount() - 1);

                llReportModule.invalidate();
                moduleIndex = llReportModule.getChildCount() - 1;
                questionIndex = reportView.getChildCount() - 1;

            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    @OnClick(R.id.ibtn_create_checkbox)
    public void clickIBtnCreateCheckBox(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_QUESTION);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                ReportModule reportModule = (ReportModule) reportView.getLLInclude().getChildAt(moduleIndex);
                ReportAnswer reportAnswer = (ReportAnswer) reportModule.getLLInclude().getChildAt(questionIndex);
                new ReportAnswerChoose(getContext(), reportAnswer.getLLInclude());
                llReportModule.invalidate();

            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    @OnClick(R.id.ibtn_create_image)
    public void clickIBtnCreateImage(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_QUESTION);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                ReportModule reportModule = (ReportModule) reportView.getLLInclude().getChildAt(moduleIndex);
                ReportAnswer reportAnswer = (ReportAnswer) reportModule.getLLInclude().getChildAt(questionIndex);
                new ReportAnswerImage(getContext(), reportAnswer.getLLInclude());
                llReportModule.invalidate();
            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    @OnClick(R.id.ibtn_create_text)
    public void clickIBtnCreateText(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_QUESTION);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                ReportModule reportModule = (ReportModule) reportView.getLLInclude().getChildAt(moduleIndex);
                ReportAnswer reportAnswer = (ReportAnswer) reportModule.getLLInclude().getChildAt(questionIndex);
                new ReportAnswerText(getContext(), reportAnswer.getLLInclude());
                llReportModule.invalidate();
            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    @OnClick(R.id.ibtn_create_unit)
    public void clickIBtnCreateUnit(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_CLICK_SHORT);
        showEnableToolsButton(CLICK_TOOLS.CLICK_QUESTION);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReportView reportView = (ReportView) llReportModule.getChildAt(llReportModule.getChildCount() - 1);
                ReportModule reportModule = (ReportModule) reportView.getLLInclude().getChildAt(moduleIndex);
                ReportAnswer reportAnswer = (ReportAnswer) reportModule.getLLInclude().getChildAt(questionIndex);
                new ReportAnswerUnit(getContext(), reportAnswer.getLLInclude());
                llReportModule.invalidate();
            }
        }, TIME_DELAY_CLICK_SHORT);
    }

    //endregion

    public void showHideView(int idButton) {
        rlAddReport.setVisibility(View.GONE);
        rlSearchReport.setVisibility(View.GONE);
        rlUploadReport.setVisibility(View.GONE);
        rlTool.setVisibility(View.GONE);
        switch (idButton) {
            case R.id.btn_add:
                rlTool.setVisibility(View.VISIBLE);
                rlAddReport.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_search:
                rlSearchReport.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_upload_report:
                rlUploadReport.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void showEnableToolsButton(CLICK_TOOLS clickAdd) {
        ibtnCreateModule.setEnabled(false);
        ibtnCreateQuestion.setEnabled(false);
        ibtnCreateCheckbox.setEnabled(false);
        ibtnCreateText.setEnabled(false);
        ibtnCreateImage.setEnabled(false);
        ibtnCreateUnit.setEnabled(false);
        btnSaveReport.setEnabled(false);
        switch (clickAdd) {
            case CLICK_ADD:
                ibtnCreateModule.setEnabled(true);
                btnSaveReport.setEnabled(true);
                break;
            case CLICK_MODULE:
                ibtnCreateQuestion.setEnabled(true);
                btnSaveReport.setEnabled(true);
                break;
            case CLICK_QUESTION:
                ibtnCreateModule.setEnabled(true);
                ibtnCreateQuestion.setEnabled(true);
                ibtnCreateCheckbox.setEnabled(true);
                ibtnCreateText.setEnabled(true);
                ibtnCreateImage.setEnabled(true);
                ibtnCreateUnit.setEnabled(true);
                btnSaveReport.setEnabled(true);
                break;
            case CLICK_SAVE:
                ibtnCreateModule.setEnabled(true);
                btnSaveReport.setEnabled(true);
                break;
        }
    }


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_report, container, false);
            unbinder = ButterKnife.bind(ReportFragment.this, rootView);
            initDataAndView(rootView);
            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIReportFragment) {
            mListener = (OnIReportFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIReportFragment");
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

    @Override
    public void initDataAndView(View rootView) throws Exception {
        database = DAO.getInstance(SqlHelper.getIntance().openDB(), getContext());

        rvListReport.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListReport.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(
                getContext(), R.anim.twinking_view
        );
        animation.setDuration(250);
        rvListReport.setAnimation(animation);
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnIReportFragment {
        void showMessage(String message, @Nullable String content, @Nullable final ISnackbarIteractions actionOK);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        moduleIndex = -1;
        questionIndex = -1;
    }

    public interface IOnDismisDialog {

        void onDismiss();
    }

    public enum CLICK_TOOLS {
        CLICK_ADD,
        CLICK_MODULE,
        CLICK_QUESTION,
        CLICK_SAVE;
    }
}
