package com.dongyangbike.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.fragment.BaseFragment;
import com.dongyangbike.app.fragment.MainFragment;
import com.dongyangbike.app.fragment.MeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int mLastFragment;
    private MainFragment mMainFragment;
    private MeFragment mMeFragment;
    private BaseFragment[] mFragments;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(mLastFragment!=0) {
                        switchFragment(mLastFragment,0);
                        mLastFragment=0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if(mLastFragment!=1) {
                        switchFragment(mLastFragment,1);
                        mLastFragment=1;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mLastFragment = 0;
        mMainFragment = new MainFragment();
        mMeFragment = new MeFragment();
        mFragments = new BaseFragment[] {mMainFragment, mMeFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,mMainFragment).show(mMainFragment).commit();
    }

    private void switchFragment(int lastfragment,int index) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastfragment]);
        if(mFragments[index].isAdded()==false) {
            transaction.add(R.id.mainview,mFragments[index]);
        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();
    }

}
