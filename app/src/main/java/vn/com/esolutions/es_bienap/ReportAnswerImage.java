package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReportAnswerImage extends AbModuleReport {
    private EditText etNameCapture;
    private View rowView;
    private LinearLayout llParent;

    public ReportAnswerImage(Context context, LinearLayout llParent) {
        super(context);

        this.llParent = llParent;
        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_image, null);
        etNameCapture = (EditText) rowView.findViewById(R.id.et_name_image);

        this.addView(rowView);
        this.llParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.llParent.setOrientation(LinearLayout.VERTICAL);
        this.llParent.addView(this);
        this.llParent.invalidate();
    }

    public EditText getEtNameCapture() {
        return etNameCapture;
    }
}