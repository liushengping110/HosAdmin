package wizrole.hosadmin.login.preserent;

import wizrole.hosadmin.login.model.LoginBack;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public interface LoginInterface {

    void LoginSucc(LoginBack loginBack);
    void LoginFail(String msg);
}
