package wizrole.hosadmin.maintenance.view;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.GroupListAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.maintenance.model.grouplist.ChildBean;
import wizrole.hosadmin.maintenance.model.grouplist.GroupBean;

/**
 * Created by liushengping on 2018/3/8.
 * 何人执笔？
 */

public class GroupListActivity extends BaseActivity implements View.OnClickListener,GroupListAdapter.GroupOnClick
,GroupListAdapter.GroupLongOnClick,GroupListAdapter.ChildOnClick,GroupListAdapter.ChildLongClick{

    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.expanded_list)ExpandableListView expanded_list;

    private GroupListAdapter adapter;
    private List<GroupBean> list;


    @Override
    protected int getLayout() {
        return R.layout.activity_grouplist;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("群组列表");
        text_right.setText("新建群组");
        //初始化数据
        initListData();
        adapter = new GroupListAdapter(list, this,expanded_list);
        expanded_list.setAdapter(adapter);
        expanded_list.setGroupIndicator(null);
        adapter.setChildOnClick(this);
        adapter.setGroupLongOnClick(this);
        adapter.setGroupOnClick(this);
        adapter.setChildLongClick(this);
    }


    public void initListData() {
        list = new ArrayList<GroupBean>();
        {
            List<ChildBean> list1 = new ArrayList<ChildBean>();
            ChildBean cb1 = new ChildBean("挂号缴费机1", "123");
            ChildBean cb2 = new ChildBean("挂号缴费机2", "456");
            ChildBean cb3 = new ChildBean("挂号缴费机3", "789");
            ChildBean cb4 = new ChildBean("挂号缴费机4", "000");
            ChildBean cb5 = new ChildBean("挂号缴费机5", "000");
            ChildBean cb6 = new ChildBean("挂号缴费机6", "000");
            ChildBean cb7=  new ChildBean("挂号缴费机7", "000");
            list1.add(cb1);
            list1.add(cb2);
            list1.add(cb3);
            list1.add(cb4);
            list1.add(cb5);
            list1.add(cb6);
            list1.add(cb7);
            GroupBean gb1 = new GroupBean("建行自助机", list1);
            list.add(gb1);
        }
        {
            List<ChildBean> list1 = new ArrayList<ChildBean>();
            ChildBean cb1 = new ChildBean("检验报告打印机1", "123");
            ChildBean cb2 = new ChildBean("检验报告打印机2", "456");
            ChildBean cb3 = new ChildBean("检验报告打印机3", "789");
            ChildBean cb4 = new ChildBean("检验报告打印机4", "000");
            ChildBean cb5 = new ChildBean("检验报告打印机5", "1111");
            ChildBean cb6 = new ChildBean("检验报告打印机6", "222");
            ChildBean cb7 = new ChildBean("检验报告打印机7", "3333333");
            list1.add(cb1);
            list1.add(cb2);
            list1.add(cb3);
            list1.add(cb4);
            list1.add(cb5);
            list1.add(cb6);
            list1.add(cb7);
            GroupBean gb1 = new GroupBean("农行自助机", list1);
            list.add(gb1);
        }
        {
            List<ChildBean> list1 = new ArrayList<ChildBean>();
            ChildBean cb1 = new ChildBean("建卡充值一体机1", "123");
            ChildBean cb2 = new ChildBean("建卡充值一体机2", "456");
            ChildBean cb4 = new ChildBean("建卡充值一体机3", "000");
            ChildBean cb5 = new ChildBean("建卡充值一体机4", "000");
            ChildBean cb6 = new ChildBean("建卡充值一体机5", "000");
            ChildBean cb7 = new ChildBean("建卡充值一体机6", "000");
            list1.add(cb1);
            list1.add(cb2);
            list1.add(cb4);
            list1.add(cb5);
            list1.add(cb6);
            list1.add(cb7);
            GroupBean gb1 = new GroupBean("中行建卡充值一体机", list1);
            list.add(gb1);
        }
        {
            List<ChildBean> list1 = new ArrayList<ChildBean>();
            ChildBean cb1 = new ChildBean("工行自助处方打印机1", "123");
            ChildBean cb2 = new ChildBean("工行自助处方打印机2", "456");
            ChildBean cb3 = new ChildBean("工行自助处方打印机3", "789");
            ChildBean cb4 = new ChildBean("工行自助处方打印机4", "000");
            ChildBean cb5 = new ChildBean("工行自助处方打印机5", "000");
            list1.add(cb1);
            list1.add(cb2);
            list1.add(cb3);
            list1.add(cb4);
            list1.add(cb5);
            GroupBean gb1 = new GroupBean("工行自助处方打印机", list1);
            list.add(gb1);
        }
    }

    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        text_right.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back:
                finish();
                break;
            case R.id.text_right://添加群组
                initDialog(1);
                break;
        }
    }

    /**
     * group点击
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
     * group长按
     * @param groupPosition
     */
    @Override
    public void groupLongClick(int groupPosition) {
        ChangeOrDel(1);
    }

    /**
     * child的点击
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void childOnClick(int groupPosition, int childPosition) {
        ChangeOrDel(2);
    }

    /**
     * child的长按监听
     * @param groupPosition
     * @param childPosition
     * 长按  ----批量操作   删除设备  移动到其他群组
     */
    @Override
    public void childLongClick(int groupPosition, int childPosition) {

    }


    /**
     * group和child的长按监听 dialog
     */
    public AlertDialog group_dialog;
    public void ChangeOrDel(final int type){
        group_dialog=new AlertDialog.Builder(this).create();
        group_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view_cd= LayoutInflater.from(this).inflate(R.layout.dialog_group_longclick,null);
        TextView text_change_name=(TextView)view_cd.findViewById(R.id.text_change_name);
        TextView text_kfgn=(TextView)view_cd.findViewById(R.id.text_kfgn);
        TextView text_del=(TextView)view_cd.findViewById(R.id.text_del);
        TextView text_adddevice=(TextView)view_cd.findViewById(R.id.text_adddevice);
        View view_three=(View)view_cd.findViewById(R.id.view_three);
        View view_four=(View)view_cd.findViewById(R.id.view_four);
        switch (type){
            case 1://group的长按
                text_change_name.setText("修改群组名称");
                text_del.setText("删除该群组");
                text_adddevice.setText("添加设备到群组");
                text_kfgn.setText("修改群组开放功能");
                text_change_name.setTextColor(getResources().getColor(R.color.black));
                text_del.setTextColor(getResources().getColor(R.color.black));
                text_adddevice.setTextColor(getResources().getColor(R.color.black));
                text_kfgn.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2://child
                text_change_name.setText("从该群组中删除该设备");
                text_del.setText("移动该设备到其他群组");
                text_change_name.setTextColor(getResources().getColor(R.color.black));
                text_del.setTextColor(getResources().getColor(R.color.black));

                text_kfgn.setVisibility(View.GONE);
                text_adddevice.setVisibility(View.GONE);
                view_four.setVisibility(View.GONE);
                view_three.setVisibility(View.GONE);
                break;
        }
        text_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_dialog.dismiss();
                switch (type){
                    case 1://修改群组名称
                        initDialog(2);
                        break;
                    case 2://删除该设备
                        NoticeCate(2);
                        break;
                }
            }
        });
        text_del.setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {
                group_dialog.dismiss();
                switch (type){
                    case 1://删除该群组
                        NoticeCate(1);
                        break;
                    case 2://移动到其他群组
                        break;
                }
            }
        });
        text_adddevice.setOnClickListener(new View.OnClickListener() {//添加设备到群组
            @Override
            public void onClick(View v) {

            }
        });
        text_kfgn.setOnClickListener(new View.OnClickListener() {//修改群组开放功能
            @Override
            public void onClick(View v) {

            }
        });
        //设置date布局
        group_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        group_dialog.setView(view_cd);
        group_dialog.show();
        //设置大小
        WindowManager.LayoutParams layoutParams = group_dialog.getWindow().getAttributes();
        layoutParams.width = (WindowManager.LayoutParams.MATCH_PARENT-100);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        group_dialog.getWindow().setAttributes(layoutParams);
    }



    /**
     * 添加群组dialog
     * @param dialo_type---1=添加    2=修改
     */
    public AlertDialog edit_dialog;
    public void initDialog(final int dialo_type){
        edit_dialog=new AlertDialog.Builder(this).create();
        edit_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_add_change_groupname,null);
        final EditText edit_add_type=(EditText)view.findViewById(R.id.edit_add_type);
        TextView text_add_title=(TextView)view.findViewById(R.id.text_add_title);//温馨提示
        TextView dialog_cancle=(TextView)view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure=(TextView)view.findViewById(R.id.dialog_sure);
        switch (dialo_type){
            case 1://添加群组
                text_add_title.setText("添加群组：");
                break;
            case 2://修改群组名称
                text_add_title.setText("修改群组：");
                break;
        }
        dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_dialog.dismiss();
            }
        });
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_add_type.getText().toString().length()==0){
                    ToastShow("请输入群组名称");
                }else{
                    edit_dialog.dismiss();
                }
            }
        });//确定注销监听
        //设置date布局
        edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        edit_dialog.setView(view);
        edit_dialog.show();
        //设置大小
        WindowManager.LayoutParams layoutParams = edit_dialog.getWindow().getAttributes();
        layoutParams.width = (WindowManager.LayoutParams.WRAP_CONTENT);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        edit_dialog.getWindow().setAttributes(layoutParams);
    }

    public AlertDialog Notice_dialog;

    /**
     * 删除群组--设备提示
     * 1  群组   2设备
     * @param type
     */
    public void NoticeCate(int type){
        Notice_dialog=new AlertDialog.Builder(this).create();
        Notice_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view_cd= LayoutInflater.from(this).inflate(R.layout.dialog_exits_notice,null);
        TextView text_message=(TextView)view_cd.findViewById(R.id.text_message);
        TextView text_sure=(TextView)view_cd.findViewById(R.id.text_sure);
        TextView text_cancle=(TextView)view_cd.findViewById(R.id.text_cancle);
        switch (type){
            case 1://群组
                text_message.setText("删除该群组，对应群组下的设备也会被删除，是否删除？");
                break;
            case 2://设备
                text_message.setText("删除该设备，是否删除？");
                break;
        }
        text_message.setTextColor(getResources().getColor(R.color.colorAccent));
        text_cancle.setOnClickListener(new View.OnClickListener() {//修改
            @Override
            public void onClick(View v) {
                Notice_dialog.dismiss();

            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {//删除
            @Override
            public void onClick(View v) {
                Notice_dialog.dismiss();
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

}
