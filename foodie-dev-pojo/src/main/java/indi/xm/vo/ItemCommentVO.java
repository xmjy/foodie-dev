package indi.xm.vo;

import java.util.Date;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.vo
 * @ClassName: CommentVO
 * @Author: albert.fang
 * @Description: 展示商品评价的VO
 * @Date: 2021/10/14 15:42
 */
public class ItemCommentVO {

    private String commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
