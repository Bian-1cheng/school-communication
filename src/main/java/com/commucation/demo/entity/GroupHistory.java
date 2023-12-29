package com.commucation.demo.entity;



import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class GroupHistory implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    //群组id
    private Integer group_id;
    private String group_name;
    //用户id
    private Integer user_id;
    private String user_identity_no;
    //发送用户id
    private Integer send_id;
    private String send_identity_no;
    private String send_username;
    private String headDir;
    private byte[] send_head;
    //消息内容
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmt_create;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmt_modified;

    @Override
    public String toString() {
        return "GroupHistory{" +
                "id=" + id +
                ", group_id=" + group_id +
                ", group_name='" + group_name + '\'' +
                ", user_id=" + user_id +
                ", user_identity_no='" + user_identity_no + '\'' +
                ", send_id=" + send_id +
                ", send_identity_no='" + send_identity_no + '\'' +
                ", send_username='" + send_username + '\'' +
                ", headDir='" + headDir + '\'' +
                ", send_head=" + Arrays.toString(send_head) +
                ", content='" + content + '\'' +
                ", gmt_create=" + gmt_create +
                ", gmt_modified=" + gmt_modified +
                ", is_read=" + is_read +
                ", is_deleted=" + is_deleted +
                ", is_group_notice=" + is_group_notice +
                ", group_notice_read_num=" + group_notice_read_num +
                ", readUserList=" + readUserList +
                '}';
    }

    //是否已读
    private Integer is_read;
    private Integer is_deleted;

    //是否是群公告
    private Integer is_group_notice;
    //此群公告已读人数
    private Integer group_notice_read_num;
    //此群公告已读人列表
    private List<User> readUserList;

    public GroupHistory() {}

    public String getHeadDir() {
        return headDir;
    }

    public void setHeadDir(String headDir) {
        this.headDir = headDir;
    }

    public Integer getGroup_notice_read_num() {
        return group_notice_read_num;
    }

    public void setGroup_notice_read_num(Integer group_notice_read_num) {
        this.group_notice_read_num = group_notice_read_num;
    }

    public List<User> getReadUserList() {
        return readUserList;
    }

    public void setReadUserList(List<User> readUserList) {
        this.readUserList = readUserList;
    }

    public String getGroup_name() {
        return group_name;
    }

    public Integer getIs_group_notice() {
        return is_group_notice;
    }

    public void setIs_group_notice(Integer is_group_notice) {
        this.is_group_notice = is_group_notice;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public byte[] getSend_head() {
        return send_head;
    }

    public void setSend_head(byte[] send_head) {
        this.send_head = send_head;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_identity_no() {
        return user_identity_no;
    }

    public void setUser_identity_no(String user_identity_no) {
        this.user_identity_no = user_identity_no;
    }

    public String getSend_identity_no() {
        return send_identity_no;
    }

    public void setSend_identity_no(String send_identity_no) {
        this.send_identity_no = send_identity_no;
    }

    public Integer getSend_id() {
        return send_id;
    }

    public void setSend_id(Integer send_id) {
        this.send_id = send_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public LocalDateTime getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(LocalDateTime gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public Integer getIs_read() {
        return is_read;
    }

    public void setIs_read(Integer is_read) {
        this.is_read = is_read;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getSend_username() {
        return send_username;
    }

    public void setSend_username(String send_username) {
        this.send_username = send_username;
    }
}
