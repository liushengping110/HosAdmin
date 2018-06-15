package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.SpinnerCompanyAdapter;
import wizrole.hosadmin.adapter.SpinnerDeparentAdapter;
import wizrole.hosadmin.adapter.SpinnerIdentityAdapter;
import wizrole.hosadmin.authority.model.getcompay.CompayBack;
import wizrole.hosadmin.authority.model.getcompay.CompayList;
import wizrole.hosadmin.authority.model.getdeparent.DeparentBack;
import wizrole.hosadmin.authority.model.getdeparent.DeparentList;
import wizrole.hosadmin.authority.model.getidentity.IdentityBack;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;
import wizrole.hosadmin.authority.model.getuser.UserList;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayInterface;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayPreserent;
import wizrole.hosadmin.authority.preserent.getdeparent.GetDeparentInterface;
import wizrole.hosadmin.authority.preserent.getdeparent.GetDeparentPreserent;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityInterface;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityPreserent;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.pop.PopupWindowPotting;

/**
 * Created by 何人执笔？ on 2018/4/18.
 * liushengping
 * 修改--删除--查看用户信息
 */

public class AddOrChangeUserActivity extends BaseActivity implements View.OnClickListener,GetCompayInterface,GetIdentityInterface,GetDeparentInterface {
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.lin_user_password)LinearLayout lin_user_password;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.text_item_userName)TextView text_item_userName;
    @BindView(R.id.text_item_password)TextView text_item_password;
    @BindView(R.id.text_item_identity)Spinner spinner_identity;
    @BindView(R.id.text_item_deparName)Spinner spinner_deparent;
    @BindView(R.id.text_item_comName)Spinner spinner_comName;
    public UserList userList;
    public Dialog dialog;

    public GetCompayPreserent getCompayPreserent=new GetCompayPreserent(this);//获取公司pop列表
    public GetIdentityPreserent getIdentityPreserent=new GetIdentityPreserent(this);//获取身份pop列表
    public GetDeparentPreserent getDeparentPreserent=new GetDeparentPreserent(this);//获取科室列表pop
    @Override
    protected int getLayout() {
        return R.layout.activity_addorchangeuser;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        userList=(UserList) getIntent().getSerializableExtra("userinfor");
        text_right.setText("提交");
        if(userList!=null){
            text_title.setText(userList.getUserName());
            text_item_userName.setText(userList.getUserName());
            lin_user_password.setVisibility(View.GONE);
        }else{
            text_title.setText("添加用户");
            lin_user_password.setVisibility(View.VISIBLE);
            text_item_userName.setHint("请输入用户名");
            text_item_password.setHint("请输入密码");
        }
        dialog= LoadingDailog.createLoadingDialog(AddOrChangeUserActivity.this,"加载中");
        getCompayList();//获取公司pop列表
        getIdentityList();//获取身份列表
        getDeparentList();//获取科室列表
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

    /*******************获取公司名称*************************/
    public List<CompayList> compayLists=new ArrayList<>();
    @Override
    public void getCompaySucc(CompayBack back) {
        compayLists=back.getCompayLists();
        handler.sendEmptyMessage(3);
    }

    @Override
    public void getCompayFail(String msg) {
        if(msg.equals("")){
            handler.sendEmptyMessage(4);
        }else{
            handler.sendEmptyMessage(5);
        }
    }
    /*******************获取身份列表************************/
    public List<IdentityList> identityLists=new ArrayList<>();
    @Override
    public void getIdentitySucc(IdentityBack back) {
        identityLists=back.getIdentityLists();
        handler.sendEmptyMessage(6);
    }

    @Override
    public void getIdentityFail(String msg) {
        if(msg.equals("")){
            handler.sendEmptyMessage(7);
        }else{
            handler.sendEmptyMessage(8);
        }
    }
    /*******************获取科室列表***********************/
    public List<DeparentList> deparentLists=new ArrayList<>();
    @Override
    public void getDeparentSucc(DeparentBack deparentBack) {
        deparentLists=deparentBack.getDeparentLists();
        handler.sendEmptyMessage(9);
    }

    @Override
    public void getDeparentFail(String msg) {
        if(msg.equals("")){
            handler.sendEmptyMessage(10);
        }else{
            handler.sendEmptyMessage(11);
        }
    }

    public SpinnerCompanyAdapter spinnerCompanyAdapter;
    public CompayList compayList;
    public int com_temp=-1;

    public SpinnerIdentityAdapter spinnerIdentityAdapter;
    public IdentityList identityList;
    public int identity_temp=-1;

    public SpinnerDeparentAdapter spinnerDeparentAdapter;
    public DeparentList deparentList;
    public int deparent_temp=-1;


    public int status=0;//身份，科室，公司请求成功标记
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 3://公司名称列表成功
                    spinnerCompanyAdapter=new SpinnerCompanyAdapter(MyApplication.getContextObject(),compayLists);
                    spinner_comName.setAdapter(spinnerCompanyAdapter);
                    if (userList!=null){
                        //公司
                        for (int i=0;i<compayLists.size();i++){
                            if(userList.getUserCompanyName().equals(compayLists.get(i).getCompayName())){
                                com_temp=i;
                                break;
                            }
                        }
                        spinner_comName.setSelection(com_temp,true);
                    }
                    setDialogDiss();
                    break;
                case 4://公司名称列表获取失败
                    compayList=new CompayList();
                    compayList.setCompayName("公司列表数据获取失败");
                    compayLists.add(compayList);
                    spinnerCompanyAdapter=new SpinnerCompanyAdapter(MyApplication.getContextObject(),compayLists);
                    spinner_comName.setAdapter(spinnerCompanyAdapter);
                    setDialogDiss();
                    break;
                case 5://公司名称列表无数据
                    compayList=new CompayList();
                    compayList.setCompayName("公司列表无数据");
                    compayLists.add(compayList);
                    spinnerCompanyAdapter=new SpinnerCompanyAdapter(MyApplication.getContextObject(),compayLists);
                    spinner_comName.setAdapter(spinnerCompanyAdapter);
                    setDialogDiss();
                    break;
                case 6://身份（角色）列表获取成功
                    spinnerIdentityAdapter=new SpinnerIdentityAdapter(MyApplication.getContextObject(),identityLists);
                    spinner_identity.setAdapter(spinnerIdentityAdapter);
                    if(userList!=null){
                        //身份
                        for(int i=0;i<identityLists.size();i++){
                            if(userList.getUserIdentity().equals(identityLists.get(i).getIdentityName())){
                                identity_temp=i;
                                break;
                            }
                        }
                        spinner_identity.setSelection(identity_temp,true);
                    }
                    setDialogDiss();
                    break;
                case 7://身份（角色）列表获取失败
                    identityList=new IdentityList();
                    identityList.setIdentityName("当前身份获取失败");
                    identityList.setIdentityNo("当前身份获取失败");
                    identityLists.add(identityList);
                    spinnerIdentityAdapter=new SpinnerIdentityAdapter(MyApplication.getContextObject(),identityLists);
                    spinner_identity.setAdapter(spinnerIdentityAdapter);
                    setDialogDiss();
                    break;
                case 8://身份（角色）列表获取无数据
                    identityList=new IdentityList();
                    identityList.setIdentityName("身份列表无数据");
                    identityList.setIdentityNo("身份列表无数据");
                    identityLists.add(identityList);
                    spinnerIdentityAdapter=new SpinnerIdentityAdapter(MyApplication.getContextObject(),identityLists);
                    spinner_identity.setAdapter(spinnerIdentityAdapter);
                    setDialogDiss();
                    break;
                case 9://科室列表获取成功
                    spinnerDeparentAdapter=new SpinnerDeparentAdapter(MyApplication.getContextObject(),deparentLists);
                    spinner_deparent.setAdapter(spinnerDeparentAdapter);
                    if(userList!=null){
                        //科室
                        for(int i=0;i<deparentLists.size();i++){
                            if(userList.getUserDeparentName().equals(deparentLists.get(i).getDeparentName())){
                                deparent_temp=i;
                                break;
                            }
                        }
                        spinner_deparent.setSelection(deparent_temp);
                    }
                    setDialogDiss();
                    break;
                case 10://科室列表获取失败
                    deparentList=new DeparentList();
                    deparentList.setDeparentName("科室列表获取失败");
                    deparentLists.add(deparentList);
                    spinnerDeparentAdapter=new SpinnerDeparentAdapter(MyApplication.getContextObject(),deparentLists);
                    spinner_deparent.setAdapter(spinnerDeparentAdapter);
                    setDialogDiss();
                    break;
                case 11://科室列表无数据
                    deparentList=new DeparentList();
                    deparentList.setDeparentName("科室列表无数据");
                    deparentLists.add(deparentList);
                    spinnerDeparentAdapter=new SpinnerDeparentAdapter(MyApplication.getContextObject(),deparentLists);
                    spinner_deparent.setAdapter(spinnerDeparentAdapter);
                    setDialogDiss();
                    break;
            }
        }
    };

    public void setDialogDiss(){
        ++status;
        if(status==3){
            LoadingDailog.closeDialog(dialog);
        }
    }
    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                initExitPop();
                break;
        }
    }
    public PopupWindowPotting popupWindowPotting;
    public TextView text_message;
    public TextView text_cancle;
    public TextView text_sure;
    public void initExitPop(){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(AddOrChangeUserActivity.this,5) {
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
