package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import vn.com.esolutions.es_bienap.R;

/**
 * Created by VINH-PC on 2/9/2018.
 */

public class ReportAnswerText extends AbModuleReport {
    private EditText etText;
    private View rowView;

    public ReportAnswerText(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_text, null);
        etText = (EditText) rowView.findViewById(R.id.et_text);

        this.addView(rowView);
    }
}
