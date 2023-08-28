package com.aikinggroup.diabetecontrole.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aikinggroup.diabetecontrole.fragment.RegistrationTabFragment;
import com.aikinggroup.diabetecontrole.fragment.LoginTabFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    private LoginTabFragment patientLoginTabFragment;
    private RegistrationTabFragment consultantTabFragment;
    int totalTabs;
    public LoginAdapter(FragmentManager fragmentManager, Context context, int totalTabs){
        super(fragmentManager);
        this.context =context;
        this.totalTabs =totalTabs;
        patientLoginTabFragment = new LoginTabFragment();
        consultantTabFragment = new RegistrationTabFragment();


    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return patientLoginTabFragment;
            case 1:
                return consultantTabFragment;
            default:
                return null;

        }

    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Patient";
            case 1:
                return "S'incrire";
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
