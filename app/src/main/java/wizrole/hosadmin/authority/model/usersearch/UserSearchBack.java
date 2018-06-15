package wizrole.hosadmin.authority.model.usersearch;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 用户管理搜索
 */

public class UserSearchBack {

    public String SearchContent;

    public UserSearchBack(){
        super();
    }
    public UserSearchBack(String SearchContent){
        this.SearchContent=SearchContent;
    }
    public void setSearchContent(String searchContent) {
        SearchContent = searchContent;
    }

    public String getSearchContent() {
        return SearchContent;
    }
}
