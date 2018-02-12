package vn.com.esolutions.es_bienap;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;
import vn.com.esolutions.es_bienap.DeviceFragment.IOnDeviceFragment;
import vn.com.esolutions.es_bienap.HomeFragment.OnIHomeFragment;
import vn.com.esolutions.es_bienap.ReportFragment.OnIReportFragment;

import static vn.com.esolutions.es_bienap.Common.BUNDLE_MODE;

public class MainActivity extends BaseActivity implements
        OnIReportFragment,
        OnIHomeFragment,
        IOnDeviceFragment {
//    @BindView(R.id.cc_main)
//    CoordinatorLayout coordinatorLayoutMain;

    /*View binding*/
    private Unbinder unbind;

    @BindView(R.id.cl_main)
    CoordinatorLayout clMain;

    @BindView(R.id.nav_bottom_menu)
    BottomBar bottomBar;

    @BindView(R.id.rl_fragment)
    RelativeLayout rlReplaceFragment;


    private Common.MODE mode;
    private DAO database;

    /*Fragment*/
    private ReportFragment mReportFragment;
    private HomeFragment mHomeFragment;
    private FragmentManager mFragmentManager;
    private DeviceFragment mDeviceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            unbind = ButterKnife.bind(this);
            super.setupFullScreen();
            initDataAndView(this.getWindow().getDecorView());
            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
        try {
            SqlHelper.getIntance().closeDB();
        } catch (Exception e) {
            e.printStackTrace();
            showSnackBar(e.getMessage(), null, null);
        }
    }

    //region BaseActivity
    @Override
    public void initDataAndView(View rootView) throws Exception {
        super.setCoordinatorLayout(clMain);

        getBundle();

        setActionBarTittle(bottomBar.getCurrentTab().getTitle());

        database = DAO.getInstance(SqlHelper.getIntance().openDB(), this);
    }

    private void getBundle() {
        if (getIntent().hasExtra(BUNDLE_MODE)) {
            mode = (Common.MODE) getIntent().getSerializableExtra(BUNDLE_MODE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_camera:
                return (true);

            case R.id.menu_date:
                return (true);

        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTittle(String message) {
        SpannableString s = new SpannableString(message);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = mFragmentManager.beginTransaction();

                switch (tabId) {
                    case R.id.bbar_home_menu:
                        mHomeFragment = HomeFragment.newInstance(mode);
                        ft.replace(rlReplaceFragment.getId(), mHomeFragment).commit();

                        bottomBar.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.bbar_manage_device_menu:
                        mDeviceFragment = DeviceFragment.newInstance(mode);
                        ft.replace(rlReplaceFragment.getId(), mDeviceFragment).commit();

                        bottomBar.setVisibility(View.VISIBLE);
                        break;

                    case R.id.bbar_edit_note_menu:
                        mReportFragment = ReportFragment.newInstance(mode);
                        ft.replace(rlReplaceFragment.getId(), mReportFragment).commit();

                        bottomBar.setVisibility(View.VISIBLE);
                        break;

                    case R.id.bbar_history_upload_menu:

                        bottomBar.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    //endregion

    //region OnIHomeFragment
    @Override
    public void clickUpdateBtn(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        MainActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomBar.selectTabWithId(R.id.bbar_history_upload_menu);
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }

    @Override
    public void clickManagerDeviceBtn(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        MainActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomBar.selectTabWithId(R.id.bbar_manage_device_menu);
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }

    @Override
    public void clickManagerReportBtn(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, Common.TIME_DELAY_CLICK_SHORT);

        MainActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomBar.selectTabWithId(R.id.bbar_edit_note_menu);
            }
        }, Common.TIME_DELAY_CLICK_SHORT);
    }
    //endregion


    //region IOnDeviceFragment
    @Override
    public void showMessage(String message, @Nullable String content, @Nullable ISnackbarIteractions actionOK) {
        super.showSnackBar(message, content, actionOK);
    }
    //endregion
}
