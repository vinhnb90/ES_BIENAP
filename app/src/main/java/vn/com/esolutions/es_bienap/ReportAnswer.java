package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportAnswer extends AbModuleReport {
    private LinearLayout llReportAnswer;
    private TextView tvTitleQuestion;
    private TextView tvTitleQuestionAnswer;
    private EditText etChoose;
    private EditText etQuestion;
    private View rowView;

    List<LinearLayout> viewList = new ArrayList<>();

    public ReportAnswer(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer, null);
        tvTitleQuestion = (TextView) rowView.findViewById(R.id.tv_title_question);
        tvTitleQuestionAnswer = (TextView) rowView.findViewById(R.id.tv_title_question_answer);
        etQuestion = (EditText) rowView.findViewById(R.id.et_question_name);


        llReportAnswer = (LinearLayout) rowView.findViewById(R.id.ll_module_answer_include);

        //add view and validate
        for (LinearLayout ll : viewList) {
            llReportAnswer.addView(ll);
        }

        this.addView(rowView);
    }

    public ReportAnswer addView(LinearLayout view) {
        this.viewList.add(view);
        this.llReportAnswer.addView(view);
        this.rowView.invalidate();
        return this;
    }
}