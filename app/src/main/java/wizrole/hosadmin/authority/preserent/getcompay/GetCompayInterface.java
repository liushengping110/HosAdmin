package wizrole.hosadmin.authority.preserent.getcompay;

import wizrole.hosadmin.authority.model.getcompay.CompayBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public interface GetCompayInterface {

    void getCompaySucc(CompayBack back);
    void getCompayFail(String msg);
}
