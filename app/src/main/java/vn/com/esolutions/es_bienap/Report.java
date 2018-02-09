package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class Report extends AbModuleReport {
    private LinearLayout llReport;
    private View rowView;

    List<LinearLayout> viewList;

    public Report(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report, null);
        llReport = (LinearLayout) rowView.findViewById(R.id.ll_report);

        //add view and validate
        for (LinearLayout ll : viewList) {
            llReport.addView(ll);
        }

        this.addView(rowView);
        super.invalidateView(rowView);
    }

    public Report setViewList(List<LinearLayout> viewList) {
        this.viewList = viewList;
        this.addView(rowView);
        super.invalidateView(rowView);
        return this;
    }
}