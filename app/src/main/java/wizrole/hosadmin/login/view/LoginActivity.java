package wizrole.hosadmin.login.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.base.BaseActivity;
import wizrole.hosadmin.login.model.LoginBack;
import wizrole.hosadmin.login.preserent.LoginInterface;
import wizrole.hosadmin.login.preserent.LoginPreserent;
import wizrole.hosadmin.ui.MainActivity;
import wizrole.hosadmin.util.dialog.LoadingDailog;
import wizrole.hosadmin.util.image.ImageLoading;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public class LoginActivity extends BaseActivity implements LoginInterface,View.OnClickListener {
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_forget_password)
    TextView text_forget_password;
    @BindView(R.id.lin_back)
    LinearLayout lin_back;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_name)
    EditText edit_name;
    public LoginPreserent preserent = new LoginPreserent(this);
    public Dialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        text_title.setText("用户登录");

        Drawable drawable_tel = getResources().getDrawable(R.drawable.login_tel_left);
        drawable_tel.setBounds(0, 0, 70, 70);
        edit_name.setCompoundDrawables(drawable_tel, null, null, null);
        Drawable drawable_pass = getResources().getDrawable(R.drawable.login_pass_left);
        drawable_pass.setBounds(0, 0, 70, 70);
        edit_password.setCompoundDrawables(drawable_pass, null, null, null);
    }

    @Override
    protected void setListener() {
        btn_login.setOnClickListener(this);
        lin_back.setOnClickListener(this);
        text_forget_password.setOnClickListener(this);
    }

    public boolean check() {
        if (edit_name.getText().length() == 0) {
            ToastShow("管理员账号不能为空");
            return false;
        } else if (edit_password.getText().length() == 0) {
            ToastShow("管理员密码不能为空");
            return false;
        } else {
            return true;
        }
    }

    public LoginBack loginback;
    public String err_msg;

    @Override
    public void LoginSucc(LoginBack loginBack) {
        loginback = loginBack;
        if (loginback.getResultCode().equals("0")) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void LoginFail(String msg) {
        err_msg = msg;
        handler.sendEmptyMessage(1);
    }

    public Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                if (check()) {
//                    dialog = LoadingDailog.createLoadingDialog(LoginActivity.this, "登录中");
//                    preserent.Login(edit_name.getText().toString(), edit_password.getText().toString());
//                }
                if(check()){
                    dialog=LoadingDailog.createLoadingDialog(LoginActivity.this,"登录中");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginSucc();
                        }
                    }, 2000);
                }
                break;
            case R.id.lin_back://返回
                setResult(3000);
                finish();
                break;
            case R.id.text_forget_password://忘记密码

                break;
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LoadingDailog.closeDialog(dialog);
                    ToastShow("登录成功");
                    loginSucc();
                    break;
                case 1:
                    LoadingDailog.closeDialog(dialog);
                    if (err_msg.equals("")) {
                        ToastShow("登录失败，请检查网络");
                    } else {
                        ToastShow(err_msg);
                    }
                    break;
            }
        }
    };

    public void loginSucc(){
        setResult(2000);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(3000);
        finish();
    }
}
