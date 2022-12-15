package com.icare.mvvm.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPageUtils {
    public static void setupViewPager(ViewPager viewPager, ArrayList<Fragment> mData,FragmentManager manager ){
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        for (int i = 0; i < mData.size(); i++) {
            adapter.addFrag(mData.get(i));
        }
        viewPager.setAdapter(adapter);
    }
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }


    }
}