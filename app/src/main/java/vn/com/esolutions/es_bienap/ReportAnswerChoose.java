package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReportAnswerChoose extends AbModuleReport {
    private LinearLayout llParent;
    private EditText etChoose;
    private View rowView;

    public ReportAnswerChoose(Context context,final LinearLayout llParent) {
        super(context);
        this.llParent = llParent;
        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_choose, null);
        etChoose = (EditText) rowView.findViewById(R.id.et_choose);

        this.addView(rowView);

        this.llParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.llParent.setOrientation(LinearLayout.VERTICAL);
        this.llParent.addView(this);
        this.llParent.invalidate();
    }

}

