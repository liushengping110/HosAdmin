package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.IDPermissChildAdapter;
import wizrole.hosadmin.adapter.IDPermissParentAdapter;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;
import wizrole.hosadmin.authority.model.getrolelist.Datas;
import wizrole.hosadmin.authority.model.getrolelist.GrandSonPermissionList;
import wizrole.hosadmin.authority.model.getrolelist.RoleListBack;
import wizrole.hosadmin.authority.preserent.getrolelist.GetRoleListInterface;
import wizrole.hosadmin.authority.preserent.getrolelist.GetRoleListPreserent;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.pop.PopupWindowPotting;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 * 添加  修改  角色列表页面
 */

public class AddOrChangeIdenActivity extends BaseActivity implements GetRoleListInterface
,ExpandableListView.OnGroupExpandListener, IDPermissParentAdapter.OnChildTreeViewClickListener{

    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.text_item_identityNo)EditText text_item_identityNo;
    @BindView(R.id.text_item_identityName)EditText text_item_identityName;
    @BindView(R.id.lin_wifi_err)LinearLayout lin_wifi_err;
    @BindView(R.id.text_no_data)TextView text_no_data;
    @BindView(R.id.img_net_err)ImageView img_net_err;
    @BindView(R.id.list_role)ExpandableListView list_role;
    public IdentityList identityList;
    public Dialog dialog;

    public GetRoleListPreserent getRoleListPreserent=new GetRoleListPreserent(this,AddOrChangeIdenActivity.this);
    @Override
    protected int getLayout() {
        return R.layout.activity_addorchangeidentity;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        identityList=(IdentityList) getIntent().getSerializableExtra("identity");
        dialog= LoadingDailog.createLoadingDialog(AddOrChangeIdenActivity.this,"加载中");
        setView();
        getPermiss();
    }

    public void getPermiss(){
        getRoleListPreserent.getRoleList();
    }
    public void setView(){
        text_right.setText("提交");
        if(identityList==null){
            text_title.setText("新建角色");
        }else{
            text_title.setText(identityList.getIdentityName());
            text_item_identityName.setText(identityList.getIdentityName());
            text_item_identityNo.setText(identityList.getIdentityNo());
        }
    }

    public List<Datas> datasList;
    @Override
    public void getRoleListSucc(RoleListBack roleListBack) {
        datasList=roleListBack.getDatas();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void getRoleListFail(String msg) {
        handler.sendEmptyMessage(1);
    }

    public IDPermissParentAdapter adapter;
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapter = new IDPermissParentAdapter(MyApplication.getContextObject(), datasList,list_role);
                    list_role.setAdapter(adapter);
                    adapter.setOnChildTreeViewClickListener(AddOrChangeIdenActivity.this);
                    list_role.setOnGroupExpandListener(AddOrChangeIdenActivity.this);
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
        lin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initExitPop();
            }
        });
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSel().size()==0){
                    ToastShow("当前尚未选中任何一项权限");
                }else{
                    grandSonPermissionLists=getSel();
                    for (int i=0;i<grandSonPermissionLists.size();i++){
                        sel_permissId+=grandSonPermissionLists.get(i).getPermissionId()+"-";
                    }
                }
                ToastShow(sel_permissId);
                Log.e("----",sel_permissId);
                sel_permissId="10-";//置空
            }
        });
    }
    public String sel_permissId="10-";//RoleId 初始化
    /**
     *  展开一项，关闭其他项，保证每次只能展开一项
     * @param groupPosition
     */
    @Override
    public void onGroupExpand(int groupPosition) {
//        for (int i = 0; i < datasList.size(); i++) {
//            if (i != groupPosition) {
//                list_role.collapseGroup(i);
//            }
//        }
    }

    /**
     * 点击子ExpandableListView的子项时，回调本方法，根据下标获取值来做相应的操作
     * @param parentPosition
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void onClickPosition(int parentPosition, int groupPosition, int childPosition,IDPermissChildAdapter childAdapter) {
        GrandSonPermissionList grandSonPermissionList = datasList.get(parentPosition).getChildPermissionList()
                .get(groupPosition).getGrandSonPermissionLists().get(childPosition);
        Toast.makeText( MyApplication.getContextObject(), grandSonPermissionList.getPermissionDec(), Toast.LENGTH_SHORT).show();
        if(grandSonPermissionList.getCheckStates().equals("")){
            grandSonPermissionList.setCheckStates("check");
        }else{
            grandSonPermissionList.setCheckStates("");
        }
        childAdapter.notifyDataSetChanged();
    }
    public List<GrandSonPermissionList> grandSonPermissionLists=new ArrayList<>();//选中得
    public List<GrandSonPermissionList> getSel(){
        List<GrandSonPermissionList> temp=new ArrayList<>();
        for (int i=0;i<datasList.size();i++){
            for (int j=0;j<datasList.get(i).getChildPermissionList().size();j++){
                for (int a=0;a<datasList.get(i).getChildPermissionList().get(j).getGrandSonPermissionLists().size();a++){
                    if(datasList.get(i).getChildPermissionList().get(j).getGrandSonPermissionLists().get(a).getCheckStates().equals("check")){
                        temp.add(datasList.get(i).getChildPermissionList().get(j).getGrandSonPermissionLists().get(a));
                    }
                }
            }
        }
        return temp;
    }


    public PopupWindowPotting popupWindowPotting;
    public TextView text_message;
    public TextView text_cancle;
    public TextView text_sure;
    public void initExitPop(){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(AddOrChangeIdenActivity.this,5) {
                @Override
                protected int getLayout() {
                    return R.layout.dialog_exits_notice;
                }

                @Override
                protected void initUI() {
                    text_sure=$(R.id.text_sure);
                    text_cancle=$(R.id.text_cancle);
                    text_message=$(R.id.text_message);
                    text_message.setText("信息尚未提交，是否退出？");
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
                            finish();
                        }
                    });
                }
            };
        }
        popupWindowPotting.Show(lin_back);
    }

    @Override
    public void onBackPressed() {
        initExitPop();
    }
}
