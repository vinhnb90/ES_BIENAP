package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by VINH-PC on 2/9/2018.
 */

public class ReportAnswerText extends AbModuleReport {
    private LinearLayout llParent;
    private EditText etText;
    private View rowView;

    public ReportAnswerText(Context context,final LinearLayout llParent) {
        super(context);
        this.llParent = llParent;
        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer_text, null);
        etText = (EditText) rowView.findViewById(R.id.et_text);

        this.addView(rowView);

        this.llParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.llParent.setOrientation(LinearLayout.VERTICAL);
        this.llParent.addView(this);
        this.llParent.invalidate();
    }

    public EditText getEtText() {
        return etText;
    }
}

