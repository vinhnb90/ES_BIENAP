package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReportAnswerUnit extends AbModuleReport {
    private EditText etUnit;
    private View rowView;

    public ReportAnswerUnit(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_unit, null);
        etUnit = (EditText) rowView.findViewById(R.id.et_unit);

        this.addView(rowView);
    }
}