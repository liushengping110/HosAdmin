package wizrole.hosadmin.maintenance.view;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.cons.DPMode;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.GeneralDayAdapter;
import wizrole.hosadmin.adapter.StatutDayAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.maintenance.model.generalhoilday.GeneralDay;
import wizrole.hosadmin.maintenance.model.statutoryhoilday.StatutoryDay;
import wizrole.hosadmin.view.CustGridView;

/**
 * Created by liushengping on 2018/3/9.
 * 何人执笔？
 * 属性维护
 */

public class PropertActivity extends BaseActivity implements View.OnClickListener,StatutDayAdapter.ItemClick,StatutDayAdapter.LongClick
,GeneralDayAdapter.GenerItemClick,GeneralDayAdapter.GenerLongClick{
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    public boolean only_status=false;//弹窗法定节假日和普通节假日
    public boolean add_status=false;
    //法定
    public StatutDayAdapter top_adapter;
    @BindView(R.id.text_fd_cancle)TextView text_fd_cancle;
    @BindView(R.id.text_fd_allsel)TextView text_fd_allsel;
    @BindView(R.id.text_fd_add)TextView text_fd_add;
    @BindView(R.id.grid_fd_view)CustGridView grid_fd_view;
    public boolean all_sel=false;//全选和全不选
    public boolean add_or_del=false;//执行删除或添加
    public boolean cust_status=true;//点击item，是否显示全选、全不选
    public List<StatutoryDay> sta_sel=new ArrayList<>();//选中的
    public List<StatutoryDay> string_top=new ArrayList<>();

    //普通
    public GeneralDayAdapter bottom_adapter;
    @BindView(R.id.grid_pt_view)CustGridView grid_pt_view;
    @BindView(R.id.text_pt_add)TextView text_pt_add;
    @BindView(R.id.text_pt_cancle)TextView text_pt_cancle;
    @BindView(R.id.text_pt_allsel)TextView text_pt_allsel;
    public boolean all_sel_pt=false;//全选和全不选
    public boolean add_or_del_pt=false;//执行删除或添加
    public boolean cust_status_pt=true;//点击item，是否显示全选、全不选
    public List<GeneralDay> sta_sel_pt=new ArrayList<>();//选中的
    public List<GeneralDay> string_bottom=new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_propertis;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("属性维护");
        text_right.setText("提交");
        setView();
    }


    public void initTopData(StatutoryDay top_time){
        if(string_top.size()==1){
            if(string_top.get(0).getName().equals("无")){
                string_top.clear();
                string_top.add(top_time);
            }else{
                if(!string_top.get(0).getName().equals(top_time.getName())){
                    string_top.add(top_time);
                }else{
                    ToastShow("该日期已存在，不可重复添加");
                }
            }
        }else{
            for(int i=0;i<string_top.size();i++){
                if(!string_top.get(i).getName().equals(top_time.getName())){
                    if(i==(string_top.size()-1)){
                        string_top.add(top_time);
                        break;
                    }
                }else{
                    ToastShow("该日期已存在，不可重复添加");
                    break;
                }
            }
        }
    }


    public void initBottomData(GeneralDay bottom_time){
        if(string_bottom.size()==1){
            if(string_bottom.get(0).getName().equals("无")){
                string_bottom.clear();
                string_bottom.add(bottom_time);
            }else{
                if(!string_bottom.get(0).getName().equals(bottom_time.getName())){
                    string_bottom.add(bottom_time);
                }else{
                    ToastShow("该日期已存在，不可重复添加");
                }
            }
        }else{
            for(int i=0;i<string_bottom.size();i++){
                if(!string_bottom.get(i).getName().equals(bottom_time.getName())){
                    if(i==(string_bottom.size()-1)){
                        string_bottom.add(bottom_time);
                        break;
                    }
                }else{
                    ToastShow("该日期已存在，不可重复添加");
                    break;
                }
            }
        }
    }

    public void setView(){
        StatutoryDay  day=new StatutoryDay();
        day.setName("无");
        day.setShow(false);
        string_top.add(day);
        top_adapter=new StatutDayAdapter(PropertActivity.this,string_top);
        grid_fd_view.setAdapter(top_adapter);
        top_adapter.setItemClick(this);
        top_adapter.setLongClick(this);

        GeneralDay generalDay=new GeneralDay();
        generalDay.setName("无");
        generalDay.setShow(false);
        string_bottom.add(generalDay);
        bottom_adapter=new GeneralDayAdapter(PropertActivity.this,string_bottom);
        grid_pt_view.setAdapter(bottom_adapter);
        bottom_adapter.setItemClick(this);
        bottom_adapter.setLongClick(this);
    }
    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_fd_add.setOnClickListener(this);
        text_fd_allsel.setOnClickListener(this);
        text_fd_cancle.setOnClickListener(this);
        text_pt_add.setOnClickListener(this);
        text_pt_allsel.setOnClickListener(this);
        text_pt_cancle.setOnClickListener(this);
        text_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                if(string_top.size()==1){
                    if(string_top.get(0).getName().equals("无")){
                        if(string_bottom.size()==1){
                            if(string_bottom.get(0).getName().equals("无")){
                                finish();
                            }else{
                                NoticeCate();
                            }
                        }else{
                            NoticeCate();
                        }
                    }else{
                        NoticeCate();
                    }
                }else{
                    NoticeCate();
                }
                break;
            case R.id.text_right://提交
                if(string_top.size()==1){
                    if(string_top.get(0).getName().equals("无")){
                        if(string_bottom.size()==1){
                            if(string_bottom.get(0).getName().equals("无")){
                                ToastShow("当前尚未添加时间");
                            }else{
                                UpData();
                            }
                        }else{
                            UpData();
                        }
                    }else{
                        UpData();
                    }
                }else{
                    UpData();
                }
                break;
            /**********************************法定***************************************/
            case R.id.text_fd_add://法定节假日添加
                if(!add_or_del){//添加
                    only_status=false;
                    showDialog();
                }else{//删除
                    for (int i=0;i<string_top.size();i++){
                        for (int j=0;j<sta_sel.size();j++){
                            if(string_top.get(i).getName().equals(sta_sel.get(j).getName())){
                                string_top.remove(string_top.get(i));
                            }
                        }
                    }
                    sta_sel.clear();
                    if(string_top.size()==0){
                        StatutoryDay  day=new StatutoryDay();
                        day.setName("无");
                        day.setShow(false);
                        string_top.add(day);
                    }
                    top_adapter.setShowCheck(false);
                    top_adapter.notifyDataSetChanged();
                    text_fd_cancle.setVisibility(View.GONE);
                    text_fd_allsel.setVisibility(View.GONE);
                    text_fd_add.setText("添加");
                    add_or_del=false;//执行--添加
                }
                break;
            case R.id.text_fd_allsel://全选
                if(!all_sel){//执行全选-进入
                    all_sel=true;
                    for (int i=0;i<string_top.size();i++){
                        StatutoryDay day=string_top.get(i);
                        day.setShow(true);
                    }
                    text_fd_allsel.setText("全不选");
                    sta_sel.addAll(string_top);
                }else{//全不选-进入
                    all_sel=false;
                    for (int i=0;i<string_top.size();i++){
                        StatutoryDay day=string_top.get(i);
                        day.setShow(false);
                    }
                    text_fd_allsel.setText("全选");
                    sta_sel.clear();
                }
                top_adapter.notifyDataSetChanged();
                break;
            case R.id.text_fd_cancle://取消
                all_sel=false;
                top_adapter.setShowCheck(false);
                top_adapter.notifyDataSetChanged();
                text_fd_cancle.setVisibility(View.GONE);
                text_fd_allsel.setVisibility(View.GONE);
                text_fd_add.setText("添加");
                add_or_del=false;//执行--添加
                for(int i=0;i<string_top.size();i++){
                    StatutoryDay day1=string_top.get(i);
                    day1.setShow(false);
                }
                sta_sel.clear();//清空
                break;
            //********************************普通*******************************************/
            case R.id.text_pt_add://普通节假日添加
                if(!add_or_del_pt){//添加
                    only_status=true;
                    showDialog();
                }else{//删除
                    for (int i=0;i<string_bottom.size();i++){
                        for (int j=0;j<sta_sel_pt.size();j++){
                            if(string_bottom.get(i).getName().equals(sta_sel_pt.get(j).getName())){
                                string_bottom.remove(string_bottom.get(i));
                            }
                        }
                    }
                    sta_sel_pt.clear();
                    if(string_bottom.size()==0){
                        GeneralDay  day=new GeneralDay();
                        day.setName("无");
                        day.setShow(false);
                        string_bottom.add(day);
                    }
                    bottom_adapter.setShowCheck(false);
                    bottom_adapter.notifyDataSetChanged();
                    text_pt_cancle.setVisibility(View.GONE);
                    text_pt_allsel.setVisibility(View.GONE);
                    text_pt_add.setText("添加");
                    add_or_del_pt=false;//执行--添加
                }
                break;
            case R.id.text_pt_allsel://全选
                if(!all_sel_pt){//执行全选-进入
                    all_sel_pt=true;
                    for (int i=0;i<string_bottom.size();i++){
                        GeneralDay day=string_bottom.get(i);
                        day.setShow(true);
                    }
                    text_pt_allsel.setText("全不选");
                    sta_sel_pt.addAll(string_bottom);
                }else{//全不选-进入
                    all_sel_pt=false;
                    for (int i=0;i<string_bottom.size();i++){
                        GeneralDay day=string_bottom.get(i);
                        day.setShow(false);
                    }
                    text_pt_allsel.setText("全选");
                    sta_sel_pt.clear();
                }
                bottom_adapter.notifyDataSetChanged();
                break;
            case R.id.text_pt_cancle://取消
                all_sel_pt=false;
                bottom_adapter.setShowCheck(false);
                bottom_adapter.notifyDataSetChanged();
                text_pt_cancle.setVisibility(View.GONE);
                text_pt_allsel.setVisibility(View.GONE);
                text_pt_add.setText("添加");
                add_or_del_pt=false;//执行--添加
                for(int i=0;i<string_bottom.size();i++){
                    GeneralDay day1=string_bottom.get(i);
                    day1.setShow(false);
                }
                sta_sel_pt.clear();//清空
                break;
        }
    }

    /*****************************法定****************************************************/
    //法定节假日的长按
    @Override
    public void longClick(int position) {
        if(string_top.size()==1){
            if(!string_top.get(0).getName().equals("无")){
                StatutoryDay day=string_top.get(position);
                day.setShow(true);
                top_adapter.setShowCheck(true);
                top_adapter.notifyDataSetChanged();
                text_fd_allsel.setVisibility(View.VISIBLE);
                text_fd_cancle.setVisibility(View.VISIBLE);
                text_fd_add.setText("删除");
                add_or_del=true;//执行--删除
                sta_sel.add(string_top.get(position));//添加
                all_sel=true;
                text_fd_allsel.setText("全不选");
            }
        }else{
            StatutoryDay day=string_top.get(position);
            day.setShow(true);
            top_adapter.setShowCheck(true);
            top_adapter.notifyDataSetChanged();
            text_fd_allsel.setVisibility(View.VISIBLE);
            text_fd_allsel.setText("全选");
            text_fd_cancle.setVisibility(View.VISIBLE);
            text_fd_add.setText("删除");
            add_or_del=true;//执行--删除
            sta_sel.add(string_top.get(position));//添加
        }
    }
    //法定节假日的点击
    @Override
    public void itemClick(int position) {
        StatutoryDay day=string_top.get(position);
        if(day.isShow()){
            sta_sel.remove(string_top.get(position));
            day.setShow(false);
        }else {
            sta_sel.add(string_top.get(position));
            day.setShow(true);
        }
        top_adapter.notifyDataSetChanged();
        for(int i=0;i<string_top.size();i++){
            StatutoryDay day1=string_top.get(i);
            if(!day1.isShow()){
                cust_status=false;
            }
        }
        if(cust_status){
            text_fd_allsel.setText("全不选");
            all_sel=true;
        }
        for(int i=0;i<string_top.size();i++){
            StatutoryDay day1=string_top.get(i);
            if(!day1.isShow()){
                text_fd_allsel.setText("全选");
                all_sel=false;
            }
        }
        cust_status=true;//还原默认值
    }
    /*****************************普通****************************************************/
    //法定节假日的长按
    @Override
    public void generlongClick(int position) {
        if(string_bottom.size()==1){
            if(!string_bottom.get(0).getName().equals("无")){
                GeneralDay day=string_bottom.get(position);
                day.setShow(true);
                bottom_adapter.setShowCheck(true);
                bottom_adapter.notifyDataSetChanged();
                text_pt_allsel.setVisibility(View.VISIBLE);
                text_pt_cancle.setVisibility(View.VISIBLE);
                text_pt_add.setText("删除");
                add_or_del_pt=true;//执行--删除
                sta_sel_pt.add(string_bottom.get(position));//添加
                all_sel_pt=true;
                text_pt_allsel.setText("全不选");
            }
        }else{
            GeneralDay day=string_bottom.get(position);
            day.setShow(true);
            bottom_adapter.setShowCheck(true);
            bottom_adapter.notifyDataSetChanged();
            text_pt_allsel.setVisibility(View.VISIBLE);
            text_pt_allsel.setText("全选");
            text_pt_cancle.setVisibility(View.VISIBLE);
            text_pt_add.setText("删除");
            add_or_del_pt=true;//执行--删除
            sta_sel_pt.add(string_bottom.get(position));//添加
        }
    }
    //法定节假日的点击
    @Override
    public void generItemClick(int position) {
        GeneralDay day=string_bottom.get(position);
        if(day.isShow()){
            sta_sel_pt.remove(string_bottom.get(position));
            day.setShow(false);
        }else {
            sta_sel_pt.add(string_bottom.get(position));
            day.setShow(true);
        }
        bottom_adapter.notifyDataSetChanged();
        for(int i=0;i<string_bottom.size();i++){
            GeneralDay day1=string_bottom.get(i);
            if(!day1.isShow()){
                cust_status_pt=false;
            }
        }
        if(cust_status_pt){
            text_pt_allsel.setText("全不选");
            all_sel_pt=true;
        }
        for(int i=0;i<string_bottom.size();i++){
            GeneralDay day1=string_bottom.get(i);
            if(!day1.isShow()){
                text_pt_allsel.setText("全选");
                all_sel_pt=false;
            }
        }
        cust_status_pt=true;//还原默认值
    }






    public void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(PropertActivity.this).create();
        dialog.show();
        cn.aigestudio.datepicker.views.DatePicker picker = new cn.aigestudio.datepicker.views.DatePicker(PropertActivity.this);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM");
        String temp = format.format(date);
        String[] s = temp.split("-");
        picker.setDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new cn.aigestudio.datepicker.views.DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if(!only_status){//法定节假日
                    StatutoryDay  day=new StatutoryDay();
                    day.setName(date);
                    day.setShow(false);
                    initTopData(day);
                    top_adapter.notifyDataSetChanged();
                }else{//普通节假日
                    GeneralDay  day=new GeneralDay();
                    day.setName(date);
                    day.setShow(false);
                    initBottomData(day);
                    bottom_adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }


    /**
     * 删除群组--设备提示
     * 1  群组   2设备
     * @param type
     */
    public AlertDialog Notice_dialog;
    public void NoticeCate(){
        Notice_dialog=new AlertDialog.Builder(this).create();
        Notice_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view_cd= LayoutInflater.from(this).inflate(R.layout.dialog_exits_notice,null);
        TextView text_message=(TextView)view_cd.findViewById(R.id.text_message);
        TextView text_sure=(TextView)view_cd.findViewById(R.id.text_sure);
        TextView text_cancle=(TextView)view_cd.findViewById(R.id.text_cancle);
        text_message.setText("设备属性尚未提交，是否退出？");
        text_message.setTextColor(getResources().getColor(R.color.colorAccent));
        text_cancle.setOnClickListener(new View.OnClickListener() {//不退出
            @Override
            public void onClick(View v) {
                Notice_dialog.dismiss();
            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {//退出
            @Override
            public void onClick(View v) {
                Notice_dialog.dismiss();
                finish();
            }
        });
        //设置date布局
        Notice_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Notice_dialog.setView(view_cd);
        Notice_dialog.show();
        //设置大小
        WindowManager.LayoutParams layoutParams = Notice_dialog.getWindow().getAttributes();
        layoutParams.width = (WindowManager.LayoutParams.WRAP_CONTENT);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Notice_dialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onBackPressed() {
        if(string_top.size()==1){
            if(string_top.get(0).getName().equals("无")){
                if(string_bottom.size()==1){
                    if(string_bottom.get(0).getName().equals("无")){
                        finish();
                    }else{
                        NoticeCate();
                    }
                }else{
                    NoticeCate();
                }
            }else{
                NoticeCate();
            }
        }else{
            NoticeCate();
        }
    }

    /**
     * 提交数据
     */
    public void UpData(){

    }
}
