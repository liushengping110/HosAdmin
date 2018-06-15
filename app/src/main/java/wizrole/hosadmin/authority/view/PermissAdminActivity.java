package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.DelPermissAdpater;
import wizrole.hosadmin.adapter.PermissVPTAdapter;
import wizrole.hosadmin.adapter.SpinnerPermissAdapter;
import wizrole.hosadmin.authority.model.getrolelist.Datas;
import wizrole.hosadmin.authority.model.getrolelist.RoleListBack;
import wizrole.hosadmin.authority.preserent.getrolelist.GetRoleListInterface;
import wizrole.hosadmin.authority.preserent.getrolelist.GetRoleListPreserent;
import wizrole.hosadmin.authority.view.fragment.Fragment_PermissType;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.PopupMaxHeightListView;

/**
 * Created by 何人执笔？ on 2018/4/23.
 * liushengping
 * 权限管理
 */

public class PermissAdminActivity extends BaseActivity implements View.OnClickListener,GetRoleListInterface {
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.view_pager)ViewPager view_pager;
    @BindView(R.id.tab_layout)TabLayout tab_layout;
    @BindView(R.id.img_add)ImageView img_add;
    @BindView(R.id.img_sub)ImageView img_sub;
    @BindView(R.id.lin_add_sub)LinearLayout lin_add_sub;

    public GetRoleListPreserent getRoleListPreserent=new GetRoleListPreserent(this,PermissAdminActivity.this);
    @Override
    protected int getLayout() {
        return R.layout.activity_permissadmin;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("权限管理");
        getAllData();
    }

    public Dialog dialog;
    public void getAllData(){
        dialog= LoadingDailog.createLoadingDialog(PermissAdminActivity.this,"加载中");
        getRoleListPreserent.getRoleList();
    }
    public List<Datas> datases;
    public List<Fragment> fragments;
    @Override
    public void getRoleListSucc(RoleListBack roleListBack) {
        datases=roleListBack.getDatas();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getRoleListFail(String msg) {
        handler.sendEmptyMessage(1);
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    fragments=new ArrayList<Fragment>();
                    for(int i=0;i<datases.size();i++){
                        if(i==0){
                            fragments.add(Fragment_PermissType.newInstance(1,false));
                        }else{
                            fragments.add(Fragment_PermissType.newInstance(i+1,true));
                        }
                    }
                    FragmentManager supportFragmentManager = getSupportFragmentManager();
                    PermissVPTAdapter adapter=new PermissVPTAdapter(datases,fragments,supportFragmentManager);
                    view_pager.setAdapter(adapter);
                    view_pager.setOffscreenPageLimit(fragments.size());
                    tab_layout.setupWithViewPager(view_pager);
                    LoadingDailog.closeDialog(dialog);
                    break;
                case 1:
                    LoadingDailog.closeDialog(dialog);
                    break;
            }
        }
    };
    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        img_add.setOnClickListener(this);
        img_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                finish();
                break;
            case R.id.img_add://添加
                initAddPermiss();
                break;
            case R.id.img_sub://删除主权限
                initDelPermissPop();
                break;
        }
    }

    /*******************************添加权限*************************************/
    public PopupWindowPotting addPermissPop;
    public TextView pop_permiss_add_parent_permiss;
    public TextView pop_permiss_add_son_permiss;
    public void initAddPermiss(){
        if(addPermissPop==null){
            addPermissPop=new PopupWindowPotting(PermissAdminActivity.this,6) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_permiss_add_or_sub;
                }

                @Override
                protected void initUI() {
                    pop_permiss_add_parent_permiss=$(R.id.pop_permiss_add_parent_permiss);
                    pop_permiss_add_son_permiss=$(R.id.pop_permiss_add_son_permiss);
                }

                @Override
                protected void setListener() {
                    pop_permiss_add_parent_permiss.setOnClickListener(new AddOrSubOnClick());
                    pop_permiss_add_son_permiss.setOnClickListener(new AddOrSubOnClick());
                }

                class AddOrSubOnClick implements View.OnClickListener{
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.pop_permiss_add_parent_permiss://添加主权限
                                addPermissPop.Hide();
                                initAddParentPermiss();
                                break;
                            case R.id.pop_permiss_add_son_permiss://添加子权限
                                addPermissPop.Hide();
                                initAddSonPermiss();
                                break;
                        }
                    }
                }
            };
        }
        addPermissPop.Show(lin_add_sub);
    }
    /***********************添加主权限pop********************************/
    public PopupWindowPotting addParentPermiss;
    public TextView text_cancle;
    public TextView text_sure;
    public void initAddParentPermiss(){
        if(addParentPermiss==null){
            addParentPermiss=new PopupWindowPotting(PermissAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_add_parent_permiss;
                }

                @Override
                protected void initUI() {
                    text_sure=$(R.id.text_sure);
                    text_cancle=$(R.id.text_cancle);
                }

                @Override
                protected void setListener() {
                    text_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addParentPermiss.Hide();
                        }
                    });
                    text_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addParentPermiss.Hide();
                        }
                    });
                }
            };
        }
        addParentPermiss.Show(lin_add_sub);
    }

    /**********************添加子权限pop******************************/
    public PopupWindowPotting addSonPermiss;
    public TextView text_son_cancle;
    public TextView text_son_sure;
    public Spinner pop_permiss_spinner;
    public void initAddSonPermiss(){
        if(addSonPermiss==null){
            addSonPermiss=new PopupWindowPotting(PermissAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_add_son_permiss;
                }

                @Override
                protected void initUI() {
                    text_son_sure=$(R.id.text_sure);
                    text_son_cancle=$(R.id.text_cancle);
                    pop_permiss_spinner=$(R.id.pop_permiss_spinner);
                    SpinnerPermissAdapter adapter=new SpinnerPermissAdapter(PermissAdminActivity.this,datases);
                    pop_permiss_spinner.setAdapter(adapter);
                }

                @Override
                protected void setListener() {
                    text_son_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addSonPermiss.Hide();
                        }
                    });
                    text_son_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addSonPermiss.Hide();
                        }
                    });
                }
            };
        }
        addSonPermiss.Show(lin_add_sub);
    }

    /*************************删除权限************************************/
    public PopupWindowPotting delPermissPop;
    public PopupMaxHeightListView list_del_permiss;
    public TextView  text_del_sure;
    public TextView  text_del_cancle;
    public DelPermissAdpater delPermissAdpater;//主权限列表
    public void initDelPermissPop(){
        if(delPermissPop==null){
            delPermissPop=new PopupWindowPotting(PermissAdminActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.pop_del_permiss;
                }

                @Override
                protected void initUI() {
                    list_del_permiss=$(R.id.list_del_permiss);
                    text_del_sure=$(R.id.text_sure);
                    text_del_cancle=$(R.id.text_cancle);
                    list_del_permiss.setListViewHeight((int)700);
                    delPermissAdpater=new DelPermissAdpater(PermissAdminActivity.this,datases);
                    list_del_permiss.setAdapter(delPermissAdpater);
                }

                @Override
                protected void setListener() {
                    list_del_permiss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Datas datas=datases.get(position);
                            if(datas.isSel_status()){
                                datas.setSel_status(false);
                            }else {
                                datas.setSel_status(true);
                            }
                            delPermissAdpater.notifyDataSetChanged();
                        }
                    });
                    text_del_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delPermissPop.Hide();
                        }
                    });
                    text_del_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i=0;i<datases.size();i++){
                                if(datases.get(i).isSel_status()){
                                    datases.remove(datases.get(i));
                                }
                            }
                            delPermissAdpater.notifyDataSetChanged();
                            delPermissPop.Hide();
                        }
                    });
                }
            };
        }
        delPermissPop.Show(lin_add_sub);
    }
}
