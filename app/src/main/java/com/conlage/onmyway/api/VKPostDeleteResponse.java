package com.conlage.onmyway.api;

public class VKPostDeleteResponse {

    private boolean isSuccess;
    private int groupID;
    private int postID;

    public VKPostDeleteResponse(boolean isSuccess, int groupID, int postID) {
        this.isSuccess = isSuccess;
        this.groupID = groupID;
        this.postID = postID;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
