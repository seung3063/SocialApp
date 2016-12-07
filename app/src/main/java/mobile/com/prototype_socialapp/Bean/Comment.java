package mobile.com.prototype_socialapp.Bean;

/**
 * Created by SeungJun on 2016. 12. 4..
 */
public class Comment {
    private String user_name;
    private String comment_content;



    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
