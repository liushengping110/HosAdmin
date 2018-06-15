package wizrole.hosadmin.maintenance.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.DeviceListAdapter;
import wizrole.hosadmin.adapter.DeviceTypeListAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.maintenance.model.devicelist.DeviceList;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.PopupMaxHeightListView;

/**
 * Created by liushengping on 2018/3/7.
 * 何人执笔？
 * 设备信息维护页面
 */

public class DeviceListActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.text_d_type)TextView text_d_type;
    @BindView(R.id.img_d_type)ImageView img_d_type;
    @BindView(R.id.text_d_group)TextView text_d_group;
    @BindView(R.id.img_d_group)ImageView img_d_group;
    @BindView(R.id.text_d_sx)TextView text_d_sx;
    @BindView(R.id.img_d_sx)ImageView img_d_sx;
    @BindView(R.id.list_view)ListView listView;
    @BindView(R.id.view_over)View view_over;
    @BindView(R.id.rel_d_type)RelativeLayout rel_d_type;
    @BindView(R.id.rel_d_group)RelativeLayout rel_d_group;
    @BindView(R.id.rel_d_sx)RelativeLayout rel_d_sx;
    @BindView(R.id.inc_title)View inc_title;
    public Dialog dialog;
    @Override
    protected int getLayout() {
        return R.layout.activity_devicelist;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("设备列表");
        text_right.setText("添加设备");
        dialog= LoadingDailog.createLoadingDialog(DeviceListActivity.this,"加载中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentListView();
            }
        }, 2000);
        initTypePop();
        initGroupPop();
        setTypeList();
        setGroupList();
        initSelectPop();
    }

    public List<DeviceList> deviceLists=new ArrayList<>();
    public void setContentListView(){
        for (int i=0;i<30;i++){
            DeviceList deviceList=new DeviceList();
            deviceList.setDeviceType("挂号缴费机"+i);
            deviceList.setDeviceNo("NMF"+1000+i);
            deviceList.setDeviceMac(10000000+i+"");
            deviceList.setDeviceIP(20000000+i+"");
            deviceList.setDeviceGroup("建行自助机"+i);
            if(i%2==0){
                deviceList.setDeviceStatus("1");
            }else{
                deviceList.setDeviceStatus("2");
            }
            deviceLists.add(deviceList);
            deviceList=null;
        }
        DeviceListAdapter adapter=new DeviceListAdapter(DeviceListActivity.this,deviceLists,R.layout.list_devicelist_item);
        listView.setAdapter(adapter);
        LoadingDailog.closeDialog(dialog);
    }

    public List<String> strings=new ArrayList<>();
    public void setTypeList(){
        for (int i=0;i<20;i++){
            if(i%2==0){
                strings.add("挂号缴费机"+i);
            }else{
                strings.add("检验报告打印机"+i);
            }
        }
        DeviceTypeListAdapter typeListAdapter=new DeviceTypeListAdapter(DeviceListActivity.this,strings,R.layout.pop_list_type_item);
        list_type.setAdapter(typeListAdapter);
    }

    public List<String> strings_group=new ArrayList<>();
    public void setGroupList(){
        for (int i=0;i<5;i++){
            strings_group.add("建行自助机"+i);
        }
        DeviceTypeListAdapter groupListAdapter=new DeviceTypeListAdapter(DeviceListActivity.this,strings_group,R.layout.pop_list_montype_item);
        list_group.setAdapter(groupListAdapter);
    }



    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        view_over.setOnClickListener(this);
        rel_d_type.setOnClickListener(this);
        rel_d_group.setOnClickListener(this);
        rel_d_sx.setOnClickListener(this);
        text_right.setOnClickListener(this);
        inc_title.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDiss(position);
            }
        });
        view_over.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(open_close_status==1||open_close_status==2){
                    onDiss(-1);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                finish();
                break;
            case R.id.view_over://列表覆盖层
                onDiss(-1);
                break;
            case R.id.rel_d_type://设备类型
                initTypePop();
                if(open_close_status==1){
                    onDiss(-1);
                    return;
                }else if(open_close_status==2){
                    group_pop.Hide();
                }
                rotateStart(img_d_type);
                type_pop.Show(rel_d_type);
                view_over.setVisibility(View.VISIBLE);
                open_close_status=1;
                break;
            case R.id.rel_d_group://设备群组
                initGroupPop();
                if(open_close_status==2){
                    onDiss(-1);
                    return;
                }else if(open_close_status==1){
                    type_pop.Hide();
                }
                rotateStart(img_d_group);
                group_pop.Show(rel_d_group);
                view_over.setVisibility(View.VISIBLE);
                open_close_status=2;
                break;
            case R.id.rel_d_sx://筛选
                if(open_close_status==1){
                    type_pop.Hide();
                    onDiss(-1);
                }else if(open_close_status==2){
                    group_pop.Hide();
                    onDiss(-1);
                }
                select_pop.Show(rel_d_sx);
                break;
            case R.id.text_right://添加设备
                Intent intent=new Intent(DeviceListActivity.this,AddDeviceActivity.class);
                startActivity(intent);
                break;
            case R.id.inc_title:
                if(open_close_status==1){
                    type_pop.Hide();
                    onDiss(-1);
                }else if(open_close_status==2){
                    group_pop.Hide();
                    onDiss(-1);
                }
                break;
        }
    }
    /**
     * 设备类型
     */
    public PopupWindowPotting type_pop;
    public PopupMaxHeightListView list_type;
    public void initTypePop(){
        if(type_pop==null){
            type_pop=new PopupWindowPotting(DeviceListActivity.this,1) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_typegroup;
                }

                @Override
                protected void initUI() {
                    list_type=$(R.id.pop_devices_list);
                    list_type.setListViewHeight((int)(700));
                }

                @Override
                protected void setListener() {
                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            text_d_type.setTextColor(getResources().getColor(R.color.bule));
                            img_d_type.setBackgroundResource(R.drawable.turn_bottom_sel);
                            text_d_type.setText(strings.get(position));
                            type_pop.Hide();
                            rotateEnd(img_d_type);
                            view_over.setVisibility(View.INVISIBLE);
                            open_close_status=3;//清空，此时都未打开
                        }
                    });
                }
            };
        }
    }

    /**
     * 设备群组
     */
    public PopupWindowPotting group_pop;
    public PopupMaxHeightListView list_group;
    public void initGroupPop(){
        if(group_pop==null){
            group_pop=new PopupWindowPotting(DeviceListActivity.this,1) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_typegroup;
                }

                @Override
                protected void initUI() {
                    list_group=$(R.id.pop_devices_list);
                    list_group.setListViewHeight((int)(700));
                }

                @Override
                protected void setListener() {
                    list_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            text_d_group.setTextColor(getResources().getColor(R.color.bule));
                            img_d_group.setBackgroundResource(R.drawable.turn_bottom_sel);
                            text_d_group.setText(strings_group.get(position));
                            group_pop.Hide();
                            rotateEnd(img_d_group);
                            view_over.setVisibility(View.INVISIBLE);
                            open_close_status=3;//清空，此时都未打开
                        }
                    });
                }
            };
        }
    }


    /**
     * 设备筛选
     */
    public PopupWindowPotting select_pop;
    public EditText edit_d_name,edit_d_ip,edit_d_mac;
    public TextView text_device_sure,text_device_clear;
    public SpannableString ss_one;
    public SpannableString ss_two;
    public SpannableString ss_three;
    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);//设置字体大小 true表示单位是sp
    public void initSelectPop(){
        if(select_pop==null){
            select_pop=new PopupWindowPotting(DeviceListActivity.this,3) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_select;
                }

                @Override
                protected void initUI() {
                    edit_d_name=$(R.id.edit_d_name);
                    edit_d_ip=$(R.id.edit_d_ip);
                    edit_d_mac=$(R.id.edit_d_mac);
                    text_device_clear=$(R.id.text_device_clear);
                    text_device_sure=$(R.id.text_device_sure);

                    for (int i=0;i<3;i++){
                        switch (i){
                            case 0:
                                ss_one = new SpannableString("请输入终端名称");//定义hint的值
                                ss_one.setSpan(ass, 0, ss_one.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                            case 1:
                                ss_two = new SpannableString("请输入终端IP");//定义hint的值
                                ss_two.setSpan(ass, 0, ss_two.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                            case 2:
                                ss_three = new SpannableString("请输入终端MAC");//定义hint的值
                                ss_three.setSpan(ass, 0, ss_three.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                        }
                    }
                    edit_d_name.setHint(new SpannedString(ss_one));
                    edit_d_ip.setHint(new SpannedString(ss_two));
                    edit_d_mac.setHint(new SpannedString(ss_three));
                }

                @Override
                protected void setListener() {
                    text_device_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text_d_sx.setTextColor(getResources().getColor(R.color.bule));
                            img_d_sx.setBackgroundResource(R.drawable.shaixuan_sel);
                            select_pop.Hide();
                            open_close_status=3;//清空，此时都未打开
                        }
                    });
                    text_device_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text_d_sx.setTextColor(getResources().getColor(R.color.text_zw));
                            img_d_sx.setBackgroundResource(R.drawable.shaixuan);
                            select_pop.Hide();
                            open_close_status=3;//清空，此时都未打开
                        }
                    });
                }
            };
        }
    }
    /**
     * 弹窗上的三角形翻转
     */
    public void rotateStart(ImageView imageView) {
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    /**
     * 弹窗上的三角形翻转
     */
    public void rotateEnd(ImageView imageView) {
        RotateAnimation animation = new RotateAnimation(180, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }
    public int open_close_status=3;//类型、群组展开状态--初始状态
    public void onDiss(int psi) {
        if(open_close_status==1){
            rotateEnd(img_d_type);
            view_over.setVisibility(View.INVISIBLE);
        }else if(open_close_status==2){
            rotateEnd(img_d_group);
            view_over.setVisibility(View.INVISIBLE);
        }else {
            if(psi>=0){//item点击监听
                DeviceList deviceList=deviceLists.get(psi);
                Intent intent=new Intent(DeviceListActivity.this,AddDeviceActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("device",deviceList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        open_close_status=3;//清空，此时都未打开
    }
}
