package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReportAnswerImage extends AbModuleReport {
    private EditText etNameCapture;
    private View rowView;

    public ReportAnswerImage(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_image, null);
        etNameCapture = (EditText) rowView.findViewById(R.id.et_name_image);

        this.addView(rowView);
    }
}