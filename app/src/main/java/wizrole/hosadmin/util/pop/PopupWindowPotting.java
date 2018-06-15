package wizrole.hosadmin.util.pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import wizrole.hosadmin.R;
import wizrole.hosadmin.base.MyApplication;


/**
 * Created by Administrator on 2017/3/21.
 */

public abstract class PopupWindowPotting {

    private View view;
    private Activity activity;
    public Context context;
    private PopupWindow popupWindow;
    public int type;
    public PopDissListener pop_dissListener;


    public PopupWindowPotting(Activity activity, int type) {
        this.activity = activity;
        this.type=type;
        onCreate(0, 0);
    }

    public Fragment fragment;
    public PopupWindowPotting(Fragment fragment, int type) {
        this.fragment = fragment;
        this.type=type;
        pop_dissListener=(PopDissListener)fragment;
        onCreate(0, 0);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected void onCreate(int width, int height) {
        if(type==1){//Activity---设备列表下拉筛选
            if (view == null) view = LayoutInflater.from(activity).inflate(getLayout(), null);
            WindowManager manager=activity.getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            if (height == 0) height = WindowManager.LayoutParams.MATCH_PARENT;
            popupWindow = new PopupWindow(view, wid_final, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(false);
        }else if(type==2){//Fragment--设备故障（设备/部件）  下拉筛选
            if (view == null) view = LayoutInflater.from(MyApplication.getContextObject()).inflate(getLayout(), null);
            WindowManager manager=fragment.getActivity().getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            popupWindow = new PopupWindow(view, wid_final, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
        }else if(type==3){//设备列表--右侧滑pop
            if (view == null) view = LayoutInflater.from(activity).inflate(getLayout(), null);
            WindowManager manager=activity.getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            if (height == 0) height = WindowManager.LayoutParams.MATCH_PARENT;
            popupWindow = new PopupWindow(view, wid_final-200, height);
            popupWindow.setFocusable(true);
            popupWindow.setAnimationStyle(R.style.PopDeviceWindowAnimStyle);
        }else if(type==4){//添加设备--单选设备类型、单选设备群组
            if (view == null) view = LayoutInflater.from(activity).inflate(getLayout(), null);
            WindowManager manager=activity.getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            if (height == 0) height = WindowManager.LayoutParams.WRAP_CONTENT;
            popupWindow = new PopupWindow(view, wid_final-350, height);
            popupWindow.setFocusable(true);
        }else if(type==5){//页面退出提示
            if (view == null) view = LayoutInflater.from(activity).inflate(getLayout(), null);
            WindowManager manager=activity.getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            if (height == 0) height = WindowManager.LayoutParams.WRAP_CONTENT;
            popupWindow = new PopupWindow(view, wid_final-100, height);
            popupWindow.setFocusable(true);
        }else if(type==6){//右上角添加权限
            if (view == null) view = LayoutInflater.from(activity).inflate(getLayout(), null);
            WindowManager manager=activity.getWindowManager();
            int wid_final=manager.getDefaultDisplay().getWidth();
            if (height == 0) height = WindowManager.LayoutParams.WRAP_CONTENT;
            popupWindow = new PopupWindow(view, 350, height);
            popupWindow.setFocusable(true);
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(type==1){//设备列表--下拉筛选-

                }else if(type==2){//fragment
                    pop_dissListener.onDiss();
                }else if(type==3){//设备列表--右侧滑

                }else if(type==4){//设备添加--

                }else if(type==5){

                }else if(type==6){//右上角添加权限

                }
                Hide();
            }
        });
        initUI();
        setListener();
    }
    public boolean Showing=false;

    public void setShowing(boolean showing) {
        Showing = showing;
    }

    public boolean getShowing() {
        return Showing;
    }

    public void Show(View view) {
        if(type==1){//Activity---下拉筛选
            popupWindow.showAsDropDown(view, 0, 1);
        }else if(type==2){//fragment
            popupWindow.showAsDropDown(view, 0, 1);
        }else if(type==3){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 0.6f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.showAsDropDown(view, 0, 1);
        }else if(type==4){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 0.6f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.showAsDropDown(view, 0, 1);
        }else if(type==5){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 0.7f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0,0);
        }else if(type==6){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 0.7f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.showAsDropDown(view,0,0);
        }
        Showing=true;
    }

    public void Hide() {
        if(type==1){
            popupWindow.dismiss();
        }else if(type==2){
            popupWindow.dismiss();
        }else if(type==3){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 1f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.dismiss();
        }else if(type==4){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 1f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.dismiss();
        }else  if(type==5){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 1f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.dismiss();
        }else if(type==6){
            WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
            lp.alpha = 1f;
            (activity).getWindow().setAttributes(lp);
            popupWindow.dismiss();
        }
        Showing=false;
    }

    protected abstract int getLayout();

    protected abstract void initUI();

    protected abstract void setListener();

    private SparseArray<View> sparseArray = new SparseArray<>();

    protected <T extends View> T $(int rid) {
        if (sparseArray.get(rid) == null) {
            View viewgrounp = view.findViewById(rid);
            sparseArray.append(rid, viewgrounp);
            return (T) viewgrounp;
        } else {
            return (T) sparseArray.get(rid);
        }
    }

    private Intent intent;
    private Toast toast;

    protected void Skip(Class cla) {
        intent = new Intent(activity, cla);
        activity.startActivity(intent);
    }

    protected void ToastShow(String result) {
        if (toast == null) {
            toast = Toast.makeText(activity, result, Toast.LENGTH_SHORT);
        } else {
            toast.setText(result);
        }
        toast.show();
    }
}