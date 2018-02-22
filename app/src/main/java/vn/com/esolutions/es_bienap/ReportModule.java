package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ReportModule extends AbModuleReport {
    private final LinearLayout llParent;
    private LinearLayout llReportModule;
    private Button btnEditModule;
    private EditText etNameModule;
    private View rowView;

    List<LinearLayout> viewList = new ArrayList<>();

    public ReportModule(Context context, final LinearLayout llParent) {
        super(context);
        this.llParent = llParent;

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module, null);

        //init view
        btnEditModule = (Button) rowView.findViewById(R.id.btn_edit_module);
        etNameModule = (EditText) rowView.findViewById(R.id.et_module_name);
        llReportModule = (LinearLayout) rowView.findViewById(R.id.ll_module);


//        //add view and validate
//        for (LinearLayout ll : viewList) {
//            llReportModule.addView(ll);
//        }

        this.addView(rowView);
//        LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
//        FrameLayout.LayoutParams ladderParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
//        this.llParent.setOrientation(LinearLayout.VERTICAL);
//        this.llParent.setLayoutParams(LLParams);

        this.llParent.addView(this);
        this.llParent.invalidate();
    }

    public LinearLayout getLLInclude(){
        return (LinearLayout)this.findViewById(R.id.ll_module);
    }

    public ReportModule addViewInclude(final LinearLayout view) {
        this.viewList.add(view);
        this.llReportModule.addView(view);

        this.llReportModule.invalidate();

        this.llReportModule.postInvalidate();
        super.invalidate();
        return this;
    }
}