package wizrole.hosadmin.authority.model.getrolelist;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import wizrole.hosadmin.base.DataBack;
import wizrole.hosadmin.base.MyApplication;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 */

public class GetRoleListHttp {

    public DataBack dataBack;
    public Context context;
    public GetRoleListHttp(DataBack dataBack,Context context){
        this.dataBack=dataBack;
        this.context=context;
    }

    public void GetRoelList(){
        //得到本地json文本内容
        String fileName = "test.json";
        String foodJson = getJson(fileName);
        //转换为对象
        RoleListBack back = JsonToObject(foodJson, RoleListBack.class);
        dataBack.getDataBackSucc(back);
    }


    /**
     * 得到json文件中的内容
     * @param fileName
     * @return
     */
    public  String getJson( String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串转换为 对象
     * @param json
     * @param type
     * @return
     */
    public  static <T> T JsonToObject(String json, Class<T> type) {
        Gson gson =new Gson();
        return gson.fromJson(json, type);
    }
}
