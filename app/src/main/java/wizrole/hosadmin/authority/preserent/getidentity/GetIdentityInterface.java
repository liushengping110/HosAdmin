package wizrole.hosadmin.authority.preserent.getidentity;

import wizrole.hosadmin.authority.model.getidentity.IdentityBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public interface GetIdentityInterface {

    void getIdentitySucc(IdentityBack back);
    void getIdentityFail(String msg);
}
