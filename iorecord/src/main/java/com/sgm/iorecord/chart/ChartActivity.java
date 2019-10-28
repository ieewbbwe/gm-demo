package com.sgm.iorecord.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sgm.iorecord.R;
import com.sgm.iorecord.base.BaseActivity;
import com.sgm.iorecord.chart.fragment.BarChartFragment;
import com.sgm.iorecord.base.BaseChartFragment;
import com.sgm.iorecord.chart.fragment.PieChartFragment;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 * 图形化界面
 */
public class ChartActivity extends BaseActivity implements ChartContract.View, SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "ChartActivity";

    private ChartPresenter mPresenter;
    private SwipeRefreshLayout mChartSrl;
    private RadioGroup mChartRg;
    private FragmentManager mFragmentManager;
    private String mCurrentFragmentTag;
    private RadioButton mPieRb;
    private RadioButton mBarRb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Visual Chart");
        setContentView(R.layout.activity_chart);
        //mPresenter.queryIOTopAllAsync();
        mPresenter = new ChartPresenter(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChartSrl = findViewById(R.id.m_chart_srl);
        mChartRg = findViewById(R.id.m_check_rg);
        mPieRb = findViewById(R.id.m_pie_check_rb);
        mBarRb = findViewById(R.id.m_bar_check_rb);

        mChartSrl.setOnRefreshListener(this);
        mChartRg.setOnCheckedChangeListener(this);
        mPieRb.setChecked(true);
    }

    @Override
    public void showBarChart(List<IOTopBean> ioTopBeans) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        Fragment fragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
        if (fragment != null && fragment instanceof BaseChartFragment) {
            ((BaseChartFragment) fragment).refresh();
        } else {
            mChartSrl.setRefreshing(false);
        }
    }

    @Override
    public void showLoading() {
        //super.showLoading();
        if (mChartSrl != null && !mChartSrl.isRefreshing()) {
            mChartSrl.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        //super.hideLoading();
        if (mChartSrl != null && mChartSrl.isRefreshing()) {
            mChartSrl.setRefreshing(false);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        String tag = null;
        Fragment fragment = null;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCurrentFragmentTag != null) {
            Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        switch (checkId) {
            case R.id.m_pie_check_rb:
                tag = PieChartFragment.class.getSimpleName();
                Fragment pieFragment = mFragmentManager.findFragmentByTag(tag);
                if (pieFragment != null) {
                    fragment = pieFragment;
                } else {
                    fragment = new PieChartFragment();
                }
                break;
            case R.id.m_bar_check_rb:
                tag = BarChartFragment.class.getSimpleName();
                Fragment barFragment = mFragmentManager.findFragmentByTag(tag);
                if (barFragment != null) {
                    fragment = barFragment;
                } else {
                    fragment = new BarChartFragment();
                }
                break;
        }
        if (fragment != null && fragment.isAdded()) {
            transaction.show(fragment);
        } else if (fragment != null) {
            transaction.add(R.id.m_chart_fl, fragment, tag);
        }
        transaction.commit();
        mCurrentFragmentTag = tag;
        Log.d(TAG, "CurrentFragment:" + mCurrentFragmentTag);
    }
}
