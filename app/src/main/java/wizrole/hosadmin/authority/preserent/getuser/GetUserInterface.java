package wizrole.hosadmin.authority.preserent.getuser;

import wizrole.hosadmin.authority.model.getuser.GetUserBack;

/**
 * Created by liushengping on 2018/3/6.
 * 何人执笔？
 */

public interface GetUserInterface {

    void getUserSucc(GetUserBack getUserBack);
    void getUserFail(String msg);

}
