package wizrole.hosadmin.authority.preserent.getrolelist;

import wizrole.hosadmin.authority.model.getrolelist.RoleListBack;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 */

public interface GetRoleListInterface {

    void getRoleListSucc(RoleListBack roleListBack);
    void getRoleListFail(String msg);
}
