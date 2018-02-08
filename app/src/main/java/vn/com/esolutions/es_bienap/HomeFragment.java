package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment implements IBaseView{

    private String mParam1;
    private String mParam2;

    private OnIHomeFragment mListener;
    private Unbinder unbinder;

    /*View*/


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Common.MODE mode) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            unbinder = ButterKnife.bind(HomeFragment.this, rootView);
            initDataAndView(rootView);
            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIHomeFragment) {
            mListener = (OnIHomeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIHomeFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initDataAndView(View rootView) throws Exception {

    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {

    }

    public interface OnIHomeFragment {
        void clickUpdateBtn(View view);

        void clickManagerDeviceBtn(View view);

        void clickManagerReportBtn(View view);
    }

    @OnClick(R.id.ibtn_manager_device)
    public void clickManagerDeviceBtn(View view)
    {
        mListener.clickManagerDeviceBtn(view);
    }

    @OnClick(R.id.ibtn_manager_report)
    public void clickManagerReportBtn(View view)
    {
        mListener.clickManagerReportBtn(view);
    }

    @OnClick(R.id.ibtn_update)
    public void clickUpdateBtn(View view)
    {
        mListener.clickUpdateBtn(view);
    }

}
