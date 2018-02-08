package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReportFragment extends Fragment implements IBaseView {

    private OnIReportFragment mListener;
    private Unbinder unbinder;

    /*recyclerview*/
    @BindView(R.id.rv_result_search)
    RecyclerView rvSearch;

    ReportAdapter reportAdapter;


    public ReportFragment(Common.MODE mode) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_search, container, false);
            unbinder = ButterKnife.bind(ReportFragment.this, rootView);
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
        if (context instanceof OnIReportFragment) {
            mListener = (OnIReportFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIReportFragment");
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
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(
                getContext(), R.anim.twinking_view
        );
        animation.setDuration(250);
        rvSearch.setAnimation(animation);
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
        reportAdapter = new ReportAdapter();
        rvSearch.setAdapter(reportAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnIReportFragment {
        // TODO: Update argument type and name
    }
}
