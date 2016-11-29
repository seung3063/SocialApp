package mobile.com.prototype_socialapp.Bean;

/**
 * Created by SeungJun on 2016. 11. 28..
 */
public class BoardTable {
    private int idx;
    private String user_id;
    private String main_category;
    private String sub_category;
    private String title;
    private String content;
    private String time;
    private String num_of_comment;

    public String getNum_of_comment() {
        return num_of_comment;
    }

    public void setNum_of_comment(String num_of_comment) {
        this.num_of_comment = num_of_comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
