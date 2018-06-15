package wizrole.hosadmin.maintenance.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.DeviceTypeListAdapter;
import wizrole.hosadmin.adapter.SpinnerDeviceGroupAdapter;
import wizrole.hosadmin.adapter.SpinnerDeviceTypeAdapter;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.maintenance.model.devicelist.DeviceList;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.dialog.SelectDialog;
import wizrole.hosadmin.util.image.GlideImageLoader;
import wizrole.hosadmin.util.image.ImageLoading;
import wizrole.hosadmin.util.pop.PopDissListener;
import wizrole.hosadmin.util.pop.PopupWindowPotting;
import wizrole.hosadmin.view.PopupMaxHeightListView;

/**
 * Created by liushengping on 2018/3/8.
 * 何人执笔？
 * 添加设备
 */

public class AddDeviceActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.lin_back)LinearLayout lin_back;
    @BindView(R.id.text_title)TextView text_title;
    @BindView(R.id.text_right)TextView text_right;
    @BindView(R.id.text_add_status)TextView text_add_status;
    @BindView(R.id.text_add_del)TextView text_add_del;
    @BindView(R.id.img_add_deviceimg)ImageView img_add_deviceimg;
    @BindView(R.id.edit_add_name)EditText edit_add_name;
    @BindView(R.id.edit_add_ip)EditText edit_add_ip;
    @BindView(R.id.edit_add_mac)EditText edit_add_mac;
    @BindView(R.id.tol_turn)ToggleButton tol_turn;
    @BindView(R.id.spinner_type)Spinner spinner_type;
    @BindView(R.id.spinnner_group)Spinner spinner_group;
    public DeviceList deviceList;
    public Dialog dialog;
    public int group;
    public int type;
    @Override
    protected int getLayout() {
        return R.layout.activity_adddevice;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_adddevice);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        dialog= LoadingDailog.createLoadingDialog(AddDeviceActivity.this,"加载中");
        deviceList=(DeviceList) getIntent().getSerializableExtra("device");
        setTypeList();
        setGroupList();
        setView();
    }

    public void setView(){
        if(deviceList!=null){//查-删-改
            text_title.setText("设备信息");
            ImageLoading.common(AddDeviceActivity.this,R.drawable.device_gh,img_add_deviceimg,R.drawable.device_gh);
            edit_add_ip.setText(deviceList.getDeviceIP());
            edit_add_name.setText(deviceList.getDeviceNo());
            edit_add_mac.setText(deviceList.getDeviceMac());
            //类型
            for (int i=0;i<strings.size();i++){
                if(strings.get(i).equals(deviceList.getDeviceType())){
                    type=i;
                }
            }
            spinner_type.setSelection(type);
            //群组
            for (int i=0;i<strings_group.size();i++){
                if(strings_group.get(i).equals(deviceList.getDeviceGroup())){
                    group=i;
                }
            }
            spinner_group.setSelection(group);
            if(deviceList.getDeviceStatus().equals("1")){
                tol_turn.setChecked(true);
                text_add_status.setText("已启用");
                text_add_status.setTextColor(getResources().getColor(R.color.bule));
            }else{
                tol_turn.setChecked(false);
                text_add_status.setTextColor(getResources().getColor(R.color.red));
                text_add_status.setText("未启用");
            }
        }else{//增
            text_title.setText("添加设备");
            tol_turn.setChecked(true);
            text_add_status.setText("已启用");
            text_add_status.setTextColor(getResources().getColor(R.color.bule));
            text_add_del.setVisibility(View.GONE);
        }
        text_right.setText("提交");
        LoadingDailog.closeDialog(dialog);
    }

    //设备类型
    public List<String> strings=new ArrayList<>();
    public void setTypeList(){
        for (int i=0;i<20;i++){
            if(i%2==0){
                strings.add("挂号缴费机"+i);
            }else{
                strings.add("检验报告打印机"+i);
            }
        }
        SpinnerDeviceTypeAdapter typeListAdapter=new SpinnerDeviceTypeAdapter(AddDeviceActivity.this,strings);
        spinner_type.setAdapter(typeListAdapter);
    }
    //设备群组
    public List<String> strings_group=new ArrayList<>();
    public void setGroupList(){
        for (int i=0;i<5;i++){
            strings_group.add("建行自助机"+i);
        }
        SpinnerDeviceGroupAdapter groupListAdapter=new SpinnerDeviceGroupAdapter(AddDeviceActivity.this,strings_group);
        spinner_group.setAdapter(groupListAdapter);
    }



    @Override
    protected void setListener() {
        lin_back.setOnClickListener(this);
        img_add_deviceimg.setOnClickListener(this);
        /**开关监听*/
        tol_turn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isClick) {
                tol_turn.setChecked(isClick);
                if (isClick){
                    text_add_status.setTextColor(getResources().getColor(R.color.bule));
                    text_add_status.setText("已启用");
                }else{
                    text_add_status.setTextColor(getResources().getColor(R.color.red));
                    text_add_status.setText("未启用");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_back://返回
                finish();
                break;
            case R.id.img_add_deviceimg://设备图片
                if(imagePicker==null){
                    initImagePicker();
                }
                showSel();
                break;
            case R.id.text_right://提交
                break;
        }
    }

    private ArrayList<ImageItem> images = null;
    public String deviceimg="";//设备图片地址
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//头像修改
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    ImageLoading.common(AddDeviceActivity.this,images.get(0).path,img_add_deviceimg);
                    deviceimg=images.get(0).path;
                }
            }
        }
    }


    public static final int REQUEST_CODE_SELECT = 100;
    public void showSel(){
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        Intent intent = new Intent(AddDeviceActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        break;
                    case 1:
                        //打开选择
                        Intent intent1 = new Intent(AddDeviceActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent1, REQUEST_CODE_SELECT);
                        break;
                }
            }
        }, names);
    }

    /**
     * 显示选择dialog
     * @param listener
     * @param names
     * @return
     */
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(AddDeviceActivity.this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!AddDeviceActivity.this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
    public ImagePicker imagePicker;
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);//设置单选，fasle单选
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //不显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
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
