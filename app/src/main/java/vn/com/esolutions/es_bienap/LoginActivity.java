package vn.com.esolutions.es_bienap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;
import esolutions.com.esdatabaselib.example.activity.DatabaseActivity;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.ClassRoom;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.ESDbConfig;
import esolutions.com.esdatabaselib.example.source.sqliteConfig.Student;
import vn.com.esolutions.es_bienap.database.DATABASE_CONFIG;
import vn.com.esolutions.es_bienap.database.TABLE_ANSWER_REPORT;
import vn.com.esolutions.es_bienap.database.TABLE_DEVICE;
import vn.com.esolutions.es_bienap.database.TABLE_ELEMENT_REPORT;
import vn.com.esolutions.es_bienap.database.TABLE_QUESTION_REPORT;
import vn.com.esolutions.es_bienap.database.TABLE_REPORT;

public class LoginActivity extends BaseActivity {
    private Unbinder unbinder;

    @BindView(R.id.btn_login_admin)
    Button btnLoginAdmin;

    @BindView(R.id.btn_login_emp)
    Button btnLoginEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            initDataAndView(this.getWindow().getDecorView());

            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            super.showSnackBar("Gặp vấn đề", e.getMessage(), null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //region BaseActivity
    @Override
    public void initDataAndView(View rootView) throws Exception {
        super.setupFullScreen();
        super.setCoordinatorLayout((CoordinatorLayout) findViewById(R.id.cc_login));
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
        //create database
        SqlHelper.setupDB(this,
                DATABASE_CONFIG.class,
                new Class[]{TABLE_ANSWER_REPORT.class,
                        TABLE_DEVICE.class,
                        TABLE_ELEMENT_REPORT.class,
                        TABLE_QUESTION_REPORT.class,
                        TABLE_REPORT.class
                });
    }
    //endregion

    @OnClick(R.id.btn_login_admin)
    public void clickLoginAdmin(View view) {
        startActionMode(Common.MODE.ADMIN);

        //start anim
        Animation animation = AnimationUtils.loadAnimation(
                this, R.anim.twinking_view
        );
        animation.setDuration(250);
        btnLoginAdmin.startAnimation(animation);
    }

    @OnClick(R.id.btn_login_emp)
    public void clickLoginEmp(View view) {
        startActionMode(Common.MODE.EMP);

        //start anim
        Animation animation = AnimationUtils.loadAnimation(
                this, R.anim.twinking_view
        );
        animation.setDuration(250);
        btnLoginEmp.startAnimation(animation);
    }


    private void startActionMode(Common.MODE mode) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Common.BUNDLE_MODE, mode);
        intent.putExtras(bundle);
        startActivity(intent);

//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }
}
