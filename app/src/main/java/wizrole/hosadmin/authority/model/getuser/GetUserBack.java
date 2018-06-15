package wizrole.hosadmin.authority.model.getuser;

import java.util.List;

/**
 * Created by liushengping on 2018/3/6.
 * 何人执笔？
 * 用户管理
 */

public class GetUserBack {

    public String ResultCode;
    public String ResultContent;
    public List<UserList> userList;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setUserList(List<UserList> userList) {
        this.userList = userList;
    }

    public String getResultCode() {

        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<UserList> getUserList() {
        return userList;
    }
}
