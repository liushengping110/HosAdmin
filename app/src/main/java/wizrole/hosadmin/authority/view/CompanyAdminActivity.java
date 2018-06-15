package wizrole.hosadmin.authority.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.CompanyListAdapter;
import wizrole.hosadmin.authority.model.getcompay.CompayBack;
import wizrole.hosadmin.authority.model.getcompay.CompayList;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayInterface;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayPreserent;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.CirclView;

/**
 * Created by 何人执笔？ on 2018/4/25.
 * liushengping
 * 公司管理
 */

public class CompanyAdminActivity extends BaseActivity implements GetCompayInterface, View.OnClickListener,SwipeItemClickListener {
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.img_search)LinearLayout img_search;
    @BindView(R.id.lin_wifi_err)LinearLayout lin_wifi_err;
    @BindView(R.id.lin_bar)LinearLayout lin_bar;
    @BindView(R.id.list_company)SwipeMenuRecyclerView list_company;
    @BindView(R.id.circlView)CirclView circlView;
    @BindView(R.id.img_net_err)ImageView img_net_err;
    @BindView(R.id.text_no_data)TextView text_no_data;
    public CompanyListAdapter adapter;
    public GetCompayPreserent getCompayPreserent=new GetCompayPreserent(this);
    @Override
    protected int getLayout() {
        return R.layout.activity_companyadmin;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("公司列表");
        list_company.setLayoutManager(getLayoutManager());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 2000);
    }

    public void getData(){
        getCompayPreserent.getCompay();
    }
    /**
     * 获取RecyclerView的布局管理器。
     */
    private RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        return mLayoutManager;
    }

    public List<CompayList> compayListList;
    @Override
    public void getCompaySucc(CompayBack back) {
        compayListList=back.getCompayLists();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getCompayFail(String msg) {
        if(msg.equals("")){
            handler.sendEmptyMessage(1);
        }else{
            handler.sendEmptyMessage(2);
        }
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    circlView.setVisibility(View.INVISIBLE);
                    lin_bar.setVisibility(View.VISIBLE);
                    adapter=new CompanyListAdapter(compayListList);
                    list_company.setAdapter(adapter);
                    break;
                case 1:
                    circlView.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    circlView.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };


    @Override
    protected void setListener() {
        list_company.setSwipeItemClickListener(this);
        list_company.setSwipeMenuCreator(swipeMenuCreator);
        list_company.setSwipeMenuItemClickListener(mMenuItemClickListener);
        img_search.setOnClickListener(this);
        lin_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                finish();
                break;
            case R.id.img_search:
                Intent intent=new Intent(CompanyAdminActivity.this,UserSearchActivity.class);
                intent.putExtra("type","company");
                startActivity(intent);
                break;
        }
    }
    //item监听
    @Override
    public void onItemClick(View itemView, int position) {
        initLoodOrChangeCompany(position);
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(CompanyAdminActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setText("删除公司")
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

    /*****************************************/
    public PopupWindowPotting popupWindowPotting;
    public TextView text_message;
    public TextView text_cancle;
    public TextView text_sure;
    public void initExitPop(final int adapterPosition){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(CompanyAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.dialog_exits_notice;
                }

                @Override
                protected void initUI() {
                    text_sure=$(R.id.text_sure);
                    text_cancle=$(R.id.text_cancle);
                    text_message=$(R.id.text_message);
                    text_message.setText("是否删除【"+compayListList.get(adapterPosition).getCompayName()+"】？");
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
                            compayListList.remove(adapterPosition);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            };
        }
        popupWindowPotting.Show(lin_back);
    }

    /*************************查看  修改公司信息********************************/
    public PopupWindowPotting lookOrChangeCompanyPop;
    public EditText edit_c_name;
    public EditText edit_c_add;
    public TextView text_lc_cancle;
    public TextView text_lc_sure;
    public void initLoodOrChangeCompany(int position){
        if(lookOrChangeCompanyPop==null){
            lookOrChangeCompanyPop=new PopupWindowPotting(CompanyAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_company_look_change;
                }

                @Override
                protected void initUI() {
                    edit_c_add=$(R.id.edit_c_add);
                    edit_c_name=$(R.id.edit_c_name);
                    text_lc_sure=$(R.id.text_sure);
                    text_lc_cancle=$(R.id.text_cancle);
                }

                @Override
                protected void setListener() {
                    text_lc_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lookOrChangeCompanyPop.Hide();
                        }
                    });
                    text_lc_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lookOrChangeCompanyPop.Hide();
                        }
                    });
                }
            };
        }
        if(position>=0){//item点击
            edit_c_name.setText(compayListList.get(position).getCompayName());
            edit_c_add.setText(compayListList.get(position).getCompayAddress());
        }else {//添加公司
            edit_c_name.setHint("请输入公司名称");
            edit_c_add.setHint("请输入公司地址");
        }
        lookOrChangeCompanyPop.Show(lin_wifi_err);
    }
}
