package wizrole.hosadmin.maintenance.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.DeviceMonDetailAdapter;
import wizrole.hosadmin.adapter.DeviceMonitiorAdapter;
import wizrole.hosadmin.adapter.DeviceTypeListAdapter;
import wizrole.hosadmin.base.MyApplication;
import wizrole.hosadmin.maintenance.model.monitior.MonitiorDetail;
import wizrole.hosadmin.maintenance.model.monitior.MonitiorList;
import wizrole.hosadmin.util.pop.PopDissListener;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.CirclView;
import wizrole.hosadmin.view.PopupMaxHeightListView;

/**
 * Created by liushengping on 2018/3/13.
 * 何人执笔？
 */

public class Fragment_Device extends Fragment implements PopDissListener,DeviceMonitiorAdapter.MonViewItemOnClick{
    //控件是否已经初始化
    private boolean isCreateView = false;
    //是否已经加载过数据
    private boolean isLoadData = false;
    public View view,view_over;
    public RelativeLayout rel_gz_type;
    public ListView list_gz_view;
    public CirclView circlView;
    public LinearLayout lin_err;
    public LinearLayout lin_content;
    public TextView text_err_agagin;
    public TextView text_gz_type;
    public ImageView img_gz_type,img_err;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_device,null);
            isCreateView = true;
            initUI();
            initTypePop();
            setListener();
        }
        return view;
    }
    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            lazyLoad();
        }
    }
    private void lazyLoad() {
        //如果没有加载过就加载，否则就不再加载了
        if(!isLoadData){
            //加载数据操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setData();
                    setTypeList();
                }
            }, 2000);
            isLoadData=true;
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint())
            lazyLoad();
    }


    public void initUI(){
        rel_gz_type=(RelativeLayout)view.findViewById(R.id.rel_gz_type);
        list_gz_view=(ListView) view.findViewById(R.id.list_gz_view);
        circlView=(CirclView) view.findViewById(R.id.circlView);
        lin_err=(LinearLayout) view.findViewById(R.id.lin_err);
        lin_content=(LinearLayout) view.findViewById(R.id.lin_content);
        text_err_agagin=(TextView) view.findViewById(R.id.text_err_agagin);
        text_gz_type=(TextView) view.findViewById(R.id.text_gz_type);
        img_err=(ImageView) view.findViewById(R.id.img_err);
        img_gz_type=(ImageView) view.findViewById(R.id.img_gz_type);
        view_over=(View) view.findViewById(R.id.view_over);
    }

    public void setListener(){
        rel_gz_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTypePop();
                type_pop.Show(rel_gz_type);
                rotateStart(img_gz_type);
                view_over.setVisibility(View.VISIBLE);
            }
        });
    }

    public List<MonitiorList> monitiorListList=new ArrayList<>();
    public List<MonitiorDetail> monitiorDetails=new ArrayList<>();
    public void setData(){
        for(int i=0;i<10;i++){
            MonitiorDetail monitiorDetail=new MonitiorDetail();
            switch (i){
                case 0:
                    monitiorDetail.setPartName("凭条打印机");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
                case 1:
                    monitiorDetail.setPartName("发票打印机");
                    monitiorDetail.setPartStatusContent("无配置文件或配置文件错误");
                    break;
                case 2:
                    monitiorDetail.setPartName("密码键盘");
                    monitiorDetail.setPartStatusContent("端口被占用或者无此端口");
                    break;
                case 3:
                    monitiorDetail.setPartName("身份证读卡器");
                    monitiorDetail.setPartStatusContent("无配置文件或配置文件错误");
                    break;
                case 4:
                    monitiorDetail.setPartName("激光打印机");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
                case 5:
                    monitiorDetail.setPartName("发卡器");
                    monitiorDetail.setPartStatusContent("无配置文件或配置文件错误");
                    break;
                case 6:
                    monitiorDetail.setPartName("银行卡读卡器");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
                case 7:
                    monitiorDetail.setPartName("显示器触摸屏");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
                case 8:
                    monitiorDetail.setPartName("检验报告打印机");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
                case 9:
                    monitiorDetail.setPartName("LED显示器");
                    monitiorDetail.setPartStatusContent("正常");
                    break;
            }
            monitiorDetail.setPartRemark("无配置文件或配置文件错误");
            monitiorDetail.setPartStatusCode("1002");
            monitiorDetails.add(monitiorDetail);
            monitiorDetail=null;
        }

        for(int i=0;i<30;i++){
            MonitiorList monitiorList=new MonitiorList();
            monitiorList.setDeviceIP("192.168.1.10");
            monitiorList.setDeviceMac("AA-BB-CC-DD-EE");
            monitiorList.setDeviceNo("NMF100000");
            monitiorList.setDeviceType("挂号缴费机");
            monitiorList.setDeviceGroup("建行自助机");
            monitiorList.setMonitiorDetailList(monitiorDetails);
            monitiorListList.add(monitiorList);
            monitiorList=null;
        }
        circlView.setVisibility(View.INVISIBLE);
        lin_content.setVisibility(View.VISIBLE);
        DeviceMonitiorAdapter adapter=new DeviceMonitiorAdapter(MyApplication.getContextObject(),monitiorListList);
        list_gz_view.setAdapter(adapter);
        adapter.setGridViewItemOnClick(this);
    }


    /**
     * 设备类型弹窗列表
     */
    public List<String> strings=new ArrayList<>();
    public void setTypeList(){
        for (int i=0;i<20;i++){
            if(i%2==0){
                strings.add("挂号缴费机"+i);
            }else{
                strings.add("检验报告打印机"+i);
            }
        }
        DeviceTypeListAdapter typeListAdapter=new DeviceTypeListAdapter(MyApplication.getContextObject(),strings,R.layout.pop_list_montype_item);
        list_type.setAdapter(typeListAdapter);
    }


    /**
     * 设备类型
     */
    public PopupWindowPotting type_pop;
    public PopupMaxHeightListView list_type;
    public void initTypePop(){
        if(type_pop==null){
                type_pop=new PopupWindowPotting(this,2) {
                    @Override
                    protected int getLayout() {
                        return R.layout.pop_devicelist_typegroup;
                    }

                    @Override
                    protected void initUI() {
                        list_type=$(R.id.pop_devices_list);
                        list_type.setListViewHeight((int)(700));
                    }

                    @Override
                    protected void setListener() {
                        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                text_gz_type.setTextColor(getResources().getColor(R.color.bule));
                                img_gz_type.setBackgroundResource(R.drawable.turn_bottom_sel);
                                text_gz_type.setText(strings.get(position));
                                type_pop.Hide();
                            }
                        });
                    }
                };

            }
        }

    /**
     * 故障详细点击监听
     * @param monitiorDetail
     */
    @Override
    public void gridItemOnClick(MonitiorDetail monitiorDetail) {
        if(view_over.getVisibility()==View.VISIBLE){
            view_over.setVisibility(View.INVISIBLE);
            type_pop.Hide();
        }else{
            initDialog(monitiorDetail);
        }
    }

    public AlertDialog ID_dialog;
    public void initDialog(MonitiorDetail monitiorDetail){
        ID_dialog=new AlertDialog.Builder(getActivity()).create();
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_monitior_item,null);
        ID_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //共同使用以下控件
        TextView text_item_type = (TextView) view.findViewById(R.id.text_item_type);
        TextView text_monitior_bz = (TextView) view.findViewById(R.id.text_monitior_bz);
        TextView text_monitior_ms = (TextView) view.findViewById(R.id.text_monitior_ms);
        TextView text_monitior_no = (TextView) view.findViewById(R.id.text_monitior_no);
        TextView dialog_sure = (TextView) view.findViewById(R.id.dialog_sure);
        text_item_type.setText(monitiorDetail.getPartName());
        text_monitior_bz.setText(monitiorDetail.getPartRemark());
        text_monitior_ms.setText(monitiorDetail.getPartStatusContent());
        text_monitior_no.setText(monitiorDetail.getPartStatusCode());
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_dialog.dismiss();
            }
        });
        //设置date布局
        ID_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ID_dialog.setView(view);
        ID_dialog.show();
        //设置大小
        WindowManager.LayoutParams layoutParams = ID_dialog.getWindow().getAttributes();
        layoutParams.width = (WindowManager.LayoutParams.WRAP_CONTENT);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ID_dialog.getWindow().setAttributes(layoutParams);
    }


    @Override
    public void onDiss() {
        rotateEnd(img_gz_type);
        view_over.setVisibility(View.INVISIBLE);
    }

    /**
     * 弹窗上的三角形翻转
     */
    public void rotateStart(ImageView imageView) {
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    /**
     * 弹窗上的三角形翻转
     */
    public void rotateEnd(ImageView imageView) {
        RotateAnimation animation = new RotateAnimation(180, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }
}
