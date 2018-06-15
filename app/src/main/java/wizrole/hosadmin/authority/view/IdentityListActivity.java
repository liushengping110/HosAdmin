package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.IdentityListAdapter;
import wizrole.hosadmin.authority.model.getidentity.IdentityBack;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityInterface;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityPreserent;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.image.ImageLoading;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.CirclView;

/**
 * Created by 何人执笔？ on 2018/4/18.
 * liushengping
 * 角色列表
 */

public class IdentityListActivity extends BaseActivity implements View.OnClickListener,GetIdentityInterface
,SwipeItemClickListener {
    @BindView(R.id.list_role)SwipeMenuRecyclerView list_role;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.lin_wifi_err)LinearLayout lin_wifi_err;
    @BindView(R.id.text_no_data)TextView text_no_data;
    @BindView(R.id.img_net_err)ImageView img_net_err;
    @BindView(R.id.circlView)CirclView circlView;
    private RecyclerView.LayoutManager mLayoutManager;
    public IdentityListAdapter adapter;
    public GetIdentityPreserent getIdentityPreserent=new GetIdentityPreserent(this);
    @Override
    protected int getLayout() {
        return R.layout.activity_rolelist;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("角色列表");
        text_right.setText("添加");
        list_role.setLayoutManager(getLayoutManager());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRoleList();//获取角色列表
            }
        }, 2000);
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

    public void getRoleList(){
        getIdentityPreserent.setGetIdentity();
    }
    public List<IdentityList> identityLists;

    @Override
    public void getIdentitySucc(IdentityBack back) {
        identityLists=back.getIdentityLists();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getIdentityFail(String msg) {
        if(msg.equals("")){
            handler.sendEmptyMessage(1);
        }else {
            handler.sendEmptyMessage(2);
        }
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://成功
                    list_role.setVisibility(View.VISIBLE);
                    lin_wifi_err.setVisibility(View.INVISIBLE);
                    adapter=new IdentityListAdapter(identityLists);
                    list_role.setAdapter(adapter);
                    circlView.setVisibility(View.INVISIBLE);
                    break;
                case 1://失败
                    circlView.setVisibility(View.INVISIBLE);
                    lin_wifi_err.setVisibility(View.VISIBLE);
                    list_role.setVisibility(View.INVISIBLE);
                    text_no_data.setText(MyApplication.getContextObject().getString(R.string.try_again));
                    text_no_data.setTextColor(MyApplication.getContextObject().getResources().getColor(R.color.white));
                    text_no_data.setBackgroundResource(R.drawable.login_sel);
                    ImageLoading.common(MyApplication.getContextObject(),R.drawable.net_err,img_net_err,R.drawable.net_err);
                    break;
                case 2://无数据
                    circlView.setVisibility(View.INVISIBLE);
                    lin_wifi_err.setVisibility(View.VISIBLE);
                    list_role.setVisibility(View.INVISIBLE);
                    text_no_data.setText(MyApplication.getContextObject().getString(R.string.null_data));
                    text_no_data.setTextColor(MyApplication.getContextObject().getResources().getColor(R.color.huise));
                    text_no_data.setBackgroundResource(R.color.white);
                    ImageLoading.common(MyApplication.getContextObject(),R.drawable.null_data,img_net_err,R.drawable.null_data);
                    break;
            }
        }
    };

    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_right.setOnClickListener(this);
        list_role.setSwipeItemClickListener(this);
        list_role.setSwipeMenuCreator(swipeMenuCreator);
        list_role.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Bundle bundle=new Bundle();
        IdentityList identityList=identityLists.get(position);
        bundle.putSerializable("identity",identityList);
        Intent intent=new Intent(IdentityListActivity.this,AddOrChangeIdenActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,1000);
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
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(IdentityListActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setText("删除角色")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
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
                switch (menuPosition){
                    case 0:
                        initExitPop(adapterPosition);
                        break;
                }
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                finish();
                break;
            case R.id.text_right://新建
                Intent intent=new Intent(IdentityListActivity.this,AddOrChangeIdenActivity.class);
                startActivityForResult(intent,1000);
                break;
        }
    }

    /*****************************************/
    public PopupWindowPotting popupWindowPotting;
    public TextView text_message;
    public TextView text_cancle;
    public TextView text_sure;
    public void initExitPop(final int adapterPosition){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(IdentityListActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.dialog_exits_notice;
                }

                @Override
                protected void initUI() {
                    text_sure=$(R.id.text_sure);
                    text_cancle=$(R.id.text_cancle);
                    text_message=$(R.id.text_message);
                    text_message.setText("是否删除【"+identityLists.get(adapterPosition).getIdentityName()+"】角色？");
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
                            identityLists.remove(adapterPosition);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            };
        }
        popupWindowPotting.Show(lin_back);
    }
}
