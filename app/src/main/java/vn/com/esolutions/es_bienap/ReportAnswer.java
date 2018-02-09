package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class ReportAnswer extends AbModuleReport {
    private LinearLayout llReportAnswer;
    private EditText etTitleQuestion;
    private EditText etTitleQuestionAnswer;
    private EditText etChoose;
    private View rowView;

    List<LinearLayout> viewList;

    public ReportAnswer(Context context) {
        super(context);

        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer, null);
        etChoose = (EditText) rowView.findViewById(R.id.et_choose);
        etTitleQuestion = (EditText) rowView.findViewById(R.id.tv_title_question);
        etTitleQuestionAnswer = (EditText) rowView.findViewById(R.id.tv_title_question_answer);

        llReportAnswer = (LinearLayout) rowView.findViewById(R.id.ll_report_answer);

        //add view and validate
        for (LinearLayout ll : viewList) {
            llReportAnswer.addView(ll);
        }

        this.addView(rowView);
    }

    public ReportAnswer setViewList(List<LinearLayout> viewList) {
        this.viewList = viewList;
        this.addView(rowView);
        super.invalidateView(rowView);
        return this;
    }
}