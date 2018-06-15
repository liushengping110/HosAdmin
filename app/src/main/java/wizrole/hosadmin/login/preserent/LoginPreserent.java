package wizrole.hosadmin.login.preserent;

import wizrole.hosadmin.base.DataBack;
import wizrole.hosadmin.login.model.LoginBack;
import wizrole.hosadmin.login.model.LoginHttp;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public class LoginPreserent implements DataBack{

    public LoginInterface loginInterface;
    public LoginHttp loginHttp;
    public LoginPreserent(LoginInterface loginInterface){
        this.loginInterface=loginInterface;
        loginHttp=new LoginHttp(this);
    }

    public void Login(String name,String pass){
        loginHttp.LoginHttp(name,pass);
    }
    @Override
    public void getDataBackSucc(Object o) {
        loginInterface.LoginSucc((LoginBack) o);
    }

    @Override
    public void getDataFail(String msg) {
        loginInterface.LoginFail(msg);
    }
}
