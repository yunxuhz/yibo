package com.dongyangbike.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dongyangbike.app.R;
import com.dongyangbike.app.fragment.BaseFragment;
import com.dongyangbike.app.fragment.LoginPwdFragment;
import com.dongyangbike.app.fragment.LoginSmsFragment;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("快捷登录"));
        tabLayout.addTab(tabLayout.newTab().setText("密码登录"));

        mViewPager = findViewById(R.id.viewPager);

        mTitles = new ArrayList<>();
        mTitles.add("快捷登录");
        mTitles.add("密码登录");

        mFragments = new ArrayList<>();
        mFragments.add(new LoginSmsFragment());
        mFragments.add(new LoginPwdFragment());


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        tabLayout.setupWithViewPager(mViewPager);
    }
}
