package wizrole.hosadmin.authority.model.getuser;

import java.io.Serializable;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 用户管理
 */

public class UserList implements Serializable{
    public String UserName;
    public String UserIdentity;
    public String UserLashLoginTime;
    public String UserCompanyName;
    public String UserDeparentName;


    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserIdentity(String userIdentity) {
        UserIdentity = userIdentity;
    }

    public void setUserLashLoginTime(String userLashLoginTime) {
        UserLashLoginTime = userLashLoginTime;
    }

    public void setUserCompanyName(String userCompanyName) {
        UserCompanyName = userCompanyName;
    }

    public void setUserDeparentName(String userDeparentName) {
        UserDeparentName = userDeparentName;
    }

    public String getUserName() {

        return UserName;
    }

    public String getUserIdentity() {
        return UserIdentity;
    }

    public String getUserLashLoginTime() {
        return UserLashLoginTime;
    }

    public String getUserCompanyName() {
        return UserCompanyName;
    }

    public String getUserDeparentName() {
        return UserDeparentName;
    }
}
