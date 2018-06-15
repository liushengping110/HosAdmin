package wizrole.hosadmin.authority.model.getidentity;

import java.util.List;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 角色列表
 *
 * 身份列表
 */

public class IdentityBack {

    public String ResultCode;
    public String ResultContent;
    public List<IdentityList> identityLists;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setIdentityLists(List<IdentityList> identityLists) {
        this.identityLists = identityLists;
    }

    public String getResultCode() {

        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<IdentityList> getIdentityLists() {
        return identityLists;
    }
}
