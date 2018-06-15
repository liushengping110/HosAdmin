package wizrole.hosadmin.authority.model.getidentity;

import java.io.Serializable;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class IdentityList implements Serializable{

    public String IdentityNo;//角色代码
    public String IdentityName;//角色名称

    public String getIdentityName() {
        return IdentityName;
    }

    public String getIdentityNo() {
        return IdentityNo;
    }

    public void setIdentityName(String identityName) {
        IdentityName = identityName;
    }

    public void setIdentityNo(String identityNo) {
        IdentityNo = identityNo;
    }
}
