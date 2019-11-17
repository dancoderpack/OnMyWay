package com.conlage.onmyway.api;

import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class VKDeletePostRequest extends VKRequest<VKPostDeleteResponse> {

    private int groupID, postID;

    public VKDeletePostRequest(int groupID, int postID) {
        super("wall.delete");
        this.groupID = groupID;
        this.postID = postID;
        addParam("post_id", postID);
        addParam("owner_id", -groupID);
    }

    @Override
    public VKPostDeleteResponse parse(@NotNull JSONObject r) throws Exception {
        if (!r.has("error")) {
            boolean isSuccess = r.getInt("response") == 1;
            return new VKPostDeleteResponse(isSuccess, groupID, postID);
        }else{
            return new VKPostDeleteResponse(false, groupID, postID);
        }
    }
}
