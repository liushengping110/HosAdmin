package wizrole.hosadmin.ui;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.view.CompanyAdminActivity;
import wizrole.hosadmin.authority.view.IdentityListActivity;
import wizrole.hosadmin.authority.view.PermissAdminActivity;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.dailyrecord.view.RefillMoneyActivity;
import wizrole.hosadmin.login.view.LoginActivity;
import wizrole.hosadmin.maintenance.view.DeviceListActivity;
import wizrole.hosadmin.maintenance.view.GroupListActivity;
import wizrole.hosadmin.maintenance.view.MonitorActivity;
import wizrole.hosadmin.maintenance.view.PropertActivity;
import wizrole.hosadmin.authority.view.UserAdminActivity;
import wizrole.hosadmin.util.SharePreferences;
import wizrole.hosadmin.util.image.ImageLoading;
import wizrole.hosadmin.view.MainSlidingMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener,MainSlidingMenu.MenuClose{

    @BindView(R.id.rel_title)RelativeLayout rel_title;
    @BindView(R.id.view)View view;
    @BindView(R.id.img_main_logo)ImageView img_main_logo;
    @BindView(R.id.img_menu_bg)ImageView img_menu_bg;
    @BindView(R.id.img_menu_logo)ImageView img_menu_logo;
    @BindView(R.id.text_menu_name)TextView text_menu_name;
    @BindView(R.id.text_set)TextView text_set;
    @BindView(R.id.text_version)TextView text_version;
    @BindView(R.id.text_zhuxiao)TextView text_zhuxiao;
    @BindView(R.id.main_slidingmenu)MainSlidingMenu main_slidingmenu;
    @BindView(R.id.lin_yhgl)LinearLayout lin_yhgl;
    @BindView(R.id.lin_jsgl)LinearLayout lin_jsgl;
    @BindView(R.id.lin_qzgl)LinearLayout lin_qzgl;
    @BindView(R.id.lin_gsgl)LinearLayout lin_gsgl;
    @BindView(R.id.lin_jkgl)LinearLayout lin_jkgl;
    @BindView(R.id.lin_xxwh)LinearLayout lin_xxwh;
    @BindView(R.id.lin_qzwh)LinearLayout lin_qzwh;
    @BindView(R.id.lin_sxwh)LinearLayout lin_sxwh;
    @BindView(R.id.lin_czrz)LinearLayout lin_czrz;
    @BindView(R.id.lin_jkrz)LinearLayout lin_jkrz;
    @BindView(R.id.lin_ghrz)LinearLayout lin_ghrz;
    @BindView(R.id.lin_jfrz)LinearLayout lin_jfrz;
    public Intent intent;
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setView();
    }
    public void setView(){
        main_slidingmenu.setMenuClose(this);
        if(SharePreferences.getLoginState(MainActivity.this)==1){//已登录

        }else{//未登录
            ImageLoading.common(MainActivity.this,R.drawable.admin_logo,img_main_logo,R.drawable.admin_logo);
            ImageLoading.common(MainActivity.this,R.drawable.admin_logo,img_menu_logo,R.drawable.admin_logo);
            img_menu_bg.setBackgroundColor(getResources().getColor(R.color.bule));
        }
    }
    @Override
    protected void setListener() {
        img_main_logo.setOnClickListener(this);
        lin_yhgl.setOnClickListener(this);
        lin_jsgl.setOnClickListener(this);
        lin_qzgl.setOnClickListener(this);
        lin_gsgl.setOnClickListener(this);
        lin_xxwh.setOnClickListener(this);
        lin_jkgl.setOnClickListener(this);
        lin_qzwh.setOnClickListener(this);
        lin_sxwh.setOnClickListener(this);
        lin_czrz.setOnClickListener(this);
        lin_jkrz.setOnClickListener(this);
        lin_ghrz.setOnClickListener(this);
        lin_jfrz.setOnClickListener(this);

        text_menu_name.setOnClickListener(this);
        img_menu_logo.setOnClickListener(this);
        text_set.setOnClickListener(this);
        text_version.setOnClickListener(this);
        text_zhuxiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_main_logo:
                if(!main_slidingmenu.mMenuIsOpen){
                    main_slidingmenu.openMenu();
                }
                break;
            case R.id.lin_yhgl://用户管理
                intent=new Intent(MainActivity.this,UserAdminActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_jsgl://角色管理
                intent=new Intent(MainActivity.this, IdentityListActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_qzgl://权限管理
                intent=new Intent(MainActivity.this, PermissAdminActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_gsgl://公司管理
                intent=new Intent(MainActivity.this, CompanyAdminActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_xxwh://信息维护
                intent=new Intent(MainActivity.this,DeviceListActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_qzwh://群组维护
                intent=new Intent(MainActivity.this,GroupListActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_sxwh://属性维护
                intent=new Intent(MainActivity.this,PropertActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_jkgl://监控管理
                intent=new Intent(MainActivity.this,MonitorActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_czrz://充值日志
                intent=new Intent(MainActivity.this,RefillMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_jkrz://建卡
                break;
            case R.id.lin_ghrz://顾浩
                break;
            case R.id.lin_jfrz://缴费
                break;
            case R.id.text_menu_name://登录
                intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,1000);
                break;
            case R.id.text_set://设置中心
                break;
            case R.id.text_version://版本更新
                break;
            case R.id.text_zhuxiao://注销
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000://登录回调
                if(resultCode==2000){
                    text_menu_name.setText("管理员");
                    main_slidingmenu.openMenu();
                }else{text_menu_name.setText("登录");
                    main_slidingmenu.openMenu();
                }
                break;
        }
    }

    /**
     * 打开、关闭时改变titleBar的背景
     * @param status
     */
    @Override
    public void colse(boolean status) {
        if(status){//打开
            view.setBackgroundColor(getResources().getColor(R.color.white));
            rel_title.setBackgroundColor(getResources().getColor(R.color.white));
        }else{//关闭
            view.setBackgroundColor(getResources().getColor(R.color.bule));
            rel_title.setBackgroundColor(getResources().getColor(R.color.bule));
        }
    }

    // 定义是否退出程序的标记
    private boolean isExit = false;
    // 定义接受用户发送信息的handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                // 标记用户不退出状态
                isExit = false;
            }
        }
    };

    // 监听手机的物理按键点击事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断用户是否点击的是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(main_slidingmenu.mMenuIsOpen){
                main_slidingmenu.closeMenu();
            }else{
                if (!isExit) {// 如果isExit标记为false，提示用户再次按键
                    isExit = true;
                    ToastShow("再按一次，退出程序");
                    // 如果用户没有在2秒内再次按返回键的话，就发送消息标记用户为不退出状态
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                } else {// 如果isExit标记为true，退出程序
                    // 退出程序
                    finish();
                    System.exit(0);
                }
            }
        }
        return false;
    }
}
