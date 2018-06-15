package wizrole.hosadmin.maintenance.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.FragmentAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.maintenance.view.fragment.Fragment_Components;
import wizrole.hosadmin.maintenance.view.fragment.Fragment_Device;

/**
 * Created by liushengping on 2018/3/13.
 * 何人执笔？
 * 设备监控
 */

public class MonitorActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.text_bar_device)TextView text_bar_device;
    @BindView(R.id.text_bar_bujian)TextView text_bar_bujian;
    @BindView(R.id.view_pager)ViewPager view_pager;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    public FragmentAdapter adapter;
    public int nowPage=0;//当前页面下标
    @Override
    protected int getLayout() {
        return R.layout.activity_monitor;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setViewPager();
    }

    public List<Fragment> fragments=new ArrayList<>();
    public void setViewPager(){
        fragments.add(new Fragment_Device());
        fragments.add(new Fragment_Components());
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(2);
    }

    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_bar_device.setOnClickListener(this);
        text_bar_bujian.setOnClickListener(this);
        view_pager.addOnPageChangeListener(onPageChangeListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                finish();
                break;
            case R.id.text_bar_device://设备
                if(nowPage==1){
                    view_pager.setCurrentItem(0);
                }
                break;
            case R.id.text_bar_bujian://部件
                if(nowPage==0){
                    view_pager.setCurrentItem(1);
                }
                break;

        }
    }


    /**
     * ViewPager的监听
     */
    public ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeStatus(position);
            nowPage=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    /**
     * 改变titleBar的状态
     * @param num--当前选择的页面下标
     */
    public void changeStatus(int num){
        if(num==0){
            text_bar_device.setTextColor(getResources().getColor(R.color.bule));
            text_bar_bujian.setTextColor(getResources().getColor(R.color.white));
            text_bar_device.setBackgroundResource(R.drawable.mon_actionbar_left_sel);
            text_bar_bujian.setBackgroundResource(R.drawable.mon_actionbar_right_nosel);
        }else{
            text_bar_device.setTextColor(getResources().getColor(R.color.white));
            text_bar_bujian.setTextColor(getResources().getColor(R.color.bule));
            text_bar_device.setBackgroundResource(R.drawable.mon_actionbar_left_nosel);
            text_bar_bujian.setBackgroundResource(R.drawable.mon_actionbar_right_sel);
        }
    }
}
