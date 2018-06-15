package wizrole.hosadmin.adapter.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;


/**
 * Created by Administrator on 2017/1/11.
 */

public interface Holder {

    <T extends View> T getView(int rid);

    Holder setText(String result, int rid);

    Holder setText(String result, int rid, int drawable,int textColor);

    Holder setResources(int did, int rid);

    Holder setBitmap(Bitmap bitmap, int rid);

    Holder setOnClickListener(View.OnClickListener onClickListener, int rid);

    Holder setImageView(String url, int rid, int drawable);

    Holder setImageView(int d_rid, int rid, int drawable);

    Holder setCommImageView(int d_rid, int rid, int drawable);

    View getHoldView();
    /**隐藏控件*/
    Holder setVilGone(int rid);
    //显示
    Holder setVil(int rid);

}