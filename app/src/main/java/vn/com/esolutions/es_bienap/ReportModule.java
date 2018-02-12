package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ReportModule extends AbModuleReport {
    private LinearLayout llReportModule;
    private Button btnEditModule;
    private EditText etNameModule;
    private View rowView;

    List<LinearLayout> viewList = new ArrayList<>();

    public ReportModule(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module, null);
        btnEditModule = (Button) rowView.findViewById(R.id.btn_edit_module);
        etNameModule = (EditText) rowView.findViewById(R.id.et_module_name);

        llReportModule = (LinearLayout) rowView.findViewById(R.id.ll_module);


        //add view and validate
        for (LinearLayout ll : viewList) {
            llReportModule.addView(ll);
        }

        this.addView(rowView);

    }

    public ReportModule addView(LinearLayout view) {
        this.viewList.add(view);
        this.llReportModule.addView(view);
        this.rowView.invalidate();
        return this;
    }
}