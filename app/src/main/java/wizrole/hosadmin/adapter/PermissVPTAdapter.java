package wizrole.hosadmin.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.List;

import wizrole.hosadmin.authority.model.getrolelist.Datas;

/**
 * Created by liushengping on 2018/1/27.
 * 何人执笔？
 */

public class PermissVPTAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragments;
    public FragmentManager fm;
    public List<Datas> strings;
    public PermissVPTAdapter( List<Datas> strings,List<Fragment> fragments,FragmentManager fm) {
        super(fm);
        this.fragments=fragments;
        this.fm=fm;
        this.strings=strings;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("parent_name",strings.get(position).getPermissionDec());
        bundle.putSerializable("type",(Serializable) strings.get(position).getChildPermissionList());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position).getPermissionDec();
    }
}
