package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.GridItemDecoration;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.SpinnerCompanyAdapter;
import wizrole.hosadmin.adapter.SpinnerDeparentAdapter;
import wizrole.hosadmin.adapter.SpinnerIdentityAdapter;
import wizrole.hosadmin.adapter.UserCompayAdapter;
import wizrole.hosadmin.adapter.UserDeparentAdapter;
import wizrole.hosadmin.adapter.UserIdentityAdapter;
import wizrole.hosadmin.adapter.UserList_Adapter;
import wizrole.hosadmin.authority.model.getcompay.CompayBack;
import wizrole.hosadmin.authority.model.getcompay.CompayList;
import wizrole.hosadmin.authority.model.getdeparent.DeparentBack;
import wizrole.hosadmin.authority.model.getdeparent.DeparentList;
import wizrole.hosadmin.authority.model.getidentity.IdentityBack;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;
import wizrole.hosadmin.authority.model.getuser.GetUserBack;
import wizrole.hosadmin.authority.model.getuser.UserList;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayInterface;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayPreserent;
import wizrole.hosadmin.authority.preserent.getdeparent.GetDeparentInterface;
import wizrole.hosadmin.authority.preserent.getdeparent.GetDeparentPreserent;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityInterface;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityPreserent;
import wizrole.hosadmin.authority.preserent.getuser.GetUserInterface;
import wizrole.hosadmin.authority.preserent.getuser.GetUserPreserent;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.maintenance.model.devicelist.DeviceList;
import wizrole.hosadmin.maintenance.view.AddDeviceActivity;
import wizrole.hosadmin.maintenance.view.DeviceListActivity;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.image.ImageLoading;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.PopupMaxHeightListView;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public class UserAdminActivity extends BaseActivity implements View.OnClickListener,
        GetUserInterface ,GetCompayInterface,GetIdentityInterface,GetDeparentInterface,SwipeItemClickListener {
    @BindView(R.id.list_view)SwipeMenuRecyclerView list_view;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.rel_title)RelativeLayout rel_title;
    @BindView(R.id.lin_wifi_err)LinearLayout lin_wifi_err;
    @BindView(R.id.img_net_err)ImageView img_net_err;
    @BindView(R.id.img_search)ImageView img_search;
    @BindView(R.id.text_no_data)TextView text_no_data;

    @BindView(R.id.text_d_company)TextView text_d_company;
    @BindView(R.id.text_d_identity)TextView text_d_identity;
    @BindView(R.id.text_d_deparent)TextView text_d_deparent;

    @BindView(R.id.img_d_company)ImageView img_d_company;
    @BindView(R.id.img_d_identity)ImageView img_d_identity;
    @BindView(R.id.img_d_deparent)ImageView img_d_deparent;

    @BindView(R.id.rel_d_company)RelativeLayout rel_d_company;
    @BindView(R.id.rel_d_identity)RelativeLayout rel_d_identity;
    @BindView(R.id.rel_d_deparent)RelativeLayout rel_d_deparent;

    @BindView(R.id.view_over)View view_over;
    public Dialog dialog;
    public GetUserPreserent getUserPreserent=new GetUserPreserent(this);//获取用户列表
    public GetCompayPreserent getCompayPreserent=new GetCompayPreserent(this);//获取公司pop列表
    public GetIdentityPreserent getIdentityPreserent=new GetIdentityPreserent(this);//获取身份pop列表
    public GetDeparentPreserent getDeparentPreserent=new GetDeparentPreserent(this);//获取科室列表pop

    private RecyclerView.LayoutManager mLayoutManager;
    public UserList_Adapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setView();
        dialog= LoadingDailog.createLoadingDialog(UserAdminActivity.this,"加载中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getCompayList();//获取公司pop列表
                getIdentityList();//获取身份列表
                getDeparentList();//获取科室列表
                getUserList();//获取用户列表
                initCompayPop();
                initIdentityPop();
                initDeparentPop();
            }
        }, 2000);
    }
    public void setView(){
        text_right.setText("添加");
        text_title.setText("用户列表");
        list_view.setLayoutManager(getLayoutManager());
    }
    /**
     * 获取RecyclerView的布局管理器。
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        return mLayoutManager;
    }
    /**
     * 获取RecyclerView的Item分割线。
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new ListItemDecoration(ContextCompat.getColor(this, R.color.huise));
    }

    //获取用户列表
    public void getUserList(){
        getUserPreserent.getUser();
    }
    //获取身份列表
    public void getIdentityList(){
        getIdentityPreserent.setGetIdentity();
    }
    //获取科室列表
    public void getDeparentList(){
        getDeparentPreserent.getDeparent();
    }
    //获取公司pop列表
    public void getCompayList(){
        getCompayPreserent.getCompay();
    }


    /**********************获取用户列表*************************/
    public List<UserList> userLists;
    @Override
    public void getUserSucc(GetUserBack getUserBack) {
        userLists=getUserBack.getUserList();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getUserFail(String msg) {
        handler.sendEmptyMessage(1);
    }
    /*******************获取公司名称*************************/
    public List<CompayList> compayLists;
    @Override
    public void getCompaySucc(CompayBack back) {
        compayLists=back.getCompayLists();
        handler.sendEmptyMessage(3);
    }

    @Override
    public void getCompayFail(String msg) {
        handler.sendEmptyMessage(4);
    }
    /*******************获取身份列表************************/
    public List<IdentityList> identityLists;
    @Override
    public void getIdentitySucc(IdentityBack back) {
        identityLists=back.getIdentityLists();
        handler.sendEmptyMessage(5);
    }

    @Override
    public void getIdentityFail(String msg) {
        handler.sendEmptyMessage(6);
    }
    /*******************获取科室列表***********************/
    public List<DeparentList> deparentLists;
    @Override
    public void getDeparentSucc(DeparentBack deparentBack) {
        deparentLists=deparentBack.getDeparentLists();
        handler.sendEmptyMessage(7);
    }

    @Override
    public void getDeparentFail(String msg) {
        handler.sendEmptyMessage(8);
    }

    public int status=0;//多种请求成功标记
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://用户列表获取成功
                    lin_wifi_err.setVisibility(View.INVISIBLE);
                    adapter=new UserList_Adapter(MyApplication.getContextObject(),userLists);
                    list_view.setAdapter(adapter);
                    list_view.setVisibility(View.VISIBLE);
                    status++;
                    dialogDiss();
                    break;
                case 1://用户列表获取失败
                    status=-1;
                    dialogDiss();
                    break;
                case 2://用户列表无数据
                    status=-2;
                    dialogDiss();
                    break;
                case 3://公司名称列表成功
                    initCompayPop();
                    UserCompayAdapter compayAdapter=new UserCompayAdapter(MyApplication.getContextObject(),compayLists,R.layout.pop_list_type_item);
                    list_compay.setAdapter(compayAdapter);
                    status++;
                    dialogDiss();
                    break;
                case 4://公司名称列表获取失败
                    status=-1;
                    dialogDiss();
                    break;
                case 5://身份（角色）列表获取成功
                    initIdentityPop();
                    UserIdentityAdapter identityAdapter=new UserIdentityAdapter(MyApplication.getContextObject(),identityLists,R.layout.pop_list_montype_item);
                    list_identity.setAdapter(identityAdapter);
                    status++;
                    dialogDiss();
                    break;
                case 6://身份（角色）列表获取失败
                    status=-1;
                    dialogDiss();
                    break;
                case 7://科室列表获取成功
                    initDeparentPop();
                    UserDeparentAdapter deparentAdapter=new UserDeparentAdapter(MyApplication.getContextObject(),deparentLists,R.layout.pop_list_user_deparent_item);
                    list_deparent.setAdapter(deparentAdapter);
                    status++;
                    dialogDiss();
                    break;
                case 8://科室列表获取失败
                    status=-1;
                    dialogDiss();
                    break;
            }
        }
    };

    public void dialogDiss(){
        if(status==4){//都成功了
            LoadingDailog.closeDialog(dialog);
        }else if(status==-1){//网络请求失败
            LoadingDailog.closeDialog(dialog);
            list_view.setVisibility(View.INVISIBLE);
            lin_wifi_err.setVisibility(View.VISIBLE);
            text_no_data.setText(MyApplication.getContextObject().getString(R.string.net_err));
            text_no_data.setTextColor(MyApplication.getContextObject().getResources().getColor(R.color.white));
            text_no_data.setBackgroundResource(R.drawable.login_sel);
            ImageLoading.common(MyApplication.getContextObject(),R.drawable.net_err,img_net_err,R.drawable.net_err);
        }else if(status==-2){//用户列表无数据
            LoadingDailog.closeDialog(dialog);
            list_view.setVisibility(View.INVISIBLE);
            lin_wifi_err.setVisibility(View.VISIBLE);
            text_no_data.setText(MyApplication.getContextObject().getString(R.string.null_data));
            text_no_data.setTextColor(MyApplication.getContextObject().getResources().getColor(R.color.huise));
            text_no_data.setBackgroundResource(R.color.white);
            ImageLoading.common(MyApplication.getContextObject(),R.drawable.null_data,img_net_err,R.drawable.null_data);
        }else if(0<=status&&status<4){//有请求成功，有失败
            LoadingDailog.closeDialog(dialog);
            list_view.setVisibility(View.INVISIBLE);
            lin_wifi_err.setVisibility(View.VISIBLE);
            text_no_data.setText(MyApplication.getContextObject().getString(R.string.net_err));
            text_no_data.setTextColor(MyApplication.getContextObject().getResources().getColor(R.color.white));
            text_no_data.setBackgroundResource(R.drawable.login_sel);
            ImageLoading.common(MyApplication.getContextObject(),R.drawable.net_err,img_net_err,R.drawable.net_err);
        }
    }
    @Override
    public void onItemClick(View itemView, int position) {
        onDiss(position);
    }
    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_right.setOnClickListener(this);
        rel_d_company.setOnClickListener(this);
        rel_d_identity.setOnClickListener(this);
        rel_d_deparent.setOnClickListener(this);
        rel_title.setOnClickListener(this);
        img_search.setOnClickListener(this);
        list_view.setSwipeItemClickListener(this);
        list_view.setSwipeMenuCreator(swipeMenuCreator);
        list_view.setSwipeMenuItemClickListener(mMenuItemClickListener);
        view_over.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(open_pop_status==1||open_pop_status==2||open_pop_status==3){
                    onDiss(-1);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.qishi);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            {
//                SwipeMenuItem addItem = new SwipeMenuItem(ListActivity.this)
//                        .setBackground(R.drawable.selector_green)
//                        .setImage(R.drawable.ic_action_add)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。
//
//                SwipeMenuItem closeItem = new SwipeMenuItem(ListActivity.this)
//                        .setBackground(R.drawable.selector_red)
//                        .setImage(R.drawable.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
//            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(UserAdminActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setText("删除用户")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(UserAdminActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("重置密码")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(UserAdminActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition){
                    case 0://删除
                        initExitPop(true,adapterPosition);
                        break;
                    case 1://重置密码
                        initExitPop(false,adapterPosition);
                        break;
                }

            }
        }
    };


    public void onDiss(int psi) {
        if(open_pop_status==1){
            compayPopwindow.Hide();
            rotateEnd(img_d_company);
            view_over.setVisibility(View.INVISIBLE);
        }else if(open_pop_status==2){
            identityPopwindow.Hide();
            rotateEnd(img_d_identity);
            view_over.setVisibility(View.INVISIBLE);
        }else if(open_pop_status==3){
            deparentPopwindow.Hide();
            rotateEnd(img_d_deparent);
            view_over.setVisibility(View.INVISIBLE);
        }else {
            if(psi>=0){//item点击监听
                UserList userList=userLists.get(psi);
                Bundle bundle=new Bundle();
                bundle.putSerializable("userinfor",userList);
                Intent intent=new Intent(UserAdminActivity.this,AddOrChangeUserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else if(psi==-2){//返回
                finish();
            }else if(psi==-3){//搜索
                Intent intent=new Intent(UserAdminActivity.this,UserSearchActivity.class);
                intent.putExtra("type","user");
                startActivity(intent);
            }else if(psi==-4){//添加
                Intent intent=new Intent(UserAdminActivity.this,AddOrChangeUserActivity.class);
                startActivity(intent);
            }
        }
        open_pop_status=4;//清空，此时都未打开
    }

    public int open_pop_status=4;//pop展开状态 1  公司  2  身份   3  科室  4 关闭
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                onDiss(-2);
                break;
            case R.id.text_right://添加
                onDiss(-4);
                break;
            case R.id.rel_title:
                onDiss(-1);
                break;
            case R.id.rel_d_company://公司
                initCompayPop();
                changePop(1);
                break;
            case R.id.rel_d_identity://身份（角色）
                initIdentityPop();
                changePop(2);
                break;
            case R.id.rel_d_deparent://科室
                initDeparentPop();
                changePop(3);
                break;
            case R.id.img_search://搜索
                onDiss(-3);
                break;
        }
    }

    public void changePop(int type){
        switch (type){
            case 1://公司
                switch (open_pop_status){
                    case 1:
                        compayPopwindow.Hide();
                        rotateEnd(img_d_company);
                        open_pop_status=4;
                        view_over.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        identityPopwindow.Hide();
                        rotateEnd(img_d_identity);
                        compayPopwindow.Show(rel_d_company);
                        rotateStart(img_d_company);
                        open_pop_status=1;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        deparentPopwindow.Hide();
                        rotateEnd(img_d_deparent);
                        compayPopwindow.Show(rel_d_company);
                        rotateStart(img_d_company);
                        open_pop_status=1;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    default:
                        compayPopwindow.Show(rel_d_company);
                        rotateStart(img_d_company);
                        open_pop_status=1;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case 2://身份（角色）
                switch (open_pop_status){
                    case 1:
                        compayPopwindow.Hide();
                        rotateEnd(img_d_company);
                        identityPopwindow.Show(rel_d_identity);
                        rotateStart(img_d_identity);
                        open_pop_status=2;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        identityPopwindow.Hide();
                        rotateEnd(img_d_identity);
                        open_pop_status=4;
                        view_over.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        deparentPopwindow.Hide();
                        rotateEnd(img_d_deparent);
                        identityPopwindow.Show(rel_d_identity);
                        rotateStart(img_d_identity);
                        open_pop_status=2;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    default:
                        identityPopwindow.Show(rel_d_identity);
                        rotateStart(img_d_identity);
                        open_pop_status=2;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case 3://科室
                switch (open_pop_status){
                    case 1:
                        compayPopwindow.Hide();
                        rotateEnd(img_d_company);
                        deparentPopwindow.Show(rel_d_deparent);
                        rotateStart(img_d_deparent);
                        open_pop_status=3;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        identityPopwindow.Hide();
                        rotateEnd(img_d_identity);
                        deparentPopwindow.Show(rel_d_deparent);
                        rotateStart(img_d_deparent);
                        open_pop_status=3;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        deparentPopwindow.Hide();
                        rotateEnd(img_d_deparent);
                        open_pop_status=4;
                        view_over.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        deparentPopwindow.Show(rel_d_deparent);
                        rotateStart(img_d_deparent);
                        open_pop_status=3;
                        view_over.setVisibility(View.VISIBLE);
                        break;
                }
                break;
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

    /*********************科室选择pop*************************/
    public PopupWindowPotting deparentPopwindow;
    public PopupMaxHeightListView list_deparent;
    public void initDeparentPop(){
        if(deparentPopwindow==null){
            deparentPopwindow=new PopupWindowPotting(UserAdminActivity.this,1) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_typegroup;
                }

                @Override
                protected void initUI() {
                    list_deparent=$(R.id.pop_devices_list);
                    list_deparent.setListViewHeight((int)700);
                }

                @Override
                protected void setListener() {
                    list_deparent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }
            };
        }
    }
    /*********************身份选择pop*************************/
    public PopupWindowPotting identityPopwindow;
    public PopupMaxHeightListView list_identity;
    public void initIdentityPop(){
        if(identityPopwindow==null){
            identityPopwindow=new PopupWindowPotting(UserAdminActivity.this,1) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_typegroup;
                }

                @Override
                protected void initUI() {
                    list_identity=$(R.id.pop_devices_list);
                    list_identity.setListViewHeight((int)700);
                }

                @Override
                protected void setListener() {
                    list_identity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }
            };
        }
    }
    /*********************公司选择pop*************************/
    public PopupWindowPotting compayPopwindow;
    public PopupMaxHeightListView list_compay;
    public void initCompayPop(){
        if(compayPopwindow==null){
            compayPopwindow=new PopupWindowPotting(UserAdminActivity.this,1) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_devicelist_typegroup;
                }

                @Override
                protected void initUI() {
                    list_compay=$(R.id.pop_devices_list);
                    list_compay.setListViewHeight((int)(700));
                }

                @Override
                protected void setListener() {
                    list_compay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }
            };
        }
    }

    /*****************************************/
    public PopupWindowPotting popupWindowPotting;
    public TextView text_message;
    public TextView text_cancle;
    public TextView text_sure;
    public void initExitPop(final boolean status,final int adapterPosition){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(UserAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.dialog_exits_notice;
                }

                @Override
                protected void initUI() {
                    text_sure=$(R.id.text_sure);
                    text_cancle=$(R.id.text_cancle);
                    text_message=$(R.id.text_message);
                }

                @Override
                protected void setListener() {
                    text_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowPotting.Hide();
                        }
                    });
                    text_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowPotting.Hide();
                            if(status){//删除
                                userLists.remove(adapterPosition);
                                adapter.notifyDataSetChanged();
                            }else{//重置密码

                            }
                        }
                    });
                }
            };
        }
        if(status){//删除
            text_message.setText("是否删除【"+userLists.get(adapterPosition).getUserName()+"】用户？");
        }else{//重置密码
            text_message.setText("是否重置【"+userLists.get(adapterPosition).getUserName()+"】密码？");
        }
        popupWindowPotting.Show(lin_back);
    }
}
