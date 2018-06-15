package wizrole.hosadmin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import wizrole.hosadmin.R;

/**
 * Created by Administrator on 2017/7/9.
 */

public class MainSlidingMenu extends HorizontalScrollView {
    private int mMenuWidth;
    private View contentView;
    private View menuView;
    private View mShadowView;
    //手势滑动处理
    private GestureDetector mGestureDetector;
    //菜单是否打开
    public boolean mMenuIsOpen = false;
    //是否拦截
    private boolean mIsIntercept = false;

    public MainSlidingMenu(Context context) {
        this(context, null);
    }

    public MainSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MainSlidingMenu);
        float rightMargin = array.getDimension(R.styleable.MainSlidingMenu_menuRightMargin, dip2px(50));
        //计算菜单的宽度
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        array.recycle();

        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //快速滑动  只要快速滑动就会有回调  快速往左边滑动的时候是一个负数，往右边滑动是一个正数
//            Log.e("TAG", "velocityX--->" + velocityX + "velocityY--->" + velocityY + "---" + (velocityX - velocityY));
            if (mMenuIsOpen) {
                //打开往右边快速滑动就去切换(关闭)
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                //关闭的时候往左边快速滑动切换(打开)
                if (velocityX > 200) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    /**
     * dip转成px
     *
     * @param dip
     * @return
     */
    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //布局解析完毕会回调  也就是xml文件布局解析完毕  在onCreate方法中调用
        //指定宽高
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childCount = container.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能放置两个子view!");
        }

        //菜单页的宽度是屏幕的宽度 - 右边的一小部分距离(自定义属性)
        menuView = container.getChildAt(0);
        ViewGroup.LayoutParams menuParams = menuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        menuView.setLayoutParams(menuParams);
        //内容页的宽度是屏幕的宽度
        contentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        //移除之前的布局
        container.removeView(contentView);
        //然后在外面套一层阴影
        RelativeLayout contentContainer = new RelativeLayout(getContext());
        contentContainer.addView(contentView);
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#55000000"));
        contentContainer.addView(mShadowView);
        //最后再不容器放回原来的位置
        contentParams.width = getScreenWidth(getContext());
        contentContainer.setLayoutParams(contentParams);

        container.addView(contentContainer);
        //设置为透明
        mShadowView.setAlpha(0.0f);
    }

    //处理右边的缩放，左边的缩放和透明度，需要不断的获取当前滚动的位置
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        //计算一个梯度值  1f - 0f
        float scale = 1f * l / mMenuWidth;
        //控制阴影  0--1
        float alphaScale = 1 - scale;
        mShadowView.setAlpha(alphaScale);
//        //右边的缩放最小  0.7f  最大是1f  默认是以中心点缩放
//        float rightScale = 0.7f + 0.3f * scale;
//
//        //设置缩放的中心点位置
//        ViewCompat.setPivotX(contentView, 0);
//        ViewCompat.setPivotY(contentView, contentView.getMeasuredHeight() / 2);
//        //设置右边的缩放
//        ViewCompat.setScaleX(contentView, rightScale);
//        ViewCompat.setScaleY(contentView, rightScale);
//
//        //设置菜单缩放和透明度
//        //透明度是由半透明到全部透明  0.7 - 1.0f
//        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
//        ViewCompat.setAlpha(menuView, leftAlpha);
//        //缩放  0.7f - 1.0f
//        float leftScale = 0.7f + (1 - scale) * 0.3f;
//        ViewCompat.setScaleX(menuView, leftScale);
//        ViewCompat.setScaleY(menuView, leftScale);
        //设置文字平移
        ViewCompat.setTranslationX(menuView, l * 0.6f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始化进入的时候是关闭的，移动一段距离就可以了  在onResume之后调用
        scrollTo(mMenuWidth, 0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsIntercept = false;
        //当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件，
        if (mMenuIsOpen) {
            float currentX = ev.getX();
            if (currentX > mMenuWidth) {
                //关闭菜单
                closeMenu();
                //子view不需要相应任何的事件  拦截子view的事件
                mIsIntercept = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果有拦截就不执行自己的onTouchEvent
        if (mIsIntercept) {
            return true;
        }
        //快速滑动触发了下面的就不要执行了
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //  只需要处理手指抬起  根据当前滚动的距离来判断
            int currentScrollX = getScrollX();
            if (currentScrollX > mMenuWidth / 2) {
                //关闭
                closeMenu();
            }else {//打开
                openMenu();
            }
            //确保super.onTouchEvent(ev)不执行
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 关闭菜单  滚动到mMenuWidth的位置
     */
    public void closeMenu() {
        //带有动画效果
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
        if(menuClose!=null){
            menuClose.colse(false);
        }
    }

    /**
     * 打开菜单 滚动到0的位置
     */
    public void openMenu() {
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
        if(menuClose!=null){
            menuClose.colse(true);
        }
    }

    /**
     * 获取屏幕的宽度
     */
    private int getScreenWidth(Context context) {
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public interface MenuClose {
        void colse(boolean status);
    }

    public MenuClose menuClose;

    public void setMenuClose(MenuClose menuClose) {
        this.menuClose = menuClose;
    }
}
