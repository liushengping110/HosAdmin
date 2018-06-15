package wizrole.hosadmin.authority.preserent.getdeparent;

import wizrole.hosadmin.authority.model.getdeparent.DeparentBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public interface GetDeparentInterface {

    void getDeparentSucc(DeparentBack deparentBack);
    void getDeparentFail(String msg);
}
