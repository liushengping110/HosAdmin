package wizrole.hosadmin.ui;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wizrole.hosadmin.R;
import wizrole.hosadmin.base.BaseActivity;

/**
 * Created by 何人执笔？ on 2018/4/25.
 * liushengping
 * 动画
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.img_splash)ImageView img_splash;
    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000 );
    }

    @Override
    protected void setListener() {
    }
}
