package com.example.projetfinale.GUI.cookmainpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CookMainPageViewPagerAdapter extends FragmentStateAdapter {

    public CookMainPageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new com.example.projetfinale.GUI.cookmainpage.fragments.MealsTabFragment();
            case 1:
                return new com.example.projetfinale.GUI.cookmainpage.fragments.CookOrdersTabFragment();
            default:
                return new com.example.projetfinale.GUI.cookmainpage.fragments.MealsTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
