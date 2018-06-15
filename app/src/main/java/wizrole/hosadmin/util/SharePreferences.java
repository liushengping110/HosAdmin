package wizrole.hosadmin.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public class SharePreferences {

    /**保存用户登陆状态*/
    public static void saveLoginState(Context context, int status){
        SharedPreferences sp=context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("success", status);
        editor.commit();
    }
    /**获取用户登陆状态*/
    public static int getLoginState(Context context){
        SharedPreferences sp=context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        return sp.getInt("success", 2);
    }
    /**
     * 清除用户登录状态
     * @param context
     */
    public static void clearLoginState(Context context){
        SharedPreferences sp = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
