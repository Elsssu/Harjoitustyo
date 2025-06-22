package com.elssu.harkkatyo;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.elssu.harkkatyo.fragments.BattleFieldFragment;
import com.elssu.harkkatyo.fragments.HomeFragment;
import com.elssu.harkkatyo.fragments.StatisticsSaveLoad;
import com.elssu.harkkatyo.fragments.TrainingAreaFragment;

public class TabPagerAdapter extends FragmentStateAdapter {
    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TrainingAreaFragment();
            case 2:
                return new BattleFieldFragment();
            case 3:
                return new StatisticsSaveLoad();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
