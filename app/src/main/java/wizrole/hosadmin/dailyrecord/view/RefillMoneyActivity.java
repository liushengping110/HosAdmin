package wizrole.hosadmin.dailyrecord.view;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.cons.DPMode;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.RefillMoneyAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.dailyrecord.model.refillmoney.RefillMoneyList;
import wizrole.hosadmin.maintenance.model.generalhoilday.GeneralDay;
import wizrole.hosadmin.maintenance.model.statutoryhoilday.StatutoryDay;
import wizrole.hosadmin.maintenance.view.PropertActivity;

/**
 * Created by liushengping on 2018/3/14.
 * 何人执笔？
 * 充值日志
 */

public class RefillMoneyActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_refmoney_date)TextView text_refmoney_date;
    @BindView(R.id.text_refmoney_search)TextView text_refmoney_search;
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.list_remoney_view)ListView list_remoney_view;
    @BindView(R.id.edit_refmoney_no)EditText edit_refmoney_no;
    @Override
    protected int getLayout() {
        return R.layout.activity_refillmoney;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("充值日志");
        setData();
    }

    public List<RefillMoneyList> refillMoneyListList=new ArrayList<>();
    public void setData(){
        for (int i=0;i<30;i++){
            RefillMoneyList refillMoneyList=new RefillMoneyList();
            refillMoneyList.setRefillMoneyname("张三"+i);
            refillMoneyList.setRefillMoneyNumber("1000"+i);
            refillMoneyList.setRefillMoneyID("000888888"+i);
            refillMoneyListList.add(refillMoneyList);
            refillMoneyList=null;
        }
        RefillMoneyAdapter adapter=new RefillMoneyAdapter(RefillMoneyActivity.this,refillMoneyListList,R.layout.list_refillmoney_item);
        list_remoney_view.setAdapter(adapter);
    }
    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_refmoney_date.setOnClickListener(this);
        text_refmoney_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                finish();
                break;
            case R.id.text_refmoney_date://日期选择
                showDialog();
                break;
            case R.id.text_refmoney_search://搜索
                if(edit_refmoney_no.getText().toString().length()==0){
                    ToastShow("设备编号不能为空");
                }else if(text_refmoney_date.getText().toString().equals("选择日期")){
                    ToastShow("搜索时间尚未选择");
                }else{

                }
                break;
        }
    }


    public void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(RefillMoneyActivity.this).create();
        dialog.show();
        cn.aigestudio.datepicker.views.DatePicker picker = new cn.aigestudio.datepicker.views.DatePicker(RefillMoneyActivity.this);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM");
        String temp = format.format(date);
        String[] s = temp.split("-");
        picker.setDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new cn.aigestudio.datepicker.views.DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                text_refmoney_date.setText(date);
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}
