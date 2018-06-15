package wizrole.hosadmin.authority.model.getrolelist;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 */

public class ChildPermissionList implements Serializable{

    public String checkStates;//"check"
    public int  companyId;//0
    public int  functionId;//1
    public String mainIcon;//""
    public String permissionDec;//用户管理
    public int  permissionId;//3
    public String  permissionName;//userManage
    public String  permissionUrl;///getAllUser.do
    public int  roleId;//0
    public int  subordinate;//1
    public String  type;//submenu
    public List<GrandSonPermissionList> childPermissionList;

    public List<GrandSonPermissionList> getGrandSonPermissionLists() {
        return childPermissionList;
    }

    public String getCheckStates() {
        return checkStates;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getFunctionId() {
        return functionId;
    }

    public String getMainIcon() {
        return mainIcon;
    }

    public String getPermissionDec() {
        return permissionDec;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getSubordinate() {
        return subordinate;
    }

    public String getType() {
        return type;
    }

}
