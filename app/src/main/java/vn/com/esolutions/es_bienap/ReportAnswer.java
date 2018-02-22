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
    private int indexAnswer;
    private LinearLayout llParent;
    private LinearLayout llReportAnswer;
    private TextView tvTitleQuestion;
    private TextView tvTitleQuestionAnswer;
    private EditText etChoose;
    private EditText etQuestion;
    private View rowView;

    List<LinearLayout> viewList = new ArrayList<>();

    public ReportAnswer(Context context, final LinearLayout llParent, int indexAnswer) {
        super(context);
        rowView = LayoutInflater.from(context).inflate(R.layout.report_module_answer, null, false);

        this.llParent = llParent;
        this.indexAnswer = indexAnswer;

        //init view
        tvTitleQuestion = (TextView) rowView.findViewById(R.id.tv_title_question);
        tvTitleQuestionAnswer = (TextView) rowView.findViewById(R.id.tv_title_question_answer);
        etQuestion = (EditText) rowView.findViewById(R.id.et_question_name);
        llReportAnswer = (LinearLayout) rowView.findViewById(R.id.ll_module_answer_include);


        tvTitleQuestion.setText("Câu khảo sát thứ " + (this.indexAnswer + 1));
        tvTitleQuestionAnswer.setText("Nội dung cần khảo sát câu " + (this.indexAnswer + 1) + " bao gồm:");
        this.addView(rowView);

        this.llParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.llParent.setOrientation(LinearLayout.VERTICAL);
        this.llParent.addView(this);
        this.llParent.invalidate();
    }

    public ReportAnswer addView(LinearLayout view) {
        this.viewList.add(view);
        this.llReportAnswer.addView(view);
        this.rowView.invalidate();
        return this;
    }

    public LinearLayout getLLInclude() {
        return this.findViewById(R.id.ll_module_answer_include);
    }
}