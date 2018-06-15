package wizrole.hosadmin.authority.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.PermissVPTAdapter;
import wizrole.hosadmin.adapter.PermissionListAdapter;
import wizrole.hosadmin.authority.model.getrolelist.ChildPermissionList;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.CirclView;

/**
 * Created by liushengping on 2018/1/27.
 * 何人执笔？
 * 单个类型商铺分类
 * 如：美食下，二级分类--小吃、烤串等等
 */

public class Fragment_PermissType extends LazyFragment implements View.OnClickListener, PermissionListAdapter.GroupOnClick
        ,PermissionListAdapter.GroupLongOnClick,PermissionListAdapter.ChildOnClick,PermissionListAdapter.ChildLongClick {

    public static final String INTENT_INT_INDEX="index";
    private int tabIndex;
    public List<ChildPermissionList> childPermissionList;//二级分类名id
    public String parent_name;//一级分类的外层父类节点，适配器需要
    public static Fragment_PermissType newInstance(int tabIndex, boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, tabIndex);
        args.putBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        Fragment_PermissType fragment = new Fragment_PermissType();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_permisstype);
        tabIndex = getArguments().getInt(INTENT_INT_INDEX);
        Bundle bundle = getArguments();
        childPermissionList= (List<ChildPermissionList>) bundle.getSerializable("type");
        parent_name= bundle.getString("parent_name");
        initUI();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setView();
            }
        },2000);
        setListener();
    }
    public ExpandableListView expanded_list;
    public CirclView circlView;
    public ImageView img_err;
    public TextView text_err_agagin;
    public LinearLayout lin_wifi_err;
    public LinearLayout lin_bar;
    public ImageView img_net_err;
    public void initUI(){
        expanded_list=(ExpandableListView)findViewById(R.id.expanded_list);
        circlView=(CirclView)findViewById(R.id.circlView);
        img_err=(ImageView)findViewById(R.id.img_err);
        text_err_agagin=(TextView)findViewById(R.id.text_err_agagin);
        lin_wifi_err=(LinearLayout)findViewById(R.id.lin_wifi_err);
        lin_bar=(LinearLayout)findViewById(R.id.lin_bar);
        img_net_err=(ImageView) findViewById(R.id.img_net_err);
    }
    public void setView(){
        PermissionListAdapter  adapter=new PermissionListAdapter(parent_name,childPermissionList, MyApplication.getContextObject(),expanded_list);
        expanded_list.setAdapter(adapter);
        lin_bar.setVisibility(View.VISIBLE);
        circlView.setVisibility(View.INVISIBLE);
        adapter.setChildOnClick(this);
        adapter.setGroupLongOnClick(this);
        adapter.setGroupOnClick(this);
        adapter.setChildLongClick(this);
    }


    public void setListener(){
    }

    @Override
    public void onClick(View v) {

    }
    /**
     * 父类点击
     * @param groupPosition
     */
    @Override
    public void groupOnClick(int groupPosition) {
        if(expanded_list.isGroupExpanded(groupPosition)){
            expanded_list.collapseGroup(groupPosition);
        }else{
            expanded_list.expandGroup(groupPosition);
        }
    }


    /**
     * 父类长按监听
     * @param groupPosition
     */
    @Override
    public void groupLongClick(int groupPosition) {
        initGroupLongClick(true,groupPosition,-1);
    }

    /**
     * 子类监听
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void childOnClick(int groupPosition, int childPosition) {
        initGroupLongClick(false,groupPosition,childPosition);
    }

    /**
     * 子类长按监听
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void childLongClick(int groupPosition, int childPosition) {

    }
    /************************父类 子类菜单pop************************************/
    public PopupWindowPotting popupWindowPotting;
    public TextView pop_permiss_add;
    public TextView pop_permiss_del;
    public TextView pop_permiss_look_or_change;
    public View pop_permiss_view;
    public boolean status_pop;//父类 子类传递的标记位，保证数据刷新
    public int child;
    public int group;
    //status   父类子类标记   groupPosition 父类下表  childPosition子类下表
    public void initGroupLongClick(boolean status, int groupPosition, int childPosition){
        status_pop=status;
        child=childPosition;
        group=groupPosition;
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(getActivity(),5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_permiss_group;
                }

                @Override
                protected void initUI() {
                    pop_permiss_add=$(R.id.pop_permiss_add);
                    pop_permiss_del=$(R.id.pop_permiss_del);
                    pop_permiss_look_or_change=$(R.id.pop_permiss_look_or_change);
                    pop_permiss_view=$(R.id.pop_permiss_view);
                }

                @Override
                protected void setListener() {
                    pop_permiss_add.setOnClickListener(new LookOrChangeOnClick());
                    pop_permiss_del.setOnClickListener(new LookOrChangeOnClick());
                    pop_permiss_look_or_change.setOnClickListener(new LookOrChangeOnClick());
                }
            };
        }
        if(status){//父类--显示 增加三级分类
            pop_permiss_add.setVisibility(View.VISIBLE);
            pop_permiss_view.setVisibility(View.VISIBLE);
        }else{//子类不显示
            pop_permiss_add.setVisibility(View.GONE);
            pop_permiss_view.setVisibility(View.GONE);
        }
        popupWindowPotting.Show(lin_wifi_err);
    }

    class LookOrChangeOnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pop_permiss_look_or_change://查看或者修改
                    popupWindowPotting.Hide();
                    initLookOrChangePop(status_pop, group, child );
                    break;
                case R.id.pop_permiss_add://父类增加三级分类（子类不显示）
                    popupWindowPotting.Hide();
                    initAddChildPermiss();
                    break;
                case R.id.pop_permiss_del://删除
                    popupWindowPotting.Hide();
                    initDelPermiss();
                    break;
            }
        }
    }

    /************************查看或修改权限信息******************************/
    public PopupWindowPotting lookOrChangePop;
    public EditText edit_p_yname;
    public EditText edit_p_name;
    public EditText edit_p_url;
    public TextView text_p_url;
    public TextView text_cancle;
    public TextView text_sure;
    public void initLookOrChangePop( boolean status_p_s, int groupPosition, int childPosition){
        if(lookOrChangePop==null){
            lookOrChangePop=new PopupWindowPotting(getActivity(),5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_permiss_look_or_change;
                }

                @Override
                protected void initUI() {
                    edit_p_yname=$(R.id.edit_p_yname);
                    edit_p_name=$(R.id.edit_p_name);
                    edit_p_url=$(R.id.edit_p_url);
                    text_p_url=$(R.id.text_p_url);
                    text_cancle=$(R.id.text_cancle);
                    text_sure=$(R.id.text_sure);
                }

                @Override
                protected void setListener() {
                    text_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lookOrChangePop.Hide();
                        }
                    });
                    text_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lookOrChangePop.Hide();
                        }
                    });
                }
            };
        }
        if(status_p_s){//父类---显示url
            text_p_url.setVisibility(View.VISIBLE);
            edit_p_url.setVisibility(View.VISIBLE);
            edit_p_name.setText(childPermissionList.get(groupPosition).getPermissionDec());
            edit_p_yname.setText(childPermissionList.get(groupPosition).getPermissionName());
            edit_p_url.setText(childPermissionList.get(groupPosition).getPermissionUrl());
        }else{//子类 ---不显示url
            text_p_url.setVisibility(View.GONE);
            edit_p_url.setVisibility(View.GONE);
            edit_p_name.setText(childPermissionList.get(groupPosition).getGrandSonPermissionLists().get(childPosition).getPermissionDec());
            edit_p_yname.setText(childPermissionList.get(groupPosition).getGrandSonPermissionLists().get(childPosition).getPermissionName());
        }
        lookOrChangePop.Show(lin_wifi_err);
    }
    /******************************删除权限 父类 子类*******************************************/
    public PopupWindowPotting  delPermissPop;
    public TextView text_message;
    public TextView text_del_sure;
    public TextView text_del_cancle;
    public void initDelPermiss(){
        if(delPermissPop==null){
            delPermissPop=new PopupWindowPotting(getActivity(),5) {
                @Override
                protected int getLayout() {
                    return R.layout.dialog_exits_notice;
                }

                @Override
                protected void initUI() {
                    text_message=$(R.id.text_message);
                    text_del_cancle=$(R.id.text_cancle);
                    text_del_sure=$(R.id.text_sure);
                }

                @Override
                protected void setListener() {
                    text_del_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delPermissPop.Hide();
                        }
                    });
                    text_del_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delPermissPop.Hide();
                        }
                    });
                }
            };
        }
        if(status_pop){//父类
            text_message.setText("是否删除"+childPermissionList.get(group).getPermissionDec()+"?");
        }else{//子类
            text_message.setText("是否删除"+childPermissionList.get(group).getGrandSonPermissionLists().get(child).getPermissionDec()+"?");
        }
        delPermissPop.Show(lin_wifi_err);
    }
    /***************************父类添加三级权限***********************************/
    public PopupWindowPotting addChildPermissPop;
    public EditText edit_add_p_yname;
    public EditText edit_add_p_name;
    public EditText edit_add_p_url;
    public TextView text_look_change_add_permiss;
    public TextView text_add_p_url;
    public TextView text_add_cancle;
    public TextView text_add_sure;
    public void initAddChildPermiss(){
        if(addChildPermissPop==null){
            addChildPermissPop=new PopupWindowPotting(getActivity(),5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_permiss_look_or_change;
                }

                @Override
                protected void initUI() {
                    text_look_change_add_permiss=$(R.id.text_look_change_add_permiss);
                    edit_add_p_yname=$(R.id.edit_p_yname);
                    edit_add_p_name=$(R.id.edit_p_name);
                    edit_add_p_url=$(R.id.edit_p_url);
                    text_add_p_url=$(R.id.text_p_url);
                    text_add_cancle=$(R.id.text_cancle);
                    text_add_sure=$(R.id.text_sure);
                    text_add_p_url.setVisibility(View.GONE);
                    edit_add_p_url.setVisibility(View.GONE);
                    text_look_change_add_permiss.setText("添加三级权限");
                }

                @Override
                protected void setListener() {
                    text_add_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addChildPermissPop.Hide();
                        }
                    });
                    text_add_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addChildPermissPop.Hide();
                        }
                    });
                }
            };
        }
        addChildPermissPop.Show(lin_wifi_err);
    }
}
