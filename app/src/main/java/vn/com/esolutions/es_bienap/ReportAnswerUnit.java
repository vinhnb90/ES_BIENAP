package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


public class ReportAnswerUnit extends AbModuleReport {
    private EditText etUnit;
    private View rowView;
    private LinearLayout llParent;

    public ReportAnswerUnit(Context context, LinearLayout llParent) {
        super(context);

        this.llParent = llParent;

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_unit, null);
        etUnit = (EditText) rowView.findViewById(R.id.et_unit);

        this.addView(rowView);
        this.llParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.llParent.setOrientation(LinearLayout.VERTICAL);
        this.llParent.addView(this);
        this.llParent.invalidate();
    }

    public EditText getEtUnit() {
        return etUnit;
    }
}