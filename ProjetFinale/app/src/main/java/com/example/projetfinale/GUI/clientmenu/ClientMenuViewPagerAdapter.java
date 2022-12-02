package com.example.projetfinale.GUI.clientmenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetfinale.GUI.clientmenu.fragments.OrdersTabFragment;
import com.example.projetfinale.GUI.clientmenu.fragments.SearchTabFragment;

public class ClientMenuViewPagerAdapter extends FragmentStateAdapter {

    public ClientMenuViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new SearchTabFragment();
            case 1:
                return new OrdersTabFragment();
            default:
                return new SearchTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
