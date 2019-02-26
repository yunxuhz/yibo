package com.dongyangbike.app.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dongyangbike.app.fragment.BaseFragment;

import java.util.List;

public class MyFragmentpagerAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> fragList;

    public MyFragmentpagerAdapter(FragmentManager fm, List<BaseFragment> fragList) {
        super(fm);
        this.fragList = fragList;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
