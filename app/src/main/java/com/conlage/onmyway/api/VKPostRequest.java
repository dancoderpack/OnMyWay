package com.conlage.onmyway.api;

import com.vk.api.sdk.requests.VKRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class VKPostRequest extends VKRequest<VKPostResponse> {

    private int groupID;

    VKPostRequest(int groupID, String message) {
        super("wall.post");
        this.groupID = groupID;
        addParam("owner_id", -groupID);
        addParam("message", message);
    }

    @Override
    public VKPostResponse parse(@NotNull JSONObject r) throws Exception {
        if (!r.has("error")){
            int postID = r.getJSONObject("response").getInt("225826");
            return new VKPostResponse(true, groupID, postID);
        }else{
            return new VKPostResponse(false, groupID, 0);
        }
    }

}
