package com.example.ronaldhowl.cloudnote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RonaldHowl on 2018/2/1.
 */

public class MainAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainAdapter(FragmentManager fm, List fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
