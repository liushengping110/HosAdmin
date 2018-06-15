package wizrole.hosadmin.authority.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getuser.UserList;
import wizrole.hosadmin.authority.model.usersearch.UserSearchBack;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.util.db.SearchStoreReader;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.pop.CustomPopupWindow;
import wizrole.hosadmin.view.CustTextView;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 用户搜索
 */

public class UserSearchActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.list_histroy)CustTextView list_histroy;
    @BindView(R.id.lin_del)LinearLayout lin_del;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.edit_content)EditText edit_content;
    @Override
    protected int getLayout() {
        return R.layout.activity_usersearch;
    }

    public String type;
    @Override
    protected void initData() {
        ButterKnife.bind(this);
        type=getIntent().getStringExtra("type");
        switch (type){
            case "user"://用户搜索
                edit_content.setHint("请输入要查询的用户、可模糊搜索");
                list=getHostry();//获取历史记录
                break;
            case "company"://公司搜索
                edit_content.setHint("请输入要查询的公司、可模糊搜索");
                list=getHostry();//获取历史记录
                break;
        }
        setView();
        initPop();
    }

    public List<UserSearchBack> list;
    /***获取存储的集合*/
    public boolean status=false;//历史纪录集合是为0状态
    public List<UserSearchBack> getHostry() {
        if(SearchStoreReader.searchInfors(this).size()>0){
            list= SearchStoreReader.searchInfors(this);
        }else{//数据库为空
            status=true;
            list=new ArrayList<UserSearchBack>();
            UserSearchBack hostry=new UserSearchBack();
            hostry.setSearchContent("无");
            list.add(hostry);
        }
        return list;
    }

    public void  setView(){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,90);
        params.setMargins(20,10,20,10);
        if(list_histroy.getChildCount()>1){
            list_histroy.removeAllViews();
        }
        for (int i=0;i<list.size();i++){
            TextView textView=new TextView(UserSearchActivity.this);
            textView.setText(list.get(i).getSearchContent());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(13);
            textView.setPadding(20,10,20,10);
            textView.setMinWidth(120);
            textView.setLayoutParams(params);
            textView.setTextColor(getResources().getColor(R.color.shenhui));
            textView.setBackgroundColor(getResources().getColor(R.color.qrea_bg));
            textView.setOnClickListener(new ButtonItem(textView.getText().toString()));
            list_histroy.addView(textView);
        }
    }



    class ButtonItem implements View.OnClickListener{
        public String msg;
        public ButtonItem(String msg){
            this.msg=msg;
        }
        @Override
        public void onClick(View v) {
            if(!msg.equals("无")){
                item_status=true;
                dialog= LoadingDailog.createLoadingDialog(UserSearchActivity.this,"加载中");
                edit_content.setText(msg);
                addDB(msg);
            }
        }
    }

    /**
     * 初始化Pop，pop的布局是一个列表
     */
    private List<UserList> mSearchList = new ArrayList<>(); //搜索结果的数据源
    public ListView searchLv;
    public TextView text_pop_msg;
    public TextView text_err_agagin;
    public ImageView img_net_err;
    public ProgressBar progress_bar;
    public boolean item_status=false;//点击历史记录item标记
    private CustomPopupWindow mPop; //显示搜索联想的pop
    private void initPop() {
        mPop = new CustomPopupWindow.Builder(this)
                .setContentView(R.layout.search_pop_list)
                .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(1f)
                .build();
        progress_bar = (ProgressBar) mPop.getItemView(R.id.progress_bar);
        searchLv = (ListView) mPop.getItemView(R.id.search_list_lv);
        text_pop_msg = (TextView) mPop.getItemView(R.id.text_pop_msg);
        text_err_agagin = (TextView) mPop.getItemView(R.id.text_err_agagin);
        img_net_err = (ImageView) mPop.getItemView(R.id.img_net_err);
        text_err_agagin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_content.getText().toString().length()==0){
                    ToastShow("请输入要搜索的用户");
                }else{
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_content.getWindowToken(), 0) ;
                    dialog= LoadingDailog.createLoadingDialog(UserSearchActivity.this,"加载中");

                }
            }
        });
        searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(item_status){
                    item_status=false;
                }else{
                    addDB(mSearchList.get(position).getUserName());
                }

            }
        });
        searchLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_content.getWindowToken(), 0) ;
                return false;
            }
        });
    }
    public Dialog dialog;
    @Override
    protected void setListener() {
        text_right.setOnClickListener(this);
        lin_back.setOnClickListener(this);
        lin_del.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                finish();
                break;
            case R.id.text_right://搜索
                addDB(edit_content.getText().toString());
                break;
            case R.id.lin_del:
                delHostry();
                break;
        }
    }


    /**
     * 删除历史记录
     */
    public void delHostry(){
        if(list.size()>0){
            if(list.size()>1){
                list.clear();
                UserSearchBack hos = new UserSearchBack();
                hos.setSearchContent("无");
                list.add(hos);
                setView();
                list= SearchStoreReader.searchInfors(UserSearchActivity.this);
                for(int i=0;i<list.size();i++){
                    UserSearchBack hostry=list.get(i);
                    SearchStoreReader.deleteInfors(hostry,UserSearchActivity.this);
                }
            }else{
                if(!list.get(0).getSearchContent().equals("无")){
                    list.clear();
                    UserSearchBack hostry=new UserSearchBack();
                    hostry.setSearchContent("无");
                    list.add(hostry);
                    setView();
                    list= SearchStoreReader.searchInfors(UserSearchActivity.this);
                    for(int i=0;i<list.size();i++){
                        UserSearchBack h=list.get(i);
                        SearchStoreReader.deleteInfors(h,UserSearchActivity.this);
                    }
                }
            }
        }
    }

    /**
     * 存储本地历史记录
     * @param content
     */
    public void addDB(String content){
        if(status){//如果是添加第一条记录，就先删除那个【无】
            list.clear();
            status=false;
        }
        if(list.size()==0){
            UserSearchBack hostry=new UserSearchBack();
            hostry.setSearchContent(content);
            SearchStoreReader.addInfors(hostry,UserSearchActivity.this);//存储
            list.add(hostry);
        }else if(list.size()==1){
            if (!list.get(0).getSearchContent().equals(content)){
                UserSearchBack hostry=new UserSearchBack();
                hostry.setSearchContent(content);
                SearchStoreReader.addInfors(hostry,UserSearchActivity.this);//存储
                list.add(hostry);
            }
        }else{
            for (int a=0;a<list.size();a++){
                if(a<(list.size()-1)){
                    if(list.get(a).getSearchContent().equals(content)){
                        return;
                    }
                }else{
                    if(!list.get(a).getSearchContent().equals(content)){
                        UserSearchBack hostry=new UserSearchBack();
                        hostry.setSearchContent(content);
                        SearchStoreReader.addInfors(hostry,UserSearchActivity.this);//存储
                        list.add(hostry);
                    }
                }
            }
        }
        setView();
    }
}
